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
import com.satrangolimitless.Adapter.Adapter_UserAwarded_Jobs;
import com.satrangolimitless.R;
import com.satrangolimitless.model.UserAwardedJobs_model;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.get_User_awarded_Jobs;

public class User_Awarded_Jobs extends Fragment {
    View root;
    Session session;
    String user_id;
    String TAG = getClass().getName();
    RecyclerView rec_usr_award;
    ArrayList<UserAwardedJobs_model> userAwardedJobs_models = new ArrayList<>();
    Adapter_UserAwarded_Jobs adapter_userAwarded_jobs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_user_bids_awarded, container, false);
        session = new Session(getActivity());
        user_id = session.getUserId();

        System.out.println("vendorid<><>><><>    " + TAG + " = " + user_id);
        rec_usr_award = root.findViewById(R.id.rec_usr_award);
        UserjobsAwarded();
        return root;

    }


    private void UserjobsAwarded() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + get_User_awarded_Jobs;
        userAwardedJobs_models.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", user_id)
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
                                System.out.println("UserjobsAwarded-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    UserAwardedJobs_model allCommunityModel = new UserAwardedJobs_model(
                                            dataObject.getString("id"),
                                            dataObject.getString("job_id"),
                                            dataObject.getString("bid_amount"),
                                            dataObject.getString("attachment"),
                                            dataObject.getString("created_date"),
                                            dataObject.getString("estimated_time"),
                                            dataObject.getString("description"),
                                            dataObject.getString("location"),
                                            dataObject.getString("bid_min_range"),
                                            dataObject.getString("bid_max_range"),
                                            dataObject.getString("bid_per"),
                                            dataObject.getString("accept_bid_for"),
                                            dataObject.getString("v_name"),
                                            dataObject.getString("v_image"),
                                            dataObject.getString("status"),
                                            dataObject.getString("language"),
                                            dataObject.getString("total_bid"),
                                            dataObject.getString("avg_range")

                                    );
                                    userAwardedJobs_models.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_userAwarded_jobs = new Adapter_UserAwarded_Jobs(userAwardedJobs_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_usr_award.setLayoutManager(mLayoutManger);
                            rec_usr_award.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_usr_award.setItemAnimator(new DefaultItemAnimator());
                            rec_usr_award.setAdapter(adapter_userAwarded_jobs);
                            adapter_userAwarded_jobs.notifyDataSetChanged();
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
