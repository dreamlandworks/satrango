package com.satrangolimitless.Vendor_UI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_Vendor_HomeUpcomingBooking;
import com.satrangolimitless.Adapter.Adapter_Vendor_NewHot_Jobs;
import com.satrangolimitless.Adapter.Adapter_leaderboards;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Home_user.Selectlocationhomepage;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.Vendor_UI.My_Bids.Fragment_MyBids_Vendor;
import com.satrangolimitless.Vendor_UI.My_Bids.New_Jobs_Fragment;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_MyBooking;
import com.satrangolimitless.Vendor_UI.Vendor_Settings.Vendor_SettingsFragment;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.model.Confirm_Bid;
import com.satrangolimitless.model.Leaderboardsmodel;
import com.satrangolimitless.model.Vendor_New_Jobs_model;
import com.satrangolimitless.model.Vendor_UpcomingBooking;
import com.satrangolimitless.model.Vendor_hotjobs;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Get_upcoming_booking_Vendor;
import static com.satrangolimitless.Utils.Base_Url.hotJobs;
import static com.satrangolimitless.Utils.Base_Url.leaderboard;
import static com.satrangolimitless.Utils.Base_Url.vendor_online_offline_status;


public class Homefragment_service_provider extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Session_vendor session_vendor;
    Session session;
    TextView viewmore, tv_user, txt_mylocation;
    TextView txturank, txtovr_rating, txtconnection;
    GPSTracker gps;
    double latitude, longitude;
    String longi, latt, vendor_id, onlinestatus;
    RecyclerView rec_homeupcoming, recvleaderboards,rec_hotjobs;
    ArrayList<Vendor_UpcomingBooking> vendor_upcomingBookings = new ArrayList<>();
    ArrayList<Confirm_Bid> confirm_bidArrayList = new ArrayList<>();
    Adapter_Vendor_HomeUpcomingBooking adapter_vendor_upcomingBooking;
    ArrayList<Leaderboardsmodel> leaderboardsmodels = new ArrayList<>();
    Adapter_leaderboards adapter_leaderboards;
    ArrayList<Vendor_hotjobs> vendor_hotjobs = new ArrayList<>();
    Adapter_Vendor_NewHot_Jobs adapter_vendor_newHot_jobs;
    LinearLayout llupcomingviewmore,llhotjobviewmore;

    RelativeLayout rlonline, rloffline;
    TextView txtoffline, txtonline;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_homefragment, container, false);
        viewmore = root.findViewById(R.id.viewmore);
        session_vendor = new Session_vendor(getActivity());
        session = new Session(getActivity());
        vendor_id = session_vendor.getUserId();
        onlinestatus = session_vendor.get_onlinestatus();
        tv_user = root.findViewById(R.id.tv_user);
        txt_mylocation = root.findViewById(R.id.txt_mylocation);
        rec_homeupcoming = root.findViewById(R.id.rec_homeupcoming);
        recvleaderboards = root.findViewById(R.id.recvleaderboards);
        rec_hotjobs = root.findViewById(R.id.rec_hotjobs);
        rlonline = root.findViewById(R.id.rlonline);
        rloffline = root.findViewById(R.id.rloffline);
        txtonline = root.findViewById(R.id.txtonline);
        txtoffline = root.findViewById(R.id.txtoffline);
        txturank = root.findViewById(R.id.txturank);
        txtovr_rating = root.findViewById(R.id.txtovr_rating);
        txtconnection = root.findViewById(R.id.txtconnection);
        llhotjobviewmore = root.findViewById(R.id.llhotjobviewmore);
        llupcomingviewmore = root.findViewById(R.id.llupcomingviewmore);

        viewmore.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        LandingActivity_Service_provider.Layout_hader.setVisibility(View.VISIBLE);
        System.out.println("onlinestatus<><><><>        " + onlinestatus);
        if (onlinestatus.contains("1")) {

            rloffline.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.offline));
            rlonline.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.online));
            txtonline.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
            txtoffline.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

        } else {

            rlonline.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.offline));
            rloffline.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.online));
            txtonline.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            txtoffline.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));

        }

        tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity.class);
                startActivity(intent);
            }
        });


        txt_mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Selectlocationhomepage.class);
                intent.putExtra("id", "vendor");
                startActivity(intent);

            }
        });

        if (session.getUserhome_location() != null && !session.getUserhome_location().isEmpty() && !session.getUserhome_location().equals("null") && !session.getUserhome_location().equals("0")) {

            System.out.println("session vendrside location-----  " + session.getUserhome_location());

            txt_mylocation.setText(session.getUserhome_location());
            longi = session.getUserhome_long();
            latt = session.getUserhome_lat();

        } else {
            System.out.println("vendrside getlatlonmthd****  ");
            getlatlang();
        }

        rlonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rloffline.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.offline));
                rlonline.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.online));
                txtonline.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
                txtoffline.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                vendor_online_offline_status("1");
            }
        });

        rloffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlonline.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.offline));
                rloffline.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.online));
                txtonline.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                txtoffline.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
                vendor_online_offline_status("0");
            }
        });



        llhotjobviewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = null;
                fragment = new Fragment_MyBids_Vendor();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });






        llupcomingviewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new Vendor_MyBooking();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });

        UpcomingBookingsApi();
        leaderboardApi();
        Hotjobs();
        return root;

    }


    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            gps = new GPSTracker(getActivity());
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                latt = String.valueOf(latitude);
                longi = String.valueOf(longitude);
                System.out.println("lat long -----   " + latitude + "    " + longitude);
                try {
                    Geocoder geocoder;
                    List<Address> addresses = null;
                    geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    System.out.println("addressssssssssssss --------    " + knownName);
                    txt_mylocation.setText(knownName);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                gps.showSettingsAlert();

            }

        }

    }


