package com.satrangolimitless.Vendor_UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.Utils;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_MyBooking;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Booking_accept_by_vendor;
import static com.satrangolimitless.Utils.Base_Url.Booking_cancel_by_vendor;

import static com.satrangolimitless.Utils.Base_Url.View_Bookingdetail_Notificationclick;
import static com.satrangolimitless.Utils.Base_Url.getbookingtime;


public class Activity_vendor_accept_newbookingrequest extends AppCompatActivity {
    int myProgress = 0;
    ProgressBar progressBarView;

    String type, date_time, work_description, to_time, from_time, booking_date, id,start_location1;
    TextView tv_time;
    TextView txtdattime, txtlocation, txtbookdesc;
String timervalue;

    int progress;
    CountDownTimer countDownTimer;
    int endTime = 250;
    Button btn_reject, btn_accept;
    String vendor_id,bookingId,reason="",bookingtime;
    Session session;
    Session_vendor session_vendor;
    private RequestQueue rQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue_vendor_newbooking_request);
        session_vendor = new Session_vendor(getApplicationContext());




        if (session_vendor.isLoggedIn()){
            vendor_id=session_vendor.getUserId();
            bookingId=session_vendor.getBookingid();

            System.out.println("vendor id---------------     "+vendor_id);
            System.out.println("bookingId---------------     "+bookingId);
            Getbookingtime();
            Getbookingdetails();
        }
        else {

            Toast.makeText(this, "Please login ", Toast.LENGTH_SHORT).show();
        }


        progressBarView = (ProgressBar) findViewById(R.id.view_progress_bar);
        tv_time= (TextView)findViewById(R.id.tv_timer);
        txtdattime= (TextView)findViewById(R.id.txtdattime);
        txtlocation= (TextView)findViewById(R.id.txtlocation);
        txtbookdesc= (TextView)findViewById(R.id.txtbookdesc);
        btn_reject=  findViewById(R.id.btn_reject);
        btn_accept=  findViewById(R.id.btn_accept);


        /*Animation*/
        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        progressBarView.startAnimation(makeVertical);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(0);


    btn_accept.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (Utils.isInternetConnected(Activity_vendor_accept_newbookingrequest.this)){

                Booking_accept_by_vendor();
            }else {

                Toast.makeText(Activity_vendor_accept_newbookingrequest.this, "No internet", Toast.LENGTH_SHORT).show();
            }


        }
    });

    btn_reject.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Utils.isInternetConnected(Activity_vendor_accept_newbookingrequest.this)) {

                rejectalertdialogbox();
            }else{
                Toast.makeText(Activity_vendor_accept_newbookingrequest.this, "No internet", Toast.LENGTH_SHORT).show();

            }
        }
    });




}

    private void fn_countdown() {

        if (5>0) {
            myProgress = 0;

            try {
                countDownTimer.cancel();

            } catch (Exception e) {

            }


            String timeInterval = timervalue;
            progress = 1;
            endTime = Integer.parseInt(timeInterval); // up to finish time

            countDownTimer = new CountDownTimer(endTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setProgress(progress, endTime);
                    progress = progress + 1;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                    String newtime = hours + ":" + minutes + ":" + seconds;

                    if (newtime.equals("0:0:0")) {
                        tv_time.setText("00:00:00");
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + hours + ":0" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1)) {
                        tv_time.setText("0" + hours + ":0" + minutes + ":" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + hours + ":" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText(hours + ":0" + minutes + ":0" + seconds);
                    } else if (String.valueOf(hours).length() == 1) {
                        tv_time.setText("0" + hours + ":" + minutes + ":" + seconds);
                    } else if (String.valueOf(minutes).length() == 1) {
                        tv_time.setText(hours + ":0" + minutes + ":" + seconds);
                    } else if (String.valueOf(seconds).length() == 1) {
                        tv_time.setText(hours + ":" + minutes + ":0" + seconds);
                    } else {
                        tv_time.setText(hours + ":" + minutes + ":" + seconds);
                    }

                }

                @Override
                public void onFinish() {
                    setProgress(progress, endTime);
                    Intent intent = new Intent(getApplicationContext(), LandingActivity_Service_provider.class);
                    startActivity(intent);
                    finish();
                }
            };
            countDownTimer.start();
        }else {
            Toast.makeText(getApplicationContext(),"Please enter the value",Toast.LENGTH_LONG).show();
        }



    }
    public void setProgress(int startTime, int endTime) {
        progressBarView.setMax(endTime);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(startTime);

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }







    public void rejectalertdialogbox() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Activity_vendor_accept_newbookingrequest.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_vendor_reject_bookingrequest, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        final EditText edt_reason = (EditText) dialogView.findViewById(R.id.edt_reason);
        final Button btnskilnotmatch = (Button) dialogView.findViewById(R.id.btnskilnotmatch);
        final Button btnnotavailtime = (Button) dialogView.findViewById(R.id.btnnotavailtime);
        final Button btn_notinterested = (Button) dialogView.findViewById(R.id.btn_notinterested);
        final Button btn_locationfar = (Button) dialogView.findViewById(R.id.btn_locationfar);

        final Button btn_submit = (Button) dialogView.findViewById(R.id.btn_submit);

        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

         btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (reason.isEmpty()){
                    reason=edt_reason.getText().toString();
                }else
                {
                    if (Utils.isInternetConnected(Activity_vendor_accept_newbookingrequest.this)) {

                        Booking_reject_by_vendor();

                    }
                }

            }
        });


         btnskilnotmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reason="My Skillset is not matching";
                btnskilnotmatch.setBackgroundResource(R.drawable.greenbutton);
                btnskilnotmatch.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btnnotavailtime.setBackgroundResource(R.drawable.greenborderbutton);
                btnnotavailtime.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_notinterested.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notinterested.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_locationfar.setBackgroundResource(R.drawable.greenborderbutton);
                btn_locationfar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
            }
        });


        btnnotavailtime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                reason="Not Available at that time";
                btnnotavailtime.setBackgroundResource(R.drawable.greenbutton);
                btnnotavailtime.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btnskilnotmatch.setBackgroundResource(R.drawable.greenborderbutton);
                btnskilnotmatch.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_notinterested.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notinterested.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_locationfar.setBackgroundResource(R.drawable.greenborderbutton);
                btn_locationfar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
            }
        });


        btn_notinterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reason="Not Interested";
                btn_notinterested.setBackgroundResource(R.drawable.greenbutton);
                btn_notinterested.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btnskilnotmatch.setBackgroundResource(R.drawable.greenborderbutton);
                btnskilnotmatch.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnnotavailtime.setBackgroundResource(R.drawable.greenborderbutton);
                btnnotavailtime.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_locationfar.setBackgroundResource(R.drawable.greenborderbutton);
                btn_locationfar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
            }
        });

        btn_locationfar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reason="Location too far";
                btn_locationfar.setBackgroundResource(R.drawable.greenbutton);
                btn_locationfar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btnskilnotmatch.setBackgroundResource(R.drawable.greenborderbutton);
                btnskilnotmatch.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnnotavailtime.setBackgroundResource(R.drawable.greenborderbutton);
                btnnotavailtime.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_notinterested.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notinterested.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
            }
        });

        dialog.show();
    }
