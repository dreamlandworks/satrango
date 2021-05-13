package com.satrangolimitless.Vendor_UI.vendor_profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.satrangolimitless.Adapter.AutoCompletevendor_register_adapter;
import com.satrangolimitless.Adapter.SuggestionAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.model.Search_suggestion_vendor_register;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Service_list_Api;

public class VendorProfileoneActivity extends AppCompatActivity {
    GPSTracker gps;
    double latitude, longitude;
    String longi, latt;
    Button btn_next, btn_cancel;
    NachoTextView et_tag, et_skills;
    String all_Languages = "", all_Skils = "", qualification = "", about;
    List<String> languag_list = new ArrayList<String>();
    List<String> skillsList = new ArrayList<String>();
    Session_vendor session_vendor;
    EditText edt_qualification, edt_about;
    ArrayList<String> search_name = new ArrayList<>();
    List<Search_suggestion_vendor_register> getAllMedicineList = new ArrayList<>();
    SuggestionAdapter suggestionAdapter;
    RecyclerView medicine_recycler;
    ArrayList<String> SuggestionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profileone);
        et_skills = findViewById(R.id.addSkills);
        edt_about = findViewById(R.id.edt_about);
        edt_qualification = findViewById(R.id.edt_qualification);
        btn_next = findViewById(R.id.btn_next);
        btn_cancel = findViewById(R.id.btn_cancel);
        medicine_recycler = findViewById(R.id.medicine_recycler);
        session_vendor = new Session_vendor(getApplicationContext());
        et_tag = (NachoTextView) findViewById(R.id.et_tag);
//        et_skills = (NachoTextView) findViewById(R.id.et_skills);
        getlatlang();
        suggestion_list();

        String[] suggestions = new String[]{"Hindi", "English", "Telugu", "Odisa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        et_tag.setAdapter(adapter);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Chip chip : et_tag.getAllChips()) {
                    CharSequence text = chip.getText();
                    languag_list.add(text.toString());
                    all_Languages = TextUtils.join(",", languag_list);
                }
                for (Chip chip : et_skills.getAllChips()) {
                    CharSequence text = chip.getText();
                    skillsList.add(text.toString());
                    all_Skils = TextUtils.join(",", skillsList);
                }
                about = edt_about.getText().toString();
                qualification = edt_qualification.getText().toString();

                if (TextUtils.isEmpty(qualification)) {
                    edt_qualification.setError("Please enter qualification");
                    edt_qualification.requestFocus();
                } else if (TextUtils.isEmpty(all_Languages)) {
                    Toast.makeText(VendorProfileoneActivity.this, "Please select language", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(all_Skils)) {
                    Toast.makeText(VendorProfileoneActivity.this, "Please select skills", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(about)) {
                    edt_about.setError("Please add something in about");
                    edt_about.requestFocus();
                } else {

                    session_vendor.setAddlanguage(all_Languages);
                    session_vendor.setAddskills(all_Skils);
                    session_vendor.setAddqualification(qualification);
                    session_vendor.setAddabout(about);

                    Intent intent = new Intent(VendorProfileoneActivity.this, PrifileActivityTwo.class);
                    startActivity(intent);

                }


            }
        });


        et_tag.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
        et_skills.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
//        et_skills.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);


//        et_skills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
//                String listid = getAllMedicineList.get(itemIndex).getId();
//                skillsList.add(listid);
//                all_Skils = TextUtils.join(",", skillsList);
//                String mstr = et_skills.getText().toString();
//                SuggestionList.add(mstr);
//                suggestionAdapter = new SuggestionAdapter(VendorProfileoneActivity.this, SuggestionList);
//                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(VendorProfileoneActivity.this);
//                medicine_recycler.setLayoutManager(new LinearLayoutManager(VendorProfileoneActivity.this, RecyclerView.HORIZONTAL, false));
//                medicine_recycler.setAdapter(suggestionAdapter);
//                suggestionAdapter.notifyDataSetChanged();
//                et_skills.setText("");
//            }
//        });


    }


    private void suggestion_list() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Service_list_Api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            getAllMedicineList.clear();
                            JSONObject obj = new JSONObject(response);
                            System.out.println("skills api-------       " + obj.toString());
                            String result = obj.getString("result");
                            if (result.equalsIgnoreCase("true")) {
                                JSONArray heroArray = obj.getJSONArray("services");
                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    Search_suggestion_vendor_register hero = new Search_suggestion_vendor_register(
                                            heroObject.getString("id"),
                                            heroObject.getString("name"),
                                            heroObject.getString("icon")
                                    );
                                    String name = heroObject.getString("name");
                                    search_name.add(name);
                                    getAllMedicineList.add(i, hero);
                                }
//                                AutoCompletevendor_register_adapter adapter = new AutoCompletevendor_register_adapter(VendorProfileoneActivity.this, getAllMedicineList);
//                                et_skills.setAdapter(adapter);
//                                et_skills.setThreshold(1);
                                ArrayList<String> list = new ArrayList();
                                for (int i = 0; i < getAllMedicineList.size(); i++) {
                                    list.add(getAllMedicineList.get(i).getName());
                                }
                                ArrayAdapter<String> adapter1 = new ArrayAdapter(VendorProfileoneActivity.this, android.R.layout.simple_dropdown_item_1line, list);
                                et_skills.setAdapter(adapter1);
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
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

//get lat long

    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VendorProfileoneActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //    Toast.makeText(LoginActivity.this,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(getApplicationContext());
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                latt = String.valueOf(latitude);
                longi = String.valueOf(longitude);

            }
        }
    }


}