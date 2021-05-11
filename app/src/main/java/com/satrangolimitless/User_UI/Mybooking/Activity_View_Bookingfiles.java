package com.satrangolimitless.User_UI.Mybooking;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.Adapter.AtachmentBookingpendingAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Booking_reshedule_by_vendor_after_accept;
import static com.satrangolimitless.Utils.Base_Url.User_mark_appropriate_doc;

public class Activity_View_Bookingfiles extends AppCompatActivity {
    String attachm, bookingid, date,id;
    Session session;
    TextView txtbookid, txtdate;
    Button btnaprop, btnsendback;
    AtachmentBookingpendingAdapter atachmentshowAdapter;
    ArrayList<String> attachlist = new ArrayList<>();
    RecyclerView recviewfiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view__bookingfiles);
        session=new Session(getApplicationContext());
        txtbookid = findViewById(R.id.txtbookid);
        txtdate = findViewById(R.id.txtdate);
        recviewfiles = findViewById(R.id.recviewfiles);
        btnaprop = findViewById(R.id.btnaprop);
        btnsendback = findViewById(R.id.btnsendback);

        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            attachm = getIntent().getStringExtra("attachm");
            bookingid = getIntent().getStringExtra("bookingid");
            date = getIntent().getStringExtra("date");
        }
        txtbookid.setText(bookingid);
        txtdate.setText(date);






        btnaprop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User_Appropriate();
            }
        });


        btnsendback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        ArrayList aList = new ArrayList(Arrays.asList(attachm.split(",")));
        for (int i = 0; i < aList.size(); i++) {
            System.out.println(" -->   " + aList.get(i));
            String images = (String) aList.get(i);
            attachlist.add(images);
        }


        atachmentshowAdapter = new AtachmentBookingpendingAdapter(getApplicationContext(), attachlist);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
        recviewfiles.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recviewfiles.setAdapter(atachmentshowAdapter);
        atachmentshowAdapter.notifyDataSetChanged();

    }




    private void User_Appropriate() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_View_Bookingfiles.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + User_mark_appropriate_doc
                ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("User_Appropriate", response);
                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("User_Appropriate----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

                                onBackPressed();
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
                System.out.println("User_Appropriate param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}