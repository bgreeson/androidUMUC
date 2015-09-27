package edu.nighthawks.soundwave.file;

import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joe.keefe on 9/27/2015.
 */
public class CreateAccount
{

    public static int createAccount(String displayName, String password, String emailAddress)
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
            URL url = new URL("http://androidsoundappproject.appspot.com/server?action=create_user");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print("disp_nme=" + displayName);
            ps.print("&email_addr=" + emailAddress);
            ps.print("&user_pw=" + password);

            conn.getInputStream();

            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();

            ps.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return serverResponseCode;
    }

}
