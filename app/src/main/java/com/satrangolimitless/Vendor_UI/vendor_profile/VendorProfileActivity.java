package com.satrangolimitless.Vendor_UI.vendor_profile;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
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

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Get_Professions;

public class VendorProfileActivity extends AppCompatActivity {
    public HashMap<Integer, ProfessionModel> CityHashMap = new HashMap<>();
    Button btn_next, btn_cancel;
    EditText edt_name, edt_mob, edt_email;
    String name, mobile, mail, date, gender = "", profession_id;
    TextView txt_dob;
    GPSTracker gps;
    double latitude, longitude;
    String longi, latt, user_id;
    Spinner spin_profession;
    ArrayList<String> spin_city = new ArrayList<>();
    ArrayList<ProfessionModel> cityDataArrayList = new ArrayList<>();
    LinearLayout ll_male, ll_female, ll_cross;
    Session_vendor session_vendor;
    Session session;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profile);
        txt_dob = findViewById(R.id.txt_dob);
        ll_male = findViewById(R.id.ll_male);
        ll_female = findViewById(R.id.ll_female);
        ll_cross = findViewById(R.id.ll_cross);
        edt_name = findViewById(R.id.edt_name);
        edt_mob = findViewById(R.id.edt_mob);
        edt_email = findViewById(R.id.edt_email);
        btn_next = findViewById(R.id.btn_next);
        btn_cancel = findViewById(R.id.btn_cancel);
        spin_profession = findViewById(R.id.spin_profession);
        session_vendor = new Session_vendor(getApplicationContext());
        session = new Session(getApplicationContext());
//        spinner api
        getlatlang();
        getProfessionListapi();

        edt_name.setText(session.getUser_name());
        edt_mob.setText(session.getMobile());

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
                ll_male.setBackgroundColor(Color.parseColor("#003286"));
                ll_female.setBackgroundColor(Color.parseColor("#ffffff"));
                ll_cross.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        ll_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Female";
                ll_female.setBackgroundColor(Color.parseColor("#003286"));
                ll_male.setBackgroundColor(Color.parseColor("#ffffff"));
                ll_cross.setBackgroundColor(Color.parseColor("#ffffff"));

            }
        });


        ll_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Transgender";
                ll_cross.setBackgroundColor(Color.parseColor("#003286"));
                ll_female.setBackgroundColor(Color.parseColor("#ffffff"));
                ll_male.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = edt_name.getText().toString().trim();
                mobile = edt_mob.getText().toString().trim();

                date = txt_dob.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    edt_name.setError("Please enter username");
                    edt_name.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    edt_mob.setError("Please enter mobile no.");
                    edt_mob.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    Toast.makeText(VendorProfileActivity.this, "Please select Date of birth", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(gender)) {
                    Toast.makeText(VendorProfileActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }


                session_vendor.setAdduserid(session.getUserId());
                session_vendor.setadmob(mobile);
                session_vendor.setadname(name);
                session_vendor.setAddprofession_id(profession_id);
                session_vendor.setAdddob(date);
                session_vendor.setAddgender(gender);
                if (checkAndRequestPermissions()) {

                    Intent intent = new Intent(VendorProfileActivity.this, VendorProfileoneActivity.class);
                    startActivity(intent);

                }


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


                DatePickerDialog datePickerDialog = new DatePickerDialog(VendorProfileActivity.this,
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


    }


    void getProfessionListapi() {
        final ProgressDialog progressDialog = new ProgressDialog(VendorProfileActivity.this);
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
                                        (VendorProfileActivity.this, android.R.layout.simple_spinner_item, spin_city); //selected item will look like a spinner set from XML
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


    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int aud = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int vid = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (aud != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (vid != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), 101);
            return false;
        } else {
        }

        return true;
    }


    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VendorProfileActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //    Toast.makeText(LoginActivity.this,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(getApplicationContext());
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                latt = String.valueOf(latitude);
                longi = String.valueOf(longitude);
                System.out.println("lat long -----   " + latitude + "    " + longitude);

                try {
                    Geocoder geocoder;
                    List<Address> addresses = null;
                    geocoder = new Geocoder(VendorProfileActivity.this, Locale.getDefault());

                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    session_vendor.setAddaddres(knownName);


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }


}