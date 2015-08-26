package edu.umuc.soundwave.file;

import org.apache.http.client.methods.HttpPost;

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
