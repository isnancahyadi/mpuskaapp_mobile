package com.arcmedtek.mpuskaapp_mobile.config;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences _sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    public String id;

    private static final String PREF_NAME = "LOGIN";
    private static final String STATUS = "IS_LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String PRIVILEGE = "PRIVILEGE";

    public SessionManager(Context context) {
        this.context = context;
        _sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = _sharedPreferences.edit();
        editor.apply();
    }

    public void createSession(String username, String privilege) {
        editor.putBoolean(STATUS, true);
        editor.putString(USERNAME, username).commit();
        editor.putString(PRIVILEGE, privilege).commit();
        id = privilege;
    }

    public String getSession() {
        return _sharedPreferences.getString(PRIVILEGE, null);
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(USERNAME, _sharedPreferences.getString(USERNAME, null));
        user.put(PRIVILEGE, _sharedPreferences.getString(PRIVILEGE, null));
        return user;
    }

    public void removeSession() {
        editor.putString(PRIVILEGE, null).commit();
    }
}
