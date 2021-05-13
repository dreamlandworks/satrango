package com.satrangolimitless.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.satrangolimitless.LoginActivity;


public class Session extends Object {

    private static final String PREF_NAME = "Rapidine_pref2";
    private static final String IS_LOGGEDIN = "isLoggedIn";

    private static final String Mobile = "mobile";
    private static final String Email = "email";
    private static final String UserId = "user_id";
    private static final String User_name = "user_name";
    private static final String firebase_token_id = "tkenid";
    private static final String Profileimage = "proimg";

    private static final String role_ = "user_role";
    private static final String lattitude = "latt";
    private static final String longitude = "lang";
    private static final String keyword = "key";
    private static final String address = "adrs";
    private static final String userhome_location = "hmelc";
    private static final String userhome_lat = "hmlt";
    private static final String userhome_long = "hmlng";

    private static final String service_providr_status = "sps";
    private static final String start_locationSingleMoveBooking = "stl";
    private static final String start_locationMultiMoveBooking = "stm";
    private static final String end_locationMultiMoveBooking = "stme";
    private static final String start_locationSinglejobpost = "stmrp";
    private static final String end_locationSinglejobpost = "stmrdg";
    private static final String start_locationMultijobpost = "stmlti";
    private static final String end_locationMultijobpost = "stmltiend";
    private static final String from_time_singlejobpost = "timesinglejb";
    private static final String date_singlejobpost = "datesinglejb";
    private static final String from_time_bluecolrjobpost = "timesinglejb";
    private static final String date_bluecolrjobpost = "datesinglejb";
    private static final String timer = "timer";
    private static final String bookingid = "bkid";
    private static final String xtrachrg = "xtracghrg";


    private static final String Bookingcategory = "bkc";

    private static final String Pendongjob_id = "penid";
    private static final String Awardedsecjob_id_bids = "awbidd";

    private static final String mljobpostdate = "mdate";
    private static final String mljobposttime = "mtime";


    //    booking session variable----------------------------------------------------------
    private static final String bookvid = "bkvid";
    private static final String bookvname = "bkvn";
    private static final String bookminam = "bkmnam";
    private static final String bookrating = "bkrt";
    private static final String bookservice = "bkvs";
    private static final String bookdis = "bkdis";
    private static final String bookmaxam = "bkmaxam";
    private static final String bookdate = "bkdt";
    private static final String bookdatebluecollar = "bkbcdt";
    private static final String bookdatemultimove = "bkmlmdt";
    private static final String bookservicecat = "svcat";


    private Context _context;
    private SharedPreferences Rapidine_pref;
    private SharedPreferences.Editor editor;

    public Session(Context context) {
        this._context = context;
        Rapidine_pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = Rapidine_pref.edit();
        editor.apply();
    }


    public void setMobile(String mobile) {
        editor.putString(Mobile, mobile);

        editor.apply();
        editor.commit();
    }

    public String getMobile() {
        return Rapidine_pref.getString(Mobile, "");

    }


    public void setStart_locationsinglemovebooking(String mobile) {
        editor.putString(start_locationSingleMoveBooking, mobile);

        editor.apply();
        editor.commit();
    }

    public String getStart_locationsinglemovebooking() {
        return Rapidine_pref.getString(start_locationSingleMoveBooking, "");

    }


    public void setStart_locationmultimovebookingstart(String mobile) {
        editor.putString(start_locationMultiMoveBooking, mobile);
        editor.apply();
        editor.commit();
    }

    public String getStart_locationmultimovebookingstart() {
        return Rapidine_pref.getString(start_locationMultiMoveBooking, "");

    }


    public void setStart_locationmultimovebookingend(String mobile) {
        editor.putString(end_locationMultiMoveBooking, mobile);

        editor.apply();
        editor.commit();
    }

    public String getStart_locationmultimovebookingend() {
        return Rapidine_pref.getString(end_locationMultiMoveBooking, "");

    }


    public void setStart_locationSinglejobpost(String mobile) {
        editor.putString(start_locationSinglejobpost, mobile);

        editor.apply();
        editor.commit();
    }

    public String getStart_locationSinglejobpost() {
        return Rapidine_pref.getString(start_locationSinglejobpost, "");

    }


