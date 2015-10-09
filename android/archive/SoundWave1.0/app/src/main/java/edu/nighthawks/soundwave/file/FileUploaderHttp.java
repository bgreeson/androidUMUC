package edu.nighthawks.soundwave.file;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This is a simple static utility class for HTTP POST actions such as uploadFile.
 */
public class FileUploaderHttp
{
	private static final String LINE_FEED = "\r\n";


	public static String createMsg(String senderUserID, String targetUserID, String fileName, String filePath) throws Exception
	{
		String srvrResponse = "";


		try
		{
			String crlf = "\r\n";
			String twoHyphens = "--";
			String boundary =  "*****";

			HttpURLConnection httpUrlConnection = null;
			URL url = new URL("http://androidsoundappproject.appspot.com/server");
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setDoOutput(true);

			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
			httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
			httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream());

			request.writeBytes(twoHyphens + boundary + crlf);
			request.writeBytes("Content-Disposition: form-data; name=\"" + "userfile" + "\";filename=\"" + fileName + "\"" + crlf);
			request.writeBytes(crlf);


			// write file data
			FileInputStream inputStream = new FileInputStream(filePath);
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1)
			{
				request.write(buffer, 0, bytesRead);
			}


			inputStream.close();

			request.writeBytes(crlf);
			request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

			// try to add multiple parts here

			request.writeBytes("--" + boundary + LINE_FEED);
			request.writeBytes("Content-Disposition: form-data; name=\"" + "user_id_sender" + "\"" + LINE_FEED);
			request.writeBytes("Content-Type: text/plain; charset=" + "UTF-8" + LINE_FEED);
			request.writeBytes(LINE_FEED);
			request.writeBytes(senderUserID + LINE_FEED);

			request.writeBytes("--" + boundary + LINE_FEED);
			request.writeBytes("Content-Disposition: form-data; name=\"" + "user_id_target" + "\"" + LINE_FEED);
			request.writeBytes("Content-Type: text/plain; charset=" + "UTF-8" + LINE_FEED);
			request.writeBytes(LINE_FEED);
			request.writeBytes(targetUserID + LINE_FEED);

			request.writeBytes("--" + boundary + LINE_FEED);
			request.writeBytes("Content-Disposition: form-data; name=\"" + "action" + "\"" + LINE_FEED);
			request.writeBytes("Content-Type: text/plain; charset=" + "UTF-8" + LINE_FEED);
			request.writeBytes(LINE_FEED);
			request.writeBytes("message_create" + LINE_FEED);

			request.flush();

			// read response

			InputStream responseStream = new BufferedInputStream(httpUrlConnection.getInputStream());

			BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
			String line = "";
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = responseStreamReader.readLine()) != null)
			{
				stringBuilder.append(line).append("\n");
			}
			responseStreamReader.close();

			srvrResponse = stringBuilder.toString();


		}
		catch (Exception ex)
		{
			srvrResponse = ex.getMessage();
		}


		return srvrResponse; //+ "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
	}

	public static int uploadFile1(String sourceFileUri)
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
			URL url = new URL("http://androidsoundappproject.appspot.com/server2");
			//URL url = new URL("http://androidsoundappproject.appspot.com/server");

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // allow Inputs
			conn.setDoOutput(true); // allow Outputs
			conn.setUseCaches(false); // don't use cached copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			//conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("userfile", sourceFile.getAbsolutePath());

			PrintStream ps = new PrintStream(conn.getOutputStream());
			ps.print("user_id_sender=22");
			ps.print("&user_id_target=22");
			ps.print("&file_name=" + sourceFile.getAbsolutePath());
			ps.print("&action=message_create");
			ps.flush();
			ps.close();


			dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; user_id_sender=22; user_id_target=22; name=userfile; filename=" + sourceFile.getAbsolutePath() + "" + lineEnd);
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
			dos.flush();
			dos.close();



			// Responses from the server (code and message)
			serverResponseCode = conn.getResponseCode();
			serverResponseMessage = conn.getResponseMessage();

			InputStream is = conn.getInputStream();
			String encoding = conn.getContentEncoding();
			String responseString = IOUtils.toString(is, encoding);

			if (serverResponseCode == 200)
			{

			}

			// close the streams
			fileInputStream.close();
//			dos.flush();
//			dos.close();
//			ps.close();


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		return serverResponseCode;
	}

	public static int uploadFileServer2(String sourceFileUri)
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
			URL url = new URL("http://androidsoundappproject.appspot.com/server2");
			//URL url = new URL("http://androidsoundappproject.appspot.com/server");

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // allow Inputs
			conn.setDoOutput(true); // allow Outputs
			conn.setUseCaches(false); // don't use cached copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("userfile", sourceFile.getAbsolutePath());
			PrintStream ps = new PrintStream(conn.getOutputStream());
			//ps.print("user_id_sender=22");
			//ps.print("&user_id_target=22");
			//ps.print("&file_name=" + sourceFile.getAbsolutePath());
			//ps.print("&action=message_create_dev");

			dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; user_id_sender=22; user_id_target=22; name=userfile; filename=" + sourceFile.getAbsolutePath() + "" + lineEnd);
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

			InputStream is = conn.getInputStream();
			String encoding = conn.getContentEncoding();
			String responseString = IOUtils.toString(is, encoding);

			if (serverResponseCode == 200)
			{

			}

			// close the streams
			fileInputStream.close();
			dos.flush();
			dos.close();
			ps.close();


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		return serverResponseCode;
	}
}