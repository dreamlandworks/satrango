package com.satrangolimitless.Vendor_UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.satrangolimitless.Adapter.AtachmentBookingpendingAdapter;
import com.satrangolimitless.Adapter.AtachmentshowAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Home_user.HomeFragment_user;
import com.satrangolimitless.User_UI.Settings.SettingsFragment;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_CancelBooking_activity;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_MyBooking;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_ResheduleBooking_activity;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.View_Bookingdetail_Notificationclick;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Activity_Single_movebooking_details extends AppCompatActivity {
    Session_vendor session_vendor;
    String bookingId, vendor_id;
    RecyclerView recvattach;
    TextView txtusername,txtcharge,
            txtdate, txtdescr, txtlocation,
            txttimefrmto, txtdatet, txtbookingidl,txtrating;
    String booking_date,
            start_location1, date_time, work_description,
    userimage, to_time, from_time, id,user_rating,attachments;

    String name,
    date,
            bookingid,
    reting,
            price,
    image,
            frmtime,
    totime,desc,
    min_charge,user_name,upload_doc,
    user_contact;
    Fragment fragment;
    ImageView imgbackr;
    TextView txtback;
    private RequestQueue rQueue;
    RoundedImageView imgone;
    Button btncancel,btnreshedule;
CircleImageView imguser;
    AtachmentBookingpendingAdapter atachmentshowAdapter;
    ArrayList<String> attachlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_move_details);

        session_vendor = new Session_vendor(getApplicationContext());
        vendor_id = session_vendor.getUserId();

        if (getIntent()!=null){
            bookingId = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            bookingid = getIntent().getStringExtra("bookingid");
            reting = getIntent().getStringExtra("reting");
            price = getIntent().getStringExtra("price");
            image = getIntent().getStringExtra("image");
            frmtime = getIntent().getStringExtra("frmtime");
            totime = getIntent().getStringExtra("totime");
            desc = getIntent().getStringExtra("desc");
            attachments = getIntent().getStringExtra("attach");
            userimage = getIntent().getStringExtra("userimage");

        }








        System.out.println("attachments------     " + attachments);
        System.out.println("bookingId------     " + bookingId);
        System.out.println("vendor_id------     " + vendor_id);
        btnreshedule = findViewById(R.id.btnreshedule);
        btncancel = findViewById(R.id.btncancel);
        recvattach = findViewById(R.id.recvattach);
        imgone = findViewById(R.id.imgone);
        txtusername = findViewById(R.id.txtusername);
        txtcharge = findViewById(R.id.txtcharge);
        txtdate = findViewById(R.id.txtdate);
        txtdescr = findViewById(R.id.txtdescr);
        txtlocation = findViewById(R.id.txtlocation);
        txttimefrmto = findViewById(R.id.txttimefrmto);
        txtdatet = findViewById(R.id.txtdatet);
        txtrating = findViewById(R.id.txtrating);
        txtbookingidl = findViewById(R.id.txtbookingidl);
        imguser = findViewById(R.id.imguser);

        txtback = findViewById(R.id.txtback);
        imgbackr = findViewById(R.id.imgbackr);

        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgbackr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

System.out.println("userimage><><><><>      "+userimage);





        ArrayList aList= new ArrayList(Arrays.asList(attachments.split(",")));
        for(int i=0;i<aList.size();i++)
        {
            System.out.println(" -->   "+aList.get(i));
            String images= (String) aList.get(i);
            attachlist.add(images);
        }


        atachmentshowAdapter = new AtachmentBookingpendingAdapter(getApplicationContext(), attachlist);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
        recvattach.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        recvattach.setAdapter(atachmentshowAdapter);
        atachmentshowAdapter.notifyDataSetChanged();


        txtusername.setText(name);
                txtcharge.setText(price);
        txtdate.setText(date);
                txtdescr.setText(desc);
//        txtlocation.setText(price);
                txttimefrmto.setText(frmtime+" - "+totime);
                txtdatet.setText(date);
                txtrating.setText(reting);
                txtbookingidl .setText(bookingid);

        Glide.with(getApplicationContext())
                .load(mediimage_url + userimage)

                .into(imguser);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Single_movebooking_details.this, Vendor_CancelBooking_activity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", user_name);
                intent.putExtra("date", date_time);
                intent.putExtra("bookingid", id);
                intent.putExtra("reting", user_rating);
                intent.putExtra("price", min_charge);
                intent.putExtra("image", upload_doc);

                Activity_Single_movebooking_details.this.startActivity(intent);

            }
        });

        btnreshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Single_movebooking_details.this, Vendor_ResheduleBooking_activity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", user_name);
                intent.putExtra("date", booking_date);
                intent.putExtra("bookingid", bookingId);
                intent.putExtra("reting", user_rating);
                intent.putExtra("price", min_charge);
                intent.putExtra("image", upload_doc);
                intent.putExtra("frmtime", from_time);
                intent.putExtra("to_time", to_time);





                Activity_Single_movebooking_details.this.startActivity(intent);
            }
        });



        Getbookingdetails();
    }

    private void Getbookingdetails() {
        System.out.println("enter detailss>>>>>>>>      ");
        final ProgressDialog progressDialog = new ProgressDialog(Activity_Single_movebooking_details.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + View_Bookingdetail_Notificationclick,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);

                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {


                                JSONArray jsonArray = jsonObject.getJSONArray("new_booking");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    System.out.println("Activity_Single_movebooking_details----     " + dataObject.toString());
                                    id = dataObject.getString("id");
                                    booking_date = dataObject.getString("booking_date");
                                    from_time = dataObject.getString("from_time");
                                    to_time = dataObject.getString("to_time");
                                    work_description = dataObject.getString("work_description");
                                    date_time = dataObject.getString("date_time");
                                    start_location1 = dataObject.getString("start_location1");
                                    user_contact= dataObject.getString("user_contact");
                                    user_name= dataObject.getString("user_name");
                                    min_charge= dataObject.getString("min_charge");
                                    user_rating= dataObject.getString("user_rating");
                                    upload_doc= dataObject.getString("upload_doc");
                                    bookingId= dataObject.getString("bookingId");


                                }


                                try {

                                    String start_dt = booking_date;
                                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
                                    Date date = (Date) formatter.parse(start_dt);
                                    SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                    String finalString = newFormat.format(date);
                                    System.out.println("date view------     " + finalString);

                                    txtdatet.setText(finalString);
                                    txtdate.setText(finalString);
                                    txtrating.setText(user_rating);

                                    txttimefrmto.setText(from_time + " to " + to_time);
                                    txtlocation.setText(start_location1);
                                    txtdescr.setText(work_description);
                                    txtusername.setText(user_name);
                                    txtcharge.setText("₹ "+min_charge);




                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(Activity_Single_movebooking_details.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_Single_movebooking_details.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("vendor_id", vendor_id);
                params.put("bookingId", bookingId);
                System.out.println("vendor_id=======       " + vendor_id);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(Activity_Single_movebooking_details.this);
        rQueue.add(stringRequest);


    }


}
