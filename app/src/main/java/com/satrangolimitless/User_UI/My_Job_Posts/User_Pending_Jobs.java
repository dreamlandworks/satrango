package com.satrangolimitless.User_UI.My_Job_Posts;

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
import com.satrangolimitless.Adapter.Adapter_User_Pending_Jobs;
import com.satrangolimitless.R;
import com.satrangolimitless.model.User_PendingJobs_model;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.User_jobpostpending;

public class User_Pending_Jobs extends Fragment {
    View root;


    Session session;
    RecyclerView rec_usr_upc;
    String userid;
    ArrayList<User_PendingJobs_model> user_pendingJobs_models = new ArrayList<>();
    Adapter_User_Pending_Jobs adapter_user_pending_jobs;

    public static User_Pending_Jobs newInstance(String param1, String param2) {
        User_Pending_Jobs fragment = new User_Pending_Jobs();

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


        session = new Session(getActivity());
        userid = session.getUserId();

        rec_usr_upc = root.findViewById(R.id.rec_usr_upc);


        User_PendingjobsApi();


        return root;
    }


    private void User_PendingjobsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + User_jobpostpending;
        user_pendingJobs_models.clear();
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
                                System.out.println("PendingsApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    User_PendingJobs_model allCommunityModel = new User_PendingJobs_model(
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
                                            dataObject.getString("date_time"),
                                            dataObject.getString("avg_range"),
                                            dataObject.getString("total_bid")

                                    );
                                    user_pendingJobs_models.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_user_pending_jobs = new Adapter_User_Pending_Jobs(user_pendingJobs_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_usr_upc.setLayoutManager(mLayoutManger);
                            rec_usr_upc.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_usr_upc.setItemAnimator(new DefaultItemAnimator());
                            rec_usr_upc.setAdapter(adapter_user_pending_jobs);
                            adapter_user_pending_jobs.notifyDataSetChanged();
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