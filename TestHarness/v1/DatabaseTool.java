package testharness;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseTool
{
    public static String registerUser(String userName, String mailAddr, String password)
    {
        int serverResponseCode = 0;
        String serverResponseMessage = "";

        try
        {            
            URL url = new URL("http://androidsoundappproject.appspot.com/server?action=create_user");
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print("disp_nme=" + userName);
            ps.print("&email_addr=" + mailAddr);
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

        return serverResponseCode + " = " + serverResponseMessage + "\n";
    }
}
