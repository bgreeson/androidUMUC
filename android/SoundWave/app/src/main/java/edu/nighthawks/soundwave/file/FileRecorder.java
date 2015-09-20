package edu.nighthawks.soundwave.file;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class FileRecorder
{
    private boolean bRecording = false;
    private File recordedFile;
    private String tempFileName = "temp";
    private static final String LOG_TAG = "AudioRecordTest";
    //private static String mFileName = null;
    //private MediaPlayer   mPlayer = null;	
    private MediaRecorder mRecorder = null;

    public String getRecordedFileName()
    {
        return tempFileName;

    }


    public void startRecording()
    {
        recordedFile = new File(tempFileName);

        tempFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        tempFileName += "/message.amr";

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setOutputFile(tempFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        boolean bPrepared = false;

        try
        {
            mRecorder.prepare();
            bPrepared = true;
        }
        catch (IOException e)
        {
            bPrepared = false;
            Log.e(LOG_TAG, "prepare() failed");
        }

        if (bPrepared)
        {
            mRecorder.start();
            bRecording = true;
        } else
        {
            mRecorder.release();
            mRecorder = null;
            bRecording = false;
        }

    }

    public void stopRecording()
    {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        bRecording = false;
    }

    public boolean isRecording()
    {
        return bRecording;
    }

    public File getFile()
    {
        return recordedFile;
    }

    //    private void onRecord(boolean start) {
    //        if (start) {
    //            startRecording();
    //        } else {
    //            stopRecording();
    //        }
    //    }

    //    private void onPlay(boolean start) {
    //        if (start) {
    //            startPlaying();
    //        } else {
    //            stopPlaying();
    //        }
    //    }
    //
    //    private void startPlaying() {
    //        mPlayer = new MediaPlayer();
    //        try {
    //            mPlayer.setDataSource(mFileName);
    //            mPlayer.prepare();
    //            mPlayer.start();
    //        } catch (IOException e) {
    //            Log.e(LOG_TAG, "prepare() failed");
    //        }
    //    }
    //
    //    private void stopPlaying() {
    //        mPlayer.release();
    //        mPlayer = null;
    //    }


}
