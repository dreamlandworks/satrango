package com.satrangolimitless.User_UI.Home_user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_Home_Category;
import com.satrangolimitless.Adapter.Adapter_Home_Services;
import com.satrangolimitless.Adapter.Adapter_Home_Toppicks;
import com.satrangolimitless.Adapter.AutoCompleteCountryAdapter;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.PopularcategoriesActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.SearchListActivity;
import com.satrangolimitless.User_UI.Mybooking.JobCOmpleteDiglog;
import com.satrangolimitless.User_UI.Search.SearchFragment;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.model.Category_Home_Model;
import com.satrangolimitless.model.MedicinALLList;
import com.satrangolimitless.model.Services_Home_Model;
import com.satrangolimitless.model.toppicksHome_Model;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Category_Api;
import static com.satrangolimitless.Utils.Base_Url.Service_Api;
import static com.satrangolimitless.Utils.Base_Url.Service_list_Api;
import static com.satrangolimitless.Utils.Base_Url.Top_Picks;
import static com.satrangolimitless.Utils.Base_Url.update_exta_booking_status;

public class HomeFragment_user extends Fragment {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    View root;
    GPSTracker gps;
    double latitude, longitude;
    LinearLayout AV_SEARCH, l_provider;
    Fragment fragment;
    TextView txtviewallservices, txtcategoryviewall, txt_mylocation;
    RecyclerView rec_category, rec_services,recvtopicks;
    AutoCompleteTextView searchview;
    ArrayList<Category_Home_Model> featuredProductsModels = new ArrayList<>();
    ArrayList<toppicksHome_Model> toppicksmodels = new ArrayList<>();
    ArrayList<Services_Home_Model> services_home_models = new ArrayList<>();
    Adapter_Home_Category adapterProductList;
    Adapter_Home_Toppicks adapter_home_toppicks;
      Adapter_Home_Services adapter_home_services;
    String longi, latt, user_id;
    Geocoder geocoder;
    Session session;
    //    Autocomplete search list
    List<MedicinALLList> getAllMedicineList = new ArrayList<>();
    ArrayList<String> search_name = new ArrayList<>();
    Runnable mTicker;

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ((LandingActivity) getActivity()).Layout_hader.setVisibility(View.VISIBLE);

        View root = inflater.inflate(R.layout.fragment_home_maps, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        session = new Session(getActivity());
        user_id = session.getUserId();
        AV_SEARCH = root.findViewById(R.id.AV_SEARCH);
        searchview = root.findViewById(R.id.searchview);
        txt_mylocation = root.findViewById(R.id.txt_mylocation);
        txtviewallservices = root.findViewById(R.id.txtviewallservices);
        txtcategoryviewall = root.findViewById(R.id.txtcategoryviewall);
        rec_category = root.findViewById(R.id.rec_category);
        rec_services = root.findViewById(R.id.rec_services);
        recvtopicks = root.findViewById(R.id.recvtopicks);
        l_provider = root.findViewById(R.id.l_provider);
        System.out.println("user_id------------        " + user_id);

        try {
            getdiff();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (session.getXtrachrg() != null && !session.getXtrachrg().isEmpty() && !session.getXtrachrg().equals("null") && !session.getXtrachrg().equals("0")) {
                Extrachargedialog();
        }



        txt_mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Selectlocationhomepage.class);
                intent.putExtra("id", "user");
                startActivity(intent);

            }
        });


        try {
            geocoder = new Geocoder(getActivity(), Locale.getDefault());

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (session.getUserhome_location() != null && !session.getUserhome_location().isEmpty() && !session.getUserhome_location().equals("null") && !session.getUserhome_location().equals("0")) {
            System.out.println("session location-----  " + session.getUserhome_location());

            txt_mylocation.setText(session.getUserhome_location());
            longi = session.getUserhome_long();
            latt = session.getUserhome_lat();

            suggestion_list();
            ServicesApi();
            CategoryApi();


        } else {
                System.out.println("getlatlonmthd****  ");
            getlatlong();
            suggestion_list();
            ServicesApi();
            CategoryApi();

        }


        AV_SEARCH.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchListActivity.class);
                startActivity(intent);

            }

        });


        txtviewallservices.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PopularcategoriesActivity.class);
                startActivity(intent);

            }

        });

        txtcategoryviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PopularcategoriesActivity.class);
                startActivity(intent);
            }
        });

        l_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity_Service_provider.class);
                startActivity(intent);
            }
        });
        searchview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                /*
                if (s.length() > 2) {
                    if (!searchview.isPopupShowing()) {
                        Toast.makeText(getActivity(), "No data found in your area", Toast.LENGTH_SHORT).show();

                        return;
                    }
                }
             */

            }
        });

        searchview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {

                Bundle bundle = new Bundle();

                String category = getAllMedicineList.get(itemIndex).getImg1();
                String mstr = searchview.getText().toString();
                session.setBookingcategory(category);
                bundle.putString("data", "search");
                session.setKeyword(mstr);
                searchview.setText("");
                fragment = new SearchFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                fragment.setArguments(bundle);

            }
        });

        TOPpicksapi();

        return root;
    }





