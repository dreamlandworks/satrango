package com.satrangolimitless.User_UI.Mybooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.satrangolimitless.Adapter.AtachmentBookingpendingAdapter;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Actvity_Userbooking_details_ extends AppCompatActivity {
    Session session;
    String bookingId, user_id;
    TextView txtusername, txtprice,
            txtdate, txtdescr, txtlocation,
            txttimefrmto, txtdatet, txtbookingidl, txtrating, txttotp,txtfrmtotime,txtdatename;
    String image, price, reting, date, name, bookingid, date_time, work_description,
            to_time, from_time, id, user_rating,
            min_charge,
            user_name, upload_doc, otp, vendorid, totime, frmtime,
            user_contact;
    Fragment fragment;
    ImageView imgbackr;
    CircleImageView imgone;
    Button btncancel, btnreshedule;
    ImageView ivbak;
    AtachmentBookingpendingAdapter atachmentshowAdapter;
    ArrayList<String> attachlist = new ArrayList<>();
    RecyclerView revatachpendingdetail;
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_singlemove_bookingdetails_activity);

        session = new Session(getApplicationContext());
        user_id = session.getUserId();
        bookingId = getIntent().getStringExtra("id");


        btnreshedule = findViewById(R.id.btnreshedule);
        btncancel = findViewById(R.id.btncancel);
        imgone = findViewById(R.id.imgone);
        txtusername = findViewById(R.id.txtusername);
        txtprice = findViewById(R.id.txtprice);
        txtdate = findViewById(R.id.txtdate);
        txtdescr = findViewById(R.id.txtdescr);
        txtlocation = findViewById(R.id.txtlocation);
        txttimefrmto = findViewById(R.id.txttimefrmto);
        txtdatet = findViewById(R.id.txtdatet);
        txtrating = findViewById(R.id.txtrating);
        txtbookingidl = findViewById(R.id.txtbookingidl);
        txttotp = findViewById(R.id.txttotp);
        txtfrmtotime = findViewById(R.id.txtfrmtotime);
        txtdatename = findViewById(R.id.txtdatename);


        revatachpendingdetail = findViewById(R.id.revatachpendingdetail);

        ivbak = findViewById(R.id.ivbak);


        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            bookingid = getIntent().getStringExtra("bookingid");
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            reting = getIntent().getStringExtra("reting");
            price = getIntent().getStringExtra("price");
            image = getIntent().getStringExtra("image");
            work_description = getIntent().getStringExtra("desc");
            otp = getIntent().getStringExtra("otp");
            price = getIntent().getStringExtra("price");
            vendorid = getIntent().getStringExtra("vendorid");
            upload_doc = getIntent().getStringExtra("upload_doc");
            frmtime = getIntent().getStringExtra("frmtime");
            totime = getIntent().getStringExtra("totime");
            System.out.println("OPT----------        " + otp);
            System.out.println("vendorid----------        " + vendorid);
            System.out.println("date----------        " + date);


            ArrayList aList = new ArrayList(Arrays.asList(upload_doc.split(",")));
            for (int i = 0; i < aList.size(); i++) {
                System.out.println(" -->   " + aList.get(i));
                String images = (String) aList.get(i);
                attachlist.add(images);
            }


            atachmentshowAdapter = new AtachmentBookingpendingAdapter(getApplicationContext(), attachlist);
            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
            revatachpendingdetail.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
            revatachpendingdetail.setAdapter(atachmentshowAdapter);
            atachmentshowAdapter.notifyDataSetChanged();


        }
        txtusername.setText(name);
        txtrating.setText(reting);
        txtbookingidl.setText(bookingid);
        txtdatet.setText(date);
        txtdatename.setText(date);
        txtdescr.setText(work_description);
        txtprice.setText("₹ "+price);
        txttotp.setText(otp);
        txtfrmtotime.setText(frmtime+" to "+totime);
        ivbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Glide.with(getApplicationContext()).load(Image_url + image).into(imgone);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Actvity_Userbooking_details_.this, User_Cancel_Booking_Activity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("date", date);
                intent.putExtra("bookingid", bookingid);
                intent.putExtra("reting", reting);
                intent.putExtra("price", price);
                intent.putExtra("image", upload_doc);

            startActivity(intent);

            }
        });

        btnreshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Actvity_Userbooking_details_.this, User_Reshedule_BookingActivity.class);
                intent.putExtra("vendorid", vendorid);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("date", date);
                intent.putExtra("bookingid", bookingid);
                intent.putExtra("reting", reting);
                intent.putExtra("price", min_charge);
                intent.putExtra("image", upload_doc);
                intent.putExtra("vendorid", vendorid);
                intent.putExtra("otp", otp);
                intent.putExtra("frmtime", frmtime);
                intent.putExtra("totime", totime);
                intent.putExtra("price", price);


                startActivity(intent);

            }
        });

    }

/*
    private void Getbookingdetails() {
        final ProgressDialog progressDialog = new ProgressDialog(Actvity_UserSingle_movebooking_details_.this);
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
                                    System.out.println("Actvity_UserSingle_movebooking_details_----     " + dataObject.toString());
                                    id = dataObject.getString("id");
                                    booking_date = dataObject.getString("booking_date");
                                    from_time = dataObject.getString("from_time");
                                    to_time = dataObject.getString("to_time");
                                    work_description = dataObject.getString("work_description");
                                    date_time = dataObject.getString("date_time");
                                    start_location1 = dataObject.getString("start_location1");
                                    user_contact = dataObject.getString("user_contact");
                                    user_name = dataObject.getString("user_name");
                                    min_charge = dataObject.getString("min_charge");
                                    user_rating = dataObject.getString("user_rating");
                                    upload_doc = dataObject.getString("upload_doc");
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
                                    txtcharge.setText("₹ " + min_charge);


                                    Glide.with(getApplicationContext())
                                            .load(Image_url + upload_doc)
                                            .apply(new RequestOptions()
                                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                    .dontAnimate()
                                                    .centerCrop()
                                                    .dontTransform())
                                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                                            .skipMemoryCache(true)
                                            .apply(new RequestOptions().override(600, 200)).into(imgone);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(Actvity_UserSingle_movebooking_details_.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Actvity_UserSingle_movebooking_details_.this, error.toString(), Toast.LENGTH_LONG).show();
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
        rQueue = Volley.newRequestQueue(Actvity_UserSingle_movebooking_details_.this);
        rQueue.add(stringRequest);


    }
*/


}
