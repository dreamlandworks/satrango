package com.satrangolimitless.Booknow;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.Adapter.Adapter_Vendor_Availibility_time;
import com.satrangolimitless.Adapter.Adapter_attachments_images;
import com.satrangolimitless.Dynamicview.Choose_From_Time;
import com.satrangolimitless.Dynamicview.Choose_To_Time;
import com.satrangolimitless.Interface.onFrom_timeclick;
import com.satrangolimitless.Interface.onTo_timeclick;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.APIClient;
import com.satrangolimitless.Utils.ApiService;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.Utils.Utils;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.gps.GPSTracker;
import com.satrangolimitless.model.Add_Booking_Single_move;
import com.satrangolimitless.model.SubjectData;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Get_vendor_availability_time;


public class BooknowSingleMoveActivity extends AppCompatActivity implements onFrom_timeclick, onTo_timeclick {

    private final List<String> FilenamList = new ArrayList<>();
    private final ArrayList<Uri> arrayList = new ArrayList<>();
    private final int SELECT_FILE = 1;
    public String booking_date = "", desc = "", adres = "";
    RadioButton radio_booklater,
            radio_booknow;
    GPSTracker gps;
    double latitude, longitude;
    String longi, latt, location;
    RecyclerView list;
    ArrayList<SubjectData> imagearrayList = new ArrayList<SubjectData>();
    File destination;
    Adapter_attachments_images adapter_popular_categoriesActivity;
    Uri pickedImage;
    String userChoosenTask;
    String filenew1;
    ProgressDialog progressDialog;
    EditText edt_desc;
    TextView CURRENTLOCATION, txtdistance, txtrating, txt_skill,
            txtmincharge, txtprice, txtrank, txtname;
    RecyclerView rec_availibity_time;
    CardView cd_hour, cd_days, cd_weeks;
    DatePickerDialog datePickerDialog;
    ImageView imgback, img_add;
    TextView txtback, txthrs, txtdays, txtweeks;
    TextView signIn_DATE;
    String name, min_amount, max_amount, rating, service,service_id, vendorid, dis, user_id, booking_type = "";
    String totime = "", fromtime = "";
    Adapter_Vendor_Availibility_time adapter;
    Session session;
    ArrayList<Choose_From_Time> choose_from_times = new ArrayList<>();
    ArrayList<Choose_To_Time> choose_to_times = new ArrayList<>();
RadioGroup radiogrop;
    Button submit_btn, btn_cancel;
    LinearLayout lltimeslots,lldate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booknow_single_move);
        session = new Session(getApplicationContext());
        user_id = session.getUserId();
        System.out.println("user_id--------      " + session.getUserId());
