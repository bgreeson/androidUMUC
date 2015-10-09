package edu.nighthawks.soundwave.file;

/**
 * Created by joe.keefe on 9/30/2015.
 */
public class GetMessageCountThread extends Thread
{
    private String mUserIdOwner;
    private String mMessageCountAsString; // parse to int
    private boolean Done;




    public void retrieveContacts(String _userIdOwner)
    {
        mUserIdOwner = _userIdOwner;
        this.start();
    }

    @Override
    public void run()
    {
        try
        {
            mMessageCountAsString = GetMessageCountHttp.getMessageCount(mUserIdOwner);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public boolean isDone()
    {
        return Done;
    }

    public String getRawContactsString()
    {
        return mMessageCountAsString;
    }
}