    public void setFrom_time_singlejobpost(String mobile) {
        editor.putString(from_time_singlejobpost, mobile);

        editor.apply();
        editor.commit();
    }

    public String getFrom_time_singlejobpost() {
        return Rapidine_pref.getString(from_time_singlejobpost, "");

    }


    public void setDate_singlejobpost(String mobile) {
        editor.putString(date_singlejobpost, mobile);

        editor.apply();
        editor.commit();
    }

    public String getDate_singlejobpost() {
        return Rapidine_pref.getString(date_singlejobpost, "");

    }

    public void setDate_bluecolrjobpost(String mobile) {
        editor.putString(date_bluecolrjobpost, mobile);

        editor.apply();
        editor.commit();
    }

    public String getDate_bluecolrjobpost() {
        return Rapidine_pref.getString(date_bluecolrjobpost, "");

    }


    public void setFrom_time_bluecolrjobpost(String mobile) {
        editor.putString(from_time_bluecolrjobpost, mobile);

        editor.apply();
        editor.commit();
    }

    public String getFrom_time_bluecolrjobpost() {
        return Rapidine_pref.getString(from_time_bluecolrjobpost, "");

    }


    public void setStart_locationMultiMovejobpost(String mobile) {
        editor.putString(start_locationMultijobpost, mobile);

        editor.apply();
        editor.commit();
    }

    public String getStart_locationMultiMovejobpost() {
        return Rapidine_pref.getString(start_locationMultijobpost, "");

    }

    public void setTimer(String mobile) {
        editor.putString(timer, mobile);

        editor.apply();
        editor.commit();
    }

    public String getTimer() {
        return Rapidine_pref.getString(timer, "");

    }


    public void setBookingid(String mobile) {
        editor.putString(bookingid, mobile);

        editor.apply();
        editor.commit();
    }

    public String getBookingid() {
        return Rapidine_pref.getString(bookingid, "");

    }

    public void setXtrachrg(String mobile) {
        editor.putString(xtrachrg, mobile);

        editor.apply();
        editor.commit();
    }

    public String getXtrachrg() {
        return Rapidine_pref.getString(xtrachrg, "");

    }


    public void setBookingcategory(String mobile) {
        editor.putString(Bookingcategory, mobile);

        editor.apply();
        editor.commit();
    }

    public String getBookingcategory() {
        return Rapidine_pref.getString(Bookingcategory, "");

    }


    public void setPendongjob_id(String mobile) {
        editor.putString(Pendongjob_id, mobile);

        editor.apply();
        editor.commit();
    }

    public String getPendongjob_id() {
        return Rapidine_pref.getString(Pendongjob_id, "");

    }

    public void setAwardedsecjob_id_bids(String mobile) {
        editor.putString(Awardedsecjob_id_bids, mobile);

        editor.apply();
        editor.commit();
    }

    public String getAwardedsecjob_id_bids() {
        return Rapidine_pref.getString(Awardedsecjob_id_bids, "");

    }


    public String getUser_name() {
        return Rapidine_pref.getString(User_name, "");

    }

    public void setUserId(String userId) {
        editor.putString(UserId, userId);
        this.editor.apply();
    }

    public void set_role(String role) {
        editor.putString(role_, role);
        editor.apply();
        editor.commit();
    }

    public String get_role() {
        return Rapidine_pref.getString(role_, "");
    }


    public void set_lang(String lang) {
        editor.putString(role_, lang);
        editor.apply();
        editor.commit();
    }

    public String get_lang() {
        return Rapidine_pref.getString(role_, "");
    }


    public void setService_providr_status(String lang) {
        editor.putString(service_providr_status, lang);
        editor.apply();
        editor.commit();
    }

    public String getService_providr_status() {
        return Rapidine_pref.getString(service_providr_status, "");
    }


    public void setKeyword(String lang) {
        editor.putString(keyword, lang);
        editor.apply();
        editor.commit();
    }

