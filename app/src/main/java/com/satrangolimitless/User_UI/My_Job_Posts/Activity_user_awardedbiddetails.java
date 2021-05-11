package com.satrangolimitless.User_UI.My_Job_Posts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.satrangolimitless.Adapter.AtachmentsBidsAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.AwardVendorBid;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.RejectVendorBid;


public class Activity_user_awardedbiddetails extends AppCompatActivity {
    Session session;
    TextView txttime ,txtproposal, txtmembersince, txtjobs, txtskills, txtestimattime, txtname, txtrank,txtrating,txtbidamount;
    String statusawrded, bid_amount, rating,name, proposal, esttime, id, jobid,vendrid , attachment,v_image,job_points,usersconn,total;
    Button btnviewprogress,btnreject,statusbtn;
    private RequestQueue rQueue;
    RecyclerView recv_bidsimg;
    AtachmentsBidsAdapter atachmentsBidsAdapter;
    ArrayList<String> attachlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_proposal_details_awarded);
        session = new Session(getApplicationContext());
        txttime = findViewById(R.id.txttime);
        txtname = findViewById(R.id.txtname);
        txtrank = findViewById(R.id.txtrank);
        txtestimattime = findViewById(R.id.txtestimattime);
        txtskills = findViewById(R.id.txtskills);
        txtjobs = findViewById(R.id.txtjobs);
        txtmembersince = findViewById(R.id.txtmembersince);
        txtproposal = findViewById(R.id.txtproposal);
        txtrating = findViewById(R.id.txtrating);
        txtbidamount = findViewById(R.id.txtbidamount);
        recv_bidsimg = findViewById(R.id.recv_bidsimg);
        btnviewprogress = findViewById(R.id.btnviewprogress);
        statusbtn = findViewById(R.id.statusbtn);
        btnreject = findViewById(R.id.btnreject);


        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            esttime = getIntent().getStringExtra("esttime");
            proposal = getIntent().getStringExtra("proposal");
            name = getIntent().getStringExtra("name");
            jobid = getIntent().getStringExtra("jobid");
            vendrid = getIntent().getStringExtra("vendrid");
            attachment = getIntent().getStringExtra("attachment");
            v_image = getIntent().getStringExtra("v_image");
            job_points = getIntent().getStringExtra("job_points");
            usersconn = getIntent().getStringExtra("usersconn");
            total = getIntent().getStringExtra("total");
            rating = getIntent().getStringExtra("rating");
            bid_amount = getIntent().getStringExtra("bid_amount");
            statusawrded = getIntent().getStringExtra("statusawrded");



            ArrayList aList= new ArrayList(Arrays.asList(attachment.split(",")));
            for(int i=0;i<aList.size();i++)
            {
                System.out.println("Activity_user_jobpost_biddetails -->   "+aList.get(i));
                String images= (String) aList.get(i);
                attachlist.add(images);
            }


            atachmentsBidsAdapter = new AtachmentsBidsAdapter(getApplicationContext(), attachlist);
            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
            recv_bidsimg.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
            recv_bidsimg.setAdapter(atachmentsBidsAdapter);
            atachmentsBidsAdapter.notifyDataSetChanged();

        }
        txtname.setText(name);
        txtproposal.setText(proposal);
        txtestimattime.setText(esttime);
        txtrank.setText("# "+job_points);
        txtjobs.setText(total);
        txtbidamount.setText(bid_amount);
        txttime.setText(esttime);

        if (rating != null && !rating.isEmpty() && !rating.equals("null") && !rating.equals("0")) {
            txtrating.setText(rating);

        }else {
            txtrating.setText("0");


        }

        btnviewprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                AwardBidapi();
            }
        });

        btnreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                RejectBidapi();
            }
        });


        if (statusawrded.contains("1")){

            statusbtn.setBackgroundResource(R.drawable.yellowbutton);
            statusbtn.setText("Awarded");
        }else {

            statusbtn.setBackgroundResource(R.drawable.grey_button);
            statusbtn.setText("Not Awarded");
        }

    }


    private void AwardBidapi() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_user_awardedbiddetails.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + AwardVendorBid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println("AwardBidapi --------      "+jsonObject.toString());

                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");


//                                System.out.println("profile======    " + jsonObject1.toString());
                                Intent intent = new Intent(getApplicationContext(), Activity_SetGoals_Payment.class);
                                startActivity(intent);
                                finish();

                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(Activity_user_awardedbiddetails.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_user_awardedbiddetails.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", session.getUserId());
                params.put("bid_id", id);
                params.put("job_id", jobid);
System.out.println("award api params----   "+session.getUserId()+"  "+id+"  "+jobid);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(Activity_user_awardedbiddetails.this);
        rQueue.add(stringRequest);


    }
    private void RejectBidapi() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_user_awardedbiddetails.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + RejectVendorBid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);

                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");


//                                System.out.println("profile======    " + jsonObject1.toString());


                                Toast.makeText(Activity_user_awardedbiddetails.this, msg, Toast.LENGTH_SHORT).show();

                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(Activity_user_awardedbiddetails.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_user_awardedbiddetails.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", session.getUserId());
                params.put("bid_id", id);
                params.put("job_id", jobid);

                return params;
            }
        };
        rQueue = Volley.newRequestQueue(Activity_user_awardedbiddetails.this);
        rQueue.add(stringRequest);


    }


}
