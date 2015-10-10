package edu.nighthawks.soundwave.http;

/**
 * Created by joe.keefe on 10/8/2015.
 */
public class HttpServerGetAccountInfo extends HttpBaseServerAction
{
    public HttpServerGetAccountInfo(String user_id_owner)
    {
        super("http://androidsoundappproject.appspot.com/server?user_id=" + user_id_owner + "&action=user_info", null);
    }
}
