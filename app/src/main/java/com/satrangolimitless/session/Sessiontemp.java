package com.satrangolimitless.session;

import android.content.Context;

import android.content.SharedPreferences;



public class Sessiontemp extends Object {

    private static final String PREF_NAME = "Rapidine_pref2";
    private static final String IS_LOGGEDIN = "isLoggedIn";

    private static final String Mobile = "mobile";
    private static final String Pass = "pass";
    private static final String checkbox = "chk";

    private static final String Profileimage = "proimg";


    private Context _context;
    private SharedPreferences Rapidine_pref;
    private SharedPreferences.Editor  editor;

    public Sessiontemp(Context context) {
        this._context = context;
        Rapidine_pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = Rapidine_pref.edit();
        editor.apply();
    }




    public void setMobile(String mobile ) {
        editor.putString(Mobile, mobile);

        editor.apply();
        editor.commit();
    }

    public String getMobile() {
        return Rapidine_pref.getString(Mobile, "");

    }
  public void setPass(String mobile ) {
        editor.putString(Pass, mobile);

        editor.apply();
        editor.commit();
    }

    public String getPass() {
        return Rapidine_pref.getString(Pass, "");

    }
  public void setCheckbox(String mobile ) {
        editor.putString(checkbox, mobile);

        editor.apply();
        editor.commit();
    }

    public String getCheckbox() {
        return Rapidine_pref.getString(checkbox, "");

    }








}
