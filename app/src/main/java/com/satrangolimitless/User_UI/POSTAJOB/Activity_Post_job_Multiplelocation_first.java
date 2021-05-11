package com.satrangolimitless.User_UI.POSTAJOB;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.satrangolimitless.R;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.multimoveaddmore.Addmore;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Activity_Post_job_Multiplelocation_first extends AppCompatActivity {

    public String DATE = "", desc = "", adres = "";
    ArrayList<Addmore> cricketersList = new ArrayList<>();
    JsonArray jsonElements;
    LinearLayout layoutList;
    Button btnnextt;
    Button submit_btn, btn_cancel, addanother;
    EditText EndLocation;
    ImageView back;
    Session session;
    Session_vendor session_vendor;
    TextView txtdate, txttime, txtcurrlocation;
    EditText edtdecsription;
    GPSTracker gps;
    double latitude, longitude;
    TimePickerDialog timepickerdialog;
    Calendar calendar;
    String longi, latt, format, time, Start_location, description;
    DatePickerDialog datePickerDialog;
    private int CalendarHour, CalendarMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_jobpost_multimove_frist);

        session = new Session(getApplicationContext());
        btnnextt = findViewById(R.id.btnnextt);
        back = findViewById(R.id.back);
        txtdate = findViewById(R.id.txtdate);
        txttime = findViewById(R.id.txttime);
        layoutList = findViewById(R.id.layoutList);
        addanother = findViewById(R.id.addanother);
        txtcurrlocation = findViewById(R.id.txtcurrlocation);
        EndLocation = findViewById(R.id.EndLocation);
        getlatlang();



        if (session.getStart_locationMultiMovejobpost() != null && !session.getStart_locationMultiMovejobpost().isEmpty() && !session.getStart_locationMultiMovejobpost().equals("null") && !session.getStart_locationMultiMovejobpost().equals("0")) {

            txtcurrlocation.setText(session.getStart_locationMultiMovejobpost());
        }

        if (session.getMljobpostdate() != null && !session.getMljobpostdate().isEmpty() && !session.getMljobpostdate().equals("null") && !session.getMljobpostdate().equals("0")) {
            txtdate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

            txtdate.setText(session.getMljobpostdate());

        }
         if (session.getMljobposttime() != null && !session.getMljobposttime().isEmpty() && !session.getMljobposttime().equals("null") && !session.getMljobposttime().equals("0")) {
             txttime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

             txttime.setText(session.getMljobposttime());
        }




        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(Activity_Post_job_Multiplelocation_first.this,


                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                txtdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                txtdate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

                                DATE = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
System.out.println("first page DATE--**-----        "+DATE);
                                session.setMljobpostdate(DATE);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });

        txttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(Activity_Post_job_Multiplelocation_first.this,
                        new TimePickerDialog.OnTimeSetListener() {


                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }


                                txttime.setText(hourOfDay + ":" + minute + format);
                                txttime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);
                                 time=(hourOfDay + ":" + minute + format);

                                System.out.println("first page time***  "+ time);
                                session.setMljobposttime(time);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }
        });


        txtcurrlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Post_job_Multiplelocation_first.this, SearchActivity_Multimove_Jobpost.class);
                startActivity(intent);
                finish();
            }
        });
        addanother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });


        btnnextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfValidAndRead();


                jsonElements = (JsonArray) new Gson().toJsonTree(cricketersList);
                System.out.println("check arraylist$$$$$ ----------      " + jsonElements.toString());
                String tolocation = jsonElements.toString();



                Start_location=txtcurrlocation.getText().toString();
                String endlocation = EndLocation.getText().toString();

                if (DATE.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select date", Toast.LENGTH_SHORT).show();

                } else if (time.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please select time", Toast.LENGTH_SHORT).show();

                }else if (Start_location.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please select start location", Toast.LENGTH_SHORT).show();

                }
                else if (tolocation.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please select to location", Toast.LENGTH_SHORT).show();

                }
                else if (endlocation.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please select end location", Toast.LENGTH_SHORT).show();

                }else {
                    Intent intent = new Intent(Activity_Post_job_Multiplelocation_first.this, Activity_PostJob_Multiplelocation_Second.class);

                    intent.putExtra("location", Start_location);
                    intent.putExtra("tolocation", tolocation);
                    intent.putExtra("endlocation", endlocation);

                    startActivity(intent);

                }





            }
        });


    }


    private void addView() {
        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_tolocation, null, false);
        EditText editQuery = cricketerView.findViewById(R.id.edit_query);

        editQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        layoutList.addView(cricketerView);
    }


    private boolean checkIfValidAndRead() {
        cricketersList.clear();
        boolean result = true;
        for (int i = 0; i < layoutList.getChildCount(); i++) {
            View cricketerView = layoutList.getChildAt(i);
            EditText editTextName = cricketerView.findViewById(R.id.edit_query);

            Addmore cricketer = new Addmore();
            if (!editTextName.getText().toString().equals("")) {
                cricketer.setToLocation(editTextName.getText().toString().trim());
            } else {
                result = false;
                break;
            }

            cricketersList.add(cricketer);
        }

        if (!result) {
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }



    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_Post_job_Multiplelocation_first.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //    Toast.makeText(LoginActivity.this,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(getApplicationContext());
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                latt = String.valueOf(latitude);
                longi = String.valueOf(longitude);

                List<Address> addresses = null;

                try {
                    Geocoder geocoder;
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    adres = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    System.out.println("adres     " + adres);
                    txtcurrlocation.setText(adres);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }


    @Override
    public void onBackPressed() {
        session.setMljobposttime("");
        session.setMljobpostdate("");
        session.setStart_locationMultiMovejobpost("");
        super.onBackPressed();
    }
}
