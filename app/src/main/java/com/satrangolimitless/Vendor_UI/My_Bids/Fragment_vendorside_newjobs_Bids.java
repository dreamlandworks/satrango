package com.satrangolimitless.Vendor_UI.My_Bids;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.satrangolimitless.Adapter.Adapter_User_Pending_Jobs;
import com.satrangolimitless.Adapter.Adapter_Vendor_side_jobs_Bids;
import com.satrangolimitless.R;
import com.satrangolimitless.model.User_PendingJobs_model;
import com.satrangolimitless.model.Vendor_job_bids_list;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.user_getbids_placed_vendorlist;

public class Fragment_vendorside_newjobs_Bids extends Fragment {

    View root;
    ArrayList<User_PendingJobs_model> user_pendingJobs_models = new ArrayList<>();
    Adapter_User_Pending_Jobs adapter_user_pending_jobs;
    RecyclerView RvHistory;
    ArrayList<Vendor_job_bids_list> vendor_job_bids_lists = new ArrayList<>();
    Adapter_Vendor_side_jobs_Bids adapter_user_pending_jobs_bids;
    Session_vendor session;
    private BottomSheetBehavior mBehavior;
    private AppBarLayout app_bar_layout;
    private LinearLayout lyt_profile;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_vendor_side_bidsindetails, container, false);
        RvHistory = root.findViewById(R.id.RvHistory);
        session = new Session_vendor(getActivity());

        GetBids();

        return root;
    }


    private void GetBids() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        vendor_job_bids_lists.clear();
        System.out.println("Fragment_vendorside_newjobs_Bids id ----   " + session.getPendongjob_id());
        String url = BaseUrl + user_getbids_placed_vendorlist;
        AndroidNetworking.post(url)
                .addBodyParameter("job_id", session.getPendongjob_id())
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
                                System.out.println("Fragment_vendorside_newjobs_Bids-------   " + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_job_bids_list service_models = new Vendor_job_bids_list(
                                            dataObject.getString("id"),
                                            dataObject.getString("job_id"),
                                            dataObject.getString("vendor_id"),
                                            dataObject.getString("bid_amount"),
                                            dataObject.getString("proposal"),
                                            dataObject.getString("estimated_time"),
                                            dataObject.getString("attachment"),
                                            dataObject.getString("submission_type"),
                                            dataObject.getString("v_name"),
                                            dataObject.getString("v_image"),
                                            dataObject.getString("job_points"),
                                            dataObject.getString("total"),
                                            dataObject.getString("usersconn"),
                                            dataObject.getString("status"),
                                            dataObject.getString("rating"));
                                    vendor_job_bids_lists.add(service_models);
                                }


                            } else {

                            }
                            adapter_user_pending_jobs_bids = new Adapter_Vendor_side_jobs_Bids(vendor_job_bids_lists, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            RvHistory.setLayoutManager(mLayoutManger);
                            RvHistory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            RvHistory.setItemAnimator(new DefaultItemAnimator());
                            RvHistory.setAdapter(adapter_user_pending_jobs_bids);
                            adapter_user_pending_jobs_bids.notifyDataSetChanged();


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

