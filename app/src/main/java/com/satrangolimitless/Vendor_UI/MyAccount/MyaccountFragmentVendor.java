package com.satrangolimitless.Vendor_UI.MyAccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Myaccount.Activity_Transactionhistory;
import com.satrangolimitless.User_UI.Myaccount.Activity_userwithdrawfund_addfund;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.MyAccountdetails_user_side;
import static com.satrangolimitless.Utils.Base_Url.vendor_account_detail;


public class MyaccountFragmentVendor extends Fragment {

    TextView txt_bookings, txtbalance, txtacname,
            txtreferrals,
            txtjobpost,txtmonth_earnings,txtprevious_month_earnings,txtbids;
    Session session;
    String userid;
    CircleImageView imgproac;
    private RequestQueue rQueue;
LinearLayout ll_addwithfunds,lltranshistry,ll_myreviews;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_myaccount_service_provider, container, false);

        session=new Session(getActivity());

        imgproac=root.findViewById(R.id.imgproac);
//        txtjobpost=root.findViewById(R.id.txtjobpost);
        txtbids=root.findViewById(R.id.txtbids);
        txt_bookings=root.findViewById(R.id.txt_bookings);
        txtbalance=root.findViewById(R.id.txtbalance);
        txtacname=root.findViewById(R.id.txtacname);
        txtreferrals=root.findViewById(R.id.txtreferrals);
        txtmonth_earnings=root.findViewById(R.id.txtmonth_earnings);
        txtprevious_month_earnings=root.findViewById(R.id.txtprevious_month_earnings);
        ll_addwithfunds=root.findViewById(R.id.ll_addwithfunds);
        lltranshistry=root.findViewById(R.id.lltranshistry);
        ll_myreviews=root.findViewById(R.id.ll_myreviews);

        userid=session.getUserId();
        Myaccountdetails();

        lltranshistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Activity_Transactionhistory.class);
                startActivity(intent);

            }
        });
        ll_addwithfunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Activity_userwithdrawfund_addfund.class);
                startActivity(intent);


            }
        });

        ll_myreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), Activityvendr_reviews.class);
                startActivity(intent);

            }
        });
        return root;
    }


    private void Myaccountdetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + vendor_account_detail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
                                JSONObject userJson = jsonObject.getJSONObject("data");


                                String id = userJson.getString("id");
                                String fname = userJson.getString("fname");
                                String image = userJson.getString("image");
                                String total_jobs = userJson.getString("total_jobs");
                                String total_bid = userJson.getString("total_bid");
                                String total_earnings = userJson.getString("total_earnings");
                                String month_earnings = userJson.getString("month_earnings");
//                                String previous_month_earnings = userJson.getString("previous_month_earnings");
                                System.out.println("total_bid --   "+total_bid);
                                txt_bookings.setText(total_jobs);
                                 txtreferrals.setText("");
                                txtprevious_month_earnings.setText("");
                                txtmonth_earnings.setText("");
                                txtbids.setText(total_bid);
                                txtacname.setText(fname);

                                Glide.with(getActivity())
                                        .load(Image_url + image)
                                        .into(imgproac);


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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("vendor_id",userid );
                return params;
            }
        };

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);

    }

}