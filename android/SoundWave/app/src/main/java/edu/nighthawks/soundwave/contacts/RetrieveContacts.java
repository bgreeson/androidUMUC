package edu.nighthawks.soundwave.contacts;

import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joe.keefe on 9/30/2015.
 */
public class RetrieveContacts
{

    public static String retrieveContacts(String userIdOwner)
    {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int serverResponseCode = 0;
        String serverResponseMessage = "";
        String stringOfContacts = "";

        try
        {
            // Use this for Google cloud hosted change ($upload_url = CloudStorageTools::createUploadUrl('/server?action=upload_file', $options)
            URL url = new URL("http://androidsoundappproject.appspot.com/server?action=user_info");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print("&user_id_owner=" + userIdOwner);

            conn.getInputStream();

            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();
            stringOfContacts= conn.getHeaderField("user_id_member");

            ps.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return stringOfContacts;
    }

}
