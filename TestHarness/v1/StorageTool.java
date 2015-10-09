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
            JSONParser myParse = new JSONParser(IOUtils.toString(is));
            actResp += myParse.getParse(true);            
            //actResp += IOUtils.toString(is);
            
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
    
    public static String createMsgA(String userID, String targetUserID, String fileName, String sourceFileUri)
    {
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        String response = "";
        
        try
        {
            //String query1 = String.format("user_id_sender=%s", URLEncoder.encode(userID, "UTF-8"));
            //+ userID)"user_id_sender=" + userID + "&user_id_target=" + targetUserID + "&file_name=" + fileName + "&action=message_create";
            //String query2 = String.format("&user_id_target=%s", URLEncoder.encode(targetUserID, "UTF-8"));
            //String query3 = String.format("&userfile=%s", URLEncoder.encode(fileName, "UTF-8"));
            //String query4 = "&action=message_create";
            //String upldURL = String.format("&upload_url=%s", URLEncoder.encode("http://androidsoundappproject.appspot.com/server", "UTF-8"));
            
            
            //URL url = new URL("http://androidsoundappproject.appspot.com/server?user_id_sender=" + userID + "&user_id_target=" + targetUserID + "&action=message_create");
            //URL url = new URL("http://androidsoundappproject.appspot.com/server?user_id_sender=" + userID 
            //        + "&user_id_target=" + targetUserID + "&userfile=" + sourceFile.getAbsolutePath() + "&action=message_create");
            URL url = new URL("http://androidsoundappproject.appspot.com/server");
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            conn.setUseCaches(false); // don't use cached copy
            //conn.setRequestMethod("POST");
            //conn.setRequestProperty("Connection", "Keep-Alive");
            //conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            //conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            
            //conn.setRequestProperty("action", "message_create");
            //conn.setRequestProperty("&user_id_sender", userID);
            //conn.setRequestProperty("&user_id_target", targetUserID);           
            //conn.setRequestProperty("userfile", sourceFile.getAbsolutePath());
            //conn.setRequestProperty("&action", "message_create");
            
            ps = new PrintStream(conn.getOutputStream());            
            ps.print("user_id_sender=" + userID);
            ps.print("&user_id_target=" + targetUserID);
            ps.print("action=message_create");
            //conn.getInputStream();
            
            InputStream is = conn.getInputStream();
            response += IOUtils.toString(is);

            is.close();
            /*
            dos = new DataOutputStream(conn.getOutputStream());
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            
            //dos.writeBytes("user_id_sender=" + userID);
            //dos.writeBytes("&user_id_target=" + targetUserID);
            //dos.writeBytes("&action=message_create");            
            
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            //dos.writeBytes("Content-Disposition: form-data; name=userfile; filename=" + sourceFile.getAbsolutePath() + "" + lineEnd);
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

            // close the streams
            fileInputStream.close();
            dos.flush();
            dos.close();
            */
            
            
            ps.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
                
        return response + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";     
    }
    
    public static String createMsgB(String userID, String targetUserID, String fileName, String sourceFileUri)
    {
        DataOutputStream dos = null;
        String charset = "UTF-8";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        String response = "";

        try
        {
            String query1 = String.format("&user_id_sender=%s", URLEncoder.encode(userID, "UTF-8"));
            //+ userID)"user_id_sender=" + userID + "&user_id_target=" + targetUserID + "&file_name=" + fileName + "&action=message_create";
            String query2 = String.format("&user_id_target=%s", URLEncoder.encode(targetUserID, "UTF-8"));
            String query3 = String.format("&userfile=%s", URLEncoder.encode(fileName, "UTF-8"));
            String query4 = String.format("action=", URLEncoder.encode("message_create", "UTF-8"));
            String query = String.format("action=message_create&user_id_sender=%s&user_id_target=%s", URLEncoder.encode(userID, charset), URLEncoder.encode(targetUserID, charset));
            
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            //URL url = new URL("http://androidsoundappproject.appspot.com/server2?user_id_sender=" + userID +
            //        "&user_id_target=" + targetUserID + "&action=message_create");
            //URL url = new URL("http://androidsoundappproject.appspot.com/server2?action=message_create&user_id_sender=" + userID +
            //        "&user_id_target=" + targetUserID);
            //URL url = new URL("http://androidsoundappproject.appspot.com/server?action=message_create_dev");
            //URL url = new URL("http://androidsoundappproject.appspot.com/server2?action=upload_file");
            url = new URL("http://androidsoundappproject.appspot.com/server");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // allow Inputs
            conn.setDoOutput(true); // allow Outputs
            //conn.setUseCaches(false); // don't use cached copy
            //conn.setRequestMethod("POST");
            //conn.setRequestProperty("Connection", "Keep-Alive");
            //conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            //conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //conn.setRequestProperty("action", "upload_file");
            //conn.setRequestProperty("userfile", sourceFile.getAbsolutePath());

            dos = new DataOutputStream(conn.getOutputStream());
            //dos.writeBytes("user_id_sender=" + userID + "&user_id_target=" + targetUserID + "&action=message_create");
            //dos.writeBytes(query4 + query1 + query2);
            dos.write(query.getBytes("UTF-8"));
            
            //dos.writeBytes("action=upload_file");
            
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=userfile; filename=" + sourceFile.getName() + "" + lineEnd);
            //dos.writeBytes("Content-Disposition: form-data; name=userfile; filename=" + fileName + "" + lineEnd);
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
            
            InputStream is = conn.getInputStream();
            response += IOUtils.toString(is);

            is.close();

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();


            // close the streams
            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return response + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
    
    public static String createMsgC(String userID, String targetUserID, String fileName, String sourceFileUri)
    {
        String charset = "UTF-8";
        //File textFile = new File(sourceFileUri);
        File binaryFile = new File(sourceFileUri);
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
        String response = "";
        
        //url = new URL("http://androidsoundappproject.appspot.com/server");
        //conn = (HttpURLConnection) url.openConnection();
        //URLConnection connection = new URL(url).openConnection();
        //conn.setDoOutput(true);
        //conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try
        {
            String query = String.format("action=message_create&user_id_sender=%s&user_id_target=%s", URLEncoder.encode(userID, charset), URLEncoder.encode(targetUserID, charset));

            url = new URL("http://androidsoundappproject.appspot.com/server");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            
            OutputStream output = conn.getOutputStream();
            output.write(query.getBytes(charset));
            
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
            
            // Send normal param.
            //writer.append("--" + boundary).append(CRLF);
            //writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
            //writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            //writer.append(CRLF).append(query).append(CRLF).flush();
            //writer.append(CRLF).append(userID).append(CRLF);
            //writer.append(CRLF).append(targetUserID).append(CRLF);
            //writer.append("action=message_create").append(CRLF).flush();
            

            /*
            // Send text file.
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"userfile\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
            writer.append(CRLF).flush();
            Files.copy(textFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
            */
            
            
            // Send binary file.
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"userfile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
            //writer.append("Content-Disposition: form-data; name=binaryfile; filename=" + binaryFile.getName() + "").append(CRLF);
            writer.append("Content-Type: " + HttpURLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(binaryFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
            
            
            // End of multipart/form-data.
            writer.append("--" + boundary + "--").append(CRLF).flush();
            
            InputStream is = conn.getInputStream();
            response += IOUtils.toString(is);

            is.close();
            
            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            serverResponseMessage = conn.getResponseMessage();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return response + "\n\n" + serverResponseCode + " = " + serverResponseMessage + "\n";
    }
}
