package edu.nighthawks.soundwave.registration;

import java.util.StringTokenizer;

/**
 * Created by joe.keefe on 10/3/2015.
 */
public class AccountCreationData
{
    AccountCreationData(String httpResponse)
    {
        mRawResponseString = httpResponse;
        unpack();
    }

    private boolean unpack()
    {
        if (mRawResponseString == null)
            return false;

        StringTokenizer st = new StringTokenizer(mRawResponseString, ",");

        while (st.hasMoreTokens())
        {
            System.out.println(st.nextToken());
        }

        mID = 0;

        return true;
    }

    private String mRawResponseString;

    private int mID;
    private int mEmail;
    // ..
    // ..

}
