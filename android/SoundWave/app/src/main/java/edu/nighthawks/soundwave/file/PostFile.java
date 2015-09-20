package edu.nighthawks.soundwave.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class PostFile
{


  public static boolean  uploadFile(String fileName)
  {


      String urlstr = "http://androidsoundappproject.appspot.com/server";
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