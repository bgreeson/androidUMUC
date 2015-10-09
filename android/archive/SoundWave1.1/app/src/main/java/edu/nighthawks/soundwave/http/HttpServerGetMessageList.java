package edu.nighthawks.soundwave.http;

/**
 * Created by joe.keefe on 10/7/2015.
 */
public class HttpServerGetMessageList extends HttpBaseServerAction
{
    public HttpServerGetMessageList(String user_id_owner)
    {
        super("http://androidsoundappproject.appspot.com/server", "message_list");
        this.addNameValuePair("user_id_owner", Integer.toString(getUserId()));
    }
}
