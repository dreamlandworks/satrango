package com.satrangolimitless.User_UI.My_Job_Posts;

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
import com.satrangolimitless.Adapter.Adapter_User_Pending_jobs_Discussions;
import com.satrangolimitless.R;
import com.satrangolimitless.model.User_job_discussions;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.user_side_bids_discussions;

public class Fragment_Job_Discussions extends Fragment {

    View root;
    RecyclerView RvHistory;
    ArrayList<User_job_discussions> user_job_discussions = new ArrayList<>();
    Adapter_User_Pending_jobs_Discussions adapter_user_pending_jobs_discussions;
    Session session;
    String userid;
    private BottomSheetBehavior mBehavior;
    private AppBarLayout app_bar_layout;
    private LinearLayout lyt_profile;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bottom_sheet_dialog_full, container, false);
        RvHistory = root.findViewById(R.id.RvHistory);
        session = new Session(getActivity());
        userid = session.getUserId();
        GetBidsDiscussions();

        return root;
    }


    private void GetBidsDiscussions() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        user_job_discussions.clear();
        String url = BaseUrl + user_side_bids_discussions;
        AndroidNetworking.post(url)
                .addBodyParameter("job_id", session.getPendongjob_id())
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
                                System.out.println("Getdiscussion-------   " + jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray ", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    User_job_discussions service_models = new User_job_discussions(
                                            dataObject.getString("id"),
                                            dataObject.getString("vendor_id"),
                                            dataObject.getString("vendor_msg"),
                                            dataObject.getString("v_name"),
                                            dataObject.getString("v_image"));
                                    user_job_discussions.add(service_models);
                                }


                            } else {

                            }
                            adapter_user_pending_jobs_discussions = new Adapter_User_Pending_jobs_Discussions(user_job_discussions, getActivity());
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