// --------------------------------------View booking details--------------------------------


    private void Getbookingdetails() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_vendor_accept_newbookingrequest.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl +View_Bookingdetail_Notificationclick ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);

                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("new_booking");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject dataObject = jsonArray.getJSONObject(i);

                                    id = dataObject.getString("id");
                                    booking_date = dataObject.getString("booking_date");
                                    from_time = dataObject.getString("from_time");
                                    to_time = dataObject.getString("to_time");
                                    work_description = dataObject.getString("work_description");
                                    date_time = dataObject.getString("date_time");
                                    start_location1 = dataObject.getString("start_location1");
                                }


                                try {

                                    DateFormat inputFormat = new SimpleDateFormat(booking_date);
                                    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

                                    Date date = inputFormat.parse(booking_date);
                                    String outputDateStr = outputFormat.format(date);
                                    System.out.println("date =====       "+outputDateStr);
                                    txtdattime.setText(booking_date+" "+from_time+" to "+to_time);
                                    txtlocation.setText(start_location1);
                                    txtbookdesc.setText(work_description);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(Activity_vendor_accept_newbookingrequest.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_vendor_accept_newbookingrequest.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("vendor_id", vendor_id);
                params.put("bookingId", bookingId);
                System.out.println("vendor_id=======       " + vendor_id);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(Activity_vendor_accept_newbookingrequest.this);
        rQueue.add(stringRequest);


    }



//---------------------------------------Booking accept api----------------------------------

    private void Booking_accept_by_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_vendor_accept_newbookingrequest.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Booking_accept_by_vendor,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("Booking_accept data", response.toString());
                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("Booking_accept_by_vendor data----  "+ response.toString());
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {


                                Toast.makeText(Activity_vendor_accept_newbookingrequest.this, "Booking accepted Succesfully.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), LandingActivity_Service_provider.class);
                                startActivity(intent);
                                finish();


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
                params.put("vendor_id", vendor_id);
                params.put("bookingId", bookingId);

                System.out.println("booking accept pram-      "+params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }



//--------------------------------------Booking reject---------------------------------------------------


    private void Booking_reject_by_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_vendor_accept_newbookingrequest.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Booking_cancel_by_vendor,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("Booking_reject data", response.toString());
                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("Booking_reject_by_vendor data----  "+ response.toString());
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

if (msg.contains("Booking Cancelled")){


    Intent intent = new Intent(getApplicationContext(), LandingActivity_Service_provider.class);
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
                params.put("user_id", vendor_id);
                params.put("bookingId", bookingId);
                params.put("reason", reason);

                System.out.println("booking reject param-      "+params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void Getbookingtime() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_vendor_accept_newbookingrequest.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl +getbookingtime ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);

                            System.out.println("bookig get time respone--------      "+jsonObject.toString());

                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {

                                  bookingtime = jsonObject.getString("time");

                                try {
                                    getdiff();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(Activity_vendor_accept_newbookingrequest.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_vendor_accept_newbookingrequest.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("vendor_id", vendor_id);
                params.put("booking_id", bookingId);
                System.out.println("vendor_id=======       " + vendor_id);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(Activity_vendor_accept_newbookingrequest.this);
        rQueue.add(stringRequest);


    }




    void getdiff() throws ParseException {

        String currentTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());

        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("hh:mm:ss");

        // Parsing the Time Period
        Date date1 = simpleDateFormat.parse(currentTime);
        Date date2 = simpleDateFormat.parse(bookingtime);

        // Calculating the difference in milliseconds
        long differenceInMilliSeconds
                = Math.abs(date2.getTime() - date1.getTime());

        // Calculating the difference in Hours
        long differenceInHours
                = (differenceInMilliSeconds / (60 * 60 * 1000))
                % 24;

        // Calculating the difference in Minutes
        long differenceInMinutes
                = (differenceInMilliSeconds / (60 * 1000)) % 60;

        // Calculating the difference in Seconds
        long differenceInSeconds
                = (differenceInMilliSeconds / 1000) % 60;

        // Printing the answer
        System.out.println(
                "Difference is " + differenceInHours + " hours "
                        + differenceInMinutes + " minutes "
                        + differenceInSeconds + " Seconds. ");

        System.out.println("  min--------      "+differenceInMinutes);
        if (String.valueOf(differenceInMinutes).equalsIgnoreCase("3")){

            System.out.println("3 min ");

            timervalue="181";
            fn_countdown();
        }else if(String.valueOf(differenceInMinutes).equalsIgnoreCase("2")){
            System.out.println("2 min ");
            timervalue="150";
            fn_countdown();
        }else if(String.valueOf(differenceInMinutes).equalsIgnoreCase("1")){

            System.out.println("1 min ");
            timervalue="70";
            fn_countdown();
        }
        else {


        }

    }


}
