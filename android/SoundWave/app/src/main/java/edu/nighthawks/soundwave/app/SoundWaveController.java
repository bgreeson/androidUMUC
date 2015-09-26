package edu.nighthawks.soundwave.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.nighthawks.soundwave.file.FileRecorder;
import edu.nighthawks.soundwave.file.FileUploader;

public class SoundWaveController
{
	private boolean m_bTransmit = false;
	private FileRecorder recorder;
	private FileUploader uploader;
	
	public void setTransmit(boolean bTransmit)
	{
		m_bTransmit = bTransmit;
		
		if (m_bTransmit == true)
		{
			recorder = new FileRecorder();
			recorder.startRecording(generateNextFileName());
		}
		else
		{
			if (recorder != null && recorder.isRecording())
				recorder.stopRecording();
		}
	}
	
	public boolean getStatus()
	{
		return m_bTransmit;
	}
	
	public String getStatusString()
	{
		if (m_bTransmit)
			return "transmitting";
		else
			return "not transmitting";
	}
	
	public boolean getTransmit()
	{
		return m_bTransmit;		
	}
	
	public void send()
	{
		// send the recorded file
		uploader = new FileUploader();
		uploader.upload(recorder.getRecordedFileName());
	}

	private String generateNextFileName()
	{
		String fileName;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		fileName = String.format("%s-%s",
				SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserName(),
				dateFormat.format(date));

		return fileName;
	}

}
