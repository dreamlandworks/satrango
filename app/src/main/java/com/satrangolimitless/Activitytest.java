package com.satrangolimitless;


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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Adapter.Adapter_attachments_images;
import com.satrangolimitless.Utils.APIClient;
import com.satrangolimitless.Utils.ApiService;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.model.Add_Booking_Single_move;
import com.satrangolimitless.model.SubjectData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Activitytest extends AppCompatActivity {


    ImageView image;
    Button chosse,submit;
    TextView choose_image;

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
        setContentView(R.layout.activity_demo);
        chosse = findViewById(R.id.chosse);
        submit = findViewById(R.id.submit);
        choose_image = findViewById(R.id.choose_image);
        image = findViewById(R.id.image);
String book;
        list = findViewById(R.id.list);
        chosse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRunTimePermission();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddEmployeeList("1","4","2021-01-12","10:30","12:30","indore","test","book");

            }
        });

    }


    private void checkRunTimePermission() {


        String[] permissionArrays = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 11111);
        } else {
            // if already permition granted
            // PUT YOUR ACTION (Like Open cemara etc..)
            selectImage();
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Choose from Library",
                "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activitytest.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Activitytest.this);


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
            Cursor cursor = Activitytest.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();


            if (destination != null) {
                filenew1 = destination.getAbsolutePath();
                System.out.println("destinationonSelectFromGalleryResult=====        " + destination.getAbsolutePath());
                FilenamList.add(filenew1);
                System.out.println("FilenamList " + FilenamList.toString());


            } else {
                Toast.makeText(Activitytest.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap(Activitytest.this.getContentResolver(), data.getData());

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

//                            CustomAdapter customAdapter = new CustomAdapter(this, imagearrayList);
//                            list.setAdapter(customAdapter);

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
                            Toast.makeText(Activitytest.this, "Permission required", Toast.LENGTH_SHORT).show();
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
    public void AddEmployeeList(String user_id, String vendor_id, String booking_date, String from_time, String to_time, String start_location1, String work_description,String book) {
        final ProgressDialog progressDialog = new ProgressDialog(Activitytest.this);
        progressDialog.show();
        ApiService apiInterface = APIClient.getClient().create(ApiService.class);

        MultipartBody.Part[] surveyImagesParts = null;

        surveyImagesParts = new MultipartBody.Part[FilenamList.size()];
        for (int index = 0; index < FilenamList.size(); index++) {
            File file = new File(FilenamList.get(index).toString());
            System.out.println("file**********     "+imagearrayList.get(index).toString());
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("upload_doc[]", file.getName(), surveyBody);
        }
        RequestBody user_id_ = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody vendor_id_ = RequestBody.create(MediaType.parse("text/plain"), vendor_id);
        RequestBody booking_date_ = RequestBody.create(MediaType.parse("text/plain"), booking_date);
        RequestBody from_time_ = RequestBody.create(MediaType.parse("text/plain"), from_time);
        RequestBody to_time_ = RequestBody.create(MediaType.parse("text/plain"), to_time);
        RequestBody start_location1_ = RequestBody.create(MediaType.parse("text/plain"), start_location1);
        RequestBody work_description_ = RequestBody.create(MediaType.parse("text/plain"), work_description);
        RequestBody booktype = RequestBody.create(MediaType.parse("text/plain"), book);

        Call<Add_Booking_Single_move> call = apiInterface.Add_Employee(user_id_,vendor_id_,booking_date_,from_time_,to_time_,start_location1_,work_description_,booktype,surveyImagesParts);


        call.enqueue(new Callback<Add_Booking_Single_move>() {
            @Override
            public void onResponse(Call<Add_Booking_Single_move> call, retrofit2.Response<Add_Booking_Single_move> response) {
                progressDialog.dismiss();

                System.out.println("RESULT----       "+response.body().getResult());
                if (response.body().getResult().equals("true")) {
                    Toast.makeText(Activitytest.this, "Submitted Data Successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Activitytest.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_Booking_Single_move> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ccheckResponce", " :" + t.getMessage());
                Toast.makeText(Activitytest.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
*/


}
