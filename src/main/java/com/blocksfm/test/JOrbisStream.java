package com.blocksfm.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.sound.sampled.AudioFormat;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;

public class JOrbisStream extends InputStream
{
	/*
	 * InputStream to grab data from
	 */
	private InputStream in;
	
	/*
	 * Buffer to buffer data
	 * Size of the buffer
	 * A counter for the read data
	 * Current index in stream
	 */
	private byte[] buffer;
	private int BUFFER_SIZE = 4096;
	private int counter = 0;
	private int index = 0;
	
	/*
	 * Modified Buffer for JOgg and JOrbis
	 */
	private byte[] modifiedBuffer;
	private int M_BUFFER_SIZE;
	
	/*
	 * PCM-Information and index
	 */
	private float[][][] pcmInfo;
	private int[] pcmIndex;
	
	/*
	 * AudioFormat
	 */
	private AudioFormat audioFormat;
	
	/*
	 * JOgg-Stuff
	 */
	private Packet joggPacket;
	private Page joggPage;
	private StreamState joggStreamState;
	private SyncState joggSyncState;
	
	/*
	 * JOrbis-Stuff
	 */
	private DspState jorbisDspState;
	private Block jorbisBlock;
    private Comment jorbisComment;
    private Info jorbisInfo;
    
    /*
     * Datastream for decoded packets
     */
    private int audioDataBuffer;
    private PipedInputStream audioData;
    private PipedOutputStream decodedAudioData;
	
    /*
     * Constructors
     */
	public JOrbisStream(InputStream in) throws IOException
	{
		this.in = in;
		
		//Initialize JOgg
		this.joggPacket = new Packet();
		this.joggPage = new Page();
		this.joggStreamState = new StreamState();
		this.joggSyncState = new SyncState();
		
		//Initialize JOrbis
		this.jorbisDspState = new DspState();
		this.jorbisBlock = new Block(jorbisDspState);
		this.jorbisComment = new Comment();
		this.jorbisInfo = new Info();
		
		this.joggSyncState.init();
		this.joggSyncState.buffer(this.BUFFER_SIZE);
		this.buffer = joggSyncState.data;
		
		//Read header
		this.readHeader();
		
		//Init jorbis and setup sound variables (AudioFormat, PCM-Information, etc)
		this.setupAudio();
	}
	
	public JOrbisStream(File f) throws IOException
	{
		this(new FileInputStream(f));
	}
	
	/*
	 * Reads the header from the stream
	 * The header consists of 3 packets
	 */
	private void readHeader() throws IOException
	{
		int pageoutResult = 0;
		
		//First packet
		while(pageoutResult == 0)
		{
			this.counter = this.in.read(this.buffer, this.index, this.BUFFER_SIZE);
			this.joggSyncState.wrote(this.counter);
			pageoutResult = this.joggSyncState.pageout(this.joggPage);
			
			if(pageoutResult == 0)
			{
				System.out.println("Need to read more data because the buffer size was too small. You should probably increase the buffer size!");
				this.updateIndexAndBuffer();
			}
		}
		
		switch(pageoutResult)
		{
			//Invalid packet
			case -1:
				throw new IOException("Invalid header!");
			case 1:
			{
				//Initialize StreamState
				this.joggStreamState.init(this.joggPage.serialno());
				this.joggStreamState.reset();
				
				this.jorbisInfo.init();
				this.jorbisComment.init();
				
				//Check first packet 
				if(this.joggStreamState.pagein(this.joggPage) == -1 || this.joggStreamState.packetout(this.joggPacket) != 1 || this.jorbisInfo.synthesis_headerin(this.jorbisComment, this.joggPacket) < 0)
					throw new IOException("The first header packet contains an error!");
				
				break;
			}
		}
		
		//Second and third packet
		for(int i = 0; i < 2; i++)
		{
			while((pageoutResult = this.joggSyncState.pageout(this.joggPage)) == 0)
			{
				this.updateIndexAndBuffer();
				
				this.counter = this.in.read(this.buffer, this.index, this.BUFFER_SIZE);
				this.joggSyncState.wrote(this.counter);
			}
			
			//If something went wrong
			if(pageoutResult == -1)
				throw new IOException("Invalid header!");
			
			this.joggStreamState.pagein(this.joggPage);
			
			switch(this.joggStreamState.packetout(this.joggPacket))
			{
				//Invalid packet
				case -1:
					throw new IOException("Invalid header!");
				//Not enough data
				case 0:
					i--;
					break;
				case 1:
				{
					this.jorbisInfo.synthesis_headerin(this.jorbisComment, this.joggPacket);
				}
			}
			this.updateIndexAndBuffer();
		}
	}
	
