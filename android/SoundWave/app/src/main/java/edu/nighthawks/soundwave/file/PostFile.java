package edu.nighthawks.soundwave.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class PostFile
{
	public static int uploadFile(String sourceFileUri)
	{
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);
		int serverResponseCode = 0;
		String serverResponseMessage = "";

		try
		{
			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			URL url = new URL("http://androidsoundappproject.appspot.com/upload");

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // allow Inputs
			conn.setDoOutput(true); // allow Outputs
			conn.setUseCaches(false); // don't use cached copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("soundFile", sourceFile.getAbsolutePath());

			dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=soundFile; filename=" + sourceFile.getAbsolutePath() + "" + lineEnd);
			dos.writeBytes(lineEnd);

			// create a buffer of  maximum size
			bytesAvailable = fileInputStream.available();

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			while (bytesRead > 0)
			{
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			// send multi-part form data necessary after file data
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			serverResponseCode = conn.getResponseCode();
			serverResponseMessage = conn.getResponseMessage();

			if (serverResponseCode == 200)
			{

			}

			// close the streams
			fileInputStream.close();
			dos.flush();
			dos.close();


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		return serverResponseCode;
	}
}