//leaderboards and rank api

    private void leaderboardApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + leaderboard;
        leaderboardsmodels.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vendor_id)
                .addBodyParameter("lat", "22.4545")
                .addBodyParameter("lang", "75.454")

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");
                            String avg_rating = jsonObject.getString("avg_rating");
                            String rank = jsonObject.getString("rank");
                            String total_user_conn = jsonObject.getString("total_user_conn");
                            String name = jsonObject.getString("name");
                            String image = jsonObject.getString("image");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                System.out.println("leaderboardApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Leaderboardsmodel allCommunityModel = new Leaderboardsmodel(
                                            dataObject.getString("id"),
                                            dataObject.getString("fname"),
                                            dataObject.getString("image"),
                                            dataObject.getString("distance"),
                                            dataObject.getString("rank"));
                                    leaderboardsmodels.add(allCommunityModel);
                                }
                                txtconnection.setText("(" + total_user_conn + ")");
                                txtovr_rating.setText(avg_rating);
                                txturank.setText("# " + rank);

                            } else {

                            }
                            adapter_leaderboards = new Adapter_leaderboards(leaderboardsmodels, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recvleaderboards.setLayoutManager(mLayoutManger);
                            recvleaderboards.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

                            recvleaderboards.setItemAnimator(new DefaultItemAnimator());
                            recvleaderboards.setAdapter(adapter_leaderboards);
                            adapter_leaderboards.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_my_join", anError.toString());
                    }
                });
    }