//        get intent data
        try {
            name = session.getBookvname();
            vendorid = session.getBookvid();
            service = session.getBookservice();
            rating = session.getBookrating();
            min_amount = session.getBookminam();
            max_amount = session.getBookmaxam();
            dis = session.getBookdis();
            service_id = session.getBookcat();



        } catch (Exception e) {
            e.printStackTrace();
        }


        rec_availibity_time = findViewById(R.id.rec_availibity_time);
        radio_booknow = findViewById(R.id.radio_booknow);
        radio_booklater = findViewById(R.id.radio_booklater);
        submit_btn = findViewById(R.id.submit_btn);
        imgback = findViewById(R.id.imgback);
        img_add = findViewById(R.id.img_add);
        txtback = findViewById(R.id.txtback);
        edt_desc = findViewById(R.id.edt_desc);
        cd_hour = findViewById(R.id.cd_hour);
        cd_days = findViewById(R.id.cd_days);
        cd_weeks = findViewById(R.id.cd_weeks);
        txthrs = findViewById(R.id.txthrs);
        txtdays = findViewById(R.id.txtdays);
        txtweeks = findViewById(R.id.txtweeks);
        lldate = findViewById(R.id.lldate);
        lltimeslots = findViewById(R.id.lltimeslots);
        radiogrop = findViewById(R.id.radiogrop);

        signIn_DATE = findViewById(R.id.signIn_DATE);

        txtname = findViewById(R.id.txtname);
        txtrank = findViewById(R.id.txtrank);
        txtprice = findViewById(R.id.txtprice);
        txtmincharge = findViewById(R.id.txtmincharge);
        txt_skill = findViewById(R.id.txt_skill);
        txtrating = findViewById(R.id.txtrating);
        txtdistance = findViewById(R.id.txtdistance);


        CURRENTLOCATION = findViewById(R.id.CURRENTLOCATION);

        getlatlang();


        location = session.getStart_locationsinglemovebooking();





            if (session.getStart_locationsinglemovebooking() != null && !session.getStart_locationsinglemovebooking().isEmpty() && !session.getStart_locationsinglemovebooking().equals("null") && !session.getStart_locationsinglemovebooking().equals("0")) {

            CURRENTLOCATION.setText(session.getStart_locationsinglemovebooking());
        } else {
            CURRENTLOCATION.setText(adres);
        }

        if (session.getBookdateSinglMoveBoking() != null && !session.getBookdateSinglMoveBoking().isEmpty() && !session.getBookdateSinglMoveBoking().equals("null") && !session.getBookdateSinglMoveBoking().equals("0")) {

            signIn_DATE.setText(session.getBookdateSinglMoveBoking());
            signIn_DATE.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);


        } else {

        }

        list = findViewById(R.id.list);
        txtname.setText(name);
        txtprice.setText("₹" + min_amount);
        txtrating.setText(rating);


        String myString = dis;
        Float foo = Float.parseFloat(myString);
        Float i2 = foo / 1000;


        txtdistance.setText(new DecimalFormat("##.##").format(i2) + " kms away");

        System.out.println("booknow distance          " + i2);


        txt_skill.setText(service);

        txtmincharge.setText(max_amount);
        txtmincharge.setPaintFlags(txtmincharge.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//        showCustomDialog();
        availibilitytimelist();


        imgback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CURRENTLOCATION.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BooknowSingleMoveActivity.this, SearchActivity.class);

                startActivity(intent);
                finish();
            }
        });

        img_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRunTimePermission();

            }
        });




       /* radio_booknow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Toast.makeText(BooknowSingleMoveActivity.this, "now", Toast.LENGTH_SHORT).show();

            }
        });

        radio_booklater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Toast.makeText(BooknowSingleMoveActivity.this, "lter", Toast.LENGTH_SHORT).show();

                //radio_booknow.setChecked(false);
            }
        });*/


        radiogrop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.radio_booknow:
                        // TODO Something
                        booking_type = "Book for now";

                        lldate.setVisibility(View.GONE);
                        booking_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        System.out.println("booking_date========       "+booking_date);

                        break;
                    case R.id.radio_booklater:
                        // TODO Something
                        booking_type = "Schedule for later";
                        lldate.setVisibility(View.VISIBLE);

                        break;

                }
            }
        });



        signIn_DATE.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(BooknowSingleMoveActivity.this,


                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                signIn_DATE.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                signIn_DATE.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

                                booking_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                session.setBookdateSinglemoveBoking(booking_date);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });


        submit_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                desc = edt_desc.getText().toString();
                String start_location1 = CURRENTLOCATION.getText().toString();


                if (booking_date.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select date", Toast.LENGTH_SHORT).show();
                } else if (fromtime.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select  time slot", Toast.LENGTH_SHORT).show();
                } else if (totime.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select time slot", Toast.LENGTH_SHORT).show();
                } else if (booking_type.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select Booking type", Toast.LENGTH_SHORT).show();
                } else if (start_location1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please select location.", Toast.LENGTH_SHORT).show();
                } else if (desc.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter description", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utils.isInternetConnected(BooknowSingleMoveActivity.this)) {

                        AddBooking(user_id, vendorid,service_id, booking_date, fromtime, totime, start_location1, desc, booking_type);


                    } else {

                        Toast.makeText(getApplicationContext(), "No Internet connection.", Toast.LENGTH_SHORT).show();

                    }

                }


//              AddBooking(user_id,vendorid,booking_date,fromtime,totime,start_location1,desc);
            }
        });


    }




