package com.satrangolimitless.Vendor_UI.MyAccount;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_Transactionhistory;
import com.satrangolimitless.Adapter.Adapter_Vendor_rating_reviews;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Myaccount.Activity_Transactionhistory;
import com.satrangolimitless.model.Transactionhistrymodel;
import com.satrangolimitless.model.Vendor_rating;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.getTransactionhistory;
import static com.satrangolimitless.Utils.Base_Url.getvenderrating;

public class Activityvendr_reviews extends AppCompatActivity {
    RecyclerView recvrating;
    Session_vendor session_vendor;
    String userid;
    ArrayList<Vendor_rating> vendor_ratings = new ArrayList<>();

    Adapter_Vendor_rating_reviews adapter_vendor_rating_reviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_review_list);
        session_vendor = new Session_vendor(getApplicationContext());
        recvrating=findViewById(R.id.recvrating);
        Vendorratingreview();
    }



    private void Vendorratingreview() {
        final ProgressDialog progressDialog = new ProgressDialog(Activityvendr_reviews.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + getvenderrating;
        vendor_ratings.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", session_vendor.getUserId())

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
                                System.out.println("Vendorratingreview-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_rating allCommunityModel = new Vendor_rating(
                                            dataObject.getString("id"),
                                            dataObject.getString("booking_id"),
                                            dataObject.getString("Professionalism_rating"),
                                            dataObject.getString("Behaviour_rating"),
                                            dataObject.getString("Skills_rating"),
                                            dataObject.getString("overall_rating"),
                                            dataObject.getString("username"),
                                            dataObject.getString("user_image"),
                                            dataObject.getString("message"),
                                            dataObject.getString("time"));
                                    vendor_ratings.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_vendor_rating_reviews = new Adapter_Vendor_rating_reviews(vendor_ratings, Activityvendr_reviews.this);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Activityvendr_reviews.this);
                            recvrating.setLayoutManager(mLayoutManger);
                            recvrating.setLayoutManager(new LinearLayoutManager(Activityvendr_reviews.this, RecyclerView.VERTICAL, false));

                            recvrating.setItemAnimator(new DefaultItemAnimator());
                            recvrating.setAdapter(adapter_vendor_rating_reviews);
                            adapter_vendor_rating_reviews.notifyDataSetChanged();
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