//    Upcoming booking


    private void UpcomingBookingsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Get_upcoming_booking_Vendor;
        vendor_upcomingBookings.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vendor_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {

                            System.out.println("REsponse---------  " + jsonObject.toString());
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("new_booking");
                                JSONArray jsonArray2 = jsonObject.getJSONArray("confirm_bid");
                                System.out.println("UpcomingBookingsApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_UpcomingBooking allCommunityModel = new Vendor_UpcomingBooking(
                                            dataObject.getString("id"),
                                            dataObject.getString("bookingId"),
                                            dataObject.getString("user_id"),
                                            dataObject.getString("vendor_id"),
                                            dataObject.getString("service_id"),
                                            dataObject.getString("booking_date"),
                                            dataObject.getString("from_time"),
                                            dataObject.getString("to_time"),
                                            dataObject.getString("start_location1"),
                                            dataObject.getString("to_location1"),
                                            dataObject.getString("end_location1"),
                                            dataObject.getString("to_loaction2"),
                                            dataObject.getString("to_location3"),
                                            dataObject.getString("work_description"),
                                            dataObject.getString("upload_doc"),
                                            dataObject.getString("job_estimate"),
                                            dataObject.getString("weight"),
                                            dataObject.getString("date_time"),
                                            dataObject.getString("job_status"),
                                            dataObject.getString("cancel_by"),
                                            dataObject.getString("reason"),
                                            dataObject.getString("type"),
                                            dataObject.getString("booking_type"),
                                            dataObject.getString("otp"),
                                            dataObject.getString("verify_otp"),
                                            dataObject.getString("start_time"),
                                            dataObject.getString("paused_time"),
                                            dataObject.getString("resume_time"),
                                            dataObject.getString("complete_time"),
                                            dataObject.getString("modified_date"),
                                            dataObject.getString("amount"),
                                            dataObject.getString("extra_charge"),
                                            dataObject.getString("extra_material"),
                                            dataObject.getString("payment_status"),
                                            dataObject.getString("vendor_doc"),
                                            dataObject.getString("appropriate_status"),
                                            dataObject.getString("min_charge"),
                                            dataObject.getString("user_name"),
                                            dataObject.getString("user_contact"),
                                            dataObject.getString("address"),
                                            dataObject.getString("user_image"),
                                            dataObject.getString("user_rating")

                                    );
                                    vendor_upcomingBookings.add(allCommunityModel);
                                }

                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    Log.e("jsonarray2", jsonArray2.toString());
                                    JSONObject dataObject2 = jsonArray2.getJSONObject(i);
                                    Confirm_Bid confirm_bid = new Confirm_Bid(
                                            dataObject2.getString("id"),
                                            dataObject2.getString("job_id"),
                                            dataObject2.getString("vendor_id"),
                                            dataObject2.getString("bid_amount"),
                                            dataObject2.getString("proposal"),
                                            dataObject2.getString("estimated_time"),
                                            dataObject2.getString("attachment"),
                                            dataObject2.getString("submission_type"),
                                            dataObject2.getString("accepted_by"),
                                            dataObject2.getString("reject_by"),
                                            dataObject2.getString("status"),
                                            dataObject2.getString("modify_date"),
                                            dataObject2.getString("user_name"),
                                            dataObject2.getString("phone")
                                    );
                                    confirm_bidArrayList.add(confirm_bid);

                                }


                            } else {

                            }
                            adapter_vendor_upcomingBooking = new Adapter_Vendor_HomeUpcomingBooking(vendor_upcomingBookings, confirm_bidArrayList, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_homeupcoming.setLayoutManager(mLayoutManger);
                            rec_homeupcoming.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

                            rec_homeupcoming.setItemAnimator(new DefaultItemAnimator());
                            rec_homeupcoming.setAdapter(adapter_vendor_upcomingBooking);
                            adapter_vendor_upcomingBooking.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_my_join", anError.toString());
                    }
                });
    }


    private void vendor_online_offline_status(final String status) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + vendor_online_offline_status,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("vendor_online_offline_status data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {


                            } else {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();

                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", vendor_id);
                params.put("online_status", status);

                System.out.println(" vendor_online_offline_status param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }


//    Hot jobs
private void Hotjobs() {
    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
    progressDialog.setTitle("Loading..");
    progressDialog.show();
    String url = BaseUrl + hotJobs;

    vendor_hotjobs.clear();
    AndroidNetworking.post(url)
            .addBodyParameter("vendor_id", vendor_id)


            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    progressDialog.dismiss();
                    try {
                        String result = jsonObject.getString("result");
                        String msg = jsonObject.getString("msg");

                        if (result.equalsIgnoreCase("true")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            System.out.println("HotjobsApi-------   " + jsonArray);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.e("jsonarray", jsonArray.toString());
                                JSONObject dataObject = jsonArray.getJSONObject(i);
                                Vendor_hotjobs allCommunityModel = new Vendor_hotjobs(
                                        dataObject.getString("id"),
                                        dataObject.getString("job_id"),
                                        dataObject.getString("user_id"),
                                        dataObject.getString("date"),
                                        dataObject.getString("time"),
                                        dataObject.getString("location"),
                                        dataObject.getString("description"),
                                        dataObject.getString("bid_per"),
                                        dataObject.getString("bid_min_range"),
                                        dataObject.getString("bid_max_range"),
                                        dataObject.getString("estimate_time"),
                                        dataObject.getString("key_skills"),
                                        dataObject.getString("language"),
                                        dataObject.getString("job_type"),
                                        dataObject.getString("apply_type"),
                                        dataObject.getString("accept_bid_for"),
                                        dataObject.getString("attachment"),
                                        dataObject.getString("type"),
                                        dataObject.getString("vendor_id"),
                                        dataObject.getString("cancel_by"),
                                        dataObject.getString("status"),
                                        dataObject.getString("job_cat_type"),
                                        dataObject.getString("date_time"),
                                        dataObject.getString("to_location"),
                                        dataObject.getString("end_location"),
                                        dataObject.getString("weight"),
                                        dataObject.getString("vehical_type"),
                                        dataObject.getString("total_bid"),
                                        dataObject.getString("avg_range"),
                                        dataObject.getString("bid_status"));
                                vendor_hotjobs.add(allCommunityModel);
                            }


                        } else {

                        }
                        adapter_vendor_newHot_jobs = new Adapter_Vendor_NewHot_Jobs(vendor_hotjobs, getActivity());
                        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                        rec_hotjobs.setLayoutManager(mLayoutManger);
                        rec_hotjobs.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

                        rec_hotjobs.setItemAnimator(new DefaultItemAnimator());
                        rec_hotjobs.setAdapter(adapter_vendor_newHot_jobs);
                        adapter_vendor_newHot_jobs.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError anError) {
                    progressDialog.dismiss();
                    Log.e("error_my_join", anError.toString());
                }
            });
}


}