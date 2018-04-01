package com.blocksfm.audio;

import java.util.concurrent.atomic.AtomicBoolean;

import javazoom.jl.decoder.MP3Decoder;

public class AudioPlaybackThread extends Thread
{
	private static AudioPlaybackThread instance;
	
	//Running
	private AtomicBoolean running;
	
	//MP3 Audiodecoder
	private MP3Decoder mp3Decoder;
	
	private AudioPlaybackThread()
	{
		this.mp3Decoder = new MP3Decoder();
		
		this.running = new AtomicBoolean(true);
	}
	
	public void run()
	{
		
	}
	
	/*
	 * Playback-Methods
	 */
	private void playMp3()
	{
		
	}
	
	/*
	 * Thread-Methods
	 */
	public void shutdown()
	{
		this.running.set(false);
	}
	
	public synchronized static AudioPlaybackThread getInstance()
	{
		if(AudioPlaybackThread.instance == null)
			AudioPlaybackThread.instance = new AudioPlaybackThread();
		return AudioPlaybackThread.instance;
	}
}