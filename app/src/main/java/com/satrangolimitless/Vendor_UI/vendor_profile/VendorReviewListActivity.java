package com.satrangolimitless.Vendor_UI.vendor_profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;

public class VendorReviewListActivity extends AppCompatActivity {
Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_review_list);
        //recvtrhist=findViewById(R.id.recvtrhist);
        session=new Session(getApplicationContext());

    }

}
