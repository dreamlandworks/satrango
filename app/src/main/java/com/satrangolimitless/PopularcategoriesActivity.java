package com.satrangolimitless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_Popular_ServicesActivity;
import com.satrangolimitless.Adapter.Adapter_Popular_categoriesActivity;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.model.Categories_Popularactivity_Model;
import com.satrangolimitless.model.Services_Popularactivity_Model;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Category_Api;
import static com.satrangolimitless.Utils.Base_Url.Service_Api;


public class PopularcategoriesActivity extends AppCompatActivity {
    GPSTracker gps;
    double latitude, longitude;
    String longi,latt,user_id;
ImageView ivprofile;
ImageView ivback;
Session session;
    RecyclerView rec_category,rec_services;
    ArrayList<Categories_Popularactivity_Model> categories_popularactivity_models = new ArrayList<>();
    Adapter_Popular_categoriesActivity adapter_popular_categoriesActivity;
    ArrayList<Services_Popularactivity_Model> services_popularactivity_models = new ArrayList<>();
    Adapter_Popular_ServicesActivity adapter_popular_servicesActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popularcategories);
        session=new Session(getApplicationContext());
        user_id=session.getUserId();
        rec_category= findViewById(R.id.rec_category);
        rec_services= findViewById(R.id.rec_services);
        ivprofile= findViewById(R.id.ivprofile);
        ivback= findViewById(R.id.ivback);
        getlatlang();
        CategoriesApi();
        ServicesApi();

        ivprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularcategoriesActivity.this, MyprofileActivity_User.class);
                startActivity(intent);
            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    private void CategoriesApi() {
        final ProgressDialog progressDialog = new ProgressDialog(PopularcategoriesActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        categories_popularactivity_models.clear();
        String url = BaseUrl + Category_Api ;
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id )
                .addBodyParameter("lat",latt )
                .addBodyParameter("lang",longi )
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
                                System.out.println(
                                        "PopularCategoriesApi-------   "+ jsonArray
                                );
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Categories_Popularactivity_Model service_models = new Categories_Popularactivity_Model(
                                            dataObject.getString("id"),
                                            dataObject.getString("name"),
                                            dataObject.getString("icon"));
                                    categories_popularactivity_models.add(service_models);
                                }


                            } else {

                            }
                            adapter_popular_categoriesActivity = new Adapter_Popular_categoriesActivity(categories_popularactivity_models, getApplicationContext());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
                            rec_category.setLayoutManager(mLayoutManger);
                            rec_category.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                            rec_category.setItemAnimator(new DefaultItemAnimator());
                            rec_category.setAdapter(adapter_popular_categoriesActivity);
                            adapter_popular_categoriesActivity.notifyDataSetChanged();


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

    /*
    Services api---------------------------------------------
     */


    private void ServicesApi() {
        final ProgressDialog progressDialog = new ProgressDialog(PopularcategoriesActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        services_popularactivity_models.clear();
        String url = BaseUrl +Service_Api ;
        AndroidNetworking.post(url)
                .addBodyParameter("user_id",user_id )
                .addBodyParameter("lat",latt )
                .addBodyParameter("lang",longi )
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            // productDTOArrayList.clear();
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                System.out.println(
                                        "Servicespopular-------   "+ jsonArray
                                );
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Services_Popularactivity_Model service_models = new Services_Popularactivity_Model(
                                            dataObject.getString("id"),
                                            dataObject.getString("name"),
                                            dataObject.getString("icon"),
                                            dataObject.getString("distance"),
                                            dataObject.getString("cat_name"));
                                    services_popularactivity_models.add(service_models);
                                }


                            } else {

                            }
                            adapter_popular_servicesActivity = new Adapter_Popular_ServicesActivity(services_popularactivity_models, PopularcategoriesActivity.this);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(PopularcategoriesActivity.this);
                            rec_services.setLayoutManager(mLayoutManger);
                            rec_services.setLayoutManager(new LinearLayoutManager(PopularcategoriesActivity.this, RecyclerView.VERTICAL, false));

                            rec_services.setItemAnimator(new DefaultItemAnimator());
                            rec_services.setAdapter(adapter_popular_servicesActivity);
                            adapter_popular_servicesActivity.notifyDataSetChanged();
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


    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PopularcategoriesActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //    Toast.makeText(LoginActivity.this,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(getApplicationContext());
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                latt= String.valueOf(latitude);
                longi= String.valueOf(longitude);
System.out.println("lat long -----   "+String.valueOf(latitude)+"    "+String.valueOf(longitude));
            }
        }
    }

}