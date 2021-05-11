package com.satrangolimitless;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.satrangolimitless.User_UI.ActivityChangePassword;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.Profileimage_url;
import static com.satrangolimitless.Utils.Base_Url.Profileview;
import static com.satrangolimitless.Utils.Base_Url.Updateprofile;

public class MyprofileActivity_User extends AppCompatActivity {
    String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    File destination;
    String filenew1;
    String user_id, id, dob, image, phone, email, name,address;
    String sdob, smail, snumber, sname;
    Session session;
    EditText edt_name, edt_phone, edt_mail, edt_dob;
    ImageView imgeditname,imgeditmob,imgmailedt;
    TextView txt_adres,txtchangepass;
    Button btn_apply, btn_back;
    CircleImageView profilepic;
    ImageView im_back,imgdel;
    TextView txt_back;
    private RequestQueue rQueue;
    DatePickerDialog datePickerDialog;
    LinearLayout ll_adres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        session = new Session(getApplicationContext());
        user_id = session.getUserId();
        txt_adres = findViewById(R.id.txt_adres);
        txtchangepass = findViewById(R.id.txtchangepass);
        profilepic = findViewById(R.id.profilepic);
        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_mail = findViewById(R.id.edt_mail);
        edt_dob = findViewById(R.id.edt_dob);
        im_back = findViewById(R.id.im_back);
        btn_apply = findViewById(R.id.btn_apply);
        btn_back = findViewById(R.id.btn_back);
        imgeditmob = findViewById(R.id.imgeditmob);
        imgeditname = findViewById(R.id.imgeditname);
        imgmailedt = findViewById(R.id.imgmailedt);
        imgdel = findViewById(R.id.imgdel);
        ll_adres = findViewById(R.id.ll_adres);
        Getprofiledetails();



        edt_dob.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();

                int mYear = c.get(java.util.Calendar.YEAR); // current year
                int mMonth = c.get(java.util.Calendar.MONTH); // current month
                int mDay = c.get(java.util.Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MyprofileActivity_User.this,


                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edt_dob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);



                            }

                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

                datePickerDialog.show();
            }
        });


        imgdel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imgdel.setVisibility(View.GONE);
                txt_adres.setVisibility(View.GONE);
            }
        });

        imgeditname.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_name.setEnabled(true);


            }
        });
        imgeditmob.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_phone.setEnabled(true);


            }
        });

        imgmailedt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_mail.setEnabled(true);
            }
        });

        btn_apply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                sname=edt_name.getText().toString();
                snumber=edt_phone.getText().toString();
                smail=edt_mail.getText().toString();
                sdob=edt_dob.getText().toString();

                if(sname.isEmpty())
                {
                    edt_name.setError("Please Enter Name.");
                }
                else if(snumber.isEmpty())
                {
                    edt_phone.setError("Please Enter Mobile Number.");
                }
                 else if(smail.isEmpty())
                {
                    edt_mail.setError("Please Enter Email.");
                }
                 else if(sdob.isEmpty())
                  {
                     edt_dob.setError("Please Enter Date of birth.");
                  }

                else
                {
                    Updateprofile();

                }


            }
        });


        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        profilepic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRunTimePermission();
            }
        });


        im_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        txt_back.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        txtchangepass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ActivityChangePassword.class);
                startActivity(intent);
            }
        });

    }


//    get user profile details


    private void Getprofiledetails() {
        final ProgressDialog progressDialog = new ProgressDialog(MyprofileActivity_User.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Profileview,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);

                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                id = jsonObject1.getString("id");
                                name = jsonObject1.getString("fname");
                                email = jsonObject1.getString("email");
                                phone = jsonObject1.getString("phone");
                                image = jsonObject1.getString("image");
                                dob = jsonObject1.getString("dob");
                                address = jsonObject1.getString("address");


                                System.out.println("profile======    " + jsonObject1.toString());

                                try {


                                    edt_name.setText(name);
                                    edt_mail.setText(email);
                                    edt_phone.setText(phone);
                                    edt_dob.setText(dob);
                                    txt_adres.setText(address);
                                    Glide.with(getApplicationContext())
                                            .load(Image_url + image)

                                            .into(profilepic);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(MyprofileActivity_User.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyprofileActivity_User.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);
                System.out.println("user_id=======       " + user_id);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(MyprofileActivity_User.this);
        rQueue.add(stringRequest);


    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    Update profile--------------------------------------------------------------------------------


    private void Updateprofile() {
        final ProgressDialog progressDialog = new ProgressDialog(MyprofileActivity_User.this);
        progressDialog.setMessage("Processing...");
        progressDialog.show();
        System.out.println("updatesssss        "+user_id+"  "+""+sname+" "+snumber+" "+smail+"  "+destination);

        AndroidNetworking.upload(BaseUrl + Updateprofile)
                .addMultipartParameter("user_id",user_id )
                .addMultipartParameter("name",sname)
                .addMultipartParameter("mobile",snumber)
                .addMultipartParameter("email",smail)
                .addMultipartFile("image",destination)

                .setTag("update profile ")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            progressDialog.dismiss();
                            //Log.e(" post home", " " + jsonObject);
                            Log.e("update profile", jsonObject.toString());
                            System.out.println("update profile=====    " + jsonObject.toString());
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {


                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Log.e("update profile", obj.toString());

                                    System.out.println("Profile update -----       " + obj.toString());


                                }


                                Toast.makeText(MyprofileActivity_User.this, msg, Toast.LENGTH_LONG).show();


                            } else {

                                Toast.makeText(MyprofileActivity_User.this, msg, Toast.LENGTH_LONG).show();


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

    
    
    
    
    
    
//    add picture





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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder( MyprofileActivity_User.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission( MyprofileActivity_User.this);


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
                // Toast.makeText(getActivity(), "path is"+destination.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText( MyprofileActivity_User.this, "something wrong", Toast.LENGTH_SHORT).show();
            }
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profilepic.setImageBitmap(thumbnail);
    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {

            Uri pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor =  MyprofileActivity_User.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();



            if (destination != null) {
                filenew1 = destination.getAbsolutePath();

                System.out.println("onSelectFromGalleryResult=====        "+ destination.getAbsolutePath());


            } else {
                Toast.makeText( MyprofileActivity_User.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap( MyprofileActivity_User.this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("onSelectFromGalleryResult=====        "+bm.toString());

        profilepic.setImageBitmap(bm);
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
                            Toast.makeText( MyprofileActivity_User.this, "Permission required", Toast.LENGTH_SHORT).show();
                            // alertView();
                        }
                    }
                }
            }

            try {


            }catch (Exception e){

            }

            if (isPermitted){
                selectImage();

            }else {

            }

        }
    }

}