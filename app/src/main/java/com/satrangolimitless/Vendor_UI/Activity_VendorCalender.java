package com.satrangolimitless.Vendor_UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.Adapter.Adapter_Calenderbookingsbydate;
import com.satrangolimitless.Adapter.AtachmentBookingpendingAdapter;
import com.satrangolimitless.Adapter.AtachmentshowAdapter;
import com.satrangolimitless.Adapter.CalenderdateAdapter;
import com.satrangolimitless.Interface.onDateclick;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.FAQ.FaqAdapter;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.model.Calender_bookingbydate;
import com.satrangolimitless.model.Categories_Popularactivity_Model;
import com.satrangolimitless.model.GetFaqModel;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.bookingListByDate;
import static com.satrangolimitless.Utils.Base_Url.get_week_days;

public class Activity_VendorCalender extends AppCompatActivity implements onDateclick {
 RecyclerView recv_dates,recvbookings;
 String date;
 Session_vendor session_vendor;
    CalenderdateAdapter calenderdateAdapter;
    Adapter_Calenderbookingsbydate adapter_calenderbookingsbydate;
    ArrayList<String> attachlist = new ArrayList<>();
    ArrayList<Calender_bookingbydate> calender_bookingbydates = new ArrayList<>();
     @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_calender);
        session_vendor=new Session_vendor(getApplicationContext());
         recv_dates=findViewById(R.id.recv_dates);
         recvbookings=findViewById(R.id.recvbookings);


           date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            System.out.println("Activity_VendorCalender date <>><><><><><><>    "+date);
         Booking_by_datevendor();
         Bookinglist_by_datevendor(date);
    }



    private void Booking_by_datevendor() {

        final ProgressDialog progressDialog = new ProgressDialog(Activity_VendorCalender.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + get_week_days
                ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("Booking_by_datevendor data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

                                JSONArray jsonArray = obj.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());


                                    attachlist.add(jsonArray.getString(i).toString());
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();

                            e.printStackTrace();

                        }

                        calenderdateAdapter = new CalenderdateAdapter(getApplicationContext(), attachlist,Activity_VendorCalender.this);
                        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
                        recv_dates.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
                        recv_dates.setAdapter(calenderdateAdapter);
                        calenderdateAdapter.notifyDataSetChanged();
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
                params.put("vendor_id", session_vendor.getUserId());
                params.put("date", date);

                System.out.println(" Booking_by_datevendor data----   param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void Bookinglist_by_datevendor(final String date) {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_VendorCalender.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + bookingListByDate
                ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("Bookinglist_by_datevendor data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

                                JSONArray jsonArray = obj.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Calender_bookingbydate hero = new Calender_bookingbydate(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("bookingId"),
                                            jsonObject.getString("user_id"),
                                            jsonObject.getString("vendor_id"),
                                            jsonObject.getString("service_id"),
                                            jsonObject.getString("booking_date"),
                                            jsonObject.getString("from_time"),
                                            jsonObject.getString("to_time"),
                                            jsonObject.getString("start_location1"),
                                            jsonObject.getString("to_location1"),
                                            jsonObject.getString("end_location1"),
                                            jsonObject.getString("to_loaction2"),
                                            jsonObject.getString("to_location3"),
                                            jsonObject.getString("work_description"),
                                            jsonObject.getString("upload_doc"),
                                            jsonObject.getString("job_estimate"),
                                            jsonObject.getString("weight"),
                                            jsonObject.getString("date_time"),
                                            jsonObject.getString("job_status"),
                                            jsonObject.getString("cancel_by"),
                                            jsonObject.getString("reason"),
                                            jsonObject.getString("type"),
                                            jsonObject.getString("booking_type"),
                                            jsonObject.getString("otp"),
                                            jsonObject.getString("verify_otp"),
                                            jsonObject.getString("start_time"),
                                            jsonObject.getString("paused_time"),
                                            jsonObject.getString("resume_time"),
                                            jsonObject.getString("complete_time"),
                                            jsonObject.getString("modified_date"),
                                            jsonObject.getString("amount"),
                                            jsonObject.getString("extra_charge"),
                                            jsonObject.getString("extra_material"),
                                            jsonObject.getString("payment_status"),
                                            jsonObject.getString("vendor_doc"),
                                            jsonObject.getString("appropriate_status"),
                                            jsonObject.getString("extra_charge_status")

                                    );

                                    calender_bookingbydates .add(hero);


                                }

                                adapter_calenderbookingsbydate = new Adapter_Calenderbookingsbydate(calender_bookingbydates, Activity_VendorCalender.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Activity_VendorCalender.this);
                                recvbookings.setLayoutManager(mLayoutManger);
                                recvbookings.setLayoutManager(new LinearLayoutManager(Activity_VendorCalender.this, RecyclerView.VERTICAL, true));
                                recvbookings.setItemAnimator(new DefaultItemAnimator());
                                recvbookings.setAdapter(adapter_calenderbookingsbydate);
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
                params.put("vendor_id", session_vendor.getUserId());
                params.put("date", date);

                System.out.println(" Booking_by_datevendor data----   param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }






    @Override
    public void ondateclick(String name) {
        String[] splitStr = name.split("\\s+");
       String odate=splitStr[0];
        System.out.println("  CalenderdateAdapterodate   -----     "+odate);


        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd" );
        Date date;
        try {
            date = originalFormat.parse(odate);
            System.out.println("Old Format :   " + originalFormat.format(date));
            System.out.println("New Format :   " + targetFormat.format(date));


            String datettt= targetFormat.format(date);
            Bookinglist_by_datevendor(datettt);


        } catch (ParseException ex) {
            // Handle Exception.
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
