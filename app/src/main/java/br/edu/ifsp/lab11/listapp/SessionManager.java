package br.edu.ifsp.lab11.listapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by r0xxFFFF-PC on 03/06/2017.
 *
 * This class will be edited conforming the application grow
 * TODO implementar singleton nela
 */
public class SessionManager {

    private static final String SESSION_PREFERENCES = "SessionPreferences";

    public static final String IS_LOGGED = "IsLoggedIn";

    public static final String KEY_NAME = "name";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_OBJECTID = "objectID";

    private Context mContext = null;

    private SharedPreferences mPrefs = null;

    private SharedPreferences.Editor mEditor = null;

    public SessionManager(Context context) {

        this.mContext = context;
        this.mPrefs = this.mContext.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE);
        this.mEditor = this.mPrefs.edit();
    }

    public void createLoginSession(String name, String email, String objectID){
        //TODO tratar as entradas e retornar boolean true se ok
        this.mEditor.putString(KEY_NAME, name);
        this.mEditor.putString(KEY_EMAIL, email);
        this.mEditor.putString(KEY_OBJECTID, objectID);
        this.mEditor.putBoolean(IS_LOGGED, true);

        this.mEditor.commit();
    }

    public boolean isLoggedIn(){

        return this.mPrefs.getBoolean(IS_LOGGED, false);
    }

    public String getUserName(){

        return this.mPrefs.getString(KEY_NAME, null);
    }

    public String getUserEmail(){

        return this.mPrefs.getString(KEY_EMAIL, null);
    }

    public String getUserID(){

        return this.mPrefs.getString(KEY_OBJECTID, null);
    }

    public void logoutUser(){

        this.mEditor.clear();
        this.mEditor.commit();
    }
}
