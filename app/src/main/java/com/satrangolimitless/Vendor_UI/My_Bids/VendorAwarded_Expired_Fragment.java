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
import com.satrangolimitless.Adapter.Adapter_VendorAwarded_Jobs;
import com.satrangolimitless.Adapter.Adapter_Vendor_New_Jobs;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Vendor_Bidsawarded_model;
import com.satrangolimitless.model.Vendor_New_Jobs_model;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.get_Vendor_New_Jobs;
import static com.satrangolimitless.Utils.Base_Url.get_Vendor_awarded_Jobs;

public class VendorAwarded_Expired_Fragment extends Fragment {

    View root;
    Session_vendor session_vendor;
    String vendor_id;
    String TAG = getClass().getName();
    RecyclerView rec_ven_award;
    ArrayList<Vendor_Bidsawarded_model> vendor_bidsawarded_models = new ArrayList<>();
    Adapter_VendorAwarded_Jobs adapter_vendorAwarded_jobs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_vendor_bids_awarded, container, false);
        session_vendor = new Session_vendor(getActivity());
        vendor_id = session_vendor.getUserId();

        System.out.println("vendorid<><>><><>    " + TAG + " = " + vendor_id);
        rec_ven_award = root.findViewById(R.id.rec_ven_award);
        Vendor_NewjobsAwarded();

        return root;
    }

    private void Vendor_NewjobsAwarded() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + get_Vendor_awarded_Jobs;
        vendor_bidsawarded_models.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vendor_id )
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
                                System.out.println("get_Vendor_awarded_Jobs-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_Bidsawarded_model allCommunityModel = new Vendor_Bidsawarded_model(
                                            dataObject.getString("id"),
                                            dataObject.getString("job_id"),
                                            dataObject.getString("description"),
                                            dataObject.getString("location"),
                                            dataObject.getString("bid_min_range"),
                                            dataObject.getString("bid_max_range"),
                                            dataObject.getString("fname"),
                                            dataObject.getString("image"),
                                            dataObject.getString("avg_range"),
                                            dataObject.getString("status"),
                                            dataObject.getString("total_bid"),
                                            dataObject.getString("attachment"),
                                            dataObject.getString("language"),
                                            dataObject.getString("proposal"),
                                            dataObject.getString("bid_amount"),
                                            dataObject.getString("user_eastimate_time"),
                                            dataObject.getString("estimated_time")

                                    );







                                    vendor_bidsawarded_models.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_vendorAwarded_jobs = new Adapter_VendorAwarded_Jobs(vendor_bidsawarded_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_ven_award.setLayoutManager(mLayoutManger);
                            rec_ven_award.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_ven_award.setItemAnimator(new DefaultItemAnimator());
                            rec_ven_award.setAdapter(adapter_vendorAwarded_jobs);
                            adapter_vendorAwarded_jobs.notifyDataSetChanged();
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
