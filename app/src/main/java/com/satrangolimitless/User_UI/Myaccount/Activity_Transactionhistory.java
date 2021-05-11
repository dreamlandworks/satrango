package com.satrangolimitless.User_UI.Myaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_Expiringsoon_offers;
import com.satrangolimitless.Adapter.Adapter_Transactionhistory;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Latest_offers_model;
import com.satrangolimitless.model.Offers_expiringsoon_model;
import com.satrangolimitless.model.Transactionhistrymodel;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.getTransactionhistory;
import static com.satrangolimitless.Utils.Base_Url.get_expiry_soon;

public class Activity_Transactionhistory extends AppCompatActivity {
   Session session;
   RecyclerView recvtrhist;
    ArrayList<Transactionhistrymodel> transactionhistrymodels = new ArrayList<>();

    Adapter_Transactionhistory  adapter_transactionhistory;
    ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transection_history);
        recvtrhist=findViewById(R.id.recvtrhist);
        imgback=findViewById(R.id.imgback);
        session=new Session(getApplicationContext());

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Transactionhistry();

    }



    private void Transactionhistry() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Transactionhistory.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + getTransactionhistory;
        transactionhistrymodels.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", session.getUserId())

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
                                System.out.println("Transactionhistry-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Transactionhistrymodel allCommunityModel = new Transactionhistrymodel(
                                            dataObject.getString("id"),
                                            dataObject.getString("transfer_type"),
                                            dataObject.getString("money"),
                                            dataObject.getString("created_date"));
                                    transactionhistrymodels.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_transactionhistory = new Adapter_Transactionhistory(transactionhistrymodels, Activity_Transactionhistory.this);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Activity_Transactionhistory.this);
                            recvtrhist.setLayoutManager(mLayoutManger);
                            recvtrhist.setLayoutManager(new LinearLayoutManager(Activity_Transactionhistory.this, RecyclerView.VERTICAL, false));

                            recvtrhist.setItemAnimator(new DefaultItemAnimator());
                            recvtrhist.setAdapter(adapter_transactionhistory);
                            adapter_transactionhistory.notifyDataSetChanged();
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