package com.satrangolimitless.Vendor_UI.My_Bids;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Adapter.AtachmentsBidsAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session_vendor;

import java.util.ArrayList;
import java.util.Arrays;

public class Activity_vendor_awarded_expired_alldetails extends AppCompatActivity {
    ArrayList<String> attachlist = new ArrayList<>();
    Session_vendor session_vendor;
    String attachmnt, desx, estimated_time, min, totalbid, avgeragebid, jobid, id, avg, max,language,usereastmattime,bidamount, proposal;
    TextView txtproposal, txtuseresttime,txtbidamount, txtmin_maxrange, txtdesxc, txtestimatetime, txtdate, txtlanguages, txtbidsrecieved, txtavg;
    AtachmentsBidsAdapter atachmentsBidsAdapter;
     RecyclerView recv_placebidatachm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actviity_vendor_bids_award_expired_all_details);
        session_vendor = new Session_vendor(getApplicationContext());
        txtmin_maxrange = findViewById(R.id.txtmin_maxrange);
        txtdesxc = findViewById(R.id.txtdesxc);
        txtestimatetime = findViewById(R.id.txtestimatetime);
        txtdate = findViewById(R.id.txtdate);
        txtlanguages = findViewById(R.id.txtlanguages);
        txtbidsrecieved = findViewById(R.id.txtbidsrecieved);
        txtavg = findViewById(R.id.txtavg);
        txtbidamount = findViewById(R.id.txtbidamount);
        txtuseresttime = findViewById(R.id.txtuseresttime);
        txtproposal = findViewById(R.id.txtproposal);
        recv_placebidatachm = findViewById(R.id.recv_placebidatachm);


        if (getIntent() != null) {

            desx = getIntent().getStringExtra("desx");
            id = getIntent().getStringExtra("id");
            jobid = getIntent().getStringExtra("jobid");
            avgeragebid = getIntent().getStringExtra("avgeragebid");
            totalbid = getIntent().getStringExtra("totalbid");
            min = getIntent().getStringExtra("min");
            max = getIntent().getStringExtra("max");
            avg = getIntent().getStringExtra("avg");
            attachmnt = getIntent().getStringExtra("attachmnt");
            language = getIntent().getStringExtra("language");
            estimated_time = getIntent().getStringExtra("estimated_time");

            usereastmattime= getIntent().getStringExtra("usereastmattime");
                    bidamount= getIntent().getStringExtra("bidamount");
            proposal= getIntent().getStringExtra("proposal");
            ArrayList aList= new ArrayList(Arrays.asList(attachmnt.split(",")));
            for(int i=0;i<aList.size();i++)
            {
                System.out.println("Activity_vendor_awarded_expired_alldetails -->   "+aList.get(i));
                String images= (String) aList.get(i);
                attachlist.add(images);
            }


            atachmentsBidsAdapter = new AtachmentsBidsAdapter(getApplicationContext(), attachlist);
            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
            recv_placebidatachm.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
            recv_placebidatachm.setAdapter(atachmentsBidsAdapter);
            atachmentsBidsAdapter.notifyDataSetChanged();



        }

        txtdesxc.setText(desx);
        txtmin_maxrange.setText("₹ "+min+" - "+max);
        txtestimatetime.setText(estimated_time);
        txtavg.setText(avgeragebid);
        txtbidsrecieved.setText(totalbid);
        txtdate.setText("");
        txtlanguages.setText(language);
        txtbidamount.setText("₹ "+bidamount);
        txtuseresttime.setText(usereastmattime);
        txtproposal.setText(proposal);



    }




}
