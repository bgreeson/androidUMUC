package edu.nighthawks.soundwave.file;


/**
 * This is a class for driving the file upload process
 * It uses the FileUploaderHttp static utility method for HTTP file upload.
 *
 * This file runs a thread for file upload as not to hold up the UI.
 */
public class FileUploaderThread extends Thread
{
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
			FileUploaderHttp.createMsg("22", "22", m_fileName, m_fileName);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
