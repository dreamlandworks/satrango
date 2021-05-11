package com.satrangolimitless.User_UI.My_Job_Posts;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_User_Pending_Jobs;
import com.satrangolimitless.Adapter.AtachmentshowAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.model.User_PendingJobs_model;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.User_jobdiscussion;

public class Activity_User_jobs_awarded_details extends AppCompatActivity {
Session session;
TextView txtlanguage,txtbids,txtavgbid,txtestimtime, txttime,txtdate, txtdetails, txtexpires,txtminrangemaxrange;
String avgrange,esttime,time,date,desc,lang,id,bid,userid,attachments,minrange,maxrange,aceptbidfor,bidper;
LinearLayout ll_discussion, ll_bids;
    RecyclerView RvHistory,recvmltimoveatch;
    ArrayList<User_PendingJobs_model> user_pendingJobs_models = new ArrayList<>();
    Adapter_User_Pending_Jobs adapter_user_pending_jobs;
    AtachmentshowAdapter atachmentshowAdapter;
    ArrayList<String> attachlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_pending_job_details);
        session=new Session(getApplicationContext());
        txtlanguage=findViewById(R.id.txtlanguage);
        txtbids=findViewById(R.id.txtbids);
        txtavgbid=findViewById(R.id.txtavgbid);
        txtestimtime=findViewById(R.id.txtestimtime);
        txttime=findViewById(R.id.txttime);
        txtdate=findViewById(R.id.txtdate);
        txtdetails=findViewById(R.id.txtdetails);
        ll_bids=findViewById(R.id.ll_bids);
        ll_discussion=findViewById(R.id.ll_discussion);
        recvmltimoveatch=findViewById(R.id.recvmltimoveatch);
        txtminrangemaxrange=findViewById(R.id.txtminrangemaxrange);
        txtexpires=findViewById(R.id.txtexpires);



        FragmentJobBids_show_in_awarded fdr = new FragmentJobBids_show_in_awarded();
        replaceFragment(fdr, false, R.id.frame_bids_discussions);

        ll_bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentJobBids_show_in_awarded fdr = new FragmentJobBids_show_in_awarded();
                replaceFragment(fdr, false, R.id.frame_bids_discussions);
            }
        });

        ll_discussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_Job_Discussions fdr = new Fragment_Job_Discussions();
                replaceFragment(fdr, false, R.id.frame_bids_discussions);
            }
        });
        userid=session.getUserId();

        if (getIntent()!=null){
            avgrange = getIntent().getStringExtra("avgrange");
            esttime = getIntent().getStringExtra("esttime");
//            time = getIntent().getStringExtra("time");
            date = getIntent().getStringExtra("date");
            desc = getIntent().getStringExtra("desc");
            lang = getIntent().getStringExtra("lang");
            id = getIntent().getStringExtra("id");
            bid = getIntent().getStringExtra("bid");
            attachments = getIntent().getStringExtra("attachment");
            bidper = getIntent().getStringExtra("bidper");

System.out.println("avgrange------        "+avgrange+" "+bid);
            minrange = getIntent().getStringExtra("minrange");
            maxrange = getIntent().getStringExtra("maxrange");
            aceptbidfor = getIntent().getStringExtra("aceptbidfor");

            txtdetails.setText(desc);
            txtdate.setText(date);
            txttime.setText(esttime);
            txtestimtime.setText(esttime+" "+bidper);
            

            txtbids.setText(bid);
            txtlanguage.setText(lang);
            txtexpires.setText(aceptbidfor);
            txtminrangemaxrange.setText("₹ "+minrange+" - "+maxrange);

            txtavgbid.setText("₹ "+avgrange);



            ArrayList aList= new ArrayList(Arrays.asList(attachments.split(",")));
            for(int i=0;i<aList.size();i++)
            {
                System.out.println(" -->   "+aList.get(i));
                String images= (String) aList.get(i);
                attachlist.add(images);
            }


            atachmentshowAdapter = new AtachmentshowAdapter(getApplicationContext(), attachlist);
            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
            recvmltimoveatch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
            recvmltimoveatch.setAdapter(atachmentshowAdapter);
            atachmentshowAdapter.notifyDataSetChanged();
        }

//        User_PendingjobsApi();

    }



    private void User_PendingjobsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_User_jobs_awarded_details.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + User_jobdiscussion;
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
                            adapter_user_pending_jobs = new Adapter_User_Pending_Jobs(user_pendingJobs_models, Activity_User_jobs_awarded_details.this);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Activity_User_jobs_awarded_details.this);
                            RvHistory.setLayoutManager(mLayoutManger);
                            RvHistory.setLayoutManager(new LinearLayoutManager(Activity_User_jobs_awarded_details.this, RecyclerView.VERTICAL, false));

                            RvHistory.setItemAnimator(new DefaultItemAnimator());
                            RvHistory.setAdapter(adapter_user_pending_jobs);
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

    /*....................replaceFragment()....................................*/
    @SuppressLint("WrongConstant")
    public void replaceFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        String backStackName = fragment.getClass().getName();
        FragmentManager fm = getSupportFragmentManager();
        int i = fm.getBackStackEntryCount();
        while (i > 0) {
            fm.popBackStackImmediate();
            i--;
        }
        boolean fragmentPopped = getFragmentManager().popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerId, fragment, backStackName).setTransition(FragmentTransaction.TRANSIT_UNSET);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }
}