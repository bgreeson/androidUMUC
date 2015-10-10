package testharness;

/*
 * FILE: StorageTool.java
 * Creates a storage tool object which can be used to 
 * perform actions on the SoundWave server cloud storage.
 */

import org.apache.commons.io.IOUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Base64;

public class StorageTool
{
    private static URL url;  //Variable takes on the value of the URL and action to take place on server
    private static HttpURLConnection conn; //Variable takes on the value of the HTTP connection to the URL
    private static PrintStream ps; //Variable used to print a stream on the HTTP connection
    
    //Server response variables
    private static int serverResponseCode = 0;
    private static String serverResponseMessage = "";
    
    public static String createMsg(String senderUserID, String targetUserID, String filePath) throws Exception
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String actResp = ""; //String variable used for the response from the server for this action
        
        try 
        {
            HttpPost httppost = new HttpPost("http://androidsoundappproject.appspot.com/server");

            FileBody bin = new FileBody(new File(filePath));
            //StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("action", new StringBody("message_create", ContentType.TEXT_PLAIN))
                    .addPart("user_id_sender", new StringBody(senderUserID, ContentType.TEXT_PLAIN))
                    .addPart("user_id_target", new StringBody(targetUserID, ContentType.TEXT_PLAIN))
                    .addPart("userfile", bin)
                    .build();


            httppost.setEntity(reqEntity);

            //System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse httpResp = httpclient.execute(httppost);
            try 
            {
                
                //System.out.println("----------------------------------------");
                //System.out.println(response.getStatusLine());
                HttpEntity resEntity = httpResp.getEntity();
                //if (resEntity != null) 
                //{
                //    System.out.println("Response content length: " + resEntity.getContentLength());
                //}
                EntityUtils.consume(resEntity);
            } 
            finally 
            {
                //serverResponseCode = conn.getResponseCode();
                //serverResponseMessage = conn.getResponseMessage();
                actResp = httpResp.toString();
                httpResp.close();                
            }
        } 
        finally 
        {
            httpclient.close();
        }
        
