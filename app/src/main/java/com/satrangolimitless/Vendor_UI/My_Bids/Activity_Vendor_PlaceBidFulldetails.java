package com.satrangolimitless.Vendor_UI.My_Bids;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Adapter.Adapter_attachments_images;
import com.satrangolimitless.Adapter.AtachmentshowAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.model.SubjectData;
import com.satrangolimitless.session.Session_vendor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_Vendor_PlaceBidFulldetails extends AppCompatActivity {

    private final int SELECT_FILE = 1;
    private final List<String> FilenamList = new ArrayList<>();

    Session_vendor session_vendor;
    ArrayList<Uri> arrayList = new ArrayList<>();
    String est_time, desx, time, date, avgeragebid, jobid, bidamount, newestimatetime, vendrdesc;
    Adapter_attachments_images adapter_popular_categoriesActivity;
    ArrayList<SubjectData> imagearrayList = new ArrayList<SubjectData>();
    RecyclerView list;
    String user_id;
    String  submissiontype,bid_amount, proposal, estimated_time,id,attachment;
    TextView txtdescrip, txtnewestimtime, txtbidamount, txttime, txtdate , txtestmtime, txtavgbid, txtproposal, txtsubmissiontype;
    Button btnedit, Vcancel;
    Fragment fragment;
    String userChoosenTask, filenew1;
    Uri pickedImage;
    File destination;

    AtachmentshowAdapter atachmentshowAdapter;
    ArrayList<String> attachlist = new ArrayList<>();
    RecyclerView listss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_place_bid_fulldetails);

        listss = findViewById(R.id.listss);
        txtsubmissiontype = findViewById(R.id.txtsubmissiontype);
        txtproposal = findViewById(R.id.txtproposal);
        txtavgbid = findViewById(R.id.txtavgbid);
        txtestmtime = findViewById(R.id.txtestmtime);
        txtnewestimtime = findViewById(R.id.txtnewestimtime);
        txtdescrip = findViewById(R.id.txtdescrip);
        txtdate = findViewById(R.id.txtdate);
        txtbidamount = findViewById(R.id.txtbidamount);
        btnedit = findViewById(R.id.btnedit);
        Vcancel = findViewById(R.id.Vcancel);

        if (getIntent() != null) {



            desx = getIntent().getStringExtra("desx");

            vendrdesc = getIntent().getStringExtra("vendrdesc");
            newestimatetime = getIntent().getStringExtra("newestimatetime");

            id = getIntent().getStringExtra("id");
            jobid = getIntent().getStringExtra("jobid");
            avgeragebid = getIntent().getStringExtra("avgeragebid");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            est_time = getIntent().getStringExtra("est_time");
            attachment = getIntent().getStringExtra("attachment");
            proposal = getIntent().getStringExtra("proposal");
            bid_amount = getIntent().getStringExtra("bid_amount");
            submissiontype = getIntent().getStringExtra("submissiontype");


            ArrayList aList= new ArrayList(Arrays.asList(attachment.split(",")));
            for(int i=0;i<aList.size();i++)
            {
                System.out.println(" -->   "+aList.get(i));
                String images= (String) aList.get(i);
                attachlist.add(images);
            }


            atachmentshowAdapter = new AtachmentshowAdapter(getApplicationContext(), attachlist);
            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
            listss.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
            listss.setAdapter(atachmentshowAdapter);
            atachmentshowAdapter.notifyDataSetChanged();

        }

        txtdescrip.setText(desx);
        txtavgbid.setText("₹ " + avgeragebid);
        txtbidamount.setText("₹ " + bid_amount);
        txtestmtime.setText(est_time);
        txtnewestimtime.setText(est_time);
        txtsubmissiontype.setText(submissiontype);
        txtdate.setText(date);
        txtproposal.setText(proposal);

            btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), Activity_Vendor_PlaceBid_Update.class);
                    intent.putExtra("id", id);
                    intent.putExtra("jobid", jobid);
                    intent.putExtra("avgeragebid", avgeragebid);
                    intent.putExtra("date", date);
                    intent.putExtra("time", time);
                    intent.putExtra("est_time", est_time);
                    intent.putExtra("desx", desx);
                    intent.putExtra("proposal", proposal);
                    intent.putExtra("bid_amount", bid_amount);
                    startActivity(intent);

                }
            });


        Vcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
