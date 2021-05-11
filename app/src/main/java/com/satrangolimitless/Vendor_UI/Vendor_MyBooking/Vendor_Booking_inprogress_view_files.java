package com.satrangolimitless.Vendor_UI.Vendor_MyBooking;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Adapter.Adapter_attachments_images;

import com.satrangolimitless.Adapter.AtachmentBookingpendingAdapter;
import com.satrangolimitless.Booknow.Activity_booking_timer_show_dialogue;

import com.satrangolimitless.R;
import com.satrangolimitless.Utils.APIClient;
import com.satrangolimitless.Utils.ApiService;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.model.Add_Booking_Single_move;
import com.satrangolimitless.model.SubjectData;
import com.satrangolimitless.session.Session_vendor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Vendor_Booking_inprogress_view_files extends AppCompatActivity {

    TextView txtbookingid, txtdate;
    RecyclerView recviefiles;
    String attachm, date, bookingid;
    Button btnreviradd,
            btnssendreview;
    Session_vendor session_vendor;

    AtachmentBookingpendingAdapter atachmentshowAdapter;
    ArrayList<String> attachlist = new ArrayList<>();
    RecyclerView recviewfiles;

    RecyclerView list;
    ArrayList<SubjectData> imagearrayList = new ArrayList<SubjectData>();
    File destination;
    Adapter_attachments_images adapter_popular_categoriesActivity;
    Uri pickedImage;
    String userChoosenTask;
    String filenew1;
    private final List<String> FilenamList = new ArrayList<>();
    private final ArrayList<Uri> arrayList = new ArrayList<>();
    private final int SELECT_FILE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorsidebooking_inprogress_viewfiles);
        session_vendor = new Session_vendor(getApplicationContext());
        txtbookingid = findViewById(R.id.txtbookingid);
        txtdate = findViewById(R.id.txtdate);
        btnssendreview = findViewById(R.id.btnssendreview);
        btnreviradd = findViewById(R.id.btnreviradd);
        recviefiles = findViewById(R.id.recviefiles);

        if (getIntent() != null) {


            bookingid = getIntent().getStringExtra("bookingid");
            date = getIntent().getStringExtra("date");
            attachm = getIntent().getStringExtra("attachm");


        }


        txtdate.setText(date);
        txtbookingid.setText(bookingid);





        btnreviradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkRunTimePermission();
            }
        });

        btnssendreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        ArrayList aList = new ArrayList(Arrays.asList(attachm.split(",")));
        for (int i = 0; i < aList.size(); i++) {
            System.out.println(" -->   " + aList.get(i));
            String images = (String) aList.get(i);
            attachlist.add(images);
        }


        atachmentshowAdapter = new AtachmentBookingpendingAdapter(getApplicationContext(), attachlist);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
        recviewfiles.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recviewfiles.setAdapter(atachmentshowAdapter);
        atachmentshowAdapter.notifyDataSetChanged();

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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Vendor_Booking_inprogress_view_files.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Vendor_Booking_inprogress_view_files.this);


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
        startActivityForResult(i,   SELECT_FILE);
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
            Cursor cursor = Vendor_Booking_inprogress_view_files.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();


            if (destination != null) {
                filenew1 = destination.getAbsolutePath();
                System.out.println("destinationonSelectFromGalleryResult=====        " + destination.getAbsolutePath());
                FilenamList.add(filenew1);
                System.out.println("FilenamList " + FilenamList.toString());


            } else {
                Toast.makeText(Vendor_Booking_inprogress_view_files.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap(Vendor_Booking_inprogress_view_files.this.getContentResolver(), data.getData());

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
                            Toast.makeText(Vendor_Booking_inprogress_view_files.this, "Permission required", Toast.LENGTH_SHORT).show();
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




/*
    public void Send_for_review(String user_id, String vendor_id, String booking_date, String from_time, String to_time, String start_location1, String work_description, String Booking_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Vendor_Booking_inprogress_view_files.this);
        progressDialog.show();
        ApiService apiInterface = APIClient.getClient().create(ApiService.class);

        MultipartBody.Part[] surveyImagesParts = null;

        surveyImagesParts = new MultipartBody.Part[FilenamList.size()];
        for (int index = 0; index < FilenamList.size(); index++) {
            File file = new File(FilenamList.get(index));
            System.out.println("file**********     " + imagearrayList.get(index).toString());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("vendor_doc[]", file.getName(), surveyBody);
        }
        RequestBody USER_ID = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody VENDRO_ID = RequestBody.create(MediaType.parse("text/plain"), vendor_id);
        RequestBody BOOKING_DATE = RequestBody.create(MediaType.parse("text/plain"), booking_date);
        RequestBody FROM_TIME = RequestBody.create(MediaType.parse("text/plain"), from_time);
        RequestBody TO_TIME = RequestBody.create(MediaType.parse("text/plain"), to_time);
        RequestBody START_LOCATION1 = RequestBody.create(MediaType.parse("text/plain"), start_location1);
        RequestBody WORK_DESCRIPTION = RequestBody.create(MediaType.parse("text/plain"), work_description);
        RequestBody BOOKING_TYPE = RequestBody.create(MediaType.parse("text/plain"), Booking_type);

        Call<Add_Booking_Single_move> call = apiInterface.Add_Employee(USER_ID, VENDRO_ID, BOOKING_DATE, FROM_TIME, TO_TIME, START_LOCATION1, WORK_DESCRIPTION, BOOKING_TYPE, surveyImagesParts);


        call.enqueue(new Callback<Add_Booking_Single_move>() {
            @Override
            public void onResponse(Call<Add_Booking_Single_move> call, retrofit2.Response<Add_Booking_Single_move> response) {
                progressDialog.dismiss();

                System.out.println("RESULT----       " + response.body().getResult());
                if (response.body().getResult().equals("true")) {
                    String bookingid = response.body().getData().getId();
                    System.out.println("<><><><><>bookingid       "+bookingid);

                    Intent intent = new Intent(Vendor_Booking_inprogress_view_files.this, Activity_booking_timer_show_dialogue.class);
                    intent.putExtra("id", bookingid);

                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(Vendor_Booking_inprogress_view_files.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_Booking_Single_move> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ccheckResponce", " :" + t.getMessage());
                Toast.makeText(Vendor_Booking_inprogress_view_files.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
*/

}
