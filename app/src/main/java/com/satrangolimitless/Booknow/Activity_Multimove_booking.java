package com.satrangolimitless.Booknow;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.satrangolimitless.Adapter.Adapter_Vendor_Availibility_time;
import com.satrangolimitless.Adapter.Adapter_attachments_images;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.APIClient;
import com.satrangolimitless.Utils.ApiService;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.Utils.Utils;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.model.Add_Booking_Multi_move;
import com.satrangolimitless.model.SubjectData;
import com.satrangolimitless.multimoveaddmore.Addmore;
import com.satrangolimitless.session.Session;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Activity_Multimove_booking extends AppCompatActivity {

    private final List<String> FilenamList = new ArrayList<>();
    private final ArrayList<Uri> arrayList = new ArrayList<>();
    private final int SELECT_FILE = 1;
    public String booking_date = "", desc = "", adres = "";
    ArrayList<Addmore> cricketersList = new ArrayList<>();
    JsonArray jsonElements;
    RadioButton radio_booklater, radio_booknow;
    GPSTracker gps;
    double latitude, longitude;
    String longi, latt;
    RecyclerView list;
    ArrayList<SubjectData> imagearrayList = new ArrayList<SubjectData>();
    File destination;
    Adapter_attachments_images adapter_popular_categoriesActivity;
    Uri pickedImage;
    String userChoosenTask;
    String filenew1;
    ProgressDialog progressDialog;
    EditText edt_desc, EndLocation;
    TextView CURRENTLOCATION, txtdistance, txtrating, txt_skill,
            txtmincharge, txtprice, txtrank, txtname;
    RecyclerView rec_availibity_time;
    CardView cd_heavy, cd_medium, cd_lite;
    DatePickerDialog datePickerDialog;
    ImageView imgback, img_add;
    TextView txtback, txthrs, txtdays, txtweeks;
    TextView signIn_DATE, signIn_time;
    String name, min_amount, max_amount, rating, service,serviceid, vendorid, dis, user_id, booking_type = "",location,endlocation="";
    String totime = "00", fromtime = "";
    Adapter_Vendor_Availibility_time adapter;
    Session session;

    Button submit_btn, btn_cancel, addanother;
    TimePickerDialog timepickerdialog;
    Calendar calendar;
    String format, time, start_location1, end_location, weight;
    LinearLayout layoutList;
    private int CalendarHour, CalendarMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_multimove_booking);
        session = new Session(getApplicationContext());
        user_id = session.getUserId();
        System.out.println("user_id--------      " + session.getUserId());


        rec_availibity_time = findViewById(R.id.rec_availibity_time);
        radio_booknow = findViewById(R.id.radio_booknow);
        radio_booklater = findViewById(R.id.radio_booklater);
        submit_btn = findViewById(R.id.submit_btn);
        imgback = findViewById(R.id.imgback);
        img_add = findViewById(R.id.img_add);
        addanother = findViewById(R.id.addanother);
        list = findViewById(R.id.list);

        edt_desc = findViewById(R.id.edt_desc);
        EndLocation = findViewById(R.id.EndLocation);
        cd_lite = findViewById(R.id.cd_lite);
        cd_medium = findViewById(R.id.cd_medium);
        cd_heavy = findViewById(R.id.cd_heavy);


        signIn_DATE = findViewById(R.id.signIn_DATE);
        signIn_time = findViewById(R.id.signIn_time);

        txtname = findViewById(R.id.txtname);
