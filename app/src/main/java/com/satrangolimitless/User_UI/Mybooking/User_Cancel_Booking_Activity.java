package com.satrangolimitless.User_UI.Mybooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Booking_cancel_by_vendor_after_accept;

public class User_Cancel_Booking_Activity extends AppCompatActivity {
    CardView cardfour, cardone, cardtwo, cardthree;
    ImageView ivc_back;
    TextView txtspeciality, txtcharge, txtrating, txtname, txtdate, txtbookid;
    Button btnback, buttonSubmit;
    String reason, date, name, bookingid, id, user_id, reting, price;
    Session session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercancelbooking);
        session = new Session(getApplicationContext());
        user_id = session.getUserId();
        ivc_back = findViewById(R.id.ivc_back);
        cardone = findViewById(R.id.cardone);
        cardtwo = findViewById(R.id.cardtwo);
        cardthree = findViewById(R.id.cardthree);
        cardfour = findViewById(R.id.cardfour);
        txtbookid = findViewById(R.id.txtbookid);
        txtdate = findViewById(R.id.txtdate);
        txtname = findViewById(R.id.txtname);
        txtrating = findViewById(R.id.txtrating);
        txtcharge = findViewById(R.id.txtcharge);
        txtspeciality = findViewById(R.id.txtspeciality);
        btnback = findViewById(R.id.btnback);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            bookingid = getIntent().getStringExtra("bookingid");
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            price = getIntent().getStringExtra("price");
            reting = getIntent().getStringExtra("reting");

        }


        txtdate.setText(date);
        txtname.setText(name);
        txtrating.setText(reting);
        txtcharge.setText(price);
        txtspeciality.setText("");
        txtbookid.setText(bookingid);

        ivc_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cardone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                reason = "Service Provider not responding properly";

                cardone.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cardtwo.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cardthree.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cardfour.setCardBackgroundColor(Color.parseColor("#ffffff"));


            }
        });

        cardtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reason = "Looks like we haven't got the chance to serve you. We will wait till next time.";
                cardtwo.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cardone.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cardthree.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cardfour.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        cardthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = "Found some other app/option to serve better";
                cardthree.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cardone.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cardtwo.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cardfour.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        cardfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = "I am not in need of this service now";
                cardfour.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cardone.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cardtwo.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cardthree.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Booking_cancel_by_vendor();
            }
        });

    }


    //    Booking reject api after accepting booking---------------------------------------------------------

    private void Booking_cancel_by_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(User_Cancel_Booking_Activity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Booking_cancel_by_vendor_after_accept
                ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("Booking_accreject data", response);
                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("Booking_reject_accpetedby_user data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

                                if (msg.contains("Booking Cancelled")) {


                                    Intent intent = new Intent(User_Cancel_Booking_Activity.this, LandingActivity.class);
                                    startActivity(intent);
                                    finish();

                                }


                            } else {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();

                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("bookingId", id);
                params.put("reason", reason);

                System.out.println("acceptedbooking reject param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
