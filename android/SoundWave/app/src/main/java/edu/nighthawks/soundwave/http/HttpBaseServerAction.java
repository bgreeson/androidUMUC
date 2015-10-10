package edu.nighthawks.soundwave.http;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Set;

import edu.nighthawks.soundwave.app.SoundWaveApplication;
import edu.nighthawks.soundwave.interfaces.IGetUserInfo;

/**
 * Created by joe.keefe on 9/27/2015.
 *
 * This class is a base class for SoundWave HTTP server transactions
 *
 * Sub classes will override the exectute action and supply specific customization where needed
 */
public abstract class HttpBaseServerAction extends Thread implements IGetUserInfo
{
    private String mUrl;
    private String mAction;
    private Hashtable<String, String> variables = new Hashtable<String, String>();

    private int mHttpResponseCode;
    private String mHttpResponseMessage;
    private String mRawResponseBodyJson;

    // Thread
    private boolean mDone;



    public HttpBaseServerAction(String url, String action)
    {
        mUrl = url;
        mAction = action;

        mHttpResponseCode = 0;
        mHttpResponseMessage = "";
        mRawResponseBodyJson = "";
    }


    /**
     * This method executes a single HTTP server transaction
     * @return
     */
    private void execute() throws Exception
    {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;

        //URL url = new URL("http://androidsoundappproject.appspot.com/server");
        URL url = new URL(mUrl);

        conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true); // allow Inputs
        conn.setDoOutput(true); // allow Outputs
        PrintStream ps = new PrintStream(conn.getOutputStream());

        // add all name/values
        Set<String> keys = variables.keySet();
        for (String key : keys)
        {
            String name = key;
            String value = variables.get(key);
            ps.print("&" + name + "=" + value);
        }

        // Add Action
        if (mAction != null && !mAction.isEmpty())
            ps.print("&action=" + mAction);


        // Read back server response
        InputStream is = conn.getInputStream();
        String encoding = conn.getContentEncoding();
        this.mRawResponseBodyJson = IOUtils.toString(is, encoding);

        // Get HTTP return code and message
        mHttpResponseCode = conn.getResponseCode();
        mHttpResponseMessage = conn.getResponseMessage();

        // Close Streams
        is.close();
        ps.close();
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

    public boolean isDone()
    {
        return mDone;
    }

    // Add name and value pairs to be added to HTTP request
    public void addNameValuePair(String name, String value)
    {
        variables.put(name, value);
    }


    public String getUserEmail()
    {
        return SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserEmail();
    }

    public String getUserDiplayName()
    {
        return SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserName();
    }

    public int getUserId()
    {
        return SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserId();
    }

    public int getmHttpResponseCode()
    {
        return mHttpResponseCode;
    }


    public String getmHttpResponseMessage()
    {
        return mHttpResponseMessage;
    }


    public String getmRawResponseBodyJson()
    {
        return mRawResponseBodyJson;
    }

    public String getmRawResponseBodyJsonWithoutBrackets()
    {
        return mRawResponseBodyJson.substring(1, mRawResponseBodyJson.length() -1);
    }




}