/*
--------------------------------------------Category Api
 */


    private void CategoryApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Category_Api;
        featuredProductsModels.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("lat", latt)
                .addBodyParameter("lang", longi)

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
                                System.out.println("CategoryApi-------   " + jsonArray);
                                if (jsonArray.length() < 3) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Log.e("jsonarray", jsonArray.toString());
                                        JSONObject dataObject = jsonArray.getJSONObject(i);
                                        Category_Home_Model allCommunityModel = new Category_Home_Model(
                                                dataObject.getString("id"),
                                                dataObject.getString("name"),
                                                dataObject.getString("icon"));
                                        featuredProductsModels.add(allCommunityModel);
                                    }


                                } else {

                                    for (int i = 0; i < 3; i++) {
                                        Log.e("jsonarray", jsonArray.toString());
                                        JSONObject dataObject = jsonArray.getJSONObject(i);
                                        Category_Home_Model allCommunityModel = new Category_Home_Model(
                                                dataObject.getString("id"),
                                                dataObject.getString("name"),
                                                dataObject.getString("icon"));
                                        featuredProductsModels.add(allCommunityModel);
                                    }

                                }


                            } else {

                            }
                            adapterProductList = new Adapter_Home_Category(featuredProductsModels, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_category.setLayoutManager(mLayoutManger);
                            rec_category.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                            rec_category.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                            rec_category.setItemAnimator(new DefaultItemAnimator());
                            rec_category.setAdapter(adapterProductList);
                            adapterProductList.notifyDataSetChanged();
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

//Services api-----------------------------------------------------------------

    private void ServicesApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        services_home_models.clear();
        String url = BaseUrl + Service_Api;
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("lat", latt)
                .addBodyParameter("lang", longi)

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
                                System.out.println("ServicesApi-------   " + jsonArray);

                                if (jsonArray.length() < 4) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Log.e("jsonarray", jsonArray.toString());
                                        JSONObject dataObject = jsonArray.getJSONObject(i);
                                        Services_Home_Model service_models = new Services_Home_Model(
                                                dataObject.getString("id"),
                                                dataObject.getString("name"),
                                                dataObject.getString("icon"),
                                                dataObject.getString("distance"),
                                                dataObject.getString("cat_name"));
                                                services_home_models.add(service_models);
                                    }


                                } else {
                                    for (int i = 0; i < 4; i++) {
                                        Log.e("jsonarray", jsonArray.toString());
                                        JSONObject dataObject = jsonArray.getJSONObject(i);
                                        Services_Home_Model service_models = new Services_Home_Model(
                                                dataObject.getString("id"),
                                                dataObject.getString("name"),
                                                dataObject.getString("icon"),
                                                dataObject.getString("distance"),
                                                dataObject.getString("cat_name"));
                                        services_home_models.add(service_models);
                                    }


                                }


                            } else {

                            }
                            adapter_home_services = new Adapter_Home_Services(services_home_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_services.setLayoutManager(mLayoutManger);
                            rec_services.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                            rec_services.setLayoutManager(new GridLayoutManager(getActivity(), 4));


                            rec_services.setItemAnimator(new DefaultItemAnimator());
                            rec_services.setAdapter(adapter_home_services);
                            adapter_home_services.notifyDataSetChanged();
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
Search api services---------------------------------------------------
 */

    private void suggestion_list() {
        System.out.println("suggestion_list-------       ");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Service_list_Api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            getAllMedicineList.clear();
                            JSONObject obj = new JSONObject(response);
                            System.out.println("suggestion_list-------       " + obj.toString());
                            String result = obj.getString("result");
                            if (result.equalsIgnoreCase("true")) {
                                JSONArray heroArray = obj.getJSONArray("services");
                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    MedicinALLList hero = new MedicinALLList(
                                            heroObject.getString("id"),
                                            heroObject.getString("name"),
                                            heroObject.getString("icon"),
                                            heroObject.getString("cate_name")
                                    );
                                    String name = heroObject.getString("name");
                                    search_name.add(name);
                                    getAllMedicineList.add(i, hero);
                                }


                                AutoCompleteCountryAdapter adapter = new AutoCompleteCountryAdapter(getActivity(), getAllMedicineList);
                                searchview.setAdapter(adapter);
                                searchview.setThreshold(1);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getActivity(), "Something Went Wrong ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", session.getUserId());
                params.put("lat", latt);
                params.put("lang", longi);

                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    void getlatlong() {


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            gps = new GPSTracker(getActivity());
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                latt = String.valueOf(latitude);
                longi = String.valueOf(longitude);
                System.out.println("lat long -----   " + latitude + "    " + longitude);

                List<Address> addresses = null;
                try {

                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    session.setLattitude(latt);
                    session.setLongitude(longi);
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    System.out.println("addressssssssssssss --------    " + knownName);
                    txt_mylocation.setText(knownName);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                gps.showSettingsAlert();

            }

        }
    }





