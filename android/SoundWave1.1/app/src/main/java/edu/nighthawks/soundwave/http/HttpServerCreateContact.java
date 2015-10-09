package edu.nighthawks.soundwave.http;

/**
 * Created by joe.keefe on 10/8/2015.
 */
public class HttpServerCreateContact extends HttpBaseServerAction
{
    public HttpServerCreateContact(String userIdMember)
    {
        super("http://androidsoundappproject.appspot.com/server", "contact_create");

        addNameValuePair("user_id_owner", Integer.toString(getUserId()));
        addNameValuePair("user_id_member", userIdMember);
    }
}
