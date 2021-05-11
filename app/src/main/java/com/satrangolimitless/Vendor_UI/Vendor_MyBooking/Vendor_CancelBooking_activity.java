package com.satrangolimitless.Vendor_UI.Vendor_MyBooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Booking_cancel_by_vendor_after_accept;

public class Vendor_CancelBooking_activity extends AppCompatActivity {
    Session_vendor session_vendor;
    TextView txtamount, txtrating,txtname, txtdate, txtbookingid;
    Button btn_close, btn_cancel,btn_notmyskill, btn_nottime, btn_notacceptreshedule, btn_notrespon;
    ImageView ivback;
    String vendor_id, bookingId,image, price, reting;
    String reason="", date,name, bookingid, id,desc;
EditText edtdesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorbooking_cancel);
        session_vendor = new Session_vendor(getApplicationContext());
        txtamount = findViewById(R.id.txtamount);
        txtrating = findViewById(R.id.txtrating);
        txtdate = findViewById(R.id.txtdate);
        txtbookingid = findViewById(R.id.txtbookingid);
        txtname = findViewById(R.id.txtname);
        edtdesc = findViewById(R.id.edtdesc);
        ivback = findViewById(R.id.ivback);

        btn_close = findViewById(R.id.btn_close);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_notmyskill = findViewById(R.id.btn_notmyskill);
        btn_nottime = findViewById(R.id.btn_nottime);
        btn_notacceptreshedule = findViewById(R.id.btn_notacceptreshedule);
        btn_notrespon = findViewById(R.id.btn_notrespon);
        session_vendor = new Session_vendor(getApplicationContext());
        vendor_id = session_vendor.getUserId();
            if (getIntent() != null) {
                id = getIntent().getStringExtra("id");
                bookingid = getIntent().getStringExtra("bookingid");
                name = getIntent().getStringExtra("name");
                date = getIntent().getStringExtra("date");
                reting = getIntent().getStringExtra("reting");
                price = getIntent().getStringExtra("price");
                image = getIntent().getStringExtra("image");
                System.out.println("image  "+image);

            }
        txtname.setText(name);
        txtdate.setText(date);
        txtbookingid.setText(bookingid);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (reason.isEmpty()){
                    desc=edtdesc.getText().toString();
if (desc.isEmpty()){
    Toast.makeText(Vendor_CancelBooking_activity.this, "Please select reason or give any description", Toast.LENGTH_SHORT).show();
}else{

    Booking_cancel_by_vendor();
}
                }else {
                    Booking_cancel_by_vendor();

                }

            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_notmyskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reason = "My Skillset is not matching";
                btn_notmyskill.setBackgroundResource(R.drawable.greenbutton);
                btn_notmyskill.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btn_nottime.setBackgroundResource(R.drawable.greenborderbutton);
                btn_nottime.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_notacceptreshedule.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notacceptreshedule.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_notrespon.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notrespon.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));


            }
        });
        btn_nottime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reason = "Unable to attend the booking time";
                btn_nottime.setBackgroundResource(R.drawable.greenbutton);
                btn_nottime.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btn_notmyskill.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notmyskill.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_notacceptreshedule.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notacceptreshedule.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_notrespon.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notrespon.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

            }
        });

        btn_notacceptreshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = "User not accept for reshedule  request";
                btn_notacceptreshedule.setBackgroundResource(R.drawable.greenbutton);
                btn_notacceptreshedule.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btn_notmyskill.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notmyskill.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_nottime.setBackgroundResource(R.drawable.greenborderbutton);
                btn_nottime.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_notrespon.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notrespon.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

            }
        });
        btn_notrespon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = "User not responding properly";
                btn_notrespon.setBackgroundResource(R.drawable.greenbutton);
                btn_notrespon.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btn_notmyskill.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notmyskill.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_nottime.setBackgroundResource(R.drawable.greenborderbutton);
                btn_nottime.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btn_notacceptreshedule.setBackgroundResource(R.drawable.greenborderbutton);
                btn_notacceptreshedule.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


//    Booking reject api after accepting booking---------------------------------------------------------

    private void Booking_cancel_by_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(Vendor_CancelBooking_activity.this);
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
                            System.out.println("Booking_reject_accpetedby_vendor data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

                                if (msg.contains("Booking Cancelled")) {


                                    Intent intent = new Intent(Vendor_CancelBooking_activity.this, LandingActivity_Service_provider.class);
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
                params.put("bookingId", id);
                params.put("reason", reason);

                System.out.println("acceptedbooking reject param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}

