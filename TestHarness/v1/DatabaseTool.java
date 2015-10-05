package testharness;

/*
 * FILE: DatabaseTool.java
 * Creates a database tool object which can be used to 
 * perform actions on the SoundWave server database.
 */

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;

public class DatabaseTool
{
    
    private static URL url;  //Variable takes on the value of the URL and action to take place on server
    private static HttpURLConnection conn; //Variable takes on the value of the HTTP connection to the URL
    private static PrintStream ps; //Variable used to print a stream on the HTTP connection
    
    //Server response variables
    private static int serverResponseCode = 0;
    private static String serverResponseMessage = "";
    
    //Method used to create a new user
    public static String createUser(String dispName, String mailAddr, String password)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server");
                                    
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());
            ps.print("disp_nme=" + dispName);
            ps.print("&email_addr=" + mailAddr);
            ps.print("&user_pw=" + password);
            ps.print("&action=user_create");
            
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
    
    //Method used to get an existing user's meta info via 0 = User ID# or 1 = eMail address
    public static String getUserInfo(String input, int type)
    {
        String urlString = "", userInfo = "";
        
        if (type == 0)
        {
            urlString = "http://androidsoundappproject.appspot.com/server?user_id=" + input + "&action=user_info";
            userInfo = "User Info (User ID = " + input + "):\n";
        }
        else
        {
            urlString = "http://androidsoundappproject.appspot.com/server?email_addr=" + input + "&action=user_info_email";
            userInfo = "User Info (eMail Address = " + input + "):\n";
        }

        try
        {            
            url = new URL(urlString);
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            InputStream is = conn.getInputStream();
            userInfo += IOUtils.toString(is);
            
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();

            is.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }  
        
        return userInfo + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
            
    //Method used to add an existing member to an existing user's contact list
    public static String createContact(String userID, String memberID)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            ps = new PrintStream(conn.getOutputStream());
            ps.print("user_id_owner=" + userID);
            ps.print("&user_id_member=" + memberID);
            ps.printf("&action=contact_create");

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
    
    //Method used to get the contact list of the specified user ID #
    public static String getContList(String userID)
    {
        String response = "";
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?user_id_owner=" + userID + "&action=contact_all");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            //ps = new PrintStream(conn.getOutputStream());
            //ps.print("user_id_owner=" + userName);
            //ps.print("user_id_member=" + memberName);
            //conn.getInputStream();
            
            InputStream is = conn.getInputStream();
            response += IOUtils.toString(is);
            
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();

            is.close();
            //ps.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
                
        return response + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
    
    //Method used to delete a listed contact using a contact ID#
    public static String deleteContact(String contactID)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            ps = new PrintStream(conn.getOutputStream());
            ps.print("contact_id=" + contactID);
            ps.print("&action=contact_delete");

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
    
    //Method used to edit an existing user
    public static String editUser(String userID, String newName, String newMail, String newPass)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());
            ps.print("user_id=" + userID);
            ps.print("&disp_nme=" + newName);
            ps.print("&email_addr=" + newMail);
            ps.print("&user_pw=" + newPass);
            ps.print("&action=user_edit");
            
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
    
    //Method used to disable an existing user
    public static String disableUser(String userID)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            ps = new PrintStream(conn.getOutputStream());
            ps.print("user_id=" + userID);
            ps.print("&action=user_disable");

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
    
    //Method used to delete an existing user
    public static String deleteUser(String userID)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            ps = new PrintStream(conn.getOutputStream());
            ps.print("user_id=" + userID);
            ps.print("&action=user_delete");

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
