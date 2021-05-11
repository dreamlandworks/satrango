package com.satrangolimitless.Vendor_UI.My_Bids;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Adapter.AtachmentshowAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session_vendor;

import java.util.ArrayList;
import java.util.Arrays;

public class Activity_view_job_details_bids_discussions_vendr_side extends AppCompatActivity {
    Session_vendor session_vendor;
    LinearLayout ll_bids, ll_discussion;
    String attach,bid,bid_per, avgrange, esttime, time, date, desc, lang, id, totalbids, max, min, avgeragebid, est_time, desx;
    TextView txtavg, txtbidsrecieved, txtlanguages, txtestimatetime, txtfrmtime_totime, txtdate, txtdesxc, txtmin_maxrange;
Button btnplacebid;
    AtachmentshowAdapter atachmentshowAdapter;
    ArrayList<String> attachlist = new ArrayList<>();
RecyclerView recv_placebidatachm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_pending_biddetails);

        session_vendor = new Session_vendor(getApplicationContext());
        ll_bids = findViewById(R.id.ll_bids);
        ll_discussion = findViewById(R.id.ll_discussion);
        txtbidsrecieved = findViewById(R.id.txtbidsrecieved);
        txtlanguages = findViewById(R.id.txtlanguages);
        txtestimatetime = findViewById(R.id.txtestimatetime);
        txtfrmtime_totime = findViewById(R.id.txtfrmtime_totime);
        txtavg = findViewById(R.id.txtavg);
        txtdate = findViewById(R.id.txtdate);
        txtdesxc = findViewById(R.id.txtdesxc);
        txtmin_maxrange = findViewById(R.id.txtmin_maxrange);
        btnplacebid = findViewById(R.id.btnplacebid);
        recv_placebidatachm = findViewById(R.id.recv_placebidatachm);

        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            lang = getIntent().getStringExtra("lang");
            desc = getIntent().getStringExtra("desx");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            esttime = getIntent().getStringExtra("est_time");
            totalbids = getIntent().getStringExtra("totalbids");
            avgeragebid = getIntent().getStringExtra("avgeragebid");
            min = getIntent().getStringExtra("min");
            max = getIntent().getStringExtra("max");
            attach = getIntent().getStringExtra("attach");
            desc = getIntent().getStringExtra("desx");
            bid_per = getIntent().getStringExtra("bid_per");


            ArrayList aList= new ArrayList(Arrays.asList(attach.split(",")));
            for(int i=0;i<aList.size();i++)
            {
                System.out.println(" -->   "+aList.get(i));
                String images= (String) aList.get(i);
                attachlist.add(images);
            }


            atachmentshowAdapter = new AtachmentshowAdapter(getApplicationContext(), attachlist);
            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
            recv_placebidatachm.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
            recv_placebidatachm.setAdapter(atachmentshowAdapter);
            atachmentshowAdapter.notifyDataSetChanged();
        }

        txtbidsrecieved.setText(totalbids);
        txtlanguages.setText(lang);
        txtmin_maxrange.setText("₹ "+min+" - "+max);
        txtdesxc.setText(desc);
        txtfrmtime_totime.setText(time);
        txtdate.setText(date);
        txtestimatetime.setText(esttime+" "+bid_per);

        if (avgeragebid != null && !avgeragebid.isEmpty() && !avgeragebid.equals("null") && !avgeragebid.equals("0")) {
            txtavg.setText("₹ "+avgeragebid);
        }else{

            txtavg.setText("₹ 0");
        }


        Fragment_vendorside_newjobs_Bids fdr = new Fragment_vendorside_newjobs_Bids();
        replaceFragment(fdr, false, R.id.frame_vendor_bids_discussions);

        ll_bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_vendorside_newjobs_Bids fdr = new Fragment_vendorside_newjobs_Bids();
                replaceFragment(fdr, false, R.id.frame_vendor_bids_discussions);
            }
        });

        ll_discussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_vendorside_newjobs_Discussions fdr = new Fragment_vendorside_newjobs_Discussions();
                replaceFragment(fdr, false, R.id.frame_vendor_bids_discussions);
            }
        });

        btnplacebid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Vendor_PlaceBid.class);
                intent.putExtra("id", id);
                intent.putExtra("avgeragebid", avgeragebid);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("est_time", esttime);
                intent.putExtra("desx", desc);
                startActivity(intent);








            }
        });



    }


    /*....................replaceFragment()....................................*/
    @SuppressLint("WrongConstant")
    public void replaceFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        String backStackName = fragment.getClass().getName();
        FragmentManager fm = getSupportFragmentManager();
        int i = fm.getBackStackEntryCount();
        while (i > 0) {
            fm.popBackStackImmediate();
            i--;
        }
        boolean fragmentPopped = getFragmentManager().popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerId, fragment, backStackName).setTransition(FragmentTransaction.TRANSIT_UNSET);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }
}
