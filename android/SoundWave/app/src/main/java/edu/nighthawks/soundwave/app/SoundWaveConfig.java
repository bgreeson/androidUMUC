package edu.nighthawks.soundwave.app;

/**
 * Created by joe.keefe on 9/26/2015.
 *
 * This class holds all soundWave configuration and settings.
 */
public class SoundWaveConfig
{
    private String UserName;
    private String UserEmail;
    private String RawContactsString;
    private int UserId;

    /**
     * This method must deserialize settings from (server/local shared prefs) and load them
     */
    public void initFromSettings()
    {
        //TODO get all this from server
        UserName = "Joe"; // set this with registration user name
        UserEmail = "joe.keefe@gmail.com"; // set this with users email
        setUserId(22);

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
}
