package com.satrangolimitless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.satrangolimitless.Utils.Config;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.Vendor_UI.Activity_vendor_accept_newbookingrequest;
import com.satrangolimitless.Vendor_UI.vendor_profile.VendorProfilFourActivity;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.session.PrefrenceManager;
import com.satrangolimitless.session.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    ImageView imageView;
    private GPSTracker gps;
    private Double lat;
    private Double lng;
    public static String regId;
    PrefrenceManager prefrenceManager;
    Context mContext;
    Session session1;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String languagename = Locale.getDefault().getDisplayLanguage();
        String country = Locale.getDefault().getCountry();
        mContext = SplashActivity.this;
        session1=new Session(SplashActivity.this);
        printHashKey();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                prefrenceManager=new PrefrenceManager(SplashActivity.this);
                prefrenceManager.setTokenId(mContext,newToken);
                putFirebaseRegId(newToken);
                System.out.println("firebase token   ----     "+newToken);
                setPreference(mContext,"regId",newToken);
                getPreference(mContext, "regId");
                session1.setFirebase_token_id(newToken);
                displayFirebaseRegId();
            }
        });





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (session1.isLoggedIn())
                {
                    Intent i=new Intent(SplashActivity.this, Activity_login_type.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashActivity.this, WelcomeSliderActivity.class);
                    startActivity(i);
                    finish();
                }


            }

        }, SPLASH_SCREEN_TIME_OUT);


    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        Log.e("check splash", " : " + regId);
    }

    private void putFirebaseRegId(String newToken) {
        Object getMonth;

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("regId", newToken);
        editor.commit();
    }

    public static boolean setPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        String value=settings.getString(key,"default value");
        Log.e("get fb key ++ ",value);

        return settings.getString(key, "defaultValue");
    }



    private void printHashKey() {
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(
                    "com.satrangolimitless",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {

            Log.e("error hash key ", "" + e);
        }


    }

}