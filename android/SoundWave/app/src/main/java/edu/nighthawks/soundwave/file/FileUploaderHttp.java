package edu.nighthawks.soundwave.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.nighthawks.soundwave.app.SoundWaveApplication;
import edu.nighthawks.soundwave.http.HttpConstants;
import edu.nighthawks.soundwave.interfaces.IGetUserInfo;

/**
 * This class does not derive from the base server action class because it handles the
 * file upload task with both a body of data and server variables.
 *
 *
 */
public class FileUploaderHttp extends Thread implements IGetUserInfo
{
	private boolean mDone;

	private String mSenderUserID;
	private String mTargetUserID;
	private String mFileName;
	private String mFilePath;

	public FileUploaderHttp(String targetContactId, String filePathIn)
	{
		mDone = false;

		mSenderUserID = Integer.toString(getUserId());
		mTargetUserID = targetContactId;
		mFileName = filePathIn;
		mFilePath = filePathIn;
	}

	@Override
	public void run()
	{
		try
		{
			this.execute();
			mDone = true;
		}
		catch (Exception e)
		{
			mDone = true;
		}

	}


	private String execute() throws Exception
	{
		String srvrResponse = "";


		try
		{
			HttpURLConnection httpUrlConnection = null;
			URL url = new URL("http://androidsoundappproject.appspot.com/server");
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setDoOutput(true);

			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
			httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
			httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + HttpConstants.BOUNDARY);

			DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream());

			request.writeBytes(HttpConstants.TWO_HYPHENS + HttpConstants.BOUNDARY + HttpConstants.LINE_END);
			request.writeBytes("Content-Disposition: form-data; name=\"" + "userfile" + "\";filename=\"" + mFileName + "\"" + HttpConstants.LINE_END);
			request.writeBytes(HttpConstants.LINE_END);


			// write file data
			FileInputStream inputStream = new FileInputStream(mFilePath);
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1)
			{
				request.write(buffer, 0, bytesRead);
			}


			inputStream.close();

			request.writeBytes(HttpConstants.LINE_END);
			request.writeBytes(HttpConstants.TWO_HYPHENS + HttpConstants.BOUNDARY + HttpConstants.TWO_HYPHENS + HttpConstants.LINE_END);

			// try to add multiple parts here

			request.writeBytes("--" + HttpConstants.BOUNDARY + HttpConstants.LINE_END);
			request.writeBytes("Content-Disposition: form-data; name=\"" + "user_id_sender" + "\"" + HttpConstants.LINE_FEED);
			request.writeBytes("Content-Type: text/plain; charset=" + "UTF-8" + HttpConstants.LINE_FEED);
			request.writeBytes(HttpConstants.LINE_FEED);
			request.writeBytes(mSenderUserID + HttpConstants.LINE_FEED);

			request.writeBytes("--" + HttpConstants.BOUNDARY + HttpConstants.LINE_FEED);
			request.writeBytes("Content-Disposition: form-data; name=\"" + "user_id_target" + "\"" + HttpConstants.LINE_FEED);
			request.writeBytes("Content-Type: text/plain; charset=" + "UTF-8" + HttpConstants.LINE_FEED);
			request.writeBytes(HttpConstants.LINE_FEED);
			request.writeBytes(mTargetUserID + HttpConstants.LINE_FEED);

			request.writeBytes("--" + HttpConstants.BOUNDARY + HttpConstants.LINE_FEED);
			request.writeBytes("Content-Disposition: form-data; name=\"" + "action" + "\"" + HttpConstants.LINE_FEED);
			request.writeBytes("Content-Type: text/plain; charset=" + "UTF-8" + HttpConstants.LINE_FEED);
			request.writeBytes(HttpConstants.LINE_FEED);
			request.writeBytes("message_create" + HttpConstants.LINE_FEED);

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

			mDone = true;
		}
		catch (Exception ex)
		{
			srvrResponse = ex.getMessage();
		}


		return srvrResponse; //+ "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
	}

	@Override
	public String getUserEmail()
	{
		return SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserEmail();
	}

	@Override
	public String getUserDiplayName()
	{
		return SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserName();
	}

	@Override
	public int getUserId()
	{
		return SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserId();
	}
}