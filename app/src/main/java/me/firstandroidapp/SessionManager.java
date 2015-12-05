package me.firstandroidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInstaller;

/**
 * Created by PJ on 12/4/2015.
 */
public class SessionManager {
    // shared preferences file name
    private static final String PREF_NAME = "AndroidSources";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    //shared prefs
    SharedPreferences pref;
    Editor editor;
    Context _context;

    // Shared preference mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        //commit changed
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
