package com.satrangolimitless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 4000;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        String languagename = Locale.getDefault().getDisplayLanguage();
        String country = Locale.getDefault().getCountry();


        //  Toast.makeText(this, " "+languagename, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(ConfirmationActivity.this, LandingActivity.class);
                startActivity(i);
                finish();
            }

        }, SPLASH_SCREEN_TIME_OUT);

    }
}