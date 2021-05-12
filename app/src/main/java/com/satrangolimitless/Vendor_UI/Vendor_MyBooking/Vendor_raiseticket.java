package com.satrangolimitless.Vendor_UI.Vendor_MyBooking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.complaints;

public class Vendor_raiseticket extends AppCompatActivity {
    private RequestQueue rQueue;
    String reason,
            message;
    Button btnothers,
            btnuser,
            btnbugs,
            btnfundtrans,
            btnbook;
    Session_vendor session_vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_raiseticket);
        session_vendor = new Session_vendor(getApplicationContext());

        btnothers = findViewById(R.id.btnothers);
        btnuser = findViewById(R.id.btnuser);
        btnbugs = findViewById(R.id.btnbugs);
        btnfundtrans = findViewById(R.id.btnfundtrans);
        btnbook = findViewById(R.id.btnbook);


        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = "Booking";
                btnbook.setBackgroundResource(R.drawable.greenbutton);
                btnbook.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btnothers.setBackgroundResource(R.drawable.greenborderbutton);
                btnothers.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnuser.setBackgroundResource(R.drawable.greenborderbutton);
                btnuser.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnfundtrans.setBackgroundResource(R.drawable.greenborderbutton);
                btnfundtrans.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnbugs.setBackgroundResource(R.drawable.greenborderbutton);
                btnbugs.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));


            }
        });

        btnfundtrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reason = "funds";
                btnfundtrans.setBackgroundResource(R.drawable.greenbutton);
                btnfundtrans.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btnothers.setBackgroundResource(R.drawable.greenborderbutton);
                btnothers.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnuser.setBackgroundResource(R.drawable.greenborderbutton);
                btnuser.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnbook.setBackgroundResource(R.drawable.greenborderbutton);
                btnbook.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnbugs.setBackgroundResource(R.drawable.greenborderbutton);
                btnbugs.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

            }
        });
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = "user";
                btnuser.setBackgroundResource(R.drawable.greenbutton);
                btnuser.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btnothers.setBackgroundResource(R.drawable.greenborderbutton);
                btnothers.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnfundtrans.setBackgroundResource(R.drawable.greenborderbutton);
                btnfundtrans.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnbook.setBackgroundResource(R.drawable.greenborderbutton);
                btnbook.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnbugs.setBackgroundResource(R.drawable.greenborderbutton);
                btnbugs.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

            }
        });
        btnbugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = "bugs";
                btnbugs.setBackgroundResource(R.drawable.greenbutton);
                btnbugs.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btnothers.setBackgroundResource(R.drawable.greenborderbutton);
                btnothers.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnfundtrans.setBackgroundResource(R.drawable.greenborderbutton);
                btnfundtrans.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnbook.setBackgroundResource(R.drawable.greenborderbutton);
                btnbook.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnuser.setBackgroundResource(R.drawable.greenborderbutton);
                btnuser.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

            }
        });

        btnothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = "bugs";
                btnothers.setBackgroundResource(R.drawable.greenbutton);
                btnothers.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                btnbugs.setBackgroundResource(R.drawable.greenborderbutton);
                btnbugs.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnfundtrans.setBackgroundResource(R.drawable.greenborderbutton);
                btnfundtrans.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnbook.setBackgroundResource(R.drawable.greenborderbutton);
                btnbook.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

                btnuser.setBackgroundResource(R.drawable.greenborderbutton);
                btnuser.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));

            }
        });

    }


    private void CallComplaintsApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + complaints,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");
                            Log.e("<><><>namiikdnjcdshnj", jsonObject.toString());
                            if (result.equals(true)) {

                                Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();

                            } else {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", session_vendor.getUserId());
                params.put("reason", reason);
                params.put("message", message);
                params.put("type", "vendor");
                return params;
            }
        };

        rQueue = Volley.newRequestQueue(getApplicationContext());
        rQueue.add(stringRequest);

    }

}