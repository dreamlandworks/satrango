package com.satrangolimitless.Vendor_UI;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class My_profile_vendor extends AppCompatActivity {
    TextView txtname, txt_mob_no;
    Button UserTypes,Serviceprovider;
    public  static TextView txt_personal, txtskills, txttarrif;
    TextView txt_back;
    ImageView img_back;
    String phone,password;
    EditText edt_mobile,edt_password;
    CircleImageView imgviewprovendr;
    Session session;
    Session_vendor session_vendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile_service_provider);
        session=new Session(getApplicationContext());
        imgviewprovendr = findViewById(R.id.imgviewprovendr);
        txtname = findViewById(R.id.txtname);
        txt_mob_no = findViewById(R.id.txt_mob_no);
        txt_personal = findViewById(R.id.txt_personal);
        txtskills = findViewById(R.id.txtskills);
        txttarrif = findViewById(R.id.txttarrif);
        img_back = findViewById(R.id.img_back);
        txt_back = findViewById(R.id.txt_back);
        txtname.setText(session.getUser_name());
        txt_mob_no.setText(session.getMobile());

        Glide.with(getApplicationContext())
                .load(Image_url + session.getProfileimage())
                .into(imgviewprovendr);

        //call fragment
        My_Profile_vendor_personal fdr = new My_Profile_vendor_personal();
        replaceFragment(fdr, false, R.id.frame_layout);


        txt_personal.setBackgroundResource(R.drawable.tab_selected);
        txt_personal.setTextColor(getResources().getColor(R.color.white));
        txt_personal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_personal.setBackgroundResource(R.drawable.tab_selected);
                txt_personal.setTextColor(getResources().getColor(R.color.white));
                txtskills.setBackgroundResource(R.drawable.tab_unselected);
                txtskills.setTextColor(getResources().getColor(R.color.vendorprimerycolor));
                txttarrif.setBackgroundResource(R.drawable.tab_unselected);
                txttarrif.setTextColor(getResources().getColor(R.color.vendorprimerycolor));
                My_Profile_vendor_personal fdr = new My_Profile_vendor_personal();
                replaceFragment(fdr, false, R.id.frame_layout);
            }
        });


        txtskills.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtskills.setBackgroundResource(R.drawable.tab_selected);
                txtskills.setTextColor(getResources().getColor(R.color.white));
                txttarrif.setBackgroundResource(R.drawable.tab_unselected);
                txttarrif.setTextColor(getResources().getColor(R.color.vendorprimerycolor));
                txt_personal.setBackgroundResource(R.drawable.tab_unselected);
                txt_personal.setTextColor(getResources().getColor(R.color.vendorprimerycolor));
                My_Profile_vendor_skills fdr = new My_Profile_vendor_skills();
                replaceFragment(fdr, false, R.id.frame_layout);


            }
        });

        txttarrif.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txttarrif.setBackgroundResource(R.drawable.tab_selected);
                txttarrif.setTextColor(getResources().getColor(R.color.white));

                txtskills.setBackgroundResource(R.drawable.tab_unselected);
                txtskills.setTextColor(getResources().getColor(R.color.vendorprimerycolor));

                txt_personal.setBackgroundResource(R.drawable.tab_unselected);
                txt_personal.setTextColor(getResources().getColor(R.color.vendorprimerycolor));


                My_Profile_vendor_tarrif_timings fdr = new My_Profile_vendor_tarrif_timings();
                replaceFragment(fdr, false, R.id.frame_layout);


            }
        });
                img_back.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                txt_back.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
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