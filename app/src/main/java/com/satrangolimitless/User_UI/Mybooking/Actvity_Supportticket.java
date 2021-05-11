package com.satrangolimitless.User_UI.Mybooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Settings.SettingsFragment;
import com.satrangolimitless.session.Session;

public class Actvity_Supportticket extends AppCompatActivity {
    Session session;
    Button btnsubmit, btnback, idbtn1, idbtn2, idbtn3, idbtn4, idbtn5;
    ImageView iv_back;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue rQueue;
    Fragment fragment;
    String reson,message="";
    EditText et_suggestionmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actvity__supportticket);

        btnsubmit = findViewById(R.id.btnsubmit);
        btnback =  findViewById(R.id.btnback);
        iv_back =  findViewById(R.id.iv_back);
        et_suggestionmsg =  findViewById(R.id.et_suggestionmsg);
        idbtn1 =  findViewById(R.id.idbtn1);
        idbtn2 =  findViewById(R.id.idbtn2);
        idbtn3 =  findViewById(R.id.idbtn3);
        idbtn4 =  findViewById(R.id.idbtn4);
        idbtn5 =  findViewById(R.id.idbtn5);



        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
             }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=et_suggestionmsg.getText().toString();
//                CallComplaintsApi();
            }
        });


        idbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn1.getText().toString();
                idbtn1.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn2.setBackgroundResource(R.color.white);

                idbtn3.setBackgroundResource(R.color.white);

                idbtn4.setBackgroundResource(R.color.white);
                idbtn5.setBackgroundResource(R.color.white);

            }
        });
        idbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn2.getText().toString();
                idbtn2.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn1.setBackgroundResource(R.color.white);

                idbtn3.setBackgroundResource(R.color.white);

                idbtn4.setBackgroundResource(R.color.white);
                idbtn5.setBackgroundResource(R.color.white);

            }
        });
        idbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn3.getText().toString();
                idbtn3.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn1.setBackgroundResource(R.color.white);

                idbtn2.setBackgroundResource(R.color.white);

                idbtn4.setBackgroundResource(R.color.white);
                idbtn5.setBackgroundResource(R.color.white);
            }
        });
        idbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn4.getText().toString();
                idbtn4.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn1.setBackgroundResource(R.color.white);

                idbtn3.setBackgroundResource(R.color.white);

                idbtn2.setBackgroundResource(R.color.white);
                idbtn5.setBackgroundResource(R.color.white);
            }
        });
        idbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn5.getText().toString();
                idbtn5.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn1.setBackgroundResource(R.color.white);

                idbtn3.setBackgroundResource(R.color.white);

                idbtn4.setBackgroundResource(R.color.white);
                idbtn2.setBackgroundResource(R.color.white);
            }
        });
    }
}