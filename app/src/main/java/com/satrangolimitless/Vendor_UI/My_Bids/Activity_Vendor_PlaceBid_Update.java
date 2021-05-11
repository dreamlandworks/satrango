package com.satrangolimitless.Vendor_UI.My_Bids;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.R;
import com.satrangolimitless.model.SubjectData;
import com.satrangolimitless.session.Session_vendor;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity_Vendor_PlaceBid_Update extends AppCompatActivity {



    Session_vendor session_vendor;
    TextView txtaverage, txtdescrip, txtestimatetime, txttime, txtdate, txtbidsavailable;
    EditText edtvendorproposal, edtestimatetime, edt_bidamount;
    Button btnsubmit, btncancel;
    CardView cdopen, cdsealed, cdhours, cddays;
    String est_time, time, date, avgeragebid, id, jobid, desx;
    String finaldate;
//    Add Attachment
    String vendrdesc, newestimatetime, bidamount, submission_type,bid_amount,proposal;
    ImageView img_add;
    TextView txthours, txtday, txtsealed, txtopen;
    RecyclerView list;
    ArrayList<SubjectData> imagearrayList = new ArrayList<SubjectData>();
    File destination;
    Uri pickedImage;
    String userChoosenTask;
    String filenew1, lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_placebid);

        session_vendor = new Session_vendor(getApplicationContext());
        txtaverage = findViewById(R.id.txtaverage);
        txtdescrip = findViewById(R.id.txtdescrip);
        txtestimatetime = findViewById(R.id.txtestimatetime);
        txttime = findViewById(R.id.txttime);
        txtdate = findViewById(R.id.txtdate);
        txtbidsavailable = findViewById(R.id.txtbidsavailable);
        edtvendorproposal = findViewById(R.id.edtvendorproposal);
        edtestimatetime = findViewById(R.id.edtestimatetime);
        edt_bidamount = findViewById(R.id.edt_bidamount);
        img_add = findViewById(R.id.img_add);
        list = findViewById(R.id.list);

        txtopen = findViewById(R.id.txtopen);
        txtsealed = findViewById(R.id.txtsealed);
        txtday = findViewById(R.id.txtday);
        txthours = findViewById(R.id.txthours);

        cdhours = findViewById(R.id.cdhours);
        cddays = findViewById(R.id.cddays);
        cdopen = findViewById(R.id.cdopen);
        cdsealed = findViewById(R.id.cdsealed);

        btnsubmit = findViewById(R.id.btnsubmit);
        btncancel = findViewById(R.id.btncancel);


        if (getIntent() != null) {

            jobid = getIntent().getStringExtra("jobid");
            id = getIntent().getStringExtra("id");
            avgeragebid = getIntent().getStringExtra("avgeragebid");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            est_time = getIntent().getStringExtra("est_time");
            desx = getIntent().getStringExtra("desx");
            proposal = getIntent().getStringExtra("proposal");
            bid_amount = getIntent().getStringExtra("bid_amount");



            txtdescrip.setText(desx);
            txtestimatetime.setText(est_time);
            txttime.setText(time);

            txtaverage.setText("₹ "+avgeragebid);
            edtvendorproposal.setText(proposal);
            edt_bidamount.setText(bid_amount);

        try {
            String newdate = date;
            String start_dt = newdate;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
            Date date = formatter.parse(start_dt);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
              finaldate = newFormat.format(date);
            System.out.println("finaldate------     " + finaldate);
            txtdate.setText(finaldate);


        } catch (Exception e) {
            e.printStackTrace();
        }


        }
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        cdhours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cdhours.setCardBackgroundColor(Color.parseColor("#55b537"));
                txthours.setTextColor(getResources().getColor(R.color.white));
                cddays.setCardBackgroundColor(Color.parseColor("#ffffff"));
                txtday.setTextColor(getResources().getColor(R.color.black));
            }
        });

        cddays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdhours.setCardBackgroundColor(Color.parseColor("#ffffff"));
                txthours.setTextColor(getResources().getColor(R.color.black));
                cddays.setCardBackgroundColor(Color.parseColor("#55b537"));
                txtday.setTextColor(getResources().getColor(R.color.white));
            }
        });

        cdopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submission_type = "open";
                cdsealed.setCardBackgroundColor(Color.parseColor("#ffffff"));
                txtsealed.setTextColor(getResources().getColor(R.color.black));
                cdopen.setCardBackgroundColor(Color.parseColor("#55b537"));
                txtopen.setTextColor(getResources().getColor(R.color.white));

            }
        });

        cdsealed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submission_type = "sealed";
                cdsealed.setCardBackgroundColor(Color.parseColor("#55b537"));
                txtsealed.setTextColor(getResources().getColor(R.color.white));
                cdopen.setCardBackgroundColor(Color.parseColor("#ffffff"));
                txtopen.setTextColor(getResources().getColor(R.color.black));

            }
        });


        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bidamount = edt_bidamount.getText().toString();
                newestimatetime = edtestimatetime.getText().toString();
                vendrdesc = edtvendorproposal.getText().toString();

                Intent intent = new Intent(Activity_Vendor_PlaceBid_Update.this, Activity_Vendor_PlaceBidConfirmSubmit_Update.class);
                intent.putExtra("desx", desx);
                intent.putExtra("est_time", est_time);
                intent.putExtra("time", time);
                intent.putExtra("date", finaldate);
                intent.putExtra("avgeragebid", avgeragebid);
                intent.putExtra("jobid", id);
                intent.putExtra("bidamount", bidamount);
                intent.putExtra("newestimatetime", newestimatetime);
                intent.putExtra("vendrdesc", vendrdesc);
                intent.putExtra("submission_type", submission_type);


                startActivity(intent);
            }
        });


    }



}
//Activity_Vendor_PlaceBidConfirmSubmit