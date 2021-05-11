package com.satrangolimitless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SignupUsertypes extends AppCompatActivity {
Button UserTypes,Serviceprovider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_usertypes);
        UserTypes= findViewById(R.id.UserTypes);
        Serviceprovider= findViewById(R.id.Serviceprovider);



        findViewById(R.id.UserTypes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupUsertypes.this, LandingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.Serviceprovider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupUsertypes.this, LandingActivity_Service_provider.class);
                startActivity(intent);
                finish();
            }
        });

    }
}