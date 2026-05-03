package com.example.lendahand;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionState {

    public static SessionState getInstance(Context context) {
        if (instance == null) {
            instance = new SessionState(context);
        }
        return instance;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void logIn(String sessionKey) {
        loggedIn = true;
        this.sessionKey = sessionKey;
        updatePrefs();
    }

    public void logOut() {
        sessionKey = "";
        loggedIn = false;
        updatePrefs();
    }
    static SessionState instance;
    SharedPreferences sharedPreferences;

    String sessionKey;
    boolean loggedIn;

    private SessionState(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences("com.example.lendahand.PREFERENCE_FILE", Context.MODE_PRIVATE);
        sessionKey = sharedPreferences.getString("sessionKey", "");
        loggedIn = !sessionKey.isEmpty();
    }

    private void updatePrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sessionKey", sessionKey);
        editor.apply();
    }

}
