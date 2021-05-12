package com.satrangolimitless.Vendor_UI;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.satrangolimitless.Adapter.Tarrif_adapter;
import com.satrangolimitless.Dynamicview.Choose_Day_Name;
import com.satrangolimitless.Dynamicview.Choose_Day_Type;
import com.satrangolimitless.Dynamicview.Choose_From_Time;
import com.satrangolimitless.Dynamicview.Choose_To_Time;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
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
import static com.satrangolimitless.Utils.Base_Url.View_vendor_profile;
import static com.satrangolimitless.Utils.Base_Url.View_vendor_tarrif;
import static com.satrangolimitless.Utils.Base_Url.vendor_update_tariff;

public class My_Profile_vendor_tarrif_timings extends Fragment {
    View root;
    ProgressDialog progressDialog;
    Fragment fragment;
    RecyclerView rec_slot;
    Session session;
    ArrayList<Choose_From_Time> choose_from_times = new ArrayList<>();
    ArrayList<Choose_To_Time> choose_to_times = new ArrayList<>();
    ArrayList<Choose_Day_Type> choose_day_types = new ArrayList<>();
    ArrayList<Choose_Day_Name> choose_day_names = new ArrayList<>();
    Tarrif_adapter adapter;
    EditText edt_extra_charge, edt_min_charge, edt_per_day, edt_per_hr;
    Session_vendor session_vendor;
    String pr_days = "", pr_hours = "", extra_charge = "", min_charge = "";
    String per_day = "",
            per_hr = "";
    LinearLayout lladd;
    Button Vcancel, btn_next;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_myprofile_vendor_tarrif, container, false);

        session = new Session(getActivity());

        rec_slot = root.findViewById(R.id.rec_slot);

        btn_next = root.findViewById(R.id.btn_next);
        Vcancel = root.findViewById(R.id.Vcancel);
        edt_extra_charge = root.findViewById(R.id.edt_extra_charge);
        edt_min_charge = root.findViewById(R.id.edt_min_charge);
        edt_per_day = root.findViewById(R.id.edt_per_day);
        edt_per_hr = root.findViewById(R.id.edt_per_hr);
        lladd = root.findViewById(R.id.lladd);

        tarrif_list();
        Viewprofile_vendorApi();

        lladd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), Update_vendor_tarrif_details_slots.class);
                startActivity(intent);

            }
        });

        btn_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                per_hr = edt_per_hr.getText().toString();
                extra_charge = edt_extra_charge.getText().toString();
                min_charge = edt_min_charge.getText().toString();
                per_day = edt_per_day.getText().toString();

                if (per_hr.isEmpty()) {

                    edt_per_hr.setError("Please enter per hour charge");
                    edt_per_hr.requestFocus();

                    return;
                }
                if (per_day.isEmpty()) {

                    edt_per_day.setError("Please enter per day charge");
                    edt_per_day.requestFocus();

                    return;
                }
                if (min_charge.isEmpty()) {
                    edt_min_charge.setError("Please enter minimum charge");
                    edt_min_charge.requestFocus();
                    return;
                }
                if (extra_charge.isEmpty()) {
                    edt_extra_charge.setError("Please enter extra charge");
                    edt_extra_charge.requestFocus();
                    return;
                } else {

                    Update_vendor();
                }


            }
        });

        Vcancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), LandingActivity_Service_provider.class);
                startActivity(in);
                getActivity().finish();
            }
        });

        return root;

    }

    private void tarrif_list() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + View_vendor_tarrif,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            System.out.println("tarrif api-------       " + obj.toString());
                            String result = obj.getString("result");
                            if (result.equalsIgnoreCase("true")) {
                                JSONObject JBJ = obj.getJSONObject("data");

                                String fromtime = JBJ.getString("from_time");
                                String totime = JBJ.getString("to_time");
                                String day_name = JBJ.getString("day_name");
                                String day_type = JBJ.getString("day_type");

                                System.out.println("from time**********       " + fromtime);


                                JSONArray from_timearray = new JSONArray(fromtime);

                                for (int i = 0; i < from_timearray.length(); i++) {
                                    JSONObject object = from_timearray.getJSONObject(i);
                                    String id = object.getString("FromTime");

                                    System.out.println("FromTime----^^^^^^^       " + id);
                                    Choose_From_Time choose_from_time = new Choose_From_Time(id);
                                    choose_from_times.add(choose_from_time);
                                }


                                JSONArray to_timearray = new JSONArray(totime);

                                for (int i = 0; i < to_timearray.length(); i++) {
                                    JSONObject object = to_timearray.getJSONObject(i);
                                    String id = object.getString("ToTime");

                                    System.out.println("ToTime----^^^^^^^       " + id);
                                    Choose_To_Time choose_to_time = new Choose_To_Time(id);
                                    choose_to_times.add(choose_to_time);
                                }


                                JSONArray day_typearray = new JSONArray(day_type);

                                for (int i = 0; i < day_typearray.length(); i++) {
                                    JSONObject object = day_typearray.getJSONObject(i);
                                    String id = object.getString("Day_Type");

                                    System.out.println("day_typearray----^^^^^^^       " + id);
                                    Choose_Day_Type choose_day_type = new Choose_Day_Type(id);
                                    choose_day_types.add(choose_day_type);
                                }


                                JSONArray day_namearray = new JSONArray(day_name);

                                for (int i = 0; i < day_namearray.length(); i++) {
                                    JSONObject object = day_namearray.getJSONObject(i);
                                    String id = object.getString("Day_Name");

                                    System.out.println("Day_Name----^^^^^^^       " + id);
                                    List<String> items = Arrays.asList(id.split("\\s*,\\s*"));


                                    System.out.println("item$$$$$$$         " + items);
                                    Choose_Day_Name choose_day_name = new Choose_Day_Name(id);
                                    choose_day_names.add(choose_day_name);

                                }

                                adapter = new Tarrif_adapter(choose_from_times, choose_to_times, choose_day_types, choose_day_names, getActivity());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                rec_slot.setLayoutManager(mLayoutManger);

                                rec_slot.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                rec_slot.setItemAnimator(new DefaultItemAnimator());
                                rec_slot.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
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


                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


//View min charge max charge vendor profile api


    private void Viewprofile_vendorApi() {


        String url = BaseUrl + View_vendor_profile;

        AndroidNetworking.post(url)
                .addBodyParameter("user_id", session.getUserId())

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

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

                                    min_charge = dataObject.getString("min_charge");
                                    extra_charge = dataObject.getString("extra_charge");
                                    pr_hours = dataObject.getString("pr_hours");
                                    pr_days = dataObject.getString("pr_days");


                                }

                                edt_per_hr.setText(pr_hours);
                                edt_per_day.setText(pr_days);
                                edt_min_charge.setText(min_charge);
                                edt_extra_charge.setText(extra_charge);
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


    private void Update_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing...");
        progressDialog.show();


        AndroidNetworking.upload(BaseUrl + vendor_update_tariff)
                .addMultipartParameter("user_id", session.getUserId())
                .addMultipartParameter("pr_hours", per_hr)
                .addMultipartParameter("pr_days", per_day)
                .addMultipartParameter("min_charge", min_charge)
                .addMultipartParameter("extra_charge", extra_charge)

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
                            System.out.println("update_vendortarrif=====    " + jsonObject.toString());
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