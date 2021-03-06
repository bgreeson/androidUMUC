package edu.nighthawks.soundwave.contacts;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joe.keefe on 9/27/2015.
 */
public class ContactCreatorHttp
{

    public static int createContact(String userIdOwner, String userIdMember)
    {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int serverResponseCode = 0;
        String serverResponseMessage = "";

        try
        {
            // Use this for Google cloud hosted change ($upload_url = CloudStorageTools::createUploadUrl('/server?action=upload_file', $options)
            URL url = new URL("http://androidsoundappproject.appspot.com/server");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print("&user_id_owner=" + userIdOwner);
            ps.print("&user_id_member=" + userIdMember);
            ps.print("&action=contact_create");


            InputStream is = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            String data = IOUtils.toString(is, encoding);

            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();

            is.close();
            ps.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return serverResponseCode;
    }

}
