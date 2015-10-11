package edu.nighthawks.soundwave.http;

/**
 * Created by joe.keefe on 10/7/2015.
 */
public class HttpServerGetMessageCount extends HttpBaseServerAction
{
    public HttpServerGetMessageCount(String user_id_owner)
    {
        super("http://androidsoundappproject.appspot.com/server?action=message_count&user_id_target=" + user_id_owner, null);
        //super("http://androidsoundappproject.appspot.com/server", "message_count");
        //this.addNameValuePair("user_id_owner", Integer.toString(getUserId()));
    }
}
