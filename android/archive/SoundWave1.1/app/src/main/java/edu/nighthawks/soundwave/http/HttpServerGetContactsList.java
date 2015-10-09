package edu.nighthawks.soundwave.http;

/**
 * Created by joe.keefe on 10/8/2015.
 */
public class HttpServerGetContactsList extends HttpBaseServerAction
{
    public HttpServerGetContactsList(int userIdOwner)
    {
        // May be
        super("http://androidsoundappproject.appspot.com/server?action=user_info", "contact_all");
        //super("http://androidsoundappproject.appspot.com/server", "contact_all");

        addNameValuePair("user_id_owner", Integer.toString(userIdOwner));
    }
}
