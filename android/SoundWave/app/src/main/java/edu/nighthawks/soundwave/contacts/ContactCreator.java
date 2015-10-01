package edu.nighthawks.soundwave.contacts;

/**
 * This class encapsulates account creation (registration)
 */
public class ContactCreator extends Thread
{
    private String mUserIdOwner;
    private String mUserIdMember;



    public void createContact(String _userIdOwner, String _userIdMember)
    {
        mUserIdOwner = _userIdOwner;
        mUserIdMember = _userIdMember;
        this.start();
    }

    @Override
    public void run()
    {
        try
        {
            CreateContact.createContact(mUserIdOwner, mUserIdMember);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
