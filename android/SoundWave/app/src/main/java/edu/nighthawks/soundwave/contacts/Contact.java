package edu.nighthawks.soundwave.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Contact implements Serializable 
{
    /**
     * Generated Serial version Id
     */
    private static final long serialVersionUID = -55857686305273843L;

    private String mName;
    private String mEmail;
    @JsonProperty("CONTACT_ID")
    private String mCountactId;
    @JsonProperty("DATE_ADDED")
    private String mDateAdded;
    @JsonProperty("USER_ID_OWNER")
    private String mUserIdOwner;
    @JsonProperty("USER_ID_MEMBER")
    private String mUserIdMember;

    public String getmUserIdOwner()
    {
        return mUserIdOwner;
    }

    public String getUserIdContact()
    {
        return mUserIdMember;
    }


    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }



    public void setmUserIdMember(String mUserIdMember)
    {
        this.mUserIdMember = mUserIdMember;
    }


    public void setmDateAdded(String mDateAdded)
    {
        this.mDateAdded = mDateAdded;
    }



    public String getmCountactId()
    {
        return mCountactId;
    }



    @Override
    public String toString()
    {
        String value = "name : " + mName + "\nemail : " + mEmail + "\n";
        return value;
    }
    

    /**
     * Setters and getters methods.
     */

    public Contact(){};

    public Contact(String name, String email)
    {
        mName = name;
        mEmail = email;
    }

    
   public String getName()
    {
        return mName;
    }

   public void setName(String name) {
       this.mName = name;
   }


   public String getEmail()
    {
        return mEmail;
    }

   public void setEmail(String email) {
       this.mEmail = email;
   }


} 