//        txtrank = findViewById(R.id.txtrank);
        txtprice = findViewById(R.id.txtprice);
        txtmincharge = findViewById(R.id.txtmincharge);
        txt_skill = findViewById(R.id.txt_skill);
        txtrating = findViewById(R.id.txtrating);
        layoutList = findViewById(R.id.layoutList);
        CURRENTLOCATION = findViewById(R.id.CURRENTLOCATION);



        try {

            if (session.getStart_locationmultimovebookingstart() != null && !session.getStart_locationmultimovebookingstart().isEmpty() && !session.getStart_locationmultimovebookingstart().equals("null") && !session.getStart_locationmultimovebookingstart().equals("0")) {

                System.out.println("multi start location  --    "+session.getStart_locationmultimovebookingstart() );
                location=session.getStart_locationmultimovebookingstart();
                CURRENTLOCATION.setText(location);

            }else
            {
                CURRENTLOCATION.setText(adres);

            }


   if (session.getStart_locationmultimovebookingend() != null && !session.getStart_locationmultimovebookingend().isEmpty() && !session.getStart_locationmultimovebookingend().equals("null") && !session.getStart_locationmultimovebookingend().equals("0")) {
            end_location=session.getStart_locationmultimovebookingend();
               EndLocation.setText(end_location);
            }





            name = session.getBookvname();
            vendorid = session.getBookvid();
            service = session.getBookservice();
            rating = session.getBookrating();
            min_amount = session.getBookminam();

            max_amount = session.getBookmaxam();
            dis = session.getBookdis();
            serviceid = session.getBookcat();


        } catch (Exception e) {
            e.printStackTrace();
        }


        if (session.getBookdateMultimoveBoking() != null && !session.getBookdateMultimoveBoking().isEmpty() && !session.getBookdateMultimoveBoking().equals("null") && !session.getBookdateMultimoveBoking().equals("0")) {
            signIn_DATE.setText(session.getBookdateMultimoveBoking());
            signIn_DATE.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);


        } else {
            signIn_DATE.setText("");

        }

        getlatlang();
        addView();


        CURRENTLOCATION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Multimove_booking.this, SearchActivityMultimove.class);
                startActivity(intent);
                finish();
            }
        });
        EndLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Multimove_booking.this, SearchActivityMultimoveEndlocation.class);
                startActivity(intent);
                finish();
            }
        });

        txtname.setText(name);
        txtprice.setText("₹ " + min_amount);
        txtrating.setText(rating);


        String myString = dis;
        Float foo = Float.parseFloat(myString);
        Float i2 = foo / 1000;


        txt_skill.setText(service);

        txtmincharge.setText(max_amount);
        txtmincharge.setPaintFlags(txtmincharge.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//        showCustomDialog();

        addanother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRunTimePermission();

            }
        });

        radio_booknow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                booking_type = "Book for now";
            }
        });

        radio_booklater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                booking_type = "Schedule for later";
            }
        });


        signIn_DATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(Activity_Multimove_booking.this,


                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                signIn_DATE.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                signIn_DATE.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

                                booking_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                session.setBookdateMultimoveBoking(booking_date);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });


        signIn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(Activity_Multimove_booking.this,
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


                                signIn_time.setText(hourOfDay + ":" + minute + format);
                                signIn_time.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);
                                time = signIn_time.getText().toString();

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();


            }
        });

        cd_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd_heavy.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_medium.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_lite.setCardBackgroundColor(Color.parseColor("#ffffff"));
                weight = "medium";
            }
        });
        cd_lite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd_heavy.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_lite.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_medium.setCardBackgroundColor(Color.parseColor("#ffffff"));
                weight = "light";
            }
        });
        cd_heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd_lite.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_heavy.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_medium.setCardBackgroundColor(Color.parseColor("#ffffff"));
                weight = "heavy";
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfValidAndRead();

                desc = edt_desc.getText().toString();
                start_location1 = CURRENTLOCATION.getText().toString();
                jsonElements = (JsonArray) new Gson().toJsonTree(cricketersList);
                System.out.println("check arraylist   ----------      " + jsonElements.toString());
                String tolocation = jsonElements.toString();
                end_location = EndLocation.getText().toString();
                System.out.println("Multimove params -- -  "+user_id+"  "+ vendorid+"  "+ booking_date+"  "+ time+"  "+ totime+"  "+ start_location1+"  "+ tolocation+"  "+ end_location+"  "+ weight+"  "+ desc+"  "+ booking_type);

               booking_date = signIn_DATE.getText().toString();

                if (booking_date.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select date", Toast.LENGTH_SHORT).show();
                }  else if(time.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please select Time", Toast.LENGTH_SHORT).show();

                }
                else if(start_location1.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please select location", Toast.LENGTH_SHORT).show();

                }
                else if(tolocation.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please select to location", Toast.LENGTH_SHORT).show();

                }
                else if(end_location.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please select end location", Toast.LENGTH_SHORT).show();

                }
                else if(weight.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please select weight", Toast.LENGTH_SHORT).show();

                }
                else if(desc.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_SHORT).show();

                }
                else if(booking_type.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please select booking type", Toast.LENGTH_SHORT).show();

                }


                else {
                    if (Utils.isInternetConnected(Activity_Multimove_booking.this)) {

                        AddBooking(user_id, vendorid, serviceid,booking_date, time, totime, start_location1, tolocation, end_location, weight, desc, booking_type);


                    } else {

                        Toast.makeText(getApplicationContext(), "No Internet connection.", Toast.LENGTH_SHORT).show();
                    }

                }



            }
        });


    }




/*
 Get availibility time vendor---------------------------------------------------------------------------------------------------------
 */


