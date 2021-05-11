package com.satrangolimitless.User_UI.Mybooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.satrangolimitless.R;

public class Activity_Mark_complete_invoicesummary extends AppCompatActivity {

    Button btnback, buttonSubmit;
    TextView txtrating, txtname, txtdate, txtbookingid, txtbilamount;
    String amount,
            paid_amount,
            extra_charge,
            balance;
String date,bookingid,id,name,vendorid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityuser_mark_complete_invoice);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        btnback = findViewById(R.id.btnback);
        txtrating = findViewById(R.id.txtrating);
        txtname = findViewById(R.id.txtname);
        txtdate = findViewById(R.id.txtdate);
        txtbookingid = findViewById(R.id.txtbookingid);
        txtbilamount = findViewById(R.id.txtbilamount);

        if (getIntent() != null) {
            amount = getIntent().getStringExtra("amount");
            paid_amount = getIntent().getStringExtra("paid_amount");
            extra_charge = getIntent().getStringExtra("extra_charge");
            balance = getIntent().getStringExtra("balance");

            date = getIntent().getStringExtra("date");
            bookingid = getIntent().getStringExtra("bookingid");
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            vendorid = getIntent().getStringExtra("vendorid");

        }

        //   txtrating.setText();
        txtname.setText(name);
        txtdate.setText(date);
        txtbookingid.setText(bookingid);
        txtbilamount.setText(amount);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JobCOmpleteDiglog.class);
                intent.putExtra("id", id);
                intent.putExtra("vendorid", vendorid);
                startActivity(intent);
            }
        });


    }
}
