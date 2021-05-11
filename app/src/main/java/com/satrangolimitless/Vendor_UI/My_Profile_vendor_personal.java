package com.satrangolimitless.Vendor_UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.model.ProfessionModel;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.satrangolimitless.Utils.Base_Url.Add_vendor;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Get_Professions;
import static com.satrangolimitless.Utils.Base_Url.View_vendor_profile;
import static com.satrangolimitless.Utils.Base_Url.vendor_update_profile_personal;

public class My_Profile_vendor_personal extends Fragment {
    public HashMap<Integer, ProfessionModel> CityHashMap = new HashMap<>();
    GPSTracker gps;
    double latitude, longitude;
    View root;
    EditText edt_email, edt_mob, edt_name;
    TextView txt_dob;
    Button btn_next, btn_cancel;
    Session session;
    String user_id, address;
    String phone, email, vendor_name, dob, gender = "", profession_id, longi, latt;
    String Phone, Email, Name, Dob;
    LinearLayout ll_male, ll_female, ll_cross;
    Session_vendor session_vendor;
    Spinner spin_profession;
    ArrayList<String> spin_city = new ArrayList<>();
    ArrayList<ProfessionModel> cityDataArrayList = new ArrayList<>();
    private int mYear, mMonth, mDay;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activty_myprofile_vendor_personal, container, false);

        session = new Session(getActivity());

        edt_name = root.findViewById(R.id.edt_name);
        edt_mob = root.findViewById(R.id.edt_mob);
        edt_email = root.findViewById(R.id.edt_email);

        btn_next = root.findViewById(R.id.btn_next);
        btn_cancel = root.findViewById(R.id.btn_cancel);
        txt_dob = root.findViewById(R.id.txt_dob);
        user_id = session.getUserId();
        spin_profession = root.findViewById(R.id.spin_profession);
        ll_male = root.findViewById(R.id.ll_male);
        ll_female = root.findViewById(R.id.ll_female);
        ll_cross = root.findViewById(R.id.ll_cross);
        getProfessionListapi();

        if (session.isLoggedIn()) {
            Viewprofile_vendorApi();
        } else {
            Toast.makeText(getActivity(), "Please login", Toast.LENGTH_SHORT).show();
        }

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
                try {
                    Geocoder geocoder;
                    List<Address> addresses = null;
                    geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    address = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    System.out.println("address --------    " + address);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                gps.showSettingsAlert();

            }

        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Phone = edt_mob.getText().toString();
                Name = edt_name.getText().toString();
                Email = edt_email.getText().toString();
                Dob = txt_dob.getText().toString();


                Update_vendor();

            }
        });

        spin_profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profession_id = CityHashMap.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Male";

                ll_male.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.tab_selected));

                ll_female.setBackgroundColor(Color.parseColor("#ffffff"));
                ll_cross.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        ll_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Female";
                ll_female.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.tab_selected));
                ll_male.setBackgroundColor(Color.parseColor("#ffffff"));
                ll_cross.setBackgroundColor(Color.parseColor("#ffffff"));

            }
        });


        ll_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Transgender";
                ll_cross.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.tab_selected));
                ll_female.setBackgroundColor(Color.parseColor("#ffffff"));
                ll_male.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });


        txt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txt_dob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent in = new Intent(getActivity(), LandingActivity_Service_provider.class);
                startActivity(in);
                getActivity().finish();

            }
        });

        return root;
    }


    private void Viewprofile_vendorApi() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + View_vendor_profile;

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
                                System.out.println("View_vendor_profile-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    String id = dataObject.getString("id");
                                    vendor_name = dataObject.getString("vendor_name");
                                    email = dataObject.getString("email");
                                    phone = dataObject.getString("phone");
                                    dob = dataObject.getString("dob");
                                    gender = dataObject.getString("gender");
                                }


                                edt_email.setText(email);
                                edt_mob.setText(phone);
                                edt_name.setText(vendor_name);
                                txt_dob.setText(dob);


                                if (gender.contains("Male")) {
                                    ll_male.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.tab_selected));

                                } else if (gender.contains("Female")) {
                                    ll_female.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.tab_selected));

                                } else if (gender.contains("Transgender")) {
                                    ll_cross.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.tab_selected));

                                }

                            } else {

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


//    Add vendor details


    private void Update_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing...");
        progressDialog.show();


        AndroidNetworking.upload(BaseUrl + vendor_update_profile_personal)
                .addMultipartParameter("user_id", user_id)
                .addMultipartParameter("name", Name)
                .addMultipartParameter("mobile", Phone)
                .addMultipartParameter("profession_id", profession_id)
                .addMultipartParameter("dob", Dob)
                .addMultipartParameter("gender", gender)
                .addMultipartParameter("email", Email)
                .setTag("update_vendor")
                .setPriority(Priority.LOW)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            progressDialog.dismiss();
                            //Log.e(" post home", " " + jsonObject);
                            Log.e("update_vendor  ", jsonObject.toString());
                            System.out.println("update_vendor=====    " + jsonObject.toString());
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {


                                Toast.makeText(getActivity(), "Details updated successfully", Toast.LENGTH_LONG).show();

                             /*   // Reload current fragment
                                Fragment frag = new My_Profile_vendor_personal();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame, frag).commit();*/

                            } else {

                                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();


                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        Log.e("error = ", "" + error);
                    }
                });


    }


//    profession spinner data api


    void getProfessionListapi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        String url = BaseUrl + Get_Professions;
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            progressDialog.dismiss();

                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray data = jsonObject.getJSONArray("data");

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject jsonObject1 = data.getJSONObject(i);
                                    String id = jsonObject1.getString("id");
                                    String name = jsonObject1.getString("name");


                                    spin_city.add(i, name);
                                    cityDataArrayList.add(i, new ProfessionModel(id, name));
                                    CityHashMap.put(i, new ProfessionModel(id, name));
                                }
                                Log.e("spin_citysize", "" + spin_city.size());
                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (getActivity(), android.R.layout.simple_spinner_item, spin_city); //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin_profession.setAdapter(spinnerArrayAdapter);


                            } else {
                                progressDialog.dismiss();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("error_my_join", anError.toString());


                    }
                });


    }

}