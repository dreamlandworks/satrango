package com.satrangolimitless.User_UI.POSTAJOB;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
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
import com.satrangolimitless.User_UI.My_Job_Posts.MyJobPostsFragment;
import com.satrangolimitless.Utils.APIClient;
import com.satrangolimitless.Utils.ApiService;
import com.satrangolimitless.Utils.Utility;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.model.Add_Job_Post_Multimove;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Service_list_Api;

public class Activity_PostJob_Multiplelocation_Fourth extends AppCompatActivity {

    private final List<String> FilenamList = new ArrayList<>();
    private final ArrayList<Uri> arrayList = new ArrayList<>();
    private final int SELECT_FILE = 1;
    Button btnnextt;
    ImageView back;
    Session session;
    Session_vendor session_vendor;
    String date, user_id, lang, all_Skils, time, description, location, accept_bid_for, Estimatedtime, bid_per, bid_max_range, bid_min_range, type = "";
    List<String> Skills_list = new ArrayList<String>();
    ArrayList<String> search_name = new ArrayList<>();
    List<Search_suggestion_vendor_register> getAllMedicineList = new ArrayList<>();
    AutoCompleteTextView searchview;
    SuggestionAdapter suggestionAdapter;
    RecyclerView medicine_recycler;
    ArrayList<String> SuggestionList = new ArrayList<>();
    ArrayList<String> Imagelist = new ArrayList<>();
    CardView cdmotor, cdcar, cdauto, cdbus, cdvan, cdtruck, cdcarier, cdothers;
    String vehicle = "", endlocation, tolocation, weight;
    String[] Language = {"Select a language", "Hindi", "English", "Bengali", "Marathi", "Telugu", "Tamil", "Gujrati", "Kannada", "Malyalam"};
    Spinner spin_language;
    ImageView img_add;
    RecyclerView list;
    ArrayList<SubjectData> imagearrayList = new ArrayList<SubjectData>();
    File destination;
    Adapter_attachments_images adapter_popular_categoriesActivity;
    Uri pickedImage;
    String userChoosenTask;
    String filenew1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_jobpost_multimove_fourth);
        session = new Session(getApplicationContext());
        user_id = session.getUserId();
        btnnextt = findViewById(R.id.btnnextt);
        back = findViewById(R.id.back);
        cdmotor = findViewById(R.id.cdmotor);
        cdcar = findViewById(R.id.cdcar);
        cdauto = findViewById(R.id.cdauto);
        cdbus = findViewById(R.id.cdbus);
        cdvan = findViewById(R.id.cdvan);
        cdtruck = findViewById(R.id.cdtruck);
        cdcarier = findViewById(R.id.cdcarier);
        cdothers = findViewById(R.id.cdothers);
        img_add = findViewById(R.id.img_add);
        list = findViewById(R.id.list);
        medicine_recycler = findViewById(R.id.medicine_recycler);
        searchview = findViewById(R.id.searchview);
        spin_language = (Spinner) findViewById(R.id.spin_language);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Language);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_language.setAdapter(aa);
        suggestion_list();
        btnnextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if   (vehicle.isEmpty()){
                 Toast.makeText(getApplicationContext(), "Please select vehicle", Toast.LENGTH_SHORT).show();

             }
                  else if (all_Skils.isEmpty())  {
                 Toast.makeText(getApplicationContext(), "Please select skills", Toast.LENGTH_SHORT).show();

             }   else{
                 PostamultimoveJob();
             }

            }
        });

        spin_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                lang = Language[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (getIntent() != null) {

            bid_min_range = getIntent().getStringExtra("bid_min_range");
            bid_max_range = getIntent().getStringExtra("bid_max_range");
            bid_per = getIntent().getStringExtra("bid_per");
            Estimatedtime = getIntent().getStringExtra("Estimatedtime");
            accept_bid_for = getIntent().getStringExtra("accept_bid_for");

            location = getIntent().getStringExtra("location");
            description = getIntent().getStringExtra("description");


            all_Skils = getIntent().getStringExtra("all_Skils");
            lang = getIntent().getStringExtra("lang");
            weight = getIntent().getStringExtra("weight");
            tolocation = getIntent().getStringExtra("tolocation");
            endlocation = getIntent().getStringExtra("endlocation");

            System.out.println("location--  " + location);

            System.out.println("endlocation--  " + endlocation);
            System.out.println("date--  " + session.getMljobpostdate());
            System.out.println("time--  " + session.getMljobposttime());


            System.out.println("tolocation--  " + tolocation);
            System.out.println("weight--   " + weight);
            System.out.println("description--   " + description);
            System.out.println("bid_per--   " + bid_per);
            System.out.println("bid_min_range--    " + bid_min_range);
            System.out.println("bid_max_range--   " + bid_max_range);
            System.out.println("Estimatedtime--   " + Estimatedtime);
            System.out.println("accept_bid_for--   " + accept_bid_for);

        }

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRunTimePermission();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cdmotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle = "Motor";
                cdmotor.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdauto.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbus.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdvan.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdtruck.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdcarier.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdothers.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdcar.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        cdcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle = "Car";
                cdcar   .setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdauto.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbus.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdvan.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdtruck.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdcarier.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdothers.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdmotor.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        cdauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle = "Auto";
                   cdauto.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdcar.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbus.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdvan.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdtruck.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdcarier.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdothers.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdmotor.setCardBackgroundColor(Color.parseColor("#ffffff"));

            }
        });
        cdbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle = "Bus";
                cdbus.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdcar.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdauto.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdvan.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdtruck.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdcarier.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdothers.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdmotor.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        cdvan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle = "Van";
                 cdvan.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdcar.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdauto.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbus.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdtruck.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdcarier.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdothers.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdmotor.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        cdtruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vehicle = "Truck";
                 cdtruck.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdcar.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdauto.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbus.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdvan.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdcarier.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdothers.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdmotor.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        cdcarier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle = "Carier";
                 cdcarier.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdcar.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdauto.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbus.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdvan.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdtruck.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdothers.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdmotor.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        cdothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle = "Others";
                 cdothers.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cdcar.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdauto.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdbus.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdvan.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdtruck.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdcarier .setCardBackgroundColor(Color.parseColor("#ffffff"));
                cdmotor.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        searchview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {


                String listid = getAllMedicineList.get(itemIndex).getId();

                Skills_list.add(listid);
                all_Skils = TextUtils.join(",", Skills_list);
                System.out.println("all_Skils=======     " + all_Skils);
                String mstr = searchview.getText().toString();
                System.out.println("autocomplete=======     " + mstr);
                SuggestionList.add(mstr);
                suggestionAdapter = new SuggestionAdapter(Activity_PostJob_Multiplelocation_Fourth.this, SuggestionList);
                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Activity_PostJob_Multiplelocation_Fourth.this);
                medicine_recycler.setLayoutManager(new LinearLayoutManager(Activity_PostJob_Multiplelocation_Fourth.this, RecyclerView.VERTICAL, false));
                medicine_recycler.setAdapter(suggestionAdapter);
                suggestionAdapter.notifyDataSetChanged();


                searchview.setText("");
            }
        });

    }

