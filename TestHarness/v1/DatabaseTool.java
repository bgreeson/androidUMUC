package testharness;

/*
 * FILE: DatabaseTool.java
 * Creates a database tool object which can be used to 
 * perform actions on the SoundWave server database.
 */

import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseTool
{
    
    private static URL url;  //Variable takes on the value of the URL and action to take place on server
    private static HttpURLConnection conn; //Variable takes on the value of the HTTP connection to the URL
    private static PrintStream ps; //Variable used to print a stream on the HTTP connection
    
    //Server response variables
    private static int serverResponseCode = 0;
    private static String serverResponseMessage = "";
    
    //Method used to create a new user
    public static String createUser(String userName, String mailAddr, String password)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=user_create");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());
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
    
    //Method used to edit an existing user
    public static String editUser(String userName, String newName, String newMail, String newPass)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=user_edit&user_id=" + userName);
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());
            if (!newName.isEmpty()) ps.print("disp_nme=" + newName);
            if (!newMail.isEmpty()) ps.print("&email_addr=" + newMail);
            if (!newPass.isEmpty()) ps.print("&user_pw=" + newPass);
            
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
    public static String disableUser(String userName)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=user_disable&user_id=" + userName);
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            ps = new PrintStream(conn.getOutputStream());
            //ps.print("user_id=" + userName);

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
    public static String deleteUser(String userName)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=user_delete&user_id=" + userName);
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            ps = new PrintStream(conn.getOutputStream());
            //ps.print("user_id=" + userName);

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
    
    //Method used to an existing user's meta info
    public static String getUserInfo(String userName)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=user_info&user_id=" + userName);
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            ps = new PrintStream(conn.getOutputStream());
            //ps.print("user_id=" + userName);          
            
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
    
    //Method used to add an existing member to an existing user's contact list
    public static String createContact(String userName, String memberName)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=contact_create");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            ps = new PrintStream(conn.getOutputStream());
            ps.print("user_id_owner=" + userName);
            ps.print("user_id_member=" + memberName);

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
    
    //Method used to delete a listed contact from an existing user's contact list
    public static String deleteContact(String userName, String contactName)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=contact_delete&contact_id=" + contactName);
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs

            ps = new PrintStream(conn.getOutputStream());
            //ps.print("user_id_owner=" + userName);
            ps.print("contact_id=" + contactName);

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
