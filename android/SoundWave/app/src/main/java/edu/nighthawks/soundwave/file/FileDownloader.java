package edu.nighthawks.soundwave.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.nighthawks.soundwave.app.SoundWaveApplication;
import edu.nighthawks.soundwave.interfaces.IGetUserInfo;

public class FileDownloader extends Thread implements IGetUserInfo
{
    private static final int BUFFER_SIZE = 4096;

    private boolean mDone;
    private String mSenderUserID;
    private String mUserID;
    private String mFileName;
    private String mFilePath;

    public FileDownloader(String myContactId, String filePathIn, String fileNameServer)
    {
        mDone = false;

        mSenderUserID = Integer.toString(getUserId());
        mUserID = myContactId;
        mFileName = fileNameServer;
        mFilePath = filePathIn;
    }

    @Override
    public void run()
    {
        try
        {
            this.execute();
            mDone = true;
        }
        catch (Exception e)
        {
            mDone = true;
        }

    }



        /**
         * Downloads a file from a URL
         * @throws IOException
         */
    public void execute() throws IOException
    {
        String fileURL = makeDownloadUrlName();

        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = mFilePath;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

        } else
        {
            throw new IOException("Failed to download file: " + responseCode);
        }
        httpConn.disconnect();
    }

    private String makeDownloadUrlName()
    {
        // start with base path
        String urlFilePath = "http://androidsoundappproject.appspot.com.storage.googleapis.com/message/";
        urlFilePath += mUserID;
        urlFilePath += "/" + this.mFileName;

        return urlFilePath;
    }

    @Override
    public String getUserEmail()
    {
        return SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserEmail();
    }

    @Override
    public String getUserDiplayName()
    {
        return SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserName();
    }

    @Override
    public int getUserId()
    {
        return SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserId();
    }
}
