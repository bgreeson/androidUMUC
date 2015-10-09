package edu.nighthawks.soundwave.contacts;

import java.io.Serializable;

public class Contact implements Serializable 
{
    /**
     * Generated Serial version Id
     */
    private static final long serialVersionUID = -55857686305273843L;

    private String _name, _email;

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String value = "name : " + _name + "\nemail : " + _email + "\n";
        return value;
    }
    

    /**
     * Setters and getters methods.
     */

    public Contact(){};

    public Contact(String name, String email)
    {
        _name = name;
        _email = email;
    }

    
   public String getName()
    {
        return _name;
    }

   public void setName(String name) {
       this._name = name;
   }


   public String getEmail()
    {
        return _email;
    }

   public void setEmail(String email) {
       this._email = email;
   }


} 