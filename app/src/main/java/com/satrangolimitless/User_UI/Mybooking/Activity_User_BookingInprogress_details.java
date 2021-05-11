package com.satrangolimitless.User_UI.Mybooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mark_complete;

public class Activity_User_BookingInprogress_details extends AppCompatActivity {

    //Declare a variable to hold count down timer's paused status
    private final boolean isPaused = false;
    //Declare a variable to hold count down timer's paused status
    private final boolean isCanceled = false;
    //Declare a variable to hold CountDownTimer remaining time
    private final long timeRemaining = 0;
    ImageView ivcbak;
    TextView txttimer, txtcharge, txtspeciality, txtname, txtdate, txtbookid, txtrating, txtviewfiles, txttopdate;
    Button btnsubmit, btn_back;
    Button img_panic;
    int flag = 0;
    String usrimage, vdrimage, vendordoc, appropriate_status, desc, reting, bookingid, date, name, id, price, vendorid, otp, votp, attachm, extra_charge_status, date_time, totime, frmtime;

    LinearLayout ll_raisextra, ll_wrksentfrreview, ll_wrkreviewandmrkaprop, ll_otpmrkcomplete;
    ImageView imgotpchk, img_raiseextra, imgwrkreview, imgwrkrevaprove, imgotpentrmrkcomplete;

    TextView txtextrachrgstatus, txttime, txtotpverify, txtraisedextrademand, txtwrksentfrreview, txtwrkreviewed, txtotpentrmrkcomplete;
    Session session;
    // on the stopwatch.
    private int seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    CircleImageView imgpro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bookinginprogress_details);
        session = new Session(getApplicationContext());
        txttimer = findViewById(R.id.txttimer);
        txtcharge = findViewById(R.id.txtcharge);
        txtspeciality = findViewById(R.id.txtspeciality);
        txtname = findViewById(R.id.txtname);
        txtdate = findViewById(R.id.txtdate);
        txttime = findViewById(R.id.txttime);
        txtbookid = findViewById(R.id.txtbookid);
        txtviewfiles = findViewById(R.id.txtviewfiles);
        txttopdate = findViewById(R.id.txttopdate);
        txtrating = findViewById(R.id.txtrating);
        txtextrachrgstatus = findViewById(R.id.txtextrachrgstatus);
        ivcbak = findViewById(R.id.ivcbak);
        img_panic = findViewById(R.id.img_panic);
        imgotpchk = findViewById(R.id.imgotpchk);
        img_raiseextra = findViewById(R.id.img_raiseextra);
        imgwrkreview = findViewById(R.id.imgwrkreview);
        imgwrkrevaprove = findViewById(R.id.imgwrkrevaprove);
        imgotpentrmrkcomplete = findViewById(R.id.imgotpentrmrkcomplete);
        txtotpverify = findViewById(R.id.txtotpverify);
        txtraisedextrademand = findViewById(R.id.txtraisedextrademand);
        txtwrksentfrreview = findViewById(R.id.txtwrksentfrreview);
        txtwrkreviewed = findViewById(R.id.txtwrkreviewed);
        txtotpentrmrkcomplete = findViewById(R.id.txtotpentrmrkcomplete);
        ll_otpmrkcomplete = findViewById(R.id.ll_otpmrkcomplete);
        ll_raisextra = findViewById(R.id.ll_raisextra);
        ll_wrksentfrreview = findViewById(R.id.ll_wrksentfrreview);
        ll_wrkreviewandmrkaprop = findViewById(R.id.ll_wrkreviewandmrkaprop);

        imgpro = findViewById(R.id.imgpro);
        btnsubmit = findViewById(R.id.btnsubmit);
        btn_back = findViewById(R.id.btn_back);
        ivcbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        running = true;
//        runTimer();

        img_panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            System.out.println("flage --------        "+flag);
                if (flag == 0) {
                    running = true;
                    img_panic.setBackgroundResource(R.drawable.panicunc);
                    flag = 1;
                } else if (flag == 1) {
                    running = false;
                    flag = 0;
                    img_panic.setBackgroundResource(R.drawable.panicred);
                    System.out.println(" else if flage --------        "+flag);

                }
