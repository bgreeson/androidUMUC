package edu.nighthawks.soundwave.http;

/**
 * Created by joe.keefe on 10/8/2015.
 */
public class HttpServerGetAccountInfoByEmail extends HttpBaseServerAction
{
    public HttpServerGetAccountInfoByEmail(String emailAddress)
    {
        //super("http://androidsoundappproject.appspot.com/server", "user_info_email");
        //super("http://androidsoundappproject.appspot.com/server?action=user_info_email", "user_info_email");
        super("http://androidsoundappproject.appspot.com/server?email_addr=" + emailAddress + "&action=user_info_email", "");


        //addNameValuePair("email_addr", emailAddress);
    }
}
