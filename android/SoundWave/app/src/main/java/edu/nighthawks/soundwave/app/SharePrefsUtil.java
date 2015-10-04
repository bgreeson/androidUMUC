package edu.nighthawks.soundwave.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by joe.keefe on 10/3/2015.
 */
public class SharePrefsUtil
{
    public static String USER_ID = "USER_ID";
    public static String USER_NAME = "USER_NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String CONTACTS = "CONTACTS";

    public static String PREF_FILE = "SoundWaveConfig";

    // get int

    // get string

    // set int

    //set string

    public static void setString(Context context, String key, String value)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE, context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREF_FILE, context.MODE_PRIVATE);
        String value = prefs.getString(key, null);
        return value;
    }

    public static void setInt(Context context, String key, int value)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE, context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREF_FILE, context.MODE_PRIVATE);
        int value = prefs.getInt(key, 0);
        return value;
    }
}
