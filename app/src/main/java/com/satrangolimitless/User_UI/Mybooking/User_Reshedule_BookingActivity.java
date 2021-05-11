package com.satrangolimitless.User_UI.Mybooking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.Adapter.Adapter_Vendor_Availibility_time;
import com.satrangolimitless.Dynamicview.Choose_From_Time;
import com.satrangolimitless.Dynamicview.Choose_To_Time;
import com.satrangolimitless.Interface.onFrom_timeclick;
import com.satrangolimitless.Interface.onTo_timeclick;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Booking_reshedule_by_vendor_after_accept;
import static com.satrangolimitless.Utils.Base_Url.Get_vendor_availability_time;

public class User_Reshedule_BookingActivity extends AppCompatActivity implements onFrom_timeclick, onTo_timeclick {
    DatePickerDialog datePickerDialog;
    String date, name, bookingid, id, vendorid,booking_date,otp,frmtime,price;
    ImageView ivback;
    TextView txtfrmtimetotime,txdate, txtotp, txtname, txtdate, txtbookid, signIn_DATE,txtcharge;
    ProgressDialog progressDialog;
    String totime = "", fromtime = "";
    Button btnsubmit, btncancel;
    Dialog dialogpleasewait;
    RecyclerView rec_availibity_time;
    Adapter_Vendor_Availibility_time adapter;
    Session session;
    ArrayList<Choose_From_Time> choose_from_times = new ArrayList<>();
    ArrayList<Choose_To_Time> choose_to_times = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userresheduled);
        rec_availibity_time = findViewById(R.id.rec_availibity_time);
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            bookingid = getIntent().getStringExtra("bookingid");
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            vendorid = getIntent().getStringExtra("vendorid");
            totime = getIntent().getStringExtra("totime");
            otp = getIntent().getStringExtra("otp");
            frmtime = getIntent().getStringExtra("frmtime");
            price = getIntent().getStringExtra("price");
            System.out.println("onCreate: "+id+
                    bookingid+
                    name+
                    date+
                    vendorid+
                    totime+
                    otp+
                    frmtime+
                    price );
        }

        signIn_DATE = findViewById(R.id.signIn_DATE);
        txtfrmtimetotime = findViewById(R.id.txtfrmtimetotime);
        txdate = findViewById(R.id.txdate);
        txtotp = findViewById(R.id.txtotp);
        txtname = findViewById(R.id.txtname);
        txtdate = findViewById(R.id.txtdate);
        txtbookid = findViewById(R.id.txtbookid);
        txtcharge = findViewById(R.id.txtcharge);
        btncancel = findViewById(R.id.btncancel);
        btnsubmit = findViewById(R.id.btnsubmit);

        txtbookid.setText(bookingid);
        txtfrmtimetotime.setText(frmtime+" - "+totime);
        txdate.setText(date);
        txtotp.setText(otp);
        txtname.setText(name);
        txtdate.setText(date);
        txtcharge.setText("₹ "+price);







        availibilitytimelist();
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking_reshedule_by_vendor();
            }
        });


        signIn_DATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(User_Reshedule_BookingActivity.this,


                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                signIn_DATE.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                signIn_DATE.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

                                booking_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });
    }


    private void availibilitytimelist() {

        progressDialog = new ProgressDialog(User_Reshedule_BookingActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Get_vendor_availability_time,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            System.out.println("availibilitytimelist api-------       " + obj.toString());
                            String result = obj.getString("result");
                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = obj.getJSONArray("times");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    fromtime = dataObject.getString("from_time");
                                    totime = dataObject.getString("to_time");


                                    System.out.println("from time**********       " + fromtime);


                                    JSONArray from_timearray = new JSONArray(fromtime);

                                    for (int ij = 0; ij < from_timearray.length(); ij++) {
                                        JSONObject object = from_timearray.getJSONObject(ij);
                                        String id = object.getString("FromTime");

                                        System.out.println("FromTime----^^^^^^^       " + id);
                                        Choose_From_Time choose_from_time = new Choose_From_Time(id);
                                        choose_from_times.add(choose_from_time);
                                    }


                                    JSONArray to_timearray = new JSONArray(totime);

                                    for (int ik = 0; ik < to_timearray.length(); ik++) {
                                        JSONObject object = to_timearray.getJSONObject(ik);
                                        String id = object.getString("ToTime");

                                        System.out.println("ToTime----^^^^^^^       " + id);
                                        Choose_To_Time choose_to_time = new Choose_To_Time(id);
                                        choose_to_times.add(choose_to_time);
                                    }


                                }


                                adapter = new Adapter_Vendor_Availibility_time(choose_from_times, choose_to_times, User_Reshedule_BookingActivity.this, User_Reshedule_BookingActivity.this, User_Reshedule_BookingActivity.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(User_Reshedule_BookingActivity.this);
                                rec_availibity_time.setLayoutManager(mLayoutManger);

                                rec_availibity_time.setLayoutManager(new LinearLayoutManager(User_Reshedule_BookingActivity.this, RecyclerView.HORIZONTAL, false));
                                rec_availibity_time.setLayoutManager(new GridLayoutManager(User_Reshedule_BookingActivity.this, 2));
                                rec_availibity_time.setItemAnimator(new DefaultItemAnimator());
                                rec_availibity_time.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(User_Reshedule_BookingActivity.this, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("vendor_id", vendorid);
System.out.println("vendor_id ---- "+ vendorid);

                return params;
            }
        };
        VolleySingleton.getInstance(User_Reshedule_BookingActivity.this).addToRequestQueue(stringRequest);
    }


    public void onitemclick(String n) {
        fromtime = n;


        System.out.println("fromtime---      " + fromtime);
    }

    public void ontotimeclick(String n) {
        totime = n;

        System.out.println("totime---      " + totime);

    }


//    User reshedule booking


    private void Booking_reshedule_by_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(User_Reshedule_BookingActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        showprocessreqdialogue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Booking_reshedule_by_vendor_after_accept
                ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("Booking_accreject data", response);
                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("Booking_reshdule user side----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                showreqsuccessdialogue();

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
                params.put("bookingId", bookingid);
                params.put("from_time", fromtime);
                params.put("to_time", totime);

                System.out.println("re shedule param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void showprocessreqdialogue() {


        dialogpleasewait= new Dialog(getApplicationContext());
        dialogpleasewait.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogpleasewait.setContentView(R.layout.dialog_reshedule_wait);
        dialogpleasewait.setCancelable(true);
        dialogpleasewait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogpleasewait.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;


//        final ImageView imgclose = (ImageView) dialogpleasewait.findViewById(R.id.imgclose);

        dialogpleasewait.show();
        dialogpleasewait.getWindow().setAttributes(lp);

    }


    public void showreqsuccessdialogue() {


        dialogpleasewait= new Dialog(getApplicationContext());
        dialogpleasewait.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogpleasewait.setContentView(R.layout.dialog_reshedule_success_wait);
        dialogpleasewait.setCancelable(true);
        dialogpleasewait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogpleasewait.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;


//        final ImageView imgclose = (ImageView) dialogpleasewait.findViewById(R.id.imgclose);

        dialogpleasewait.show();
        dialogpleasewait.getWindow().setAttributes(lp);

    }

}


 

