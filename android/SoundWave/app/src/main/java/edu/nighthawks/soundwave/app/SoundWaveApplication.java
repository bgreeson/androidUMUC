package edu.nighthawks.soundwave.app;

import android.app.Application;

public class SoundWaveApplication extends Application
{
	private static SoundWaveApplication sApp;
	
	public static SoundWaveApplication getApplicationObject()
	{
		return sApp;		
	}

	
	public SoundWaveController soundWaveController;
	public SoundWaveConfig soundWaveConfig;

	@Override
	public void onCreate()
	{
		super.onCreate();
		createApplication();				
	}
	
	private void createApplication()
	{
		sApp = this;
		soundWaveController = new SoundWaveController();
		soundWaveConfig = new SoundWaveConfig();
		soundWaveConfig.initFromSettings();
	}

}
