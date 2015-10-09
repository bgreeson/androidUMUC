package edu.nighthawks.soundwave.registration;

/**
 * This class encapsulates account creation (registration)
 */
public class AccountGetInfoThread extends Thread
{
    private String EmailAddress;



    public void getAccountInfo(String emailAddress)
    {
        EmailAddress = emailAddress;
        this.start();
    }

    @Override
    public void run()
    {
        try
        {
            AccountGetInfoHttp.getAccountInfo(EmailAddress);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
