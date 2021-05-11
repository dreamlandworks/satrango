package com.satrangolimitless.Vendor_UI.Vendor_MyBooking;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.Booknow.Activity_Multimove_booking;
import com.satrangolimitless.Booknow.BooknowSingleMoveActivity;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.Vendor_UI.Update_vendor_tarrif_details_slots;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Booking_reshedule_by_vendor_after_accept;

public class Vendor_ResheduleBooking_activity extends AppCompatActivity {

    private int mHour, mMinute;

    Session_vendor session_vendor;
    TextView txtdatepick, txtfromtime, txttotime, txtdate,
            txtbookingid,txtsheduledate,txtfrmtotime,
            txtamount, txtrating,
            txtname;
    Button btn_submit,Vcancel;
    String vendor_id, bookingId, image,format,
            price,
            reting,totime,frmtime,booking_date;
    String reason, date,
            name, bookingid, id;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timepickerdialog;
    private int CalendarHour, CalendarMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorbooking_reshedule);
        session_vendor = new Session_vendor(getApplicationContext());

        txtsheduledate = findViewById(R.id.txtsheduledate);
        txtdate = findViewById(R.id.txtdate);
        txtname = findViewById(R.id.txtname);
        txtamount = findViewById(R.id.txtamount);
        txtbookingid = findViewById(R.id.txtbookingid);
        txtrating = findViewById(R.id.txtrating);
        txttotime = findViewById(R.id.txttotime);
        txtfromtime = findViewById(R.id.txtfromtime);
        txtdatepick = findViewById(R.id.txtdatepick);
        txtfrmtotime = findViewById(R.id.txtfrmtotime);
        btn_submit = findViewById(R.id.btn_submit);
        Vcancel = findViewById(R.id.Vcancel);


        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            bookingid = getIntent().getStringExtra("bookingid");
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            reting = getIntent().getStringExtra("reting");
            price = getIntent().getStringExtra("price");
            image = getIntent().getStringExtra("image");
            frmtime = getIntent().getStringExtra("frmtime");
            totime = getIntent().getStringExtra("totime");

        }
        txtname.setText(name);
        txtbookingid.setText(bookingid);
        txtamount.setText(price);
        txtrating.setText(reting);
        txtdate.setText(date);
        txtsheduledate.setText(date);
        txtfrmtotime.setText(frmtime+" to "+totime);



        txtdatepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Vendor_ResheduleBooking_activity.this,


                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                txtdatepick.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                txtdatepick.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

                                booking_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        txtfromtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time



                timepickerdialog = new TimePickerDialog(Vendor_ResheduleBooking_activity.this,
                        new TimePickerDialog.OnTimeSetListener() {


                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }


                                txtfromtime.setText(hourOfDay + ":" + minute + format);
                                txtfromtime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);
                                frmtime = txtfromtime.getText().toString();

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();








            }
        });

        txttotime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                timepickerdialog = new TimePickerDialog(Vendor_ResheduleBooking_activity.this,
                        new TimePickerDialog.OnTimeSetListener() {


                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }


                                txttotime.setText(hourOfDay + ":" + minute + format);
                                txttotime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);
                                totime = txttotime.getText().toString();

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();




            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Booking_reshedule_by_vendor();

            }
        });

        Vcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        }


//Vendor reshedule accepted activity


    private void Booking_reshedule_by_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(Vendor_ResheduleBooking_activity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Booking_reshedule_by_vendor_after_accept
                ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("Booking_accreject data", response);
                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("Booking_reject_accpetedby_vendor data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

                                if (msg.contains("Booking Cancelled")) {


                                    Intent intent = new Intent(Vendor_ResheduleBooking_activity.this, LandingActivity_Service_provider.class);
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
                params.put("booking_date", booking_date);
                params.put("bookingId", id);
                params.put("from_time", frmtime);
                params.put("to_time", totime);

                System.out.println(" Booking_reshedule_by_vendor_after_accept param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}