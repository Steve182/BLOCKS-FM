package com.blocksfm.test;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class TestOpus
{
	public static void main(String[] args) throws Exception
	{	
		System.out.print("Please enter the absolute path to a Opus-File:");
		File file = new File(TestUtils.readConsoleLine());
		while(file == null || !file.exists() || file.isDirectory())
		{
			System.out.println("Invalid file!");
			System.out.print("Please enter the absolute path to a Opus-File:");
			file = new File(TestUtils.readConsoleLine());
		}
		
		JOrbisStream stream = new JOrbisStream(file);
		AudioFormat audioFormat = stream.getAudioFormat();
		System.out.println(audioFormat);
		Thread.sleep(250);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceLine.open(audioFormat);
        sourceLine.start();

        int read = 0;
        byte[] data = new byte[4096];
        while((read = stream.read(data)) > 0)
        {
        	sourceLine.write(data, 0, read);
        }

        stream.close();
        sourceLine.drain();
        sourceLine.close();
	}
}