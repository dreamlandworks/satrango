package com.satrangolimitless.Vendor_UI.My_Bids;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.satrangolimitless.Adapter.Adapter_Vendor_New_Jobs;
import com.satrangolimitless.R;


import com.satrangolimitless.model.Vendor_New_Jobs_model;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.get_Vendor_New_Jobs;

    public class New_Jobs_Fragment extends Fragment {
    View root;


    Session_vendor session;
    RecyclerView rec_usr_upc;
    String userid;
    ArrayList<Vendor_New_Jobs_model> vendor_new_jobs_models = new ArrayList<>();
    Adapter_Vendor_New_Jobs adapter_vendor_new_jobs;

    public static New_Jobs_Fragment newInstance(String param1, String param2) {
        New_Jobs_Fragment fragment = new New_Jobs_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_user_pending_jobs, container, false);


        session = new Session_vendor(getActivity());
        userid = session.getUserId();

        rec_usr_upc = root.findViewById(R.id.rec_usr_upc);


        Vendor_NewjobsApi();


        return root;
    }


    private void Vendor_NewjobsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + get_Vendor_New_Jobs;
        vendor_new_jobs_models.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", userid)
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
                                System.out.println("get_Vendor_New_Jobs-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_New_Jobs_model allCommunityModel = new Vendor_New_Jobs_model(
                                            dataObject.getString("total_bid"),
                                            dataObject.getString("avg_range"),
                                            dataObject.getString("image"),
                                            dataObject.getString("fname"),
                                            dataObject.getString("date_time"),
                                            dataObject.getString("status"),
                                            dataObject.getString("cancel_by"),
                                            dataObject.getString("vendor_id"),
                                            dataObject.getString("type"),
                                            dataObject.getString("attachment"),
                                            dataObject.getString("accept_bid_for"),
                                            dataObject.getString("apply_type"),
                                            dataObject.getString("job_type"),
                                            dataObject.getString("language"),
                                            dataObject.getString("key_skills"),
                                            dataObject.getString("estimate_time"),
                                            dataObject.getString("bid_max_range"),
                                            dataObject.getString("bid_min_range"),
                                            dataObject.getString("bid_per"),
                                            dataObject.getString("description"),
                                            dataObject.getString("location"),
                                            dataObject.getString("time"),
                                            dataObject.getString("date"),
                                            dataObject.getString("user_id"),
                                            dataObject.getString("job_id"),
                                            dataObject.getString("id"),
                                            dataObject.getString("bookingId")

                                    );
                                    vendor_new_jobs_models.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_vendor_new_jobs = new Adapter_Vendor_New_Jobs(vendor_new_jobs_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_usr_upc.setLayoutManager(mLayoutManger);
                            rec_usr_upc.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_usr_upc.setItemAnimator(new DefaultItemAnimator());
                            rec_usr_upc.setAdapter(adapter_vendor_new_jobs);
                            adapter_vendor_new_jobs.notifyDataSetChanged();
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