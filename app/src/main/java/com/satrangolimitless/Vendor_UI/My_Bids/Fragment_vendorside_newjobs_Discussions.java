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
import com.satrangolimitless.Adapter.Adapter_Vendor_Pending_jobs_Discussions;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Vendor_newjob_discussions;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.vendor_discussion_list;

public class Fragment_vendorside_newjobs_Discussions extends Fragment {

    View root;
    RecyclerView RvHistory;
    ArrayList<Vendor_newjob_discussions> user_job_discussions = new ArrayList<>();
    Adapter_Vendor_Pending_jobs_Discussions adapter_user_pending_jobs_discussions;
    Session_vendor session;
    String userid;
    private BottomSheetBehavior mBehavior;
    private AppBarLayout app_bar_layout;
    private LinearLayout lyt_profile;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bottom_sheet_dialog_full, container, false);
        RvHistory = root.findViewById(R.id.RvHistory);
        session = new Session_vendor(getActivity());
        userid = session.getUserId();
        GetBidsDiscussions();

        return root;
    }


    private void GetBidsDiscussions() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        user_job_discussions.clear();
        String url = BaseUrl + vendor_discussion_list;
        AndroidNetworking.post(url)
                .addBodyParameter("job_id", session.getPendongjob_id())
                .addBodyParameter("vendor_id", userid)
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
                                System.out.println("Getdiscussion-------   " + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray ", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_newjob_discussions service_models = new Vendor_newjob_discussions(
                                            dataObject.getString("id"),
                                            dataObject.getString("user_id"),
                                            dataObject.getString("vendor_msg"),
                                            dataObject.getString("user_name"),
                                            dataObject.getString("user_image"));
                                    user_job_discussions.add(service_models);
                                }


                            } else {

                            }
                            adapter_user_pending_jobs_discussions = new Adapter_Vendor_Pending_jobs_Discussions(user_job_discussions, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            RvHistory.setLayoutManager(mLayoutManger);
                            RvHistory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            RvHistory.setItemAnimator(new DefaultItemAnimator());
                            RvHistory.setAdapter(adapter_user_pending_jobs_discussions);
                            adapter_user_pending_jobs_discussions.notifyDataSetChanged();


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






