package com.satrangolimitless.User_UI.POSTAJOB;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.Adapter.Adapter_attachments_images;
import com.satrangolimitless.Adapter.AutoCompletevendor_register_adapter;
import com.satrangolimitless.Adapter.SuggestionAdapter;

import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.model.Search_suggestion_vendor_register;
import com.satrangolimitless.model.SubjectData;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Service_list_Api;

public class Activity_PostJob_Third extends AppCompatActivity {
//Autocomplete skills 
List<String> Skills_list = new ArrayList<String>();
    ArrayList<String> search_name = new ArrayList<>();
    List<Search_suggestion_vendor_register> getAllMedicineList = new ArrayList<>();
    AutoCompleteTextView searchview;
    SuggestionAdapter suggestionAdapter;
    RecyclerView medicine_recycler;
    ArrayList<String> SuggestionList = new ArrayList<>();
    String all_Skils="";
    Button btnnextt;
    ImageView back;
    Session session;
    Session_vendor session_vendor;
    String[] Language = { "Select a language","Hindi", "English", "Bengali", "Marathi", "Telugu", "Tamil", "Gujrati", "Kannada", "Malyalam"};
    Spinner spin_language;

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
    String time,date,description,location,accept_bid_for,bid_min_range,bid_max_range,bid_per, Estimatedtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_jobpost_singlemove_third);
        session = new Session(getApplicationContext());
        btnnextt= findViewById(R.id.btnnextt);
        back= findViewById(R.id.back);
        img_add= findViewById(R.id.img_add);
        searchview =  findViewById(R.id.searchview);
        spin_language = (Spinner) findViewById(R.id.spin_language);
        medicine_recycler =  findViewById(R.id.medicine_recycler);
        list = findViewById(R.id.list);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Language);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_language.setAdapter(aa);
        if (getIntent()!=null){
            Estimatedtime = getIntent().getStringExtra("Estimatedtime");
            bid_per = getIntent().getStringExtra("bid_per");
            bid_max_range = getIntent().getStringExtra("bid_max_range");
            bid_min_range = getIntent().getStringExtra("bid_min_range");
            accept_bid_for = getIntent().getStringExtra("accept_bid_for");
            location = getIntent().getStringExtra("location");
            description = getIntent().getStringExtra("description");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");

            System.out.println("Post job data "+time+"+ "+
                    date+"+ "+
                    description+"+ "+
                    location+"+ "+
                    accept_bid_for+"+ "+
                    bid_min_range+"+ "+
                    bid_max_range+"+ "+
                    bid_per+"+ "+
                    Estimatedtime);

        }







        spin_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                   lang=Language[position].toString();
                Toast.makeText(getApplicationContext(),lang, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        suggestion_list();
        btnnextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if (all_Skils.isEmpty()){
                 Toast.makeText(Activity_PostJob_Third.this, "Please select skills", Toast.LENGTH_SHORT).show();
             } else if(FilenamList.isEmpty()){
                 Toast.makeText(Activity_PostJob_Third.this, "Please select attachment file", Toast.LENGTH_SHORT).show();

             }  else{

                 Intent intent = new Intent(Activity_PostJob_Third.this, Activity_PostJob_Four.class);
                 intent.putExtra("key", FilenamList);
                 intent.putExtra("date", date);
                 intent.putExtra("time", time);
                 intent.putExtra("description", description);
                 intent.putExtra("location", location);
                 intent.putExtra("accept_bid_for", accept_bid_for);
                 intent.putExtra("bid_min_range", bid_min_range);
                 intent.putExtra("bid_max_range", bid_max_range);
                 intent.putExtra("bid_per", bid_per);
                 intent.putExtra("Estimatedtime", Estimatedtime);
                 intent.putExtra("lang", lang);
                 intent.putExtra("all_Skils", all_Skils);
                 startActivity(intent);

             }

















            }
        });

        back.setOnClickListener(new View.OnClickListener() {
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

        searchview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {


                String listid= getAllMedicineList.get(itemIndex).getId();

                Skills_list.add(listid);
                all_Skils = TextUtils.join(",", Skills_list);
                System.out.println("all_Skils=======     "+all_Skils);
                String mstr = searchview.getText().toString();
                System.out.println("autocomplete=======     "+mstr);
                SuggestionList.add(mstr);
                suggestionAdapter = new SuggestionAdapter(Activity_PostJob_Third.this, SuggestionList);
                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Activity_PostJob_Third.this);
                medicine_recycler.setLayoutManager(new LinearLayoutManager(Activity_PostJob_Third.this, RecyclerView.VERTICAL, false));
                medicine_recycler.setAdapter(suggestionAdapter);
                suggestionAdapter.notifyDataSetChanged();


                searchview.setText("");
            }
        });


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
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_PostJob_Third.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = Utility.checkPermission(Activity_PostJob_Third.this);


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
                Cursor cursor = Activity_PostJob_Third.this.getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
                cursor.close();


                if (destination != null) {
                    filenew1 = destination.getAbsolutePath();
                    System.out.println("destinationonSelectFromGalleryResult=====        " + destination.getAbsolutePath());
                    FilenamList.add(filenew1);
                    System.out.println("FilenamList " + FilenamList.toString());


                } else {
                    Toast.makeText(Activity_PostJob_Third.this, "something wrong", Toast.LENGTH_SHORT).show();
                }

                try {
                    bm = MediaStore.Images.Media.getBitmap(Activity_PostJob_Third.this.getContentResolver(), data.getData());

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
                                Toast.makeText(Activity_PostJob_Third.this, "Permission required", Toast.LENGTH_SHORT).show();
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


//    Skills api autocomplete


    private void suggestion_list() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl+Service_list_Api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            getAllMedicineList.clear();
                            JSONObject obj = new JSONObject(response);
                            System.out.println("skills api-------       "+obj.toString());
                            String result = obj.getString("result");
                            if (result.equalsIgnoreCase("true")) {
                                JSONArray heroArray = obj.getJSONArray("services");
                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    Search_suggestion_vendor_register hero = new Search_suggestion_vendor_register(
                                            heroObject.getString("id"),
                                            heroObject.getString("name"),
                                            heroObject.getString("icon")

                                    );
                                    String name = heroObject.getString("name");
                                    search_name.add(name);
                                    getAllMedicineList.add(i, hero);
                                }


                                AutoCompletevendor_register_adapter adapter = new AutoCompletevendor_register_adapter(Activity_PostJob_Third.this, getAllMedicineList);
                                searchview.setAdapter(adapter);
                                searchview.setThreshold(1);
                                // ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, search_name);
                                //  searchview.setAdapter(arrayAdapter);
                                //  searchview.setThreshold(1);



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getActivity(), "Something Went Wrong ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();



                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}



