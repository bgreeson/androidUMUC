package edu.nighthawks.soundwave.registration;

/**
 * This class encapsulates account creation (registration)
 */
public class AccountCreator extends Thread
{
    private String DispName;
    private String Password;
    private String EmailAddress;



    public void createAccount(String dispName, String password, String emailAddress)
    {
        DispName = dispName;
        Password = password;
        EmailAddress = emailAddress;
        this.start();
    }

    @Override
    public void run()
    {
        try
        {
            CreateAccount.createAccount(DispName, Password, EmailAddress);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
