package com.satrangolimitless.User_UI.Mybooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.satrangolimitless.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class  Activity_User_BookingCompleted_Details extends AppCompatActivity {
    TextView txttime, txtviewfiles, txtcharge, txtspeciality, txtrating, txtname, txtdate, txtbookid,txtddate;
    ImageView ivbak;
    String otp,desc,reting,bookingid,date,name,id,image,price,frmtime,totime;
    CircleImageView imgbkcomplt;
    Button btn_sport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bookingcompleted_details);
        txtbookid =  findViewById(R.id.txtbookid);
        txtdate =  findViewById(R.id.txtdate);
        txtname =  findViewById(R.id.txtname);
        txtrating =  findViewById(R.id.txtrating);
        txtspeciality =  findViewById(R.id.txtspeciality);
        txtcharge =  findViewById(R.id.txtcharge);
        txtviewfiles =  findViewById(R.id.txtviewfiles);
        txtddate =  findViewById(R.id.txtddate);
        imgbkcomplt =  findViewById(R.id.imgbkcomplt);
        ivbak =  findViewById(R.id.ivbak);
        txttime =  findViewById(R.id.txttime);
        btn_sport =  findViewById(R.id.btn_sport);

        if (getIntent()!=null){

            otp = getIntent().getStringExtra("otp");
            desc = getIntent().getStringExtra("desc");
            reting = getIntent().getStringExtra("reting");
            bookingid = getIntent().getStringExtra("bookingid");
            date = getIntent().getStringExtra("date");
            name = getIntent().getStringExtra("name");
            id = getIntent().getStringExtra("id");
            image = getIntent().getStringExtra("image");
            price = getIntent().getStringExtra("price");

            totime= getIntent().getStringExtra("totime");
            frmtime= getIntent().getStringExtra("frmtime");
        }

        txtname.setText(name);
        txtbookid.setText(bookingid);
        txtdate.setText(date);
        txtddate.setText(date);
        txtrating.setText(reting);
        txttime.setText( totime);
        txtcharge.setText("₹ "+price);

        Glide.with(getApplicationContext())
                .load(Image_url + image)
                .into(imgbkcomplt);

        ivbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Actvity_Supportticket.class);
                startActivity(intent);

            }
        });

    }

}