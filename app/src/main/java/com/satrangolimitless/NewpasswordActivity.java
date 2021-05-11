package com.satrangolimitless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewpasswordActivity extends AppCompatActivity {
 Button Set_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);

        Set_Password= findViewById(R.id.Set_Password);


        findViewById(R.id.Set_Password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewpasswordActivity.this, LandingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}