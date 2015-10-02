package testharness;

/*
 * FILE: StorageTool.java
 * Creates a storage tool object which can be used to 
 * perform actions on the SoundWave server cloud storage.
 */

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StorageTool
{
    private static URL url;  //Variable takes on the value of the URL and action to take place on server
    private static HttpURLConnection conn; //Variable takes on the value of the HTTP connection to the URL
    private static PrintStream ps; //Variable used to print a stream on the HTTP connection
    
    //Server response variables
    private static int serverResponseCode = 0;
    private static String serverResponseMessage = "";
    
    public static String createMsg(String userID, String targetUserID, String sourceFileUri)
    {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        try
        {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL("http://androidsoundappproject.appspot.com/server?action=upload_file");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            conn.setUseCaches(false); // don't use cached copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("userfile", sourceFile.getAbsolutePath());

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=userfile; filename=" + sourceFile.getAbsolutePath() + "" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multi-part form data necessary after file data
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();

            if (serverResponseCode == 200)
            {

            }

            // close the streams
            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }        
        
        return serverResponseCode + " = " + serverResponseMessage + "\n";
    }
    
    public static String downloadMsg(String msgID)
    {
        
        
        return serverResponseCode + " = " + serverResponseMessage + "\n";
    }
    
    public static String deleteMsg(String targetUserID, String msgID)
    {
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=message_distro_delete");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());
            ps.print("&user_id_target=" + targetUserID);
            ps.print("&msg_id=" + msgID);
            
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
    
    public static String getMsgCount(String targetUserID)
    {
        String messageCount = "Message Count = ";
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=message_count");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());
            ps.print("&user_id_target=" + targetUserID);
            
            conn.getInputStream();
            
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();
            
            InputStream is = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            messageCount += IOUtils.toString(is, encoding);

            is.close();
            ps.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return messageCount + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
    
    public static String getRcvdMsgList(String targetUserID, String newList)
    {
        String messageList = "--- Received Message List (New List = " + newList + ") ---\n";
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=message_list");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());
            ps.print("&user_id_target=" + targetUserID);
            ps.print("&new=" + newList);
            
            conn.getInputStream();
            
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();
            
            InputStream is = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            messageList += IOUtils.toString(is, encoding);

            is.close();
            ps.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return messageList + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
    
    public static String getSentMsgList(String userID)
    {
        String messageList = "--- Sent Message List ---\n";
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=message_sent");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());
            ps.print("&user_id_sender=" + userID);
            
            conn.getInputStream();
            
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();
            
            InputStream is = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            messageList += IOUtils.toString(is, encoding);

            is.close();
            ps.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return messageList + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
    
    public static String getMsgInfo(String msgID)
    {
        String messageInfo = "--- Message Info (Message ID = " + msgID + ") ---\n";
        try
        {            
            url = new URL("http://androidsoundappproject.appspot.com/server?action=message_info");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());
            ps.print("&msg_id=" + msgID);
            
            conn.getInputStream();
            
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();
            
            InputStream is = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            messageInfo += IOUtils.toString(is, encoding);

            is.close();
            ps.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return messageInfo + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
    
    public static String uploadFile(String sourceFileUri)
    {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        try
        {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL("http://androidsoundappproject.appspot.com/server?action=upload_file");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            conn.setUseCaches(false); // don't use cached copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("userfile", sourceFile.getAbsolutePath());

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=userfile; filename=" + sourceFile.getAbsolutePath() + "" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multi-part form data necessary after file data
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();

            if (serverResponseCode == 200)
            {

            }

            // close the streams
            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return serverResponseCode + " = " + serverResponseMessage + "\n";
    }
}
