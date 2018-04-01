package com.blocksfm.test;

import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.MP3Decoder;
import javazoom.jl.decoder.OutputBuffer;

public class TestMp3
{
	private static MP3Decoder decoder;
	private static Bitstream bitstream;
	private static OutputBuffer buffer, buf;
	private static SourceDataLine sourceLine;
	
	public static void main(String[] args) throws Exception
	{
		System.out.print("Please enter the absolute path to a MP3-File:");
		File file = new File(TestUtils.readConsoleLine());
		while(file == null || !file.exists() || file.isDirectory())
		{
			System.out.println("Invalid file!");
			System.out.print("Please enter the absolute path to a MP3-File:");
			file = new File(TestUtils.readConsoleLine());
		}

		decoder = new MP3Decoder();
		bitstream = new Bitstream(new FileInputStream(file));
		
		//Init output buffer
		Header h = bitstream.readFrame();
		buffer = new OutputBuffer(h.mode() == Header.SINGLE_CHANNEL ? 1 : 2, false);
        decoder.setOutputBuffer(buffer);
        buf = decoder.decodeFrame(h, bitstream);
        bitstream.closeFrame();
        
		AudioFormat audioFormat = new AudioFormat(decoder.getOutputFrequency(), 16, decoder.getOutputChannels(), true, false);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		sourceLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceLine.open(audioFormat);
        sourceLine.start();

        byte[] data;
        do
        {
        	data = buf.getBuffer();
        	sourceLine.write(data, 0, data.length);
        	
        	//Update buffer
        	buffer.reset();
            buf = decoder.decodeFrame(h, bitstream);
            bitstream.closeFrame();
        	
        } while((h = bitstream.readFrame()) != null);

        sourceLine.drain();
        sourceLine.close();
	}
}
