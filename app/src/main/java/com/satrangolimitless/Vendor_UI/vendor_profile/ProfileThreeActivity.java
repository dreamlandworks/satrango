package com.satrangolimitless.Vendor_UI.vendor_profile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.R;
import com.satrangolimitless.SignUpActivity;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.Utils.Utils;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.satrangolimitless.Utils.Base_Url.Add_vendor;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;

public class ProfileThreeActivity extends AppCompatActivity {
TextView txtdocument;
    String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    File destination;
    String filenew1;
    Button btn_submit,VBack;
LinearLayout ll_upload;
Session_vendor session_vendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_three);

        session_vendor=new Session_vendor(getApplicationContext());
        btn_submit= findViewById(R.id.btn_submit);
        VBack= findViewById(R.id.VBack);
        ll_upload= findViewById(R.id.ll_upload);
        txtdocument= findViewById(R.id.txtdocument);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("---- "+session_vendor.getUserId());
                System.out.println("---- "+session_vendor.getadname());
                System.out.println("---- "+session_vendor.getAddmob());
                System.out.println("---- "+session_vendor.getAddprofession_id());
                System.out.println("---- "+session_vendor.getAddlanguage());
                System.out.println("---- "+session_vendor.getAddskills());
                System.out.println("---- "+session_vendor.getAddqualification());
                System.out.println("---- "+session_vendor.getAddabout());
if (filenew1.isEmpty()){

    Toast.makeText(ProfileThreeActivity.this, "Please add document", Toast.LENGTH_SHORT).show();

}else  {

    if (Utils.isInternetConnected(ProfileThreeActivity.this)) {

        Add_vendor();
    } else {
        Toast.makeText(ProfileThreeActivity.this,  getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }

}

            }
        });

        VBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ll_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRunTimePermission();
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
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder( ProfileThreeActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission( ProfileThreeActivity.this);


                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
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

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        Log.e("", String.valueOf(destination));

        System.out.println("onCaptureImageResult=====        "+ String.valueOf(destination));

        FileOutputStream fo;
        try {
            //destination.createNewFile();
            fo = new FileOutputStream(destination);
            if (destination != null) {
                filenew1 = destination.getAbsolutePath();
                txtdocument.setText(destination.getAbsolutePath().toString()+".jpg");
                // Toast.makeText(getActivity(), "path is"+destination.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText( ProfileThreeActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
            }
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        profileimage.setImageBitmap(thumbnail);

    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {

            Uri pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor =  ProfileThreeActivity.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();



            if (destination != null) {
                filenew1 = destination.getAbsolutePath();
                System.out.println("onSelectFromGalleryResult=====        "+ destination.getAbsolutePath());
                txtdocument.setText(destination.getAbsolutePath().toString()+".jpg");

            } else {
                Toast.makeText( ProfileThreeActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap( ProfileThreeActivity.this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("onSelectFromGalleryResult=====        "+bm.toString());


//        profileimage.setImageBitmap(bm);

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
                             // alertView();
                        }
                    }
                }
            }

            try {
                //selectImage();
            }catch (Exception e){

            }

            if (isPermitted){
                selectImage();

            }else {

            }

        }
    }

/*
Add vendor api----------------------------------
 */

    private void Add_vendor() {
        final ProgressDialog progressDialog = new ProgressDialog(ProfileThreeActivity.this);
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        System.out.println("Path=======        "+destination.getAbsolutePath());

                AndroidNetworking.upload(BaseUrl+Add_vendor)
                .addMultipartParameter("id", session_vendor.getUserId())
                .addMultipartParameter("name",session_vendor.getadname())
                .addMultipartParameter("mobile",session_vendor.getAddmob())
                .addMultipartParameter("profession_id",session_vendor.getAddprofession_id())
                .addMultipartFile("id_proof",destination)
                .addMultipartParameter("about",session_vendor.getAddabout())
                .addMultipartParameter("language",session_vendor.getAddlanguage())
                .addMultipartParameter("skills",session_vendor.getAddskills())
                .addMultipartParameter("qualification",session_vendor.getAddqualification())
                .addMultipartParameter("about",session_vendor.getAddabout())
                .addMultipartParameter("dob",session_vendor.getAdddob())
                .addMultipartParameter("gender",session_vendor.getAddgender())
                .addMultipartParameter("address",session_vendor.getAddaddres())

                .addMultipartParameter("pr_hours",session_vendor.getAddprhr())
                .addMultipartParameter("pr_days",session_vendor.getAddprday())
                .addMultipartParameter("min_charge",session_vendor.getAddmincharge())
                .addMultipartParameter("extra_charge",session_vendor.getAddextracharge())

                .setTag("Addvendor")
                .setPriority(Priority.LOW)
                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            progressDialog.dismiss();
                            //Log.e(" post home", " " + jsonObject);
                            Log.e("Add_vendor",jsonObject.toString());
                            System.out.println("Add_vendor=====    "+jsonObject.toString());
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {



/*
  JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for(int i=0; i < jsonArray.length(); i++){
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Log.e("Add vendor",obj.toString());
                                    System.out.println("Add vendor -----       "+obj.toString());
                                }
 */




                                Intent intent = new Intent(ProfileThreeActivity.this, VendorProfilFourActivity.class);
                                startActivity(intent);
                                finish();



                                Toast.makeText( ProfileThreeActivity.this,msg, Toast.LENGTH_LONG).show();


                            } else {

                                Toast.makeText( ProfileThreeActivity.this,msg, Toast.LENGTH_LONG).show();


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