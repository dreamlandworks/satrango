package com.satrangolimitless.User_UI.POSTAJOB;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.My_Job_Posts.MyJobPostsFragment;
import com.satrangolimitless.Utils.APIClient;
import com.satrangolimitless.Utils.ApiService;
import com.satrangolimitless.model.Add_Job_Post;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Activity_PostJob_Four extends AppCompatActivity {

    Button btnnextt;
    ImageView back;
    Session session;
    Session_vendor session_vendor;
    String date,user_id, lang,all_Skils,time,description, location,accept_bid_for, Estimatedtime,bid_per, bid_max_range,bid_min_range,type="";
    ArrayList<String> Imagelist = new ArrayList<>();
    CardView cd_free ,cd_premium;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_jobpost_singlemove_four);
        session = new Session(getApplicationContext());
        user_id=session.getUserId();
        btnnextt= findViewById(R.id.btnnextt);
        back= findViewById(R.id.back);
        cd_free= findViewById(R.id.cd_free);
        cd_premium= findViewById(R.id.cd_premium);



        btnnextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostaJob();

            }
        });
        Imagelist= (ArrayList<String>) getIntent().getSerializableExtra("key");
        int listSize = Imagelist.size();

        for (int i = 0; i<listSize; i++){
            Log.i("Member name: ", Imagelist.get(i));
        }
        if (getIntent()!=null){

            bid_min_range = getIntent().getStringExtra("bid_min_range");
            bid_max_range = getIntent().getStringExtra("bid_max_range");
            bid_per = getIntent().getStringExtra("bid_per");
            Estimatedtime = getIntent().getStringExtra("Estimatedtime");
            accept_bid_for = getIntent().getStringExtra("accept_bid_for");
            location = getIntent().getStringExtra("location");
            description = getIntent().getStringExtra("description");
            time = getIntent().getStringExtra("time");
            all_Skils= getIntent().getStringExtra("all_Skils");
            lang= getIntent().getStringExtra("lang");

            date = getIntent().getStringExtra("date");
            System.out.println("list =====             "+String.valueOf(Imagelist));
            System.out.println("data =====             "+date+" language "+lang+" skills ids "+all_Skils+"time "+time+" descr "+description+"lopcation "+location+" bidfor "+accept_bid_for+" estimatetime "+Estimatedtime+" bidper "+bid_per+" maxr "+bid_max_range+"minr "+bid_min_range);



        }

cd_free.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        cd_free.setCardBackgroundColor(Color.parseColor("#fdc400"));
        cd_premium.setCardBackgroundColor(Color.parseColor("#ffffff"));
        type="Free";
    }
});

        cd_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd_premium.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_free.setCardBackgroundColor(Color.parseColor("#ffffff"));
                type="Paid";
            }
        });

          back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }




    public void PostaJob() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_PostJob_Four.this);
        progressDialog.show();
        ApiService apiInterface = APIClient.getClient().create(ApiService.class);

        MultipartBody.Part[] surveyImagesParts = null;

        surveyImagesParts = new MultipartBody.Part[Imagelist.size()];
        for (int index = 0; index < Imagelist.size(); index++) {
            File file = new File(Imagelist.get(index).toString());
             RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("attachment[]", file.getName(), surveyBody);
        }
        RequestBody USER_ID = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody DATE = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody TIME = RequestBody.create(MediaType.parse("text/plain"), time);
        RequestBody LOCATION = RequestBody.create(MediaType.parse("text/plain"), location);
        RequestBody ESTIMATE_TIME = RequestBody.create(MediaType.parse("text/plain"), Estimatedtime);
        RequestBody DESCRIPTION = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody BID_FOR = RequestBody.create(MediaType.parse("text/plain"), accept_bid_for);
        RequestBody BID_PER = RequestBody.create(MediaType.parse("text/plain"), bid_per);
        RequestBody BID_MINRANGE = RequestBody.create(MediaType.parse("text/plain"), bid_min_range);
        RequestBody BID_MAXRANGE = RequestBody.create(MediaType.parse("text/plain"), bid_max_range);
        RequestBody LANGUAGE = RequestBody.create(MediaType.parse("text/plain"), lang);
        RequestBody KEYSKILLS = RequestBody.create(MediaType.parse("text/plain"), all_Skils);
        RequestBody JOBTYPE = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody APPLYTYPE = RequestBody.create(MediaType.parse("text/plain"), "yes");

        Call<Add_Job_Post> call = apiInterface.ADD_JOB_POST(USER_ID,DATE,TIME,LOCATION,ESTIMATE_TIME,DESCRIPTION,BID_FOR,BID_PER,BID_MINRANGE,BID_MAXRANGE,LANGUAGE,KEYSKILLS,JOBTYPE,APPLYTYPE,surveyImagesParts);


        call.enqueue(new Callback<Add_Job_Post>() {
            @Override
            public void onResponse(Call<Add_Job_Post> call, retrofit2.Response<Add_Job_Post> response) {
                progressDialog.dismiss();

                System.out.println("RESULT----  jobb post         "+response.body().getResult());
                if (response.body().getResult().equals("true")) {

                    session.setFrom_time_singlejobpost("");
                    session.setDate_singlejobpost("");
                    alertdialogbox();
                   
                } else {
                    Toast.makeText(Activity_PostJob_Four.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_Job_Post> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ccheckResponce", " :" + t.getMessage());
                Toast.makeText(Activity_PostJob_Four.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    
    
    
    /* MyJobPostsFragment fragment = new MyJobPostsFragment();
                
                   ((FragmentActivity) Activity_PostJob_Four.this ).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, fragment)
                            .commit();
*/






    public void alertdialogbox() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Activity_PostJob_Four.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_jobpostsuccessfulsingle, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        ImageView img_close = (ImageView) dialogView.findViewById(R.id.img_close);
        Button btn_yes = (Button) dialogView.findViewById(R.id.btn_yes);
        Button btn_no = (Button) dialogView.findViewById(R.id.btn_no);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent in=new Intent(Activity_PostJob_Four.this,LandingActivity.class);
                startActivity(in);
                finish();
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent in=new Intent(Activity_PostJob_Four.this,LandingActivity.class);
                startActivity(in);
                finish();
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent in=new Intent(Activity_PostJob_Four.this,LandingActivity.class);
                startActivity(in);
                finish();
            }
        });
        dialog.show();
    }


}


