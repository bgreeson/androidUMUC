package edu.nighthawks.soundwave.app;

import android.content.Context;

/**
 * Created by joe.keefe on 9/26/2015.
 *
 * This class holds all soundWave configuration and settings.
 */
public class SoundWaveConfig
{
    private String UserName = "Joe";
    private String UserEmail = "joe.keefe@gmail.com";
    private String RawContactsString;
    private int UserId = 22;
    private boolean Registered;

    /**
     * This method must deserialize settings from (server/local shared prefs) and load them
     */
    public void initFromSharedPrefs()
    {
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();

        UserId = SharePrefsUtil.getInt(context, SharePrefsUtil.USER_ID);
        UserEmail = SharePrefsUtil.getString(context, SharePrefsUtil.USER_EMAIL);
        UserName = SharePrefsUtil.getString(context, SharePrefsUtil.USER_NAME);
    }

    public void saveToSharedPrefs()
    {
        Context context = SoundWaveApplication.getApplicationObject().getApplicationContext();

        SharePrefsUtil.setInt(context, SharePrefsUtil.USER_ID, UserId);
        SharePrefsUtil.setString(context, SharePrefsUtil.USER_EMAIL, UserEmail);
        SharePrefsUtil.setString(context, SharePrefsUtil.USER_NAME, UserName);
    }

    public String getUserName()
    {
        return UserName;
    }

    public void setUserName(String userName)
    {
        UserName = userName;
    }

    public String getUserEmail()
    {
        return UserEmail;
    }

    public void setUserEmail(String userEmail)
    {
        UserEmail = userEmail;
    }

    public String getRawContactsString()
    {
        return RawContactsString;
    }

    public void setRawContactsString(String rawContactsString)
    {
        RawContactsString = rawContactsString;
    }

    public int getUserId()
    {
        return UserId;
    }

    public void setUserId(int userId)
    {
        UserId = userId;
    }

    public boolean isRegistered()
    {
        return Registered;
    }

    public void setRegistered(boolean registered)
    {
        Registered = registered;
    }
}
