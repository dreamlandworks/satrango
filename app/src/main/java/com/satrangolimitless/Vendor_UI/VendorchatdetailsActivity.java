package com.satrangolimitless.Vendor_UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.ChatAdapterVendor;
import com.satrangolimitless.Adapter.VendorsideChat_Adapter;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.FileUtils;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.model.ChatModel;
import com.satrangolimitless.model.UserChatmodel;
import com.satrangolimitless.session.Session_vendor;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.user_side_chat;
import static com.satrangolimitless.Utils.Base_Url.vendor_side_chat;
import static com.satrangolimitless.Utils.PathUtils.bitmapToFile;

public class VendorchatdetailsActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 123;
    public static List<UserChatmodel> chatmessagelist = new ArrayList<UserChatmodel>();
    private final int PICK_PDF_REQUEST = 1;
    File file;
    Uri UploadPdfUri;
    String filename;
    String vendorid, job_id, user_id, message;
    Session_vendor session_vendor;
    ImageView imgsend, img_addimage, imgatachfile;
    EditText edtvmess;
    RecyclerView rrecv;
    ChatAdapterVendor chatAdapter;
    ArrayList<ChatModel> chatModels = new ArrayList<>();
    RequestQueue requestQueue;
    StringRequest stringRequest;
    VendorsideChat_Adapter usersideChatAdapter;
    UserChatmodel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session_vendor = new Session_vendor(getApplicationContext());
        setContentView(R.layout.activity_vendorchatdetails);

        edtvmess = findViewById(R.id.edtvmess);
        rrecv = findViewById(R.id.rrecv);
        if (getIntent() != null) {
            user_id = getIntent().getStringExtra("userid");
        }

        job_id = session_vendor.getPendongjob_id();

        imgsend = findViewById(R.id.imgsend);
        img_addimage = findViewById(R.id.img_addimage);
        imgatachfile = findViewById(R.id.imgatachfile);
        requestStoragePermission();

        imgsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = edtvmess.getText().toString();
                Sendmesage();
            }
        });

        img_addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage1();
            }
        });


        imgatachfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);

            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(VendorchatdetailsActivity.this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(VendorchatdetailsActivity.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && resultData != null && resultData.getData() != null) {
            UploadPdfUri = resultData.getData();
            String Fpath = FileUtils.getPath(VendorchatdetailsActivity.this, UploadPdfUri);
            file = new File(Fpath);
            filename = file.getName();
//            et_attachfile.setText(filename);
            System.out.println("<><>=======pdfff  " + file);

        }
    }


    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(VendorchatdetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(VendorchatdetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        ActivityCompat.requestPermissions(VendorchatdetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    private void selectImage1() {

        final PickImageDialog dialog = PickImageDialog.build(new PickSetup());
        dialog.setOnPickCancel(new IPickCancel() {
            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {

                if (r.getError() == null) {
                    //If you want the Uri.
                    //Mandatory to refresh image from Uri.
                    //getImageView().setImageURI(null);
                    //Setting the real returned image.
                    //getImageView().setImageURI(r.getUri());
                    //If you want the Bitmap.

//                    proimg1.setImageBitmap(r.getBitmap());

                    Log.e("Imagepath", r.getPath());

                    file = bitmapToFile(VendorchatdetailsActivity.this, r.getBitmap());
                    Log.e("file", "" + file);
                    String filename = file.getName();
                    Log.e("filweName = ", filename);


                    //r.getPath();
                } else {
                    //Handle possible errors
                    //TODO: do what you have to do with r.getError();
                    Toast.makeText(VendorchatdetailsActivity.this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }).show(VendorchatdetailsActivity.this);
    }


    private void Sendmesage() {


        final ProgressDialog progressDialog = new ProgressDialog(VendorchatdetailsActivity.this);
        progressDialog.setMessage("Processing...");
        progressDialog.show();


        if (file != null) {

            AndroidNetworking.upload(BaseUrl + vendor_side_chat)

                    .addMultipartParameter("job_id", job_id)
                    .addMultipartParameter("user_id", user_id)
                    .addMultipartParameter("vendor_id", vendorid)
                    .addMultipartParameter("message", message)
                    .addMultipartFile("files", file)

                    .setTag("chat")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                            try {
                                progressDialog.dismiss();
                                //Log.e(" post home", " " + jsonObject);
                                Log.e("Sendmesage", jsonObject.toString());
                                System.out.println("Sendmesage=====    " + jsonObject.toString());
                                String result = jsonObject.getString("result");
                                String msg = jsonObject.getString("msg");

                                if (result.equalsIgnoreCase("true")) {
                                    getChatList(user_id, job_id);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(VendorchatdetailsActivity.this, msg, Toast.LENGTH_LONG).show();


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

        } else {
            AndroidNetworking.upload(BaseUrl + vendor_side_chat)

                    .addMultipartParameter("job_id", job_id)
                    .addMultipartParameter("user_id", user_id)
                    .addMultipartParameter("vendor_id", vendorid)
                    .addMultipartParameter("message", message)
//                    .addMultipartFile("files", file)

                    .setTag("chat")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                            try {
                                progressDialog.dismiss();
                                //Log.e(" post home", " " + jsonObject);
                                Log.e("Sendmesage", jsonObject.toString());
                                System.out.println("Sendmesage=====    " + jsonObject.toString());
                                String result = jsonObject.getString("result");
                                String msg = jsonObject.getString("msg");

                                if (result.equalsIgnoreCase("true")) {

                                    getChatList(user_id, job_id);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(VendorchatdetailsActivity.this, msg, Toast.LENGTH_LONG).show();


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


    private void getChatList(final String user_id, final String job_id) {

        chatModels.clear();

        stringRequest = new StringRequest(Request.Method.POST, BaseUrl + "vendor_get_chat", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");

                    if (result.equals("true")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject chatdata = jsonArray.getJSONObject(i);

                            ChatModel chatModel = new ChatModel();

                            chatModel.setUser_msg(chatdata.getString("user_msg"));
                            chatModel.setVendor_msg(chatdata.getString("vendor_msg"));
                            chatModel.setFiles(chatdata.getString("files"));
                            chatModel.setDate_time(chatdata.getString("date_time"));

                            chatModels.add(chatModel);
                        }

                        chatAdapter = new ChatAdapterVendor(VendorchatdetailsActivity.this, chatModels);
                        rrecv.setLayoutManager(new LinearLayoutManager(VendorchatdetailsActivity.this));
                        rrecv.setAdapter(chatAdapter);

                        chatAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(VendorchatdetailsActivity.this, "" + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                map.put("user_id", user_id);
                map.put("job_id", job_id);
                return map;
            }
        };


        requestQueue.add(stringRequest);

    }


    public void sendmessage(final String message) {
//        sendseendstatus(Groupid, userid);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + vendor_side_chat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  GetAolMesageFromGroup(Groupid);
                        Log.e("->>response", "onResponse: " + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.e("sendmessageresponse", obj.toString());

                            String result = obj.getString("result");
                            String msg = obj.getString("msg");


                            //     String body = obj.getString("body");


                            JSONArray jsonArray = obj.getJSONArray("data");

                            //  JSONObject body = object.getJSONObject("body");
                            //  Log.e(TAG, "handleDataMessage2: "+body );
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String job_id = jsonObject.getString("job_id");
                                String user_id = jsonObject.getString("user_id");
                                String vendor_id = jsonObject.getString("vendor_id");
                                String user_msg = jsonObject.getString("user_msg");
                                String vendor_msg = jsonObject.getString("vendor_msg");
                                String files = jsonObject.getString("files");
                                String date_time = jsonObject.getString("date_time");
                                String v_name = jsonObject.getString("v_name");
                                String v_image = jsonObject.getString("v_image");


                                //    Log.e("jARRAYLENTH", "onResponse: " + Postid);
                                UserChatmodel movie = new UserChatmodel(id, job_id, user_id, vendor_id, user_msg, vendor_msg, files, date_time, v_name, v_image);
                                chatmessagelist.add(movie);
                            }


                            //    Log.e("jARRAYLENTH", "onResponse: " + Postid);

                            usersideChatAdapter = new VendorsideChat_Adapter(chatmessagelist, VendorchatdetailsActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rrecv.setLayoutManager(mLayoutManager);
                            rrecv.setItemAnimator(new DefaultItemAnimator());
                            rrecv.setAdapter(usersideChatAdapter);
                            rrecv.setItemViewCacheSize(chatmessagelist.size());

                            rrecv.scrollToPosition(rrecv.getAdapter().getItemCount() - 1);

                        } catch (JSONException e) {
                            Log.e("JSONException", "onResponse: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error->", "onErrorResponse: " + error.toString());

                        Toast.makeText(VendorchatdetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", "55");
                params.put("job_id", "13");
                params.put("vendor_id", "66");
                params.put("message", "hello");


                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }


    public void GetAolMesageFromGroup() {
      /*  final ProgressDialog progressDialog = new ProgressDialog(VendorchatdetailsActivity.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + "vendor_get_chat",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            chatmessagelist.clear();

                            // progressDialog.dismiss();

                            JSONObject obj = new JSONObject(response);
                            Log.e("getgroupdetails", obj.toString());

                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            if (result.equals("true")) {

                                JSONArray jsonArray = obj.getJSONArray("data");
                                Log.e("datapostall", "onResponse: " + jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                                    String id = jsonObject.getString("id");
                                    String job_id = jsonObject.getString("job_id");
                                    String user_id = jsonObject.getString("user_id");
                                    String vendor_id = jsonObject.getString("vendor_id");
                                    String user_msg = jsonObject.getString("user_msg");
                                    String vendor_msg = jsonObject.getString("vendor_msg");
                                    String files = jsonObject.getString("files");
                                    String date_time = jsonObject.getString("date_time");
                                    String v_name = jsonObject.getString("v_name");
                                    String v_image = jsonObject.getString("v_image");


                                    //    Log.e("jARRAYLENTH", "onResponse: " + Postid);
                                    UserChatmodel movie = new UserChatmodel(id, job_id, user_id, vendor_id, user_msg, vendor_msg, files, date_time, v_name, v_image);
                                    chatmessagelist.add(movie);
                                    usersideChatAdapter = new VendorsideChat_Adapter(chatmessagelist, VendorchatdetailsActivity.this);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    rrecv.setLayoutManager(mLayoutManager);
                                    rrecv.setItemAnimator(new DefaultItemAnimator());
                                    rrecv.setAdapter(usersideChatAdapter);
                                    rrecv.setItemViewCacheSize(chatmessagelist.size());

                                    rrecv.scrollToPosition(rrecv.getAdapter().getItemCount() - 1);
                                }

                                //  for (int i = 0; i < jsonArray.length(); i++) {}
                                //// JSONObject jsonObject = jsonArray.getJSONObject();


                            } else {
                                Toast.makeText(VendorchatdetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.dismiss();
                        Toast.makeText(VendorchatdetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user_id", "55");
                params.put("job_id", "13");
                params.put("vendor_id", "66");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

}