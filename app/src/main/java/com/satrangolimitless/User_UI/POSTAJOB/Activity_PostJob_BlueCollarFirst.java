package com.satrangolimitless.User_UI.POSTAJOB;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Adapter.Adapter_attachments_images;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.model.SubjectData;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

public class Activity_PostJob_BlueCollarFirst extends AppCompatActivity {

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

 
//    Add Attachment

    RecyclerView list;
    ImageView imgback, img_add;
    ArrayList<SubjectData> imagearrayList = new ArrayList<SubjectData>();
    File destination;
    Adapter_attachments_images adapter_popular_categoriesActivity;
    Uri pickedImage;
    String userChoosenTask;
    String filenew1,lang;
    private final ArrayList<String> FilenamList = new ArrayList<>();
    private final ArrayList<Uri> arrayList = new ArrayList<>();
    private final int SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_jobpost_bluecollar_frist);
        session = new Session(getApplicationContext());
        btnnextt = findViewById(R.id.btnnextt);
        back = findViewById(R.id.back);
        txtdate = findViewById(R.id.txtdate);
        txttime = findViewById(R.id.txttime);
        img_add= findViewById(R.id.img_add);
        list = findViewById(R.id.list);
//        txtcurrlocation = findViewById(R.id.txtcurrlocation);
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



       /* if (session.getStart_locationSinglejobpost() != null && !session.getStart_locationSinglejobpost().isEmpty() && !session.getStart_locationSinglejobpost().equals("null") && !session.getStart_locationSinglejobpost().equals("0")) {

            txtcurrlocation.setText(session.getStart_locationSinglejobpost());

        }else {
            txtcurrlocation.setText(adres);
        }*/

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

/*
            txtcurrlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_PostJob_BlueCollarFirst.this, SearchActivitylocationsinglejobpost.class);
                startActivity(intent);

            }
        });
*/


        
        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(Activity_PostJob_BlueCollarFirst.this,


                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                txtdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                txtdate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

                                Date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                session.setDate_bluecolrjobpost(Date);
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


                timepickerdialog = new TimePickerDialog(Activity_PostJob_BlueCollarFirst.this,
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
                             session.setFrom_time_bluecolrjobpost(time);

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }
        });

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRunTimePermission();
            }
        });


        btnnextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = edtdecsription.getText().toString();
//                location = txtcurrlocation.getText().toString();
                if (Date.isEmpty()) {
                    Toast.makeText(Activity_PostJob_BlueCollarFirst.this, "Please select date", Toast.LENGTH_SHORT).show();

                } else if (time.isEmpty()) {

                    Toast.makeText(Activity_PostJob_BlueCollarFirst.this, "Please select time", Toast.LENGTH_SHORT).show();

                } else if (description.isEmpty()) {
                    Toast.makeText(Activity_PostJob_BlueCollarFirst.this, "Please enter description", Toast.LENGTH_SHORT).show();

                } /*else if (location.isEmpty()) {
                    Toast.makeText(Activity_PostJob_BlueCollarFirst.this, "Please select location", Toast.LENGTH_SHORT).show();

                }*/ else {

                    Intent intent = new Intent(Activity_PostJob_BlueCollarFirst.this, Activity_PostJobBlueCollar_Second.class);
//                    intent.putExtra("location", location);
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
            ActivityCompat.requestPermissions(Activity_PostJob_BlueCollarFirst.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
//                    txtcurrlocation.setText(adres);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    public void onBackPressed() {
        
        super.onBackPressed();
    }




    //    Add attachment

    private void checkRunTimePermission() {


        String[] permissionArrays = new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 11111);
        } else {
            // if already permition granted
            // PUT YOUR ACTION (Like Open cemara etc..)
            selectImage();
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Choose from Library","Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_PostJob_BlueCollarFirst.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Activity_PostJob_BlueCollarFirst.this);


                if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, SELECT_FILE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            }
        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {

            pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = Activity_PostJob_BlueCollarFirst.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();


            if (destination != null) {
                filenew1 = destination.getAbsolutePath();
                System.out.println("destinationonSelectFromGalleryResult=====        " + destination.getAbsolutePath());
                FilenamList.add(filenew1);
                System.out.println("FilenamList " + FilenamList.toString());


            } else {
                Toast.makeText(Activity_PostJob_BlueCollarFirst.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap(Activity_PostJob_BlueCollarFirst.this.getContentResolver(), data.getData());

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        try {


            arrayList.add(pickedImage);

            LinkedHashSet<Uri> lhs = new LinkedHashSet<Uri>();

            lhs.addAll(arrayList);
            arrayList.clear();

            arrayList.addAll(lhs);

            imagearrayList.clear();


            for (int i = 0; i < arrayList.size(); i++) {

                String get = (arrayList.get(i)).toString();

                SubjectData allCommunityModel = new SubjectData(get);

                System.out.println("Arraylist#####   " + get);
                imagearrayList.add(allCommunityModel);

            }


            adapter_popular_categoriesActivity = new Adapter_attachments_images(imagearrayList, getApplicationContext());
            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
            list.setLayoutManager(mLayoutManger);
            list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(adapter_popular_categoriesActivity);
            adapter_popular_categoriesActivity.notifyDataSetChanged();

        } catch (Exception e) {
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean openActivityOnce = true;
        boolean openDialogOnce = true;
        if (requestCode == 11111) {

            boolean isPermitted = false;
            for (int i = 0; i < grantResults.length; i++) {
                String permission = permissions[i];

                isPermitted = grantResults[i] == PackageManager.PERMISSION_GRANTED;

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user rejected the permission
                    boolean showRationale = shouldShowRequestPermissionRationale(permission);
                    if (!showRationale) {
                        //execute when 'never Ask Again' tick and permission dialog not show
                    } else {
                        if (openDialogOnce) {
                            Toast.makeText(Activity_PostJob_BlueCollarFirst.this, "Permission required", Toast.LENGTH_SHORT).show();
                            // alertView();
                        }
                    }
                }
            }

            try {
                //selectImage();
            } catch (Exception e) {

            }

            if (isPermitted) {
                selectImage();

            } else {

            }

        }
    }
}
