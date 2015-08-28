package edu.umuc.soundwave.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;


public class PostFile 
{
  public static void uploadFile1(String fileName) throws Exception 
  {
    String url = "http://98.231.242.168:8080/upload.php";
    File file = new File(fileName);
    
    try 
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(file), -1);
        reqEntity.setContentType("binary/octet-stream");
        reqEntity.setChunked(true); // Send in multiple parts if needed
        httppost.setEntity(reqEntity);
        HttpResponse response = httpclient.execute(httppost);
        Log.i("TAG", response.getLocale().toString());
        //Do something with response...

    } 
    catch (Exception e) 
    {
        // show error
    }
  }
  
  public static boolean  uploadFile(String fileName)
  {
	    String urlstr = "http://98.231.242.168:8080/upload.php";
	    
	  HttpURLConnection conn = null;
	    DataOutputStream os = null;
	    String lineEnd = "\r\n";
	    String twoHyphens = "--";
	    String boundary =  "*****";
	    int bytesRead, bytesAvailable, bufferSize, bytesUploaded = 0;
	    byte[] buffer;
	    int maxBufferSize = 2*1024*1024;

	    try
	    {
	        FileInputStream fis = new FileInputStream(new File(fileName) );

	        URL url = new URL(urlstr);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setChunkedStreamingMode(maxBufferSize);

	        // POST settings.
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.setUseCaches(false);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Connection", "Keep-Alive");
	        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);

	        os = new DataOutputStream(conn.getOutputStream());
	        os.writeBytes(twoHyphens + boundary + lineEnd);
	        os.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + "file.amr" +"\"" + lineEnd);
	        os.writeBytes(lineEnd);

	        bytesAvailable = fis.available();
	        System.out.println("available: " + String.valueOf(bytesAvailable));
	        bufferSize = Math.min(bytesAvailable, maxBufferSize);
	        buffer = new byte[bufferSize];

	        int prog = 0;
	        bytesRead = fis.read(buffer, 0, bufferSize);
	        bytesUploaded += bytesRead;
	        while (bytesRead > 0)
	        {
	            prog = bytesUploaded/bytesAvailable;
	            os.write(buffer, 0, bufferSize);
	            bytesAvailable = fis.available();
	            bufferSize = Math.min(bytesAvailable, maxBufferSize);
	            buffer = new byte[bufferSize];
	            bytesRead = fis.read(buffer, 0, bufferSize);
	            bytesUploaded += bytesRead;
	        }
	        System.out.println("uploaded: "+String.valueOf(bytesUploaded));
	        os.writeBytes(lineEnd);
	        os.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

	        // Responses from the server (code and message)
	        conn.setConnectTimeout(2000); // allow 2 seconds timeout.
	        int rcode = conn.getResponseCode();
	        if (rcode == 200)
	        { 
	        	
	        }
	        else 
	        { // fail
	        }
	        //String rmsg = conn.getResponseMessage();
	        fis.close();
	        os.flush();
	        os.close();
	        return rcode == 200;
	    }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	        return false;
	    }
  }
}