    public String getKeyword() {
        return Rapidine_pref.getString(keyword, "");
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


    public void setLattitude(String lang) {
        editor.putString(lattitude, lang);
        editor.apply();
        editor.commit();
    }

    public String getLattitude() {
        return Rapidine_pref.getString(lattitude, "");
    }


    public void setLongitude(String lang) {
        editor.putString(longitude, lang);
        editor.apply();
        editor.commit();
    }

    public String getLongitude() {
        return Rapidine_pref.getString(longitude, "");
    }


    public void setAddress(String lang) {
        editor.putString(address, lang);
        editor.apply();
        editor.commit();
    }

    public String getAddress() {
        return Rapidine_pref.getString(address, "");
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


//booking session

    public void setBookvid(String bookvname) {
        editor.putString(bookvid, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookvid() {
        return Rapidine_pref.getString(bookvid, "");

    }

    public void setBookvname(String tees) {
        editor.putString(bookvname, tees);

        editor.apply();
        editor.commit();
    }

    public String getBookvname() {
        return Rapidine_pref.getString(bookvname, "");

    }

    public void setBookminam(String bookvname) {
        editor.putString(bookminam, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookminam() {
        return Rapidine_pref.getString(bookminam, "");

    }


    public void setBookmaxam(String bookvname) {
        editor.putString(bookmaxam, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookmaxam() {
        return Rapidine_pref.getString(bookmaxam, "");

    }


    public void setBookcat(String bookvname) {
        editor.putString(bookservicecat, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookcat() {
        return Rapidine_pref.getString(bookservicecat, "");

    }


    public void setBookdateSinglemoveBoking(String bookvname) {
        editor.putString(bookdate, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookdateSinglMoveBoking() {
        return Rapidine_pref.getString(bookdate, "");

    }


    public void setBookdateBlueCollarBoking(String bookvname) {
        editor.putString(bookdatebluecollar, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookdateBlueCollarBoking() {
        return Rapidine_pref.getString(bookdatebluecollar, "");

    }

    public void setBookdateMultimoveBoking(String bookvname) {
        editor.putString(bookdatemultimove, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookdateMultimoveBoking() {
        return Rapidine_pref.getString(bookdatemultimove, "");

    }


    public void setBookrating(String bookvname) {
        editor.putString(bookrating, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookrating() {
        return Rapidine_pref.getString(bookrating, "");

    }


    public void setMljobpostdate(String mobile) {
        editor.putString(mljobpostdate, mobile);

        editor.apply();
        editor.commit();
    }

    public String getMljobpostdate() {
        return Rapidine_pref.getString(mljobpostdate, "");

    }

    public void setMljobposttime(String mobile) {
        editor.putString(mljobposttime, mobile);

        editor.apply();
        editor.commit();
    }

    public String getMljobposttime() {
        return Rapidine_pref.getString(mljobposttime, "");

    }


    public void setUserhome_location(String mobile) {
        editor.putString(userhome_location, mobile);

        editor.apply();
        editor.commit();
    }

    public String getUserhome_location() {
        return Rapidine_pref.getString(userhome_location, "");

    }

    public void setUserhome_lat(String mobile) {
        editor.putString(userhome_lat, mobile);

        editor.apply();
        editor.commit();
    }

    public String getUserhome_lat() {
        return Rapidine_pref.getString(userhome_lat, "");

    }


    public void setUserhome_long(String mobile) {
        editor.putString(userhome_long, mobile);

        editor.apply();
        editor.commit();
    }

    public String getUserhome_long() {
        return Rapidine_pref.getString(userhome_long, "");

    }


    public void setBookservice(String bookvname) {
        editor.putString(bookservice, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookservice() {
        return Rapidine_pref.getString(bookservice, "");

    }


    public void setBookdis(String bookvname) {
        editor.putString(bookdis, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getBookdis() {
        return Rapidine_pref.getString(bookdis, "");

    }


    public void setProfileimage(String bookvname) {
        editor.putString(Profileimage, bookvname);

        editor.apply();
        editor.commit();
    }

    public String getProfileimage() {
        return Rapidine_pref.getString(Profileimage, "");

    }


    public void setFirebase_token_id(String lang) {
        editor.putString(firebase_token_id, lang);
        editor.apply();
        editor.commit();
    }

    public String getFirebase_token_id() {
        return Rapidine_pref.getString(firebase_token_id, "");
    }

}
