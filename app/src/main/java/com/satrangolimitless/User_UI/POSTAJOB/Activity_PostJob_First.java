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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.satrangolimitless.R;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Activity_PostJob_First extends AppCompatActivity {

    public String Date = "", desc = "", adres = "";
    Button btnnextt;
    ImageView back;
    Session session;
    Session_vendor session_vendor;
    TextView txtdate, txttime, txtcurrlocation;
    EditText edtdecsription;
    GPSTracker gps;
    double latitude, longitude;
    TimePickerDialog timepickerdialog;
    Calendar calendar;
    String longi, latt, format, time, location, description;
    DatePickerDialog datePickerDialog;
    private int CalendarHour, CalendarMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_jobpost_singlemove_frist);
        session = new Session(getApplicationContext());
        btnnextt = findViewById(R.id.btnnextt);
        back = findViewById(R.id.back);
        txtdate = findViewById(R.id.txtdate);
        txttime = findViewById(R.id.txttime);
        txtcurrlocation = findViewById(R.id.txtcurrlocation);
        edtdecsription = findViewById(R.id.edtdecsription);
        getlatlang();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setStart_locationSinglejobpost("");
                session.setDate_singlejobpost("");
                session.setFrom_time_singlejobpost("");
                onBackPressed();
            }
        });



        if (session.getStart_locationSinglejobpost() != null && !session.getStart_locationSinglejobpost().isEmpty() && !session.getStart_locationSinglejobpost().equals("null") && !session.getStart_locationSinglejobpost().equals("0")) {

            txtcurrlocation.setText(session.getStart_locationSinglejobpost());

        }else {
            txtcurrlocation.setText(adres);
        }

        if (session.getDate_singlejobpost() != null && !session.getDate_singlejobpost().isEmpty() && !session.getDate_singlejobpost().equals("null") && !session.getDate_singlejobpost().equals("0")) {

            txtdate.setText(session.getDate_singlejobpost());
            txtdate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

            Date = session.getDate_singlejobpost();


        }

        if (session.getFrom_time_singlejobpost() != null && !session.getFrom_time_singlejobpost().isEmpty() && !session.getFrom_time_singlejobpost().equals("null") && !session.getFrom_time_singlejobpost().equals("0")) {
            txttime.setText(session.getFrom_time_singlejobpost());

            txttime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);
            time = session.getFrom_time_singlejobpost();

        }

            txtcurrlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_PostJob_First.this, SearchActivitylocationsinglejobpost.class);
                startActivity(intent);

            }
        });


        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(Activity_PostJob_First.this,


                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                txtdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                txtdate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

                                Date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                session.setDate_singlejobpost(Date);
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


                timepickerdialog = new TimePickerDialog(Activity_PostJob_First.this,
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
                                time = txttime.getText().toString();
                             session.setFrom_time_singlejobpost(time);

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }
        });


        btnnextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = edtdecsription.getText().toString();
                location = txtcurrlocation.getText().toString();
                if (Date.isEmpty()) {
                    Toast.makeText(Activity_PostJob_First.this, "Please select date", Toast.LENGTH_SHORT).show();

                } else if (time.isEmpty()) {

                    Toast.makeText(Activity_PostJob_First.this, "Please select time", Toast.LENGTH_SHORT).show();

                } else if (description.isEmpty()) {
                    Toast.makeText(Activity_PostJob_First.this, "Please enter description", Toast.LENGTH_SHORT).show();

                } else if (location.isEmpty()) {
                    Toast.makeText(Activity_PostJob_First.this, "Please select location", Toast.LENGTH_SHORT).show();

                } else {

                    Intent intent = new Intent(Activity_PostJob_First.this, Activity_PostJob_Second.class);
                    intent.putExtra("location", location);
                    intent.putExtra("description", description);
                    intent.putExtra("date", Date);
                    intent.putExtra("time", time);
                    startActivity(intent);
                }

            }
        });

    }

    //    lat long
    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_PostJob_First.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
        session.setStart_locationSinglejobpost("");
        session.setDate_singlejobpost("");
        session.setFrom_time_singlejobpost("");
        super.onBackPressed();
    }
}