//                running = false;
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bookingmrkcompleteuserside();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                running = false;
                onBackPressed();
            }
        });

        if (getIntent() != null) {

            desc = getIntent().getStringExtra("desc");
            reting = getIntent().getStringExtra("reting");
            bookingid = getIntent().getStringExtra("bookingid");
            date = getIntent().getStringExtra("date");
            frmtime = getIntent().getStringExtra("frmtime");
            totime = getIntent().getStringExtra("totime");
            date_time = getIntent().getStringExtra("date_time");
            name = getIntent().getStringExtra("name");
            id = getIntent().getStringExtra("id");
            price = getIntent().getStringExtra("price");
            vendorid = getIntent().getStringExtra("vendorid");
            otp = getIntent().getStringExtra("otp");
            votp = getIntent().getStringExtra("votp");
            vendordoc = getIntent().getStringExtra("vendordoc");
            attachm = getIntent().getStringExtra("attachm");
            extra_charge_status = getIntent().getStringExtra("extra_charge_status");
            appropriate_status = getIntent().getStringExtra("appropriate_status");
            vdrimage= getIntent().getStringExtra("vdrimage");


            usrimage= getIntent().getStringExtra("usrimage");


        }

        Glide.with(getApplicationContext())
                .load(Image_url + vdrimage).into(imgpro);




        String str = date_time;
        String[] splitString = str.split(" ");

        System.out.println("splitString---  " + splitString[0]);

        if (votp.contains("1")) {
            imgotpchk.setImageResource(R.drawable.statuscheck);
            txtotpverify.setTextColor(getResources().getColor(R.color.colorPrimary));
            runTimer();
        }
        if (extra_charge_status.contains("1")) {

            img_raiseextra.setImageResource(R.drawable.statuscheck);
            imgotpchk.setImageResource(R.drawable.statuscheck);

            txtotpverify.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtraisedextrademand.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtextrachrgstatus.setText("You accepted extra demand");
            txtextrachrgstatus.setTextColor(getResources().getColor(R.color.colorPrimary));

        }
        if (extra_charge_status.contains("2")) {

            img_raiseextra.setImageResource(R.drawable.statuscheck);
            imgotpchk.setImageResource(R.drawable.statuscheck);

            txtotpverify.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtraisedextrademand.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtextrachrgstatus.setText("You rejected extra demand");
            txtextrachrgstatus.setTextColor(getResources().getColor(R.color.red));

        }


        if (!vendordoc.isEmpty()) {

            txtwrksentfrreview.setTextColor(getResources().getColor(R.color.colorPrimary));
            imgwrkreview.setImageResource(R.drawable.statuscheck);
            img_raiseextra.setImageResource(R.drawable.statuscheck);
            imgotpchk.setImageResource(R.drawable.statuscheck);

            txtotpverify.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtraisedextrademand.setTextColor(getResources().getColor(R.color.colorPrimary));

        }
        if (appropriate_status.contains("1")) {

            imgwrkrevaprove.setImageResource(R.drawable.statuscheck);
            imgwrkreview.setImageResource(R.drawable.statuscheck);
            img_raiseextra.setImageResource(R.drawable.statuscheck);
            imgotpchk.setImageResource(R.drawable.statuscheck);

            txtotpverify.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtraisedextrademand.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtwrksentfrreview.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtwrkreviewed.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        txtname.setText(name);
        txtdate.setText(date);
        txttime.setText(totime + " - " + frmtime);


        txtbookid.setText(bookingid);
        txtcharge.setText("₹ " + price);
        txtrating.setText(reting);
        txttopdate.setText(splitString[0]);
        txtviewfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Activity_View_Bookingfiles.class);
                intent.putExtra("id", id);
                intent.putExtra("attachm", attachm);
                intent.putExtra("date", date);
                intent.putExtra("bookingid", bookingid);
                intent.putExtra("vendordoc", vendordoc);
                startActivity(intent);

            }
        });

    }


    //    Stop Watch code------------------


    // Sets the NUmber of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    private void runTimer() {

        // Get the text view.


        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                txttimer.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }


    private void Bookingmrkcompleteuserside() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_User_BookingInprogress_details.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + mark_complete
                ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("Bookingmrkcompleteuserside ----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            String amount = obj.getString("amount");
                            String paid_amount = obj.getString("paid_amount");

                            String extra_charge = obj.getString("extra_charge");
                            String balance = obj.getString("balance");

                            if (result.equalsIgnoreCase("true")) {


                                Intent intent = new Intent(Activity_User_BookingInprogress_details.this, Activity_Mark_complete_invoicesummary.class);
                                intent.putExtra("amount", amount);
                                intent.putExtra("paid_amount", paid_amount);
                                intent.putExtra("extra_charge", extra_charge);
                                intent.putExtra("balance", balance);
                                intent.putExtra("date", date);
                                intent.putExtra("bookingid", bookingid);
                                intent.putExtra("id", id);
                                intent.putExtra("name", name);
                                intent.putExtra("vendorid", vendorid);

                                startActivity(intent);


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
                params.put("user_id", session.getUserId());
                params.put("booking_id", id);


                System.out.println(" Bookingmrkcompleteuserside param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}