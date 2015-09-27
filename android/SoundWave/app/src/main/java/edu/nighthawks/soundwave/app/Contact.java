package edu.nighthawks.soundwave.app;

/**
 * Created by Lookman Omisore on 8/28/2015.
 */
public class Contact
{
    private String _name, _email;


    public Contact(String name, String phone, String email, String address)
    {
        _name = name;
        _email = email;
    }

    public String getName()
    {
        return _name;
    }

    public String getEmail()
    {
        return _email;
    }

}
