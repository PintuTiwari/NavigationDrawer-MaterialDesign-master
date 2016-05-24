package br.liveo.ndrawer.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import br.liveo.ndrawer.ui.activity.Login;

/**
 * Created by rupa.dwivedi on 27-04-2016.
 */
public class UserSessionManager
{
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    int PRIVATE_MODE = 0;
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public UserSessionManager(Context paramContext)
    {
        this._context = paramContext;
        this.pref = this._context.getSharedPreferences("AndroidPref", this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void createUserLoginSession(String paramString1, String paramString2,String paramString3)
    {
        this.editor.putBoolean("IsUserLoggedIn", true);
        this.editor.putString("name", paramString1);
        this.editor.putString("email", paramString2);
        this.editor.putString("email1", paramString3);
        this.editor.commit();
    }

    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> localHashMap = new HashMap<String, String>();
        localHashMap.put("name", this.pref.getString("name", null));
        localHashMap.put("email", this.pref.getString("email", null));
        localHashMap.put("email1", this.pref.getString("email1", null));
        return localHashMap;
    }

    public boolean isUserLoggedIn()
    {
        return this.pref.getBoolean("IsUserLoggedIn", false);
    }

    public void logoutUser()
    {
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, Login.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

}