//    ---------------------------------------------Add image attachment

    private void checkRunTimePermission() {


        String[] permissionArrays = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 11111);
        } else {
            // if already permition granted
            // PUT YOUR ACTION (Like Open cemara etc..)
            selectImage();
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Multimove_booking.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Activity_Multimove_booking.this);


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
            Cursor cursor = Activity_Multimove_booking.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();


            if (destination != null) {
                filenew1 = destination.getAbsolutePath();
                System.out.println("destinationonSelectFromGalleryResult=====        " + destination.getAbsolutePath());
                FilenamList.add(filenew1);
                System.out.println("FilenamList " + FilenamList.toString());


            } else {
                Toast.makeText(Activity_Multimove_booking.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap(Activity_Multimove_booking.this.getContentResolver(), data.getData());

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
                            Toast.makeText(Activity_Multimove_booking.this, "Permission required", Toast.LENGTH_SHORT).show();
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


//


    //    Add booking
    //--------------- For api url check ApiService and APIClient under Utils----------------------

    public void AddBooking(String user_id, String vendor_id, String service_id, String booking_date, String from_time, String to_time, String start_location1, String to_location, String end_location, String work_description, String Jobestimate, String Booking_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Multimove_booking.this);
        progressDialog.show();
        ApiService apiInterface = APIClient.getClient().create(ApiService.class);

        MultipartBody.Part[] surveyImagesParts = null;

        surveyImagesParts = new MultipartBody.Part[FilenamList.size()];
        for (int index = 0; index < FilenamList.size(); index++) {
            File file = new File(FilenamList.get(index).toString());
            System.out.println("FilenamList**********     " + FilenamList.get(index));

            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("upload_doc[]", file.getName(), surveyBody);
        }

        RequestBody USER_ID = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody VENDRO_ID = RequestBody.create(MediaType.parse("text/plain"), vendor_id);
        RequestBody SERVICEID = RequestBody.create(MediaType.parse("text/plain"), service_id);
        RequestBody BOOKING_DATE = RequestBody.create(MediaType.parse("text/plain"), booking_date);
        RequestBody FROM_TIME = RequestBody.create(MediaType.parse("text/plain"), from_time);
        RequestBody TO_TIME = RequestBody.create(MediaType.parse("text/plain"), to_time);
        RequestBody START_LOCATION1 = RequestBody.create(MediaType.parse("text/plain"), start_location1);
        RequestBody TO_LOCATION = RequestBody.create(MediaType.parse("text/plain"), to_location);
        RequestBody END_LOCATION = RequestBody.create(MediaType.parse("text/plain"), end_location);
        RequestBody WORK_DESCRIPTION = RequestBody.create(MediaType.parse("text/plain"), work_description);
        RequestBody WEIGHT = RequestBody.create(MediaType.parse("text/plain"), Jobestimate);
        RequestBody BOOKING_TYPE = RequestBody.create(MediaType.parse("text/plain"), Booking_type);

        Call<Add_Booking_Multi_move> call = apiInterface.Add_Employee_Multi(USER_ID, VENDRO_ID, SERVICEID,BOOKING_DATE, FROM_TIME, TO_TIME, START_LOCATION1, TO_LOCATION, END_LOCATION, WEIGHT, WORK_DESCRIPTION, BOOKING_TYPE, surveyImagesParts);


        call.enqueue(new Callback<Add_Booking_Multi_move>() {
            @Override
            public void onResponse(Call<Add_Booking_Multi_move> call, retrofit2.Response<Add_Booking_Multi_move> response) {
                progressDialog.dismiss();

                System.out.println("RESULT----       " + response.body().getResult());
                if (response.body().getResult().equals("true")) {
                    String bookingid = response.body().getData().getId();
                    session.setBookvid("");
                    session.setBookvname("");
                    session.setBookminam("");
                    session.setBookmaxam("");
                    session.setBookrating("");
                    session.setBookservice("");
                    session.setBookdis("");
                    session.setBookdateMultimoveBoking("");
                    session.setStart_locationmultimovebookingend("");
                    session.setStart_locationmultimovebookingstart("");
                    Intent intent = new Intent(Activity_Multimove_booking.this, Activity_booking_timer_show_dialogue.class);
                    intent.putExtra("id", bookingid);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(Activity_Multimove_booking.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_Booking_Multi_move> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ccheckResponce", " :" + t.getMessage());
                Toast.makeText(Activity_Multimove_booking.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void addView() {
        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_tolocation, null, false);
        EditText editQuery = (EditText) cricketerView.findViewById(R.id.edit_query);

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
            EditText editTextName = (EditText) cricketerView.findViewById(R.id.edit_query);

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


    //    For lat long
    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_Multimove_booking.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }


}

 