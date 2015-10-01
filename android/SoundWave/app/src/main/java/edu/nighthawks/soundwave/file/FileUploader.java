package edu.nighthawks.soundwave.file;

import org.apache.http.client.methods.HttpPost;

/**
 * This is a class for driving the file upload process
 * It uses the PostFile static utility method for HTTP file upload.
 *
 * This file runs a thread for file upload as not to hold up the UI.
 */
public class FileUploader extends Thread
{
	private HttpPost httpPost;
	private String m_fileName;
	
	
	public void upload(String fileName)
	{
		m_fileName = fileName;
		this.start();		
	}
	
	@Override
	public void run()
	{
		try
		{
			PostFile.uploadFile(m_fileName);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