	private void updateIndexAndBuffer()
	{
		this.index = this.joggSyncState.buffer(this.BUFFER_SIZE);
		this.buffer = this.joggSyncState.data;
	}
	
	/*
	 * Init jorbis 
	 * Setup sound variables (AudioFormat, PCM-Information, etc)
	 */
	private void setupAudio() throws IOException
	{
		this.M_BUFFER_SIZE = this.BUFFER_SIZE * 2;
		this.modifiedBuffer = new byte[this.M_BUFFER_SIZE];
		
		this.jorbisDspState.synthesis_init(this.jorbisInfo);
		
		this.jorbisBlock.init(this.jorbisDspState);
		
		//Create AudioFormat
		this.audioFormat = new AudioFormat(this.jorbisInfo.rate, 16, this.jorbisInfo.channels, true, false);
		
		//Setup PCM-Information
		this.pcmInfo = new float[1][][];
		this.pcmIndex = new int[this.jorbisInfo.channels];
		
		//Setup data streams
		this.decodedAudioData = new PipedOutputStream();
		this.audioDataBuffer = (2 * this.jorbisInfo.channels * this.M_BUFFER_SIZE) * 16;
		this.audioData = new PipedInputStream(this.decodedAudioData, this.audioDataBuffer);
	}
	
	/*
	 * Decode audio
	 */
	private boolean decodePacket() throws IOException
	{
		if(joggPage.granulepos() == 0 || joggPage.eos() != 0)
			return false;
		
		int pageoutResult = 0, packetoutResult = 0;
		
		//Read packet
		while((pageoutResult = this.joggSyncState.pageout(this.joggPage)) == 0)
		{
			this.updateIndexAndBuffer();
			
			this.counter = this.in.read(this.buffer, this.index, this.BUFFER_SIZE);
			this.joggSyncState.wrote(this.counter);
			
			if(this.counter == 0)
				return false;
		}
		
		//If packet is damaged
		if(pageoutResult != -1)
		{
			this.joggStreamState.pagein(this.joggPage);
			while((packetoutResult = joggStreamState.packetout(joggPacket)) != 0)
			{
				//If packet is damaged
				if(packetoutResult == -1)
					continue;
				
				//Decode packet
				if(this.jorbisBlock.synthesis(this.joggPacket) == 0)
					this.jorbisDspState.synthesis_blockin(this.jorbisBlock);
				
				int range, samples, sampleIndex, value;
				while((samples = this.jorbisDspState.synthesis_pcmout(this.pcmInfo, this.pcmIndex)) > 0)
				{
					if(samples < this.M_BUFFER_SIZE)
						range = samples;
					else
						range = this.M_BUFFER_SIZE;
					
					for(int i = 0; i < this.jorbisInfo.channels; i++)
					{
						sampleIndex = i * 2;
						for(int j = 0; j < range; j++)
						{
							value = (int) (this.pcmInfo[0][i][this.pcmIndex[i] + j] * 32767);
							if(value > 32767)
								value = 32767;
							else if(value < -32768)
								value = -32768;
							
							if(value < 0)
								value |= 32768;
							
							this.modifiedBuffer[sampleIndex] = (byte) value;
							this.modifiedBuffer[sampleIndex + 1] = (byte) (value >>> 8);
							
							sampleIndex += 2* this.jorbisInfo.channels;
						}
					}
					
					if(this.audioData.available() + (2 * this.jorbisInfo.channels * range) > this.audioDataBuffer)
						System.out.println("BufferOverflow! Skipping " + (2 * this.jorbisInfo.channels * range) + "B");
					else
					{
						this.decodedAudioData.write(this.modifiedBuffer, 0, 2 * this.jorbisInfo.channels * range);
						this.decodedAudioData.flush();
					}
					this.jorbisDspState.synthesis_read(range);
				}
			}
		}
		return true;
	}
	
	/*
	 * InputStream overrides
	 */
	@Override
	public int read() throws IOException
	{
		if(this.audioData.available() < 1 && !this.decodePacket())
			return -1;
		return this.audioData.read();
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		while(this.audioData.available() < len)
			if(!this.decodePacket())
				return this.audioData.read(b, off, this.audioData.available());
		return this.audioData.read(b, off, len);
	}
	
	@Override
	public int read(byte[] b) throws IOException
	{
		return this.read(b, 0, b.length);
	}
	
	@Override
	public void close() throws IOException
	{
		this.joggStreamState.clear();
		this.jorbisBlock.clear();
		this.jorbisDspState.clear();
		this.jorbisInfo.clear();
		this.joggSyncState.clear();
		
		this.in.close();
	}

	/*
	 * Return AudioFormat to get a DataLine.Info
	 */
	public AudioFormat getAudioFormat()
	{
		return this.audioFormat;
	}
}
