package com.satrangolimitless;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Activity_login_type extends AppCompatActivity {
    TextView sign_up;
    Button UserTypes,Serviceprovider;
    TextView forgot_password;
    String phone,password;
    EditText edt_mobile,edt_password;
    Session session;
    Session_vendor session_vendor;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup_usertypes);
            session=new Session(getApplicationContext());
            session_vendor=new Session_vendor(getApplicationContext());
            sign_up= findViewById(R.id.sign_up);
            edt_mobile= findViewById(R.id.edt_mobile);
            edt_password= findViewById(R.id.edt_password);
            UserTypes= findViewById(R.id.UserTypes);
            Serviceprovider= findViewById(R.id.Serviceprovider);


            UserTypes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkAndRequestPermissions()) {

                        Intent intent = new Intent(Activity_login_type.this, LandingActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            });

            Serviceprovider.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (checkAndRequestPermissions()) {

                session_vendor.setUserId(session.getUserId());
                Intent intent = new Intent(Activity_login_type.this, LandingActivity_Service_provider.class);
                startActivity(intent);
                finish();

            }

        }
    });




        }



    private boolean checkAndRequestPermissions() {
         int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarselocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();


        if (location != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (coarselocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), 101);
            return false;
        } else {
        }

        return true;
    }

}