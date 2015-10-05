package edu.nighthawks.soundwave.contacts;

/**
 * Created by joe.keefe on 9/30/2015.
 */
public class ContactsListRetrieverThread extends Thread
{
    private String mUserIdOwner;
    private String rawContactsString;
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
            rawContactsString = ContactListRetreiveHttp.retreiveContactList(mUserIdOwner);
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
        return rawContactsString;
    }
}
