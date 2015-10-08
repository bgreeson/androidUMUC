package edu.nighthawks.soundwave.app;

import android.content.Context;

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

    private boolean mRegistered;

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

    public String getmUserName()
    {
        return mUserName;
    }

    public void setmUserName(String mUserName)
    {
        this.mUserName = mUserName;
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();
        SharePrefsUtil.setString(context, SharePrefsUtil.USER_NAME, mUserName);
    }

    public String getmUserEmail()
    {
        return mUserEmail;
    }

    public void setmUserEmail(String mUserEmail)
    {
        this.mUserEmail = mUserEmail;
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();
        SharePrefsUtil.setString(context, SharePrefsUtil.USER_EMAIL, mUserEmail);
    }

    public String getmRawContactsString()
    {
        return mRawContactsString;
    }

    public void setmRawContactsString(String mRawContactsString)
    {
        this.mRawContactsString = mRawContactsString;
    }

    public int getmUserId()
    {
        return mUserId;
    }

    public void setmUserId(int mUserId)
    {
        this.mUserId = mUserId;
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();
        SharePrefsUtil.setInt(context, SharePrefsUtil.USER_ID, mUserId);
    }

    public boolean ismRegistered()
    {
        return mRegistered;
    }

    public void setmRegistered(boolean mRegistered)
    {
        this.mRegistered = mRegistered;
    }
}
