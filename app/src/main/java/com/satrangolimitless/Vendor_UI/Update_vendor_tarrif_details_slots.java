package com.satrangolimitless.Vendor_UI;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.satrangolimitless.Dynamicview.Choose_Day_Name;
import com.satrangolimitless.Dynamicview.Choose_Day_Type;
import com.satrangolimitless.Dynamicview.Choose_From_Time;
import com.satrangolimitless.Dynamicview.Choose_To_Time;
import com.satrangolimitless.Dynamicview.Days_Model;
import com.satrangolimitless.Dynamicview.EditValues;
import com.satrangolimitless.Dynamicview.Size_Click;
import com.satrangolimitless.Dynamicview.StaticDaysAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.Vendor_UI.vendor_profile.ProfileThreeActivity;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.Addtarriff_Api;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;

public class Update_vendor_tarrif_details_slots extends AppCompatActivity  implements  Size_Click{
    private int mHour, mMinute;
    Button btn_next,Vcancel;
    Session_vendor session_vendor;
    LinearLayout layout_list,lladd;
    EditText edt_per_hr,edt_extra_charge,edt_min_charge,edt_per_day;
    String FROM_TIME,TO_TIME,DAY_TYPE,DAY_NAME;

    ArrayList<Choose_From_Time> choose_from_times = new ArrayList<>();
    ArrayList<Choose_To_Time> choose_to_times = new ArrayList<>();
    ArrayList<Choose_Day_Type> choose_day_types = new ArrayList<>();
    ArrayList<Choose_Day_Name> choose_day_names = new ArrayList<>();



    JsonArray jsonElementsfrom;
    JsonArray jsonElementsto;
    JsonArray jsonElementsdaytype;
    JsonArray jsonElementsdayname;
    String all_days;


