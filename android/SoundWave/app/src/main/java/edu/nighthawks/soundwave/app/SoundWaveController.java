package edu.nighthawks.soundwave.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.nighthawks.soundwave.contacts.ContactCreatorThread;
import edu.nighthawks.soundwave.contacts.ContactsRetrieverThread;
import edu.nighthawks.soundwave.registration.AccountCreatorThread;
import edu.nighthawks.soundwave.file.FileRecorder;
import edu.nighthawks.soundwave.file.FileUploaderThread;

/**
 * The purpose of this class is to act as the controller for the main SoundWave features
 * such as recording sound files, playing back sound files, and tranmistting them to the server.
 */
public class SoundWaveController
{
	private boolean m_bTransmit = false;
	private FileRecorder recorder;
	private FileUploaderThread uploader;

	private AccountCreatorThread accountCreator;
	private ContactCreatorThread contactCreator;
	private ContactsRetrieverThread contactsRetriever;

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
		// send the recorded file
		uploader = new FileUploaderThread();
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

	/**
	 * Create a new account
	 * @param dispName
	 * @param password
	 * @param emailAddress
	 */
	public void createAccount(String dispName, String password, String emailAddress)
	{
		accountCreator = new AccountCreatorThread();
		accountCreator.createAccount(dispName, password, emailAddress);
	}

	/**
	 * Create a new contact
	 */
	public void createContact(String userIdOwner, String userIdMember)
	{
		contactCreator = new ContactCreatorThread();
		contactCreator.createContact(userIdOwner, userIdMember);

	}


	/**
	 * Create a new contact
	 */
	public void retrieveContactsStart(String userIdOwner)
	{
		contactsRetriever = new ContactsRetrieverThread();
		contactsRetriever.retrieveContacts(userIdOwner);
	}

	public String retrieveContactsAfterStart()
	{
		if (contactsRetriever != null)
		{
			if (contactsRetriever.isDone())
			{
				return contactsRetriever.getRawContactsString();
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
