package com.satrangolimitless.Vendor_UI.My_Bids;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Adapter.Adapter_attachments_images;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.APIClient;
import com.satrangolimitless.Utils.ApiService;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.model.Place_Bid;
import com.satrangolimitless.model.SubjectData;
import com.satrangolimitless.model.UpdatePlace_Bid;
import com.satrangolimitless.session.Session_vendor;

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

public class Activity_Vendor_PlaceBidConfirmSubmit_Update extends AppCompatActivity {
    private final int SELECT_FILE = 1;
    private final List<String> FilenamList = new ArrayList<>();

    Session_vendor session_vendor;
    ArrayList<Uri> arrayList = new ArrayList<>();
    String est_time, desx, time, date, avgeragebid, jobid, bidamount, newestimatetime, vendrdesc, submission_type;
    Adapter_attachments_images adapter_popular_categoriesActivity;
    ArrayList<SubjectData> imagearrayList = new ArrayList<SubjectData>();
    RecyclerView list;
    String user_id;
    String bid_amount, proposal, estimated_time;
    TextView txtdescrip, txtnewestimtime, txtbidamount, txttime, txtdate, txtestmtime, txtavgbid, txtproposal, txtsubmissiontype;
    Button btnsubmit, Vcancel;
    Fragment fragment;
    String userChoosenTask, filenew1;
    Uri pickedImage;
    File destination;
    ImageView img_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_placebidsecond_update);

        session_vendor = new Session_vendor(getApplicationContext());
        user_id = session_vendor.getUserId();
        list = findViewById(R.id.listss);
        txtsubmissiontype = findViewById(R.id.txtsubmissiontype);
        txtproposal = findViewById(R.id.txtproposal);
        txtavgbid = findViewById(R.id.txtavgbid);
        txtestmtime = findViewById(R.id.txtestmtime);
        txtdate = findViewById(R.id.txtdate);
        txttime = findViewById(R.id.txttime);
        txtbidamount = findViewById(R.id.txtbidamount);
        txtnewestimtime = findViewById(R.id.txtnewestimtime);
        txtdescrip = findViewById(R.id.txtdescrip);
        Vcancel = findViewById(R.id.Vcancel);
        btnsubmit = findViewById(R.id.btnsubmit);
        img_add = findViewById(R.id.img_add);

        if (getIntent() != null) {



            desx = getIntent().getStringExtra("desx");
            submission_type = getIntent().getStringExtra("submission_type");
            vendrdesc = getIntent().getStringExtra("vendrdesc");
            newestimatetime = getIntent().getStringExtra("newestimatetime");
            bidamount = getIntent().getStringExtra("bidamount");
            jobid = getIntent().getStringExtra("jobid");
            avgeragebid = getIntent().getStringExtra("avgeragebid");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            est_time = getIntent().getStringExtra("est_time");

            System.out.println("CONFIRM PLACE DATA " + desx + " " + submission_type + " " + vendrdesc + " " + newestimatetime);
            System.out.println("CONFIRM PLACE DATA " + bidamount + " " + jobid + " " + avgeragebid + " " + date);
            System.out.println("CONFIRM PLACE DATA " + time + " " + est_time);


        }


        txtdescrip.setText(desx);
        txtavgbid.setText("₹ " + avgeragebid);
        txtdate.setText(date);
        txttime.setText(time);
        txtestmtime.setText(est_time);
        txtnewestimtime.setText(newestimatetime);
        txtproposal.setText(vendrdesc);
        txtsubmissiontype.setText(submission_type);
        txtbidamount.setText("₹ " + bidamount);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceBidapi(user_id, jobid, bidamount, vendrdesc, newestimatetime, submission_type);
                System.out.println("place bid data ---   "+user_id+" "+ jobid+" "+ bidamount+" "+ vendrdesc+" "+ newestimatetime+" "+submission_type);

            }
        });


        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRunTimePermission();
            }
        });

    }


    public void PlaceBidapi(String user_id, String job_id, String bid_amount, String proposal, String estimated_time, String submission_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Vendor_PlaceBidConfirmSubmit_Update.this);
        progressDialog.show();
        ApiService apiInterface = APIClient.getClient().create(ApiService.class);

        MultipartBody.Part[] surveyImagesParts = null;

        surveyImagesParts = new MultipartBody.Part[FilenamList.size()];
        for (int index = 0; index < FilenamList.size(); index++) {
            File file = new File(FilenamList.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("attachment[]", file.getName(), surveyBody);
        }
        System.out.println("api data pass  " + user_id + job_id);
        RequestBody VENDOR_ID = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody JOB_ID = RequestBody.create(MediaType.parse("text/plain"), job_id);
        RequestBody BID_AMOUNT = RequestBody.create(MediaType.parse("text/plain"), bid_amount);
        RequestBody PROPOSAL = RequestBody.create(MediaType.parse("text/plain"), proposal);
        RequestBody ESTIMATE_TIME = RequestBody.create(MediaType.parse("text/plain"), estimated_time);
        RequestBody SUBMISSION_TYPE = RequestBody.create(MediaType.parse("text/plain"), submission_type);

        Call<UpdatePlace_Bid> call = apiInterface.UPDATE_PLACE_BID(VENDOR_ID, JOB_ID, BID_AMOUNT, PROPOSAL, ESTIMATE_TIME, SUBMISSION_TYPE, surveyImagesParts);


        call.enqueue(new Callback<UpdatePlace_Bid>() {
            @Override
            public void onResponse(Call<UpdatePlace_Bid> call, retrofit2.Response<UpdatePlace_Bid> response) {
                progressDialog.dismiss();

                System.out.println("RESULT----UPDATE BID PLACED         " + response.body().getResult());
                if (response.body().getResult().equals("true")) {

                    DialogueSuccess();
                } else {
                    Toast.makeText(Activity_Vendor_PlaceBidConfirmSubmit_Update.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatePlace_Bid> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ccheckResponce", " :" + t.getMessage());
                Toast.makeText(Activity_Vendor_PlaceBidConfirmSubmit_Update.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void DialogueSuccess() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Vendor_PlaceBidConfirmSubmit_Update.this);
        View view = LayoutInflater.from(Activity_Vendor_PlaceBidConfirmSubmit_Update.this).inflate(R.layout.dialog_bid_successfull_submitted, null);

        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final Button btnsubmit = view.findViewById(R.id.btnsubmit);
        final ImageView imclose = view.findViewById(R.id.imclose);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingActivity_Service_provider.class);
                startActivity(intent);


            }
        });


        imclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    //    ---------------------------------------------Add image attachment


    //    Add attachment

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
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Vendor_PlaceBidConfirmSubmit_Update.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Activity_Vendor_PlaceBidConfirmSubmit_Update.this);


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
            Cursor cursor = Activity_Vendor_PlaceBidConfirmSubmit_Update.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();


            if (destination != null) {
                filenew1 = destination.getAbsolutePath();
                System.out.println("destinationonSelectFromGalleryResult=====        " + destination.getAbsolutePath());
                FilenamList.add(filenew1);
                System.out.println("FilenamList " + FilenamList.toString());


            } else {
                Toast.makeText(Activity_Vendor_PlaceBidConfirmSubmit_Update.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap(Activity_Vendor_PlaceBidConfirmSubmit_Update.this.getContentResolver(), data.getData());

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        try {

            System.out.println("entere -------       ");
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
                            Toast.makeText(Activity_Vendor_PlaceBidConfirmSubmit_Update.this, "Permission required", Toast.LENGTH_SHORT).show();
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
