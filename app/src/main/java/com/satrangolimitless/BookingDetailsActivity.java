package com.satrangolimitless;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BookingDetailsActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
Button COMPLETEJOB;
ImageView PhoneCall,UV_Chat;
    private static final int MY_PERMISSION_REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        PhoneCall=findViewById(R.id.PhoneCall);
        UV_Chat=findViewById(R.id.UV_Chat);




        //attaching click listener start
        findViewById(R.id.cancelbooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calling this method to show our android custom alert dialog
                showCustomDialog();
            }
        });

        //attaching chat section start

        UV_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(BookingDetailsActivity.this, ChatDetailsActivity.class);
//                startActivity(intent);
            }
        });


        PhoneCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+5657657));
                startActivity(callIntent);
            }

        });


        //attaching click listener
        findViewById(R.id.MarkCompleteJob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calling this method to show our android custom alert dialog
                showCustomDialog2();
            }



            private void showCustomDialog2() {

                    //before inflating the custom alert dialog layout, we will get the current activity viewgroup
                    ViewGroup viewGroup = findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(BookingDetailsActivity.this).inflate(R.layout.activity_job_c_omplete_diglog, viewGroup, false);


                     TextView COMPLETEJOB=dialogView.findViewById(R.id.COMPLETEJOB);

                    //Now we need an AlertDialog.Builder object
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingDetailsActivity.this);

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);


                COMPLETEJOB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(BookingDetailsActivity.this, LandingActivity.class);
                            startActivity(intent);
                        }
                    });
                    //finally creating the alert dialog and displaying it
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();





            }
        });
    }



    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_booking_cancel_diglog, viewGroup, false);


        TextView cancelbooking=dialogView.findViewById(R.id.CancelConfirm);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        cancelbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingDetailsActivity.this, LandingActivity.class);
                startActivity(intent);
            }
        });


        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }
}