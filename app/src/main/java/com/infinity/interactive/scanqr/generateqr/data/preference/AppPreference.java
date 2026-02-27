package com.infinity.interactive.scanqr.generateqr.data.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Bezruk on 16/10/18.
 */
public class AppPreference {

    // declare context
    private static Context mContext;

    // singleton
    private static AppPreference appPreference = null;

    // common
    private final SharedPreferences sharedPreferences;

    private final SharedPreferences.Editor editor;

    public static AppPreference getInstance(Context context) {
        if(appPreference == null) {
            mContext = context;
            appPreference = new AppPreference();
        }
        return appPreference;
    }
    private AppPreference() {
        sharedPreferences = mContext.getSharedPreferences(PrefKey.APP_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setString(String key, String value) {
        editor.putString(key , value);
        editor.commit();
    }
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
    public Boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void setInteger(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void setLong(String key, Long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public Long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public int getIntegerDialogue(String key,int val) {
        return sharedPreferences.getInt(key, val);
    }

    public int getInteger(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void setStringArray(String key, ArrayList<String> values) {

        Log.d("checkKey",key+" key");
        if (values != null && !values.isEmpty()) {
            StringBuilder value = new StringBuilder();
            for (String str : values) {
                if(str.contains("&")){
                    str=str.replace("‼","!!");
                }
                if(value.length() == 0) {
                    value = new StringBuilder(str);
                } else {
                    value.append("‼").append(str);
                }
            }
            setString(key, value.toString());
        } else if(values == null) {
            setString(key, null);
        }
    }

    public ArrayList<String> getStringArray(String key) {
        ArrayList<String> arrayList = new ArrayList<>();
        String value = getString(key);
        if (value != null) {
            arrayList = new ArrayList<>(Arrays.asList(value.split("‼")));
        }
        return arrayList;
    }


}