/*
 Get availibility time vendor---------------------------------------------------------------------------------------------------------
 */


    private void availibilitytimelist() {

        progressDialog = new ProgressDialog(BooknowSingleMoveActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Get_vendor_availability_time,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            System.out.println("availibilitytimelist api-------       " + obj.toString());
                            String result = obj.getString("result");
                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = obj.getJSONArray("times");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    fromtime = dataObject.getString("from_time");
                                    totime = dataObject.getString("to_time");


                                    JSONArray from_timearray = new JSONArray(fromtime);

                                    for (int ij = 0; ij < from_timearray.length(); ij++) {
                                        JSONObject object = from_timearray.getJSONObject(ij);
                                        String id = object.getString("FromTime");

                                        System.out.println("Get_vendor_availability_time FromTime----        " + id);
                                        Choose_From_Time choose_from_time = new Choose_From_Time(id);
                                        choose_from_times.add(choose_from_time);
                                    }


                                    JSONArray to_timearray = new JSONArray(totime);

                                    for (int ik = 0; ik < to_timearray.length(); ik++) {
                                        JSONObject object = to_timearray.getJSONObject(ik);
                                        String id = object.getString("ToTime");

                                        System.out.println("Get_vendor_availability_time ToTime----       " + id);
                                        Choose_To_Time choose_to_time = new Choose_To_Time(id);
                                        choose_to_times.add(choose_to_time);
                                    }


                                }


                                adapter = new Adapter_Vendor_Availibility_time(choose_from_times, choose_to_times, BooknowSingleMoveActivity.this, BooknowSingleMoveActivity.this, BooknowSingleMoveActivity.this);
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(BooknowSingleMoveActivity.this);
                                rec_availibity_time.setLayoutManager(mLayoutManger);

                                rec_availibity_time.setLayoutManager(new LinearLayoutManager(BooknowSingleMoveActivity.this, RecyclerView.HORIZONTAL, false));
                                rec_availibity_time.setLayoutManager(new GridLayoutManager(BooknowSingleMoveActivity.this, 2));
                                rec_availibity_time.setItemAnimator(new DefaultItemAnimator());
                                rec_availibity_time.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(BooknowActivity.this, "Something Went Wrong ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("vendor_id", session.getUserId());


                return params;
            }
        };
        VolleySingleton.getInstance(BooknowSingleMoveActivity.this).addToRequestQueue(stringRequest);
    }


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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BooknowSingleMoveActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(BooknowSingleMoveActivity.this);


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
            Cursor cursor = BooknowSingleMoveActivity.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();


            if (destination != null) {
                filenew1 = destination.getAbsolutePath();
                System.out.println("destinationonSelectFromGalleryResult=====        " + destination.getAbsolutePath());
                FilenamList.add(filenew1);
                System.out.println("FilenamList " + FilenamList.toString());


            } else {
                Toast.makeText(BooknowSingleMoveActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap(BooknowSingleMoveActivity.this.getContentResolver(), data.getData());

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
                            Toast.makeText(BooknowSingleMoveActivity.this, "Permission required", Toast.LENGTH_SHORT).show();
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

    public void AddBooking(String user_id, String vendor_id,String serviceid, String booking_date, String from_time, String to_time, String start_location1, String work_description, String Booking_type) {
        final ProgressDialog progressDialog = new ProgressDialog(BooknowSingleMoveActivity.this);
        progressDialog.show();
        ApiService apiInterface = APIClient.getClient().create(ApiService.class);

        MultipartBody.Part[] surveyImagesParts = null;

        surveyImagesParts = new MultipartBody.Part[FilenamList.size()];
        for (int index = 0; index < FilenamList.size(); index++) {
            File file = new File(FilenamList.get(index));
            System.out.println("file**********     " + imagearrayList.get(index).toString());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("upload_doc[]", file.getName(), surveyBody);
        }
        RequestBody USER_ID = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody VENDRO_ID = RequestBody.create(MediaType.parse("text/plain"), vendor_id);
        RequestBody SERVICE_ID = RequestBody.create(MediaType.parse("text/plain"), serviceid);
        RequestBody BOOKING_DATE = RequestBody.create(MediaType.parse("text/plain"), booking_date);
        RequestBody FROM_TIME = RequestBody.create(MediaType.parse("text/plain"), from_time);
        RequestBody TO_TIME = RequestBody.create(MediaType.parse("text/plain"), to_time);
        RequestBody START_LOCATION1 = RequestBody.create(MediaType.parse("text/plain"), start_location1);
        RequestBody WORK_DESCRIPTION = RequestBody.create(MediaType.parse("text/plain"), work_description);
        RequestBody BOOKING_TYPE = RequestBody.create(MediaType.parse("text/plain"), Booking_type);

        Call<Add_Booking_Single_move> call = apiInterface.Add_Employee(USER_ID, VENDRO_ID,SERVICE_ID, BOOKING_DATE, FROM_TIME, TO_TIME, START_LOCATION1, WORK_DESCRIPTION, BOOKING_TYPE, surveyImagesParts);


        call.enqueue(new Callback<Add_Booking_Single_move>() {
            @Override
            public void onResponse(Call<Add_Booking_Single_move> call, retrofit2.Response<Add_Booking_Single_move> response) {
                progressDialog.dismiss();

                System.out.println("RESULTsingle move----       " + response.body().getResult());
                if (response.body().getResult().equals("true")) {
                    String bookingid = response.body().getData().getId();
                    System.out.println("<><><><><>bookingid       "+bookingid);
                    session.setBookvid("");
                    session.setBookvname("");
                    session.setBookminam("");
                    session.setBookmaxam("");
                    session.setBookrating("");
                    session.setBookservice("");
                    session.setBookdis("");
                    session.setBookdateSinglemoveBoking("");
                    session.setStart_locationsinglemovebooking("");

                    Intent intent = new Intent(BooknowSingleMoveActivity.this, Activity_booking_timer_show_dialogue.class);
                    intent.putExtra("id", bookingid);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(BooknowSingleMoveActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_Booking_Single_move> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ccheckResponce", " :" + t.getMessage());
                Toast.makeText(BooknowSingleMoveActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onitemclick(String n) {
        fromtime = n;


        System.out.println("fromtime---      " + fromtime);
    }

    public void ontotimeclick(String n) {
        totime = n;

        System.out.println("totime---      " + totime);

    }


    public void getlatlang() {


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BooknowSingleMoveActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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

 