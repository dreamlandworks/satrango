package com.satrangolimitless.Vendor_UI.Vendor_MyBooking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.OTP_MATCH_by_vendor_usergiven;
import static com.satrangolimitless.Utils.Base_Url.Start_Booking_by_vendor;
import static com.satrangolimitless.Utils.Base_Url.generateOTPOnBooking;

public class Vendor_Booking_ConfirmDetails extends AppCompatActivity {
    private static CountDownTimer countDownTimer;
    //Declare a variable to hold count down timer's paused status
    private final boolean isPaused = false;
    //Declare a variable to hold count down timer's paused status
    private final boolean isCanceled = false;
    //Declare a variable to hold CountDownTimer remaining time
    private final long timeRemaining = 0;
    Session_vendor session_vendor;
    TextView txtnames, txtprice, txttimercounter, txttime, txtdate, txtbookingid, txtratings, txtviewfiles;
    Button btnpause, btnmkcomplete;
    Session_vendor session1;
    String e1 = "", e2 = "", e3 = "", e4 = "", otp = "";
    String vid, bookingid, image, price, reting, date, name, id, status, vdoc, jobstts, votp="";
    AlertDialog alertDialog;
    ImageView ivbak;
    ImageView imgotpstus, imgextrastus, imgtus, mg  ;
    TextView txtotpentered, txtraiseexamount, txttaskcompleted,txtmrkcomplete;
    // Number of seconds displayed
    // on the stopwatch.
    private int seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendorbookingconfirmdetails);
        session_vendor = new Session_vendor(getApplicationContext());
        txttimercounter = findViewById(R.id.txttimercounter);

        txtbookingid = findViewById(R.id.txtbookingid);
        txtnames = findViewById(R.id.txtnames);
        txtprice = findViewById(R.id.txtamount);
        txtratings = findViewById(R.id.txtratings);

        txttime = findViewById(R.id.txttime);
        txtdate = findViewById(R.id.txtdate);
        txtviewfiles = findViewById(R.id.txtviewfiles);
        btnmkcomplete = findViewById(R.id.btnmkcomplete);
        btnpause = findViewById(R.id.btnpause);
        ivbak = findViewById(R.id.ivbak);

        imgotpstus = findViewById(R.id.imgotpstus);
        imgextrastus = findViewById(R.id.imgextrastus);
        imgtus = findViewById(R.id.imgtus);
        mg = findViewById(R.id.mg);
        txtmrkcomplete = findViewById(R.id.txtmrkcomplete);
        txtotpentered = findViewById(R.id.txtotpentered);
        txtraiseexamount = findViewById(R.id.txtraiseexamount);
        txttaskcompleted = findViewById(R.id.txttaskcompleted);

        if (votp.contains("1")) {
            imgotpstus.setImageResource(R.drawable.statuscheck);
            txtotpentered.setTextColor(getResources().getColor(R.color.colorPrimary));
            runTimer();
        }


        session1 = new Session_vendor(getApplicationContext());
        vid = session1.getUserId();
        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }

        running = true;

        if (getIntent() != null) {

            id = getIntent().getStringExtra("id");
            bookingid = getIntent().getStringExtra("bookingid");
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            reting = getIntent().getStringExtra("reting");
            price = getIntent().getStringExtra("price");
            image = getIntent().getStringExtra("image");
            status = getIntent().getStringExtra("status");
            image = getIntent().getStringExtra("image");
            vdoc = getIntent().getStringExtra("vdoc");
            votp = getIntent().getStringExtra("votp");
            jobstts = getIntent().getStringExtra("jobstts");

        }
        txtnames.setText(name);
        txtprice.setText("₹ " + price);
        txtdate.setText(date);
        txtbookingid.setText(bookingid);
        txtratings.setText(reting);
        txtviewfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnpause.setText("Cancel");
        btnmkcomplete.setText("Enter OTP");
        btnmkcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Startbooking();

