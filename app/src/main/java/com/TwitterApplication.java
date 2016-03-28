package com;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;

import com.util.Constant;


public class TwitterApplication extends Application {

    public static TwitterApplication sInstance;
    SharedPreferences preferences;

    public static TwitterApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = TwitterApplication.this;
        preferences = PreferenceManager.getDefaultSharedPreferences(sInstance);
    }

    public void saveTokens(String token, String tokenSecret) {
        SharedPreferences.Editor mEditor = preferences.edit();
        mEditor.putString(Constant.KEY_TOKEN, token);
        mEditor.putString(Constant.KEY_TOKEN_SECRET, tokenSecret);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(mEditor);
    }

    public String[] getTokens() {
        String[] tokens = new String[2];
        tokens[0] = preferences.getString(Constant.KEY_TOKEN, null);
        tokens[1] = preferences.getString(Constant.KEY_TOKEN_SECRET, null);
        return tokens;
    }

    @Override
    public void onTerminate() {
        sInstance = null;
        super.onTerminate();
    }
}
