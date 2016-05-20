package com.sourcebits.fitfind.com.sourcebits.fitfind.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vaishaliarora on 20/05/16.
 */
public class UIPreference {
    private static final String MY_PREF = "user_details";

    private static SharedPreferences mPreference;

    public static SharedPreferences getSharedPref(Context ctx){
        if(mPreference == null) {
            mPreference = ctx.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        }
        return mPreference;
    }

}