//Add images---------

    //-------------------------------------------------Add image attachment

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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_PostJob_Multiplelocation_Fourth.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Activity_PostJob_Multiplelocation_Fourth.this);


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
            Cursor cursor = Activity_PostJob_Multiplelocation_Fourth.this.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            destination = new File(cursor.getString(cursor.getColumnIndex(filePath[0])));
            cursor.close();


            if (destination != null) {
                filenew1 = destination.getAbsolutePath();
                System.out.println("destinationonSelectFromGalleryResult=====        " + destination.getAbsolutePath());
                FilenamList.add(filenew1);
                System.out.println("FilenamList " + FilenamList.toString());


            } else {
                Toast.makeText(Activity_PostJob_Multiplelocation_Fourth.this, "something wrong", Toast.LENGTH_SHORT).show();
            }

            try {
                bm = MediaStore.Images.Media.getBitmap(Activity_PostJob_Multiplelocation_Fourth.this.getContentResolver(), data.getData());

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
                            Toast.makeText(Activity_PostJob_Multiplelocation_Fourth.this, "Permission required", Toast.LENGTH_SHORT).show();
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


    public void PostamultimoveJob() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_PostJob_Multiplelocation_Fourth.this);
        progressDialog.show();
        ApiService apiInterface = APIClient.getClient().create(ApiService.class);

        MultipartBody.Part[] surveyImagesParts = null;

        surveyImagesParts = new MultipartBody.Part[FilenamList.size()];
        for (int index = 0; index < FilenamList.size(); index++) {
            File file = new File(FilenamList.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("attachment[]", file.getName(), surveyBody);
        }
        RequestBody USER_ID = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody DATE = RequestBody.create(MediaType.parse("text/plain"), session.getMljobpostdate());
        RequestBody TIME = RequestBody.create(MediaType.parse("text/plain"), session.getMljobposttime());
        RequestBody LOCATION = RequestBody.create(MediaType.parse("text/plain"), location);
        RequestBody TO_LOCATION = RequestBody.create(MediaType.parse("text/plain"), tolocation);
        RequestBody END_LOCATION = RequestBody.create(MediaType.parse("text/plain"), endlocation);
        RequestBody WEIGHT = RequestBody.create(MediaType.parse("text/plain"), weight);
        RequestBody DESCRIPTION = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody BID_MINRANGE = RequestBody.create(MediaType.parse("text/plain"), bid_min_range);
        RequestBody BID_MAXRANGE = RequestBody.create(MediaType.parse("text/plain"), bid_max_range);
        RequestBody BID_PER = RequestBody.create(MediaType.parse("text/plain"), bid_per);
        RequestBody ESTIMATE_TIME = RequestBody.create(MediaType.parse("text/plain"), Estimatedtime);
        RequestBody ACCEPT_BID_FOR = RequestBody.create(MediaType.parse("text/plain"), accept_bid_for);
        RequestBody KEY_SKILLS = RequestBody.create(MediaType.parse("text/plain"), all_Skils);
        RequestBody VEHICLE = RequestBody.create(MediaType.parse("text/plain"), vehicle);
        RequestBody LANGUAGE = RequestBody.create(MediaType.parse("text/plain"), lang);

        Call<Add_Job_Post_Multimove> call = apiInterface.ADD_JOB_POST_Multimove(USER_ID, DATE, TIME, LOCATION, TO_LOCATION, END_LOCATION, WEIGHT, DESCRIPTION, BID_MINRANGE, BID_MAXRANGE, BID_PER, ESTIMATE_TIME,ACCEPT_BID_FOR, KEY_SKILLS, VEHICLE, LANGUAGE, surveyImagesParts);


        call.enqueue(new Callback<Add_Job_Post_Multimove>() {
            @Override

            public void onResponse(Call<Add_Job_Post_Multimove> call, retrofit2.Response<Add_Job_Post_Multimove> response) {
                progressDialog.dismiss();

                try {
                    System.out.println("RESULT----  jobb post multimove         " + response.body().getResult());
                    if (response.body().getResult().equals("true")) {

                        alertdialogbox();

                    } else {
                        Toast.makeText(Activity_PostJob_Multiplelocation_Fourth.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Add_Job_Post_Multimove> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ccheckResponce", " :" + t.getMessage());
                Toast.makeText(Activity_PostJob_Multiplelocation_Fourth.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


//    key skills suggestion

    private void suggestion_list() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Service_list_Api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            getAllMedicineList.clear();
                            JSONObject obj = new JSONObject(response);
                            System.out.println("skills api-------       " + obj.toString());
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


                                AutoCompletevendor_register_adapter adapter = new AutoCompletevendor_register_adapter(Activity_PostJob_Multiplelocation_Fourth.this, getAllMedicineList);
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


    public void alertdialogbox() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Activity_PostJob_Multiplelocation_Fourth.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_jobpostsuccessfulmulti, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        ImageView img_close = (ImageView) dialogView.findViewById(R.id.img_close);
        Button btn_yes = (Button) dialogView.findViewById(R.id.btn_yes);
        Button btn_no = (Button) dialogView.findViewById(R.id.btn_no);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent in = new Intent(Activity_PostJob_Multiplelocation_Fourth.this, LandingActivity.class);
                startActivity(in);
                finish();

            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent in = new Intent(Activity_PostJob_Multiplelocation_Fourth.this, LandingActivity.class);
                startActivity(in);
                finish();
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent in = new Intent(Activity_PostJob_Multiplelocation_Fourth.this, LandingActivity.class);
                startActivity(in);
                finish();
            }
        });
        dialog.show();
    }

}