//    top picks



    private void TOPpicksapi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Top_Picks;
        toppicksmodels.clear();
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
                                System.out.println("TOPpicksapi()-------   " + jsonArray);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Log.e("jsonarray", jsonArray.toString());
                                        JSONObject dataObject = jsonArray.getJSONObject(i);
                                        toppicksHome_Model allCommunityModel = new toppicksHome_Model(
                                                dataObject.getString("vendor_id"),
                                                dataObject.getString("v_name"),
                                                dataObject.getString("v_image"),
                                                dataObject.getString("total_rating"),
                                                dataObject.getString("total_job")
                                        );
                                        toppicksmodels.add(allCommunityModel);
                                    }





                            }
                            adapter_home_toppicks = new Adapter_Home_Toppicks(toppicksmodels, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recvtopicks.setLayoutManager(mLayoutManger);
                            recvtopicks.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));


                            recvtopicks.setAdapter(adapter_home_toppicks);
                            adapter_home_toppicks.notifyDataSetChanged();
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



    public void Extrachargedialog() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_extracharge, null);

        builder.setCancelable(false);


        final Button btn_yes = view.findViewById(R.id.btn_yes);
        final Button btn_no = view.findViewById(R.id.btn_no);
        final ImageView btnclose = view.findViewById(R.id.btnclose);



        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setXtrachrg("");
                alertDialog.dismiss();
            }
        });



        btn_no.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Extrachargeapi("2");
        session.setXtrachrg("");
        alertDialog.dismiss();
    }
});
btn_yes.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Extrachargeapi("1");
        session.setXtrachrg("");
        alertDialog.dismiss();
    }
});




    }






    private void Extrachargeapi(String status) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + update_exta_booking_status;
        toppicksmodels.clear();
        System.out.println("Extrachargeapi  user_id  "+user_id+"  Bookingid  "+session.getBookingid());
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("booking_id", session.getBookingid())
                .addBodyParameter("status", status)


                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        System.out.println("update_exta_booking_status-----------       "+jsonObject.toString());
                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {




                            }

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

 void getdiff() throws ParseException {

    String currentTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());

    SimpleDateFormat simpleDateFormat
            = new SimpleDateFormat("hh:mm:ss");

    // Parsing the Time Period
    Date date1 = simpleDateFormat.parse(currentTime);
    Date date2 = simpleDateFormat.parse("2:00:00");

    // Calculating the difference in milliseconds
    long differenceInMilliSeconds
            = Math.abs(date2.getTime() - date1.getTime());

    // Calculating the difference in Hours
    long differenceInHours
            = (differenceInMilliSeconds / (60 * 60 * 1000))
            % 24;

    // Calculating the difference in Minutes
    long differenceInMinutes
            = (differenceInMilliSeconds / (60 * 1000)) % 60;

    // Calculating the difference in Seconds
    long differenceInSeconds
            = (differenceInMilliSeconds / 1000) % 60;

    // Printing the answer
    System.out.println(
            "Difference is " + differenceInHours + " hours "
                    + differenceInMinutes + " minutes "
                    + differenceInSeconds + " Seconds. ");

}


}