    StaticDaysAdapter staticSizeAdapter;
    ArrayList<Days_Model> size_modelArrayList=new ArrayList<>();
    Size_Click size_click;
    ArrayList<String>get_size=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_tarrif_update);

        session_vendor=new Session_vendor(getApplicationContext());


        edt_per_hr= findViewById(R.id.edt_per_hr);
        edt_extra_charge= findViewById(R.id.edt_extra_charge);
        edt_min_charge= findViewById(R.id.edt_min_charge);
        edt_per_day= findViewById(R.id.edt_per_day);


        btn_next= findViewById(R.id.btn_next);
        Vcancel= findViewById(R.id.Vcancel);

        lladd= findViewById(R.id.lladd);
        layout_list= findViewById(R.id.layout_list);



        btn_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                checkIfValidAndRead();

            }
        });

        addView();
        lladd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                 addView();
            }
        });









    }


    private void addView() {
        final View cricketerView = getLayoutInflater().inflate(R.layout.row_slot_item, null, false);
        final TextView txt_from = (TextView) cricketerView.findViewById(R.id.txt_from);
        final TextView txt_to = (TextView) cricketerView.findViewById(R.id.txt_to);
        final Button btn_everyday = (Button) cricketerView.findViewById(R.id.btn_everyday);
        final Button btn_weekday = (Button) cricketerView.findViewById(R.id.btn_weekday);
        final Button btn_wekend = (Button) cricketerView.findViewById(R.id.btn_wekend);




        final RecyclerView recydays= (RecyclerView) cricketerView.findViewById(R.id.recydays);
        final EditText edtvalue = (EditText) cricketerView.findViewById(R.id.edtvalue);

        ImageView imageClose = (ImageView) cricketerView.findViewById(R.id.image_remove);

            imageClose.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeView(cricketerView);
                }
            });

        btn_everyday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_everyday.setBackgroundResource(R.drawable.greenbutton);
                btn_everyday.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                btn_weekday.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
                btn_wekend.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
                btn_weekday.setBackgroundResource(R.drawable.greenborderbutton);
                btn_wekend.setBackgroundResource(R.drawable.greenborderbutton);

                try {
                    edtvalue.setText("Everyday");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        btn_weekday.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    btn_weekday.setBackgroundResource(R.drawable.greenbutton);
                    btn_weekday.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    btn_everyday.setBackgroundResource(R.drawable.greenborderbutton);
                    btn_everyday.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
                    btn_wekend.setBackgroundResource(R.drawable.greenborderbutton);
                    btn_wekend.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
                    edtvalue.setText("Weekday");

                }
        });

        btn_wekend.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                  btn_wekend.setBackgroundResource(R.drawable.greenbutton);
                  btn_wekend.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                  btn_weekday.setBackgroundResource(R.drawable.greenborderbutton);
                  btn_weekday.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
                  btn_everyday.setBackgroundResource(R.drawable.greenborderbutton);
                  btn_everyday.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.vendorprimerycolor));
                  edtvalue.setText("Weekend");

                }
        });



        txt_from.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Update_vendor_tarrif_details_slots.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txt_from.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

            txt_to.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Update_vendor_tarrif_details_slots.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txt_to.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
           });

        size_modelArrayList.clear();
        Days_Model size_model=new Days_Model("1","S","S");
        size_modelArrayList.add(size_model);
        Days_Model size_model2=new Days_Model("2","M","M");
        size_modelArrayList.add(size_model2);
        Days_Model size_model3=new Days_Model("3","T","T");
        size_modelArrayList.add(size_model3);
        Days_Model size_model4=new Days_Model("4","W","W");
        size_modelArrayList.add(size_model4);
        Days_Model size_mode5l=new Days_Model("5","Th","Th");
        size_modelArrayList.add(size_mode5l);
        Days_Model size_mode6l=new Days_Model("6","F","F");
        size_modelArrayList.add(size_mode6l);
        Days_Model size_mode7l=new Days_Model("7","Sa","Sa");
        size_modelArrayList.add(size_mode7l);




        staticSizeAdapter = new StaticDaysAdapter(size_modelArrayList, Update_vendor_tarrif_details_slots.this,size_click);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recydays.setLayoutManager(layoutManager);
        recydays.setItemAnimator(new DefaultItemAnimator());
        recydays.setAdapter(staticSizeAdapter);





        layout_list.addView(cricketerView);
    }

    private void removeView(View view) {
        layout_list.removeView(view);
    }







    private boolean checkIfValidAndRead() {


        choose_from_times.clear();
        choose_to_times.clear();
        choose_day_types.clear();
        choose_day_names.clear();


        boolean result = true;
        for (int i = 0; i <  layout_list.getChildCount(); i++) {
            View cricketerView = layout_list.getChildAt(i);
            TextView txt_from = (TextView) cricketerView.findViewById(R.id.txt_from);
            TextView txt_to = (TextView) cricketerView.findViewById(R.id.txt_to);
            EditText edtvalue = (EditText) cricketerView.findViewById(R.id.edtvalue);
            EditValues cricketer = new EditValues();
            Choose_From_Time choose_from_time = new Choose_From_Time();
            Choose_To_Time choose_to_time = new Choose_To_Time();
            Choose_Day_Type choose_day_type = new Choose_Day_Type();
            Choose_Day_Name choose_day_name = new Choose_Day_Name();

                all_days = get_size.toString().replaceAll("(^\\[|\\]$)", "");
                all_days=all_days.toString().replace("","");


            if (!txt_from.getText().toString().equals("")) {

                choose_from_time.setFromTime(txt_from.getText().toString());
                System.out.println("from time---        "+txt_from.getText().toString());

            } else {
                result = false;
                break;
            } if (!txt_to.getText().toString().equals("")) {

                choose_to_time.setToTime(txt_to.getText().toString());
                System.out.println("to time---        "+txt_to.getText().toString());

            } else {
                result = false;
                break;
            }
            if (!edtvalue.getText().toString().equals("")) {

                choose_day_type.setDay_Type(edtvalue.getText().toString());
                System.out.println("DAY---        "+edtvalue.getText().toString());

            } else {
                result = false;
                break;
            }


            choose_day_name.setDay_Name(all_days);
            choose_from_times.add(choose_from_time);
            choose_to_times.add(choose_to_time);
            choose_day_types.add(choose_day_type);
            choose_day_names.add(choose_day_name);

            jsonElementsfrom = (JsonArray) new Gson().toJsonTree(choose_from_times);
            jsonElementsto = (JsonArray) new Gson().toJsonTree(choose_to_times);
            jsonElementsdaytype = (JsonArray) new Gson().toJsonTree(choose_day_types);
            jsonElementsdayname = (JsonArray) new Gson().toJsonTree(choose_day_names);

            System.out.println("fromtime--  "+jsonElementsfrom.toString());
            System.out.println("totime-- "+jsonElementsto.toString());
            System.out.println("daytype-- "+jsonElementsdaytype.toString());
            System.out.println("dayname-- "+jsonElementsdayname.toString());

            FROM_TIME=jsonElementsfrom.toString();
            TO_TIME=jsonElementsto.toString();
            DAY_TYPE=jsonElementsdaytype.toString();
            DAY_NAME=jsonElementsdayname.toString();

        }

        if (!result) {
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }else
        {
            AddtarriffApi();

        }


        return result;


    }

    @Override
    public void onsizeClick(ArrayList<String> value) {
             System.out.println(" <><><DAYS><>> "+value.toString());

        get_size=value;
    }

    @Override
    protected void onResume() {

        get_size.clear();


        super.onResume();
    }

    @Override
    public void onBackPressed() {


        get_size.clear();


        super.onBackPressed();
    }


    private void AddtarriffApi() {
        final ProgressDialog progressDialog = new ProgressDialog(Update_vendor_tarrif_details_slots.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl+Addtarriff_Api,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v("<>AddtarriffApi  ", response.toString());
                        System.out.println("AddtarriffApi response ===== "+response.toString());
                        try {

                            progressDialog.dismiss();
                             JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            if (result.equalsIgnoreCase("true")) {

//                                Intent intent = new Intent(Update_vendor_tarrif_details_slots.this, ProfileThreeActivity.class);
//                                startActivity(intent);


                            } else {

                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

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
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("from_time",FROM_TIME );
                params.put("to_time",TO_TIME );
                params.put("day_type",DAY_TYPE );
                params.put("day_name",DAY_NAME );
                params.put("user_id",session_vendor.getUserId() );

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }








}


 