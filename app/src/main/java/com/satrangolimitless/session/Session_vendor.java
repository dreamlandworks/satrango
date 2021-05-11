package com.satrangolimitless.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.satrangolimitless.LoginActivity;


public class Session_vendor extends Object {

    private static final String PREF_NAME = "Rapidine_pref2";
    private static final String IS_LOGGEDIN = "isLoggedIn";
    private static final String FAV = "fav";
    private static final String Mobile = "mobile";
    private static final String Email = "email";
    private static final String UserId = "user_id";
    private static final String User_name = "user_name";
    private static final String Pro_Image = "pro_img";
    private static final String role_ = "user_role";
    private static final String lang = "lang";

    private static final String addmob = "adm";
    private static final String addname = "adn";
    private static final String addprofession_id = "adp";
    private static final String addlanguage = "adln";
    private static final String addskills = "adskl";
    private static final String addqualification = "adqlf";
    private static final String addabout = "adabt";
    private static final String adduserid = "vid";
    private static final String adddob = "dob";
    private static final String addgender = "gen";
    private static final String addaddres = "adr";
    private static final String addprhr = "prhr";
    private static final String addprday = "prday";
    private static final String addmincharge = "minchrge";
    private static final String addextracharge = "exchrge";
    private static final String Pendongjob_id = "penid";

    private static final String bookingid = "bkid";
    private static final String timer = "timer";
    private static final String onlinestatus = "onlin";




    private Context _context;
    private SharedPreferences Rapidine_pref;
    private SharedPreferences.Editor  editor;

    public Session_vendor(Context context) {
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
    public String getUser_name() {
        return Rapidine_pref.getString(User_name, "");

    }
    public void setUserId(String userId) {
        editor.putString(UserId, userId);
        this.editor.apply();
    }

    public void setPendongjob_id(String mobile ) {
        editor.putString(Pendongjob_id, mobile);

        editor.apply();
        editor.commit();
    }

    public String getPendongjob_id() {
        return Rapidine_pref.getString(Pendongjob_id, "");

    }




    public void set_role(String role) {
        editor.putString(role_, role);
        editor.apply();
        editor.commit();
    }

    public String get_role()
    {
        return Rapidine_pref.getString(role_, "");
    }


    public void set_onlinestatus(String role) {
        editor.putString(onlinestatus, role);
        editor.apply();
        editor.commit();
    }

    public String get_onlinestatus()
    {
        return Rapidine_pref.getString(onlinestatus, "");
    }





    public void set_lang(String lang) {
        editor.putString(role_, lang);
        editor.apply();
        editor.commit();
    }

    public String get_lang()
    {
        return Rapidine_pref.getString(role_, "");
    }




    public String getUserId() {
        return Rapidine_pref.getString(UserId, "");
    }




    public void setUser_name(String user_name) {
        editor.putString(User_name, user_name);
        this.editor.apply();
    }

    public void setEmail(String email) {
        editor.putString(Email, email);
        this.editor.apply();
    }

    public String getEmail() {
        return Rapidine_pref.getString(Email, "");
    }




/*
add vendor profile session values
 */
public void setadname(String email) {
    editor.putString(addname, email);
    this.editor.apply();
}

    public String getadname() {
        return Rapidine_pref.getString(addname, "");
    }


    public void setadmob(String email) {
        editor.putString(addmob, email);
        this.editor.apply();
    }

    public String getAddmob() {
        return Rapidine_pref.getString(addmob, "");
    }

    public void setAddprofession_id(String email) {
        editor.putString(addprofession_id, email);
        this.editor.apply();
    }

    public String getAddprofession_id() {
        return Rapidine_pref.getString(addprofession_id, "");
    }



    public void setAddlanguage(String email) {
        editor.putString(addlanguage, email);
        this.editor.apply();
    }

    public String getAddlanguage() {
        return Rapidine_pref.getString(addlanguage, "");
    }



    public void setAddskills(String email) {
        editor.putString(addskills, email);
        this.editor.apply();
    }

    public String getAddskills() {
        return Rapidine_pref.getString(addskills, "");
    }


    public void setAddqualification(String email) {
        editor.putString(addqualification, email);
        this.editor.apply();
    }

    public String getAddqualification() {
        return Rapidine_pref.getString(addqualification, "");
    }

    public void setAddabout(String email) {
        editor.putString(addabout, email);
        this.editor.apply();
    }

    public String getAddabout() {
        return Rapidine_pref.getString(addabout, "");
    }



    public void setAddprhr(String email) {
        editor.putString(addprhr, email);
        this.editor.apply();
    }

    public String getAddprhr() {
        return Rapidine_pref.getString(addprhr, "");
    }


    public void setAdddob(String email) {
        editor.putString(adddob, email);
        this.editor.apply();
    }

    public String getAdddob() {
        return Rapidine_pref.getString(adddob, "");
    }

    public void setAddgender(String email) {
        editor.putString(addgender, email);
        this.editor.apply();
    }

    public String getAddgender() {
        return Rapidine_pref.getString(addgender, "");
    }




    public void setAddaddres(String email) {
        editor.putString(addaddres, email);
        this.editor.apply();
    }

    public String getAddaddres() {
        return Rapidine_pref.getString(addaddres, "");
    }



    public void setAddprday(String email) {
        editor.putString(addprday, email);
        this.editor.apply();
    }

    public String getAddprday() {
        return Rapidine_pref.getString(addprday, "");
    }



    public void setAddmincharge(String email) {
        editor.putString(addmincharge, email);
        this.editor.apply();
    }

    public String getAddmincharge() {
        return Rapidine_pref.getString(addmincharge, "");
    }

    public void setAddextracharge(String email) {
        editor.putString(addextracharge, email);
        this.editor.apply();
    }

    public String getAddextracharge() {
        return Rapidine_pref.getString(addextracharge, "");
    }



    public void setAdduserid(String email) {
        editor.putString(adduserid, email);
        this.editor.apply();
    }

    public String getAdduserid() {
        return Rapidine_pref.getString(adduserid, "");
    }



    public void setTimer(String email) {
        editor.putString(timer, email);
        this.editor.apply();
    }

    public String getTimer() {
        return Rapidine_pref.getString(timer, "");
    }



    public void logout() {
        editor.clear();
        editor.apply();
        Intent showLogin = new Intent(_context, LoginActivity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(showLogin);
    }




    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return Rapidine_pref.getBoolean(IS_LOGGEDIN, false);
    }



    public void setBookingid(String email) {
        editor.putString(bookingid, email);
        this.editor.apply();
    }

    public String getBookingid() {
        return Rapidine_pref.getString(bookingid, "");
    }



}