        return actResp + "\n";
    }
    
    public static String getMsg(String msgID)
    {
        //String urlString = "http://androidsoundappproject.appspot.com/server?msg_id=" + msgID 
        //            + "&action=message_get";
        //String urlString = "http://androidsoundappproject.appspot.com/server?action=message_get&msg_id=" + msgID; 
        
        String msgInfo = getMsgInfo(msgID);
        //String urlFilePath = "http://androidsoundappproject.appspot.com/";
        //String urlFilePath = "https://androidsoundappproject.appspot.com/message/";
        String urlFilePath = "http://storage.cloud.google.com/androidsoundappproject.appspot.com/message/";        
        //String urlFilePath = "http://androidsoundappproject.appspot.com.storage.cloud.google.com/message/";
        //String urlFilePath = "https://console.developers.google.com/m/cloudstorage/b/androidsoundappproject.appspot.com/o/message/";
        
        int begIndex = msgInfo.indexOf("USER_ID_SENDER");
        int endIndex = msgInfo.indexOf("FILE_NAME");
        
        urlFilePath += msgInfo.subSequence(begIndex + 17, endIndex - 3);
        
        begIndex = endIndex;
        endIndex = msgInfo.indexOf("FILE_TYPE");
        
        String fileName = msgInfo.substring(begIndex + 12, endIndex - 3);
        urlFilePath += "/" + fileName;
        
        File file = new File("C:\\Users\\J H Copeland\\Downloads\\SWMessages\\" + fileName);
        
        if (!file.exists())
        {
            try
            {
                System.out.println("Create file...");
                file.createNewFile();
                
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                //CloseableHttpClient httpclient = HttpClients.custom().build();
                CloseableHttpClient httpclient = HttpClients.createDefault();
                try 
                {
                    System.out.println("Open input...");
                    //String userCredentials = "username:password";
                    //String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
                    HttpGet httpget = new HttpGet(urlFilePath);
                    //httpget.setHeader("Authorization", basicAuth);
                    //httpget.setHeader("Content-Type", "text/html");
                    CloseableHttpResponse response = httpclient.execute(httpget);
                    System.out.println("Status: " + response.getStatusLine());
                        try 
                        {
                            System.out.println("Download...");
                            fileOutputStream.write(EntityUtils.toByteArray(response.getEntity()));
                            fileOutputStream.close();
                        } 
                        finally
                        {
                            response.close();
                        }
                    } 
                finally 
                {
                        httpclient.close();
                }
                
            }
            catch (Exception ex)
            {
                System.out.println("ERROR!!!");
            }
            String message = "--- Get Message (Message ID = " + msgID + ") ---\n";
            return message + urlFilePath + "\nDownload Successful\n\n";
        }
        
        String message = "--- Get Message (Message ID = " + msgID + ") ---\n";
        return message + urlFilePath + "\nDownload Error\n\n";
    }
    
    public static String deleteMsg(String targetUserID, String msgID)
    {
        String actResp = ""; //String variable used for the response from the server for this action
        
        try
        {            
            //url = new URL("http://androidsoundappproject.appspot.com/server?user_id_target=" + targetUserID 
            //        + "&msg_id=" + msgID + "&action=message_distro_delete");
            
            url = new URL("http://androidsoundappproject.appspot.com/server");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            
            ps = new PrintStream(conn.getOutputStream());            
            ps.print("user_id_target=" + targetUserID);
            ps.print("&msg_id=" + msgID);
            ps.print("&action=message_distro_delete");
            //conn.getInputStream();
            
            InputStream is = conn.getInputStream();
            actResp += IOUtils.toString(is);
            
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();
            
            is.close();
            ps.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return actResp + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
    
    public static String getMsgCount(String targetUserID)
    {
        String urlString = "http://androidsoundappproject.appspot.com/server?user_id_target=" + targetUserID 
                    + "&action=message_count";
        String message = "Message Count = ";
        String response = accessServer(urlString, message);
        return response;        
    }
    
    public static String getRcvdMsgList(String targetUserID, String newList)
    {
        String urlString = "http://androidsoundappproject.appspot.com/server?user_id_target=" + targetUserID
                    + "&new=" + newList + "&action=message_list";
        String message = "--- Received Message List (New List = " + newList + ") ---\n";
        String response = accessServer(urlString, message);
        return response;        
    }
    
    public static String getSentMsgList(String userID)
    {
        String urlString = "http://androidsoundappproject.appspot.com/server?user_id_sender=" + userID 
                    + "&action=message_sent";
        String message = "--- Sent Message List ---\n";
        String response = accessServer(urlString, message);
        return response;
    }
    
    public static String getMsgInfo(String msgID)
    {        
        String urlString = "http://androidsoundappproject.appspot.com/server?msg_id=" + msgID 
                    + "&action=message_info";
        String message = "--- Message Info (Message ID = " + msgID + ") ---\n";
        String response = accessServer(urlString, message);        
        return response;
    }
    
    private static String accessServer(String urlString, String message)
    {
        String actResp = message; //String variable used for the response from the server for this action
        
        try
        {            
            url = new URL(urlString);
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs            
            //conn.getInputStream();
            
            InputStream is = conn.getInputStream();
            String servResp = IOUtils.toString(is);
            //System.out.println(servResp);
            if (servResp.length() > 1)
            {
                //System.out.println(servResp.substring(0, 3));
                if ((servResp.charAt(0) == '[' && servResp.charAt(1) == '{') || servResp.substring(0, 4).equals("sql:"))
                {
                    JSONParser myParse = new JSONParser(servResp);
                    actResp += myParse.getParse(true);   
                }
            }                
            else actResp += servResp;
            
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();

            is.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return actResp + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
}
