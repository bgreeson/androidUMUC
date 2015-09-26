package edu.nighthawks.soundwave.app;

/**
 * Created by joe.keefe on 9/26/2015.
 */
public class SoundWaveConfig
{
    private String UserName;
    private String UserEmail;

    /**
     * This method must deserialize settings from (server/local shared prefs) and load them
     */
    public void initFromSettings()
    {
        UserName = "Joe"; // set this with registration user name
        UserEmail = "joe.keefe@gmail.com"; // set this with users email
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
}
