package edu.nighthawks.soundwave.app;

import android.app.Application;
import android.widget.Toast;

import java.io.IOException;

/**
 * This class holds all the main SoundWave applicaiton objects.
 * It also managaes the lifetime of these objects and keeps them around for the life of the app
 */
public class SoundWaveApplication extends Application
{
	private static SoundWaveApplication sApp;
	
	public static SoundWaveApplication getApplicationObject()
	{
		return sApp;		
	}

	
	public SoundWaveController soundWaveController;
	public SoundWaveConfig soundWaveConfig;
	public String soundWaveSerializedContactsList;

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
		soundWaveConfig.initFromSharedPrefs();

		try
		{
			soundWaveController.retrieveContactsStart(soundWaveConfig.getUserId());
		}
		catch (IOException ex)
		{
			Toast.makeText(this, "Failed to initilize contact list from server: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

}
