package com.satrangolimitless.Vendor_UI.Vendor_MyBooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session_vendor;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Vendor_Bookking_Completed_Details extends AppCompatActivity {
TextView txttime,txtbdate,txtamount,txtratings,txtnames,txtdate,txtbookingid;
String id,name,rating,time,To_time,date,price,image,bookid;
Session_vendor session_vendor;
CircleImageView imgvendr;
Button btnraiseticket,btncstmrcare;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_bookingcompleteddetails);
        session_vendor=new Session_vendor(getApplicationContext());
        txttime=  findViewById(R.id.txttime);
        txtbdate=  findViewById(R.id.txtbdate);
        txtamount=  findViewById(R.id.txtamount);
        txtratings=  findViewById(R.id.txtratings);
        txtnames=  findViewById(R.id.txtnames);
        txtdate=  findViewById(R.id.txtdate);
        txtbookingid=  findViewById(R.id.txtbookingid);
        imgvendr=  findViewById(R.id.imgvendr);
        btnraiseticket=  findViewById(R.id.btnraiseticket);
        btncstmrcare=  findViewById(R.id.btncstmrcare);


        if (getIntent()!=null){
            id= getIntent().getStringExtra("id");
            name= getIntent().getStringExtra("name");
            rating= getIntent().getStringExtra("rating");
            time= getIntent().getStringExtra("time");
            To_time= getIntent().getStringExtra("To_time");
            date= getIntent().getStringExtra("date");
            price= getIntent().getStringExtra("price");
            bookid= getIntent().getStringExtra("bookid");
            image= getIntent().getStringExtra("image");

        }

        Glide.with(getApplicationContext())
                .load(mediimage_url + image)
                .into(imgvendr);

        txtnames.setText(name);
        txtratings.setText(rating);
        txtamount.setText("₹ "+price);
        txtdate.setText(date);
        txtbdate.setText(date);
        txtbookingid.setText(bookid);
        txttime.setText(To_time);

        btnraiseticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), Vendor_raiseticket.class);
                startActivity(intent);


            }
        });


        btncstmrcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
