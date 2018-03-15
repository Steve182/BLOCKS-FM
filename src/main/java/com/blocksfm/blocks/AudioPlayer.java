package com.blocksfm.blocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class AudioPlayer implements Runnable
{
	private AdvancedPlayer player;
	private File audioFile;

	public AudioPlayer(File file)
	{
		this.audioFile = file;
	}

	@Override
	public void run()
	{
		try
		{
			FileInputStream fis = new FileInputStream(audioFile);

			player = new AdvancedPlayer(fis);

			player.play();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void play(File audioFile)
	{
		Thread playAudio = new Thread(new AudioPlayer(audioFile));
		playAudio.start();
	}

}
