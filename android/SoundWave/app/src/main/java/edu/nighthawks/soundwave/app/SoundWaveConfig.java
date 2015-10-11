package edu.nighthawks.soundwave.app;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.nighthawks.soundwave.contacts.Contact;

/**
 * Created by joe.keefe on 9/26/2015.
 *
 * This class holds all soundWave configuration and settings.
 */
public class SoundWaveConfig
{
    // All user info should be looked up after user ID is known
    private int mUserId = -1;

    private String mUserName = "";
    private String mUserEmail = "";
    private String mRawContactsString;
    public List<Contact> mContactList = Collections.synchronizedList(new ArrayList<Contact>());
    public Object mContactListLock = new Object();
    private boolean mRegistered;
    private boolean bPollServerForMessages;

    public boolean isPollingServerForMessages()
    {
        return bPollServerForMessages;
    }

    public void setPollServerForMessages(boolean bPollServerForMessages)
    {
        this.bPollServerForMessages = bPollServerForMessages;
    }


    /**
     * This method must deserialize settings from (server/local shared prefs) and load them
     */
    public void initFromSharedPrefs()
    {
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();

        mUserId = SharePrefsUtil.getInt(context, SharePrefsUtil.USER_ID);
        mUserEmail = SharePrefsUtil.getString(context, SharePrefsUtil.USER_EMAIL);
        mUserName = SharePrefsUtil.getString(context, SharePrefsUtil.USER_NAME);
    }

/*
    public void saveToSharedPrefs()
    {
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();

        SharePrefsUtil.setInt(context, SharePrefsUtil.USER_ID, mUserId);
        SharePrefsUtil.setString(context, SharePrefsUtil.USER_EMAIL, mUserEmail);
        SharePrefsUtil.setString(context, SharePrefsUtil.USER_NAME, mUserName);
    }
*/

    public String getUserName()
    {
        return mUserName;
    }

    public void setUserName(String mUserName)
    {
        this.mUserName = mUserName;
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();
        SharePrefsUtil.setString(context, SharePrefsUtil.USER_NAME, mUserName);
    }

    public String getUserEmail()
    {
        return mUserEmail;
    }

    public void setmUserEmail(String mUserEmail)
    {
        this.mUserEmail = mUserEmail;
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();
        SharePrefsUtil.setString(context, SharePrefsUtil.USER_EMAIL, mUserEmail);
    }

    public String getRawContactsString()
    {
        return mRawContactsString;
    }

    public void setRawContactsString(String mRawContactsString)
    {
        this.mRawContactsString = mRawContactsString;
    }

    public int getUserId()
    {
        return mUserId;
    }

    public void setUserId(int mUserId)
    {
        this.mUserId = mUserId;
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();
        SharePrefsUtil.setInt(context, SharePrefsUtil.USER_ID, mUserId);
    }

    public boolean isRegistered()
    {
        return mRegistered;
    }

    public void setRegistered(boolean mRegistered)
    {
        this.mRegistered = mRegistered;
    }
}
