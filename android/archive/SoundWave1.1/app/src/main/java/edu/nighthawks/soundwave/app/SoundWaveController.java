package edu.nighthawks.soundwave.app;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.nighthawks.soundwave.file.FileRecorder;
import edu.nighthawks.soundwave.file.FileUploaderHttp;
import edu.nighthawks.soundwave.http.HttpServerCreateContact;
import edu.nighthawks.soundwave.http.HttpServerGetAccountInfoByEmail;
import edu.nighthawks.soundwave.http.HttpServerGetContactsList;
import edu.nighthawks.soundwave.http.HttpServerRegisterAccount;
import edu.nighthawks.soundwave.json.UserData;

/**
 * The purpose of this class is to act as the controller for the main SoundWave features
 * such as recording sound files, playing back sound files, and tranmistting them to the server.
 */
public class SoundWaveController
{
	private boolean m_bTransmit = false;
	private FileRecorder recorder;
	HttpServerGetContactsList  mContactListRetriever;


	/***
	 * Set transmit state which drives file recording (start and stop)
	 * @param bTransmit
	 */
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

	/***
	 * Get transmit status
	 * @return
	 */
	public boolean getTransmit()
	{
		return m_bTransmit;		
	}

	/**
	 * Upload file to server
	 */
	public void send()
	{
		// Send the recorded file
		// TODO This should be on a worker thread. We get away with it for short files
		FileUploaderHttp fileUploader = new FileUploaderHttp("22",recorder.getRecordedFileName());
		fileUploader.start();
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

	/**
	 * Create a new account
	 * @param dispName
	 * @param password
	 * @param emailAddress
	 */
	public void createAccount(String dispName, String password, String emailAddress) throws IOException
	{
		//accountCreator = new AccountCreatorThread();
		//accountCreator.createAccount(dispName, password, emailAddress);
		HttpServerRegisterAccount accountCreator = new HttpServerRegisterAccount(dispName, emailAddress, password);
		accountCreator.start();

		// This needs to be switched to async call with Android Handler for callback
		while (accountCreator.isDone() == false)
		{}

		UserData data = new UserData(accountCreator.getmRawResponseBodyJson());
		SoundWaveApplication.getApplicationObject().soundWaveConfig.setUserId(data.getUser_id());
		SoundWaveApplication.getApplicationObject().soundWaveConfig.setmUserEmail(data.getEmail_addr());
		SoundWaveApplication.getApplicationObject().soundWaveConfig.setUserName(data.getName());
	}

	/**
	 * Create a new contact
	 */
	public void createContact(String name, String emailAddress) throws IOException
	{
		HttpServerGetAccountInfoByEmail getAccountInfoByEmail = new HttpServerGetAccountInfoByEmail(emailAddress);
		getAccountInfoByEmail.start();

		// This needs to be switched to async call with Android Handler for callback
		while (getAccountInfoByEmail.isDone() == false)
		{}


		UserData data = new UserData(getAccountInfoByEmail.getmRawResponseBodyJson());
		int userIdMember = data.getUser_id(); // TODO get this from parsered repsonse above

		// get member ID from call above

		HttpServerCreateContact contactCreator = new HttpServerCreateContact(Integer.toString(userIdMember));
		contactCreator.start();

		// This needs to be switched to async call with Android Handler for callback
		while (contactCreator.isDone() == false)
		{}

		// add contact to shared prefs list
		//SharePrefsUtil.setString(SoundWaveApplication.getApplicationObject().getBaseContext(), SharePrefsUtil.CONTACTS, userIdOwner);

	}


	/**
	 * Create a new contact
	 */
	public void retrieveContactsStart(int userIdOwner)
	{
		mContactListRetriever = new HttpServerGetContactsList(userIdOwner);
		mContactListRetriever.start();
	}

	public String retrieveContactsAfterStart()
	{
		if (mContactListRetriever != null)
		{
			if (mContactListRetriever.isDone())
			{
				return mContactListRetriever.getmRawResponseBodyJson();
			}
			else
			{
				return "";
			}
		}
		else
		{
			return "";
		}

	}

}