//                GenerateOTP();
                OTPmatchdialog();
            }
        });
        ivbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void GenerateOTP() {

        final ProgressDialog progressDialog = new ProgressDialog(Vendor_Booking_ConfirmDetails.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + generateOTPOnBooking;
        System.out.println(" generateOTPOnBooking param   " + vid + "  bookid " + id);
        AndroidNetworking.post(url)

                .addBodyParameter("booking_id", id)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        System.out.println("generateOTPOnBooking  response=========      " + jsonObject.toString());
                        progressDialog.dismiss();
                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {


                                OTPmatchdialog();

                            } else {


                                Toast.makeText(Vendor_Booking_ConfirmDetails.this, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());
                        progressDialog.dismiss();

                    }
                });


    }


    public void OTPmatchdialog() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(Vendor_Booking_ConfirmDetails.this);
        View view = LayoutInflater.from(Vendor_Booking_ConfirmDetails.this).inflate(R.layout.dialog_booking_otp_confirm, null);

        builder.setCancelable(false);


        Button btn_yes = view.findViewById(R.id.btn_yes);
        final EditText edt_one = view.findViewById(R.id.edt_one);
        final EditText edt_two = view.findViewById(R.id.edt_two);
        final EditText edt_three = view.findViewById(R.id.edt_three);
        final EditText edt_four = view.findViewById(R.id.edt_four);
        edt_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    edt_two.requestFocus();
                }
            }
        });

        edt_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    edt_three.requestFocus();
                }
            }
        });

        edt_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    edt_four.requestFocus();
                }
            }
        });


        edt_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {

                }
            }
        });


        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1 = edt_one.getText().toString();
                e2 = edt_two.getText().toString();
                e3 = edt_three.getText().toString();
                e4 = edt_four.getText().toString();
                otp = e1 + e2 + e3 + e4;

                if (otp.isEmpty()) {

                    Toast.makeText(Vendor_Booking_ConfirmDetails.this, "Please enter correct OTP.", Toast.LENGTH_SHORT).show();
                } else {

                    OTP_Matchapi();

                }

            }
        });
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.show();
    }
    //Dialog for otp match api


    private void OTP_Matchapi() {

        final ProgressDialog progressDialog = new ProgressDialog(Vendor_Booking_ConfirmDetails.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + OTP_MATCH_by_vendor_usergiven;
        AndroidNetworking.post(url)
                .addBodyParameter("otp", otp)
                .addBodyParameter("bookingId", id)
                .addBodyParameter("vendor_id", vid)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        System.out.println("booking_match_otp ---   " + "otp " + otp + " bookid " + id + " vendor_id " + vid);
                        progressDialog.dismiss();
                        try {

                            String result = jsonObject.getString("job_status");
                            String msg = jsonObject.getString("start_time");

                            if (result.equalsIgnoreCase("Inprogress")) {
                                Log.e("otpmatchapiresponce-  ", jsonObject.toString());
                                progressDialog.dismiss();
                                Startbooking();
                                alertDialog.dismiss();
                            } else {
                                progressDialog.dismiss();
                                alertDialog.dismiss();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }

//Startbooking====================================================================

    private void Startbooking() {

        final ProgressDialog progressDialog = new ProgressDialog(Vendor_Booking_ConfirmDetails.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Start_Booking_by_vendor;
        System.out.println("param vid " + vid + "  bookid " + id);
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vid)
                .addBodyParameter("bookingId", id)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("Startbookingresponce-  ", jsonObject.toString());
                                Toast.makeText(Vendor_Booking_ConfirmDetails.this, msg, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), LandingActivity_Service_provider.class);
                                startActivity(intent);
                                finish();

                            } else {


                                Toast.makeText(Vendor_Booking_ConfirmDetails.this, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }










    /*
    private void startTimer(int noOfMinutes) {
            countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                txttimercounter.setText(hms);//set text
            }

            public void onFinish() {

                txttimercounter.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
             }
        }.start();

    }
    */


//    STOP WATCH TIMER SHOW


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
                txttimercounter.setText(time);

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
}