package edu.nighthawks.soundwave.http;

/**
 * Created by joe.keefe on 10/8/2015.
 */
public class HttpServerRegisterAccount extends HttpBaseServerAction
{
    public HttpServerRegisterAccount(String displayName,
                                     String emailAddress,
                                     String password)
    {
        super("http://androidsoundappproject.appspot.com/server", "user_create");

        this.addNameValuePair("disp_nme", displayName);
        this.addNameValuePair("email_addr", emailAddress);
        this.addNameValuePair("user_pw", password);
    }

}
