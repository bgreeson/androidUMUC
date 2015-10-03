package edu.nighthawks.soundwave.registration;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joe.keefe on 9/27/2015.
 */
public class AccountGetInfoHttp
{

    public static int getAccountInfo(String emailAddress)
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
            ps.print("&email_addr=" + emailAddress);
            ps.print("&action=user_info");

            conn.getInputStream();

            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();

            InputStream is = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            String responseString = IOUtils.toString(is, encoding);

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
