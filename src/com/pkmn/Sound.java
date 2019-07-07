package com.pkmn;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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
	
	public void setVolume(double d) 
	{
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    float dB = (float) (Math.log(d) / Math.log(10.0) * 20.0);
	    gainControl.setValue(dB);
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
