package com.pkmn;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//To play sound.

public class Sound 
{
	Clip clip;
	public Sound(String filename)
	{
		try
		{
			this.clip = AudioSystem.getClip();
			this.clip.open(AudioSystem.getAudioInputStream(new File(filename)));
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	public void play()
	{
		this.clip.start();
	}
	
	public void playLoop()
	{
		this.clip.start();
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop()
	{
		this.clip.stop();
	}
}
