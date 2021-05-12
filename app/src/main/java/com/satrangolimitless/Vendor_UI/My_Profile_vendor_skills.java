package com.satrangolimitless.Vendor_UI;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.satrangolimitless.Adapter.AutoCompletevendor_register_adapter;
import com.satrangolimitless.Adapter.SuggestionAdapter;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.model.Search_suggestion_vendor_register;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Service_list_Api;
import static com.satrangolimitless.Utils.Base_Url.View_vendor_profile;
import static com.satrangolimitless.Utils.Base_Url.vendor_update_skills;

public class My_Profile_vendor_skills extends Fragment {
    View root;
    Fragment fragment;
    String about, qualification, language, skills, all_Skils;
    String user_id;
    String pr_days, pr_hours, extra_charge, min_charge;
    Session session;
    EditText edt_qualification, edt_about;
    NachoTextView et_tag, et_skills;
    ArrayList<String> SuggestionList = new ArrayList<>();
    ArrayList<String> search_name = new ArrayList<>();
    List<Search_suggestion_vendor_register> getAllMedicineList = new ArrayList<>();
    SuggestionAdapter suggestionAdapter;
    RecyclerView medicine_recycler;
    AutoCompleteTextView searchview;
    List<String> Skills_list = new ArrayList<String>();
    Button btn_next, btn_cancel;
    Session_vendor session_vendor;
    String all_Languages;
    List<String> languag_list = new ArrayList<String>();
    private String profession = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_myprofile_vendor_skills, container, false);

        session = new Session(getActivity());
        user_id = session.getUserId();
        et_tag = (NachoTextView) root.findViewById(R.id.et_tag);
        searchview = root.findViewById(R.id.searchview);
        edt_qualification = root.findViewById(R.id.edt_qualification);
        btn_cancel = root.findViewById(R.id.btn_cancel);
        btn_next = root.findViewById(R.id.btn_next);
        edt_about = root.findViewById(R.id.edt_about);
        medicine_recycler = root.findViewById(R.id.medicine_recycler);
        Viewprofile_vendorApi();
        suggestion_list();


        searchview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {


                String listid = getAllMedicineList.get(itemIndex).getId();

                Skills_list.add(listid);
                all_Skils = TextUtils.join(",", Skills_list);
                System.out.println("all_Skils=======     " + all_Skils);
                String mstr = searchview.getText().toString();
                System.out.println("autocomplete=======     " + mstr);
                SuggestionList.add(mstr);
                suggestionAdapter = new SuggestionAdapter(getActivity(), SuggestionList);
                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                medicine_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                medicine_recycler.setAdapter(suggestionAdapter);
                suggestionAdapter.notifyDataSetChanged();
                searchview.setText("");
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

        final TextInputEditText professionTv = root.findViewById(R.id.etProfession);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (Chip chip : et_tag.getAllChips()) {
//                    CharSequence text = chip.getText();
//                    System.out.println("language----  " + text);
//                    languag_list.add(text.toString());
//                    all_Languages = TextUtils.join(",", languag_list);
//                    System.out.println("all_Languages  " + all_Languages);
//                }

                all_Languages = et_tag.getText().toString().trim();
                about = edt_about.getText().toString();
                qualification = edt_qualification.getText().toString();
                profession = professionTv.getText().toString().trim();
                Update_vendor();
            }
        });


        return root;
    }


    private void Viewprofile_vendorApi() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
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
                                    skills = dataObject.getString("skills");
                                    language = dataObject.getString("language");
                                    qualification = dataObject.getString("qualification");
                                    about = dataObject.getString("about");
                                    min_charge = dataObject.getString("min_charge");
                                    extra_charge = dataObject.getString("extra_charge");
                                    pr_hours = dataObject.getString("pr_hours");
                                    pr_days = dataObject.getString("pr_days");
                                    ArrayList<String> SuggestionList = new ArrayList<>();
                                    JSONArray skilaray = dataObject.getJSONArray("skills");
                                    for (int s = 0; s < skilaray.length(); s++) {
                                        JSONObject skildataObject = skilaray.getJSONObject(s);
                                        String name = skildataObject.getString("name");
                                        System.out.println("name  -----           " + name);
                                        SuggestionList.add(name);
                                    }
                                    String[] langelements = language.split(",");
                                    List<String> fixedLenghtList = Arrays.asList(langelements);
                                    List<String> items = new ArrayList<>(fixedLenghtList);
                                    System.out.println("list from comma separated String : " + items);

//                                    String[] skillelements = skills.split(",");
//                                    List<String> fixedLenghtskillList = Arrays.asList(skillelements);


                                    et_tag.setText(items);
                                    edt_qualification.setText(qualification);
                                    edt_about.setText(about);


                                    suggestionAdapter = new SuggestionAdapter(getActivity(), SuggestionList);
                                    RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                    medicine_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                    medicine_recycler.setAdapter(suggestionAdapter);
                                    suggestionAdapter.notifyDataSetChanged();


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


//    view services suggestion


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


                                AutoCompletevendor_register_adapter adapter = new AutoCompletevendor_register_adapter(getActivity(), getAllMedicineList);
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


                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    private void Update_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing...");
        progressDialog.show();


        AndroidNetworking.upload(BaseUrl + vendor_update_skills)
                .addMultipartParameter("user_id", user_id)
                .addMultipartParameter("skills", all_Skils)
                .addMultipartParameter("qualification", qualification)
                .addMultipartParameter("language", all_Languages)
                .addMultipartParameter("about", about)
                .addMultipartParameter("profession", profession)
                .setPriority(Priority.LOW)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            progressDialog.dismiss();
                            Log.e("update_vendor  ", jsonObject.toString());
                            System.out.println("update_vendor=====    " + jsonObject.toString());
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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


}