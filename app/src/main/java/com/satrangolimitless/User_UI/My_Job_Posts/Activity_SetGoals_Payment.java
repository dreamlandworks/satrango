package com.satrangolimitless.User_UI.My_Job_Posts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.satrangolimitless.R;

public class Activity_SetGoals_Payment extends AppCompatActivity {
    Button btnnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set_goal);
        btnnext=findViewById(R.id.btnnext);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), Activity_PaymentAwardedbid.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
