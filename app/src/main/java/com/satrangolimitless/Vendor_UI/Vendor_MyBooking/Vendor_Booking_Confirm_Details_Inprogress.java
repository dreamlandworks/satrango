package com.satrangolimitless.Vendor_UI.Vendor_MyBooking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.BillDetails_Beforecomplete_by_vendor;
import static com.satrangolimitless.Utils.Base_Url.Booking_markcomplete_by_vendor;
import static com.satrangolimitless.Utils.Base_Url.Booking_paused_by_vendor;
import static com.satrangolimitless.Utils.Base_Url.Bookingcomplete_by_vendor_getOTP;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.Raiseextraamount_by_vendor;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Vendor_Booking_Confirm_Details_Inprogress extends AppCompatActivity {

    private static CountDownTimer countDownTimer;
    String currentTime, totalamount;
    Session_vendor session_vendor;
    TextView txtnames, txtprice, txttimercounter, txttime, txtdate, txtbookingid, txtratings, txtextratdemand, txtbdate, txtviewfiles;
    Button btnmkcomplete, btnpause;
    String e1 = "", e2 = "", e3 = "", e4 = "", otp = "";
    String total, professional_change,
            extra_charge, amount, verify_otp, extra_charge_status;
    String vid, bookingid, image, price, reting, date, name, id, status, job_status,frmtime,totime;
    String four, three, two, one;
    ImageView img_panic, ivbak;
    ImageView imgotpstus, imgextrastus, imgtus, mg;
    TextView txtextrachrgstatus,  txtraiseexamount, txttaskcompleted, txtmrkcomplete;
    CircleImageView imgpic;
    //Declare a variable to hold count down timer's paused status
    private final boolean isPaused = false;
    //Declare a variable to hold count down timer's paused status
    private final boolean isCanceled = false;
    //Declare a variable to hold CountDownTimer remaining time
    private final long timeRemaining = 0;
    // on the stopwatch.
    private int seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    TextView txtotpentered;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendorbookingconfirmdetails_inprogress);

        session_vendor = new Session_vendor(getApplicationContext());
        txttimercounter = findViewById(R.id.txttimercounter);
        vid = session_vendor.getUserId();
        txtbookingid = findViewById(R.id.txtbookingid);
        txtnames = findViewById(R.id.txtnames);
        txtprice = findViewById(R.id.txtamount);
        txtratings = findViewById(R.id.txtratings);
        txtextratdemand = findViewById(R.id.txtextratdemand);
        txtbdate = findViewById(R.id.txtbdate);
        txtviewfiles = findViewById(R.id.txtviewfiles);
        txtextrachrgstatus = findViewById(R.id.txtextrachrgstatus);
        ivbak = findViewById(R.id.ivbak);
        img_panic = findViewById(R.id.img_panic);
        imgpic = findViewById(R.id.imgpic);
        txtotpentered = findViewById(R.id.txtotpentered);

        imgotpstus = findViewById(R.id.imgotpstus);
        imgextrastus = findViewById(R.id.imgextrastus);
        imgtus = findViewById(R.id.imgtus);
        mg = findViewById(R.id.mg);
        txtraiseexamount = findViewById(R.id.txtraiseexamount);
        txtmrkcomplete = findViewById(R.id.txtmrkcomplete);
        txttaskcompleted = findViewById(R.id.txttaskcompleted);


        txttime = findViewById(R.id.txttime);
        txtdate = findViewById(R.id.txtdate);
        txtbdate = findViewById(R.id.txtbdate);
        btnpause = findViewById(R.id.btnpause);
        btnmkcomplete = findViewById(R.id.btnmkcomplete);
        running = true;
//


        if (getIntent() != null) {

            id = getIntent().getStringExtra("id");
            bookingid = getIntent().getStringExtra("bookingid");
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            reting = getIntent().getStringExtra("reting");
            price = getIntent().getStringExtra("price");
            image = getIntent().getStringExtra("image");
            status = getIntent().getStringExtra("status");
            extra_charge_status = getIntent().getStringExtra("extra_charge_status");
            verify_otp = getIntent().getStringExtra("verify_otp");
            job_status = getIntent().getStringExtra("job_status");
            frmtime = getIntent().getStringExtra("frmtime");
            totime = getIntent().getStringExtra("totime");

        }

        System.out.println("extracharge "+extra_charge_status);
        System.out.println("status "+status);
        System.out.println("verify_otp "+verify_otp);

        txtnames.setText(name);
        txtprice.setText("₹ " + price);
        txtdate.setText(date);

        txtbookingid.setText(bookingid);
        txtratings.setText(reting);
        txtbdate.setText(date);
        txttime.setText(frmtime+" - "+totime);

        Glide.with(getApplicationContext())
                .load(Image_url + image)
                .into(imgpic);


        currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        System.out.println("TIME CURRENT<><><><><><    " + currentTime);

        btnpause.setText("Pause");
        btnmkcomplete.setText("Mark Complete");


        if (verify_otp.contains("1")) {
            imgotpstus.setImageResource(R.drawable.statuscheck);
            txtotpentered.setTextColor(getResources().getColor(R.color.colorPrimary));
            runTimer();
        }
        if (extra_charge_status.contains("1")) {
            System.out.println("    imgextrastus1 ");
            imgotpstus.setImageResource(R.drawable.statuscheck);
            imgextrastus.setImageResource(R.drawable.statuscheck);
            txtotpentered.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtraiseexamount.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtextrachrgstatus.setText("User accepted extra demand");
            txtextrachrgstatus.setTextColor(getResources().getColor(R.color.colorPrimary));

        }
        if (extra_charge_status.contains("2")) {
            System.out.println("    imgextrastus2");
            imgotpstus.setImageResource(R.drawable.statuscheck);
            imgextrastus.setImageResource(R.drawable.statuscheck);
            txtotpentered.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtraiseexamount.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtextrachrgstatus.setText("User rejected extra demand");
            txtextrachrgstatus.setTextColor(getResources().getColor(R.color.red));

        }




        txtviewfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), Vendor_Booking_inprogress_view_files.class);
                startActivity(intent);

            }
        });

        img_panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0) {
                    running = true;
                    img_panic.setBackgroundResource(R.drawable.panicunc);
                    flag = 1;
                } else if (flag == 1) {
                    running = false;
                    flag = 0;
                    img_panic.setBackgroundResource(R.drawable.panicred);
                    System.out.println(" else if flage --------        "+flag);

                }


            }
        });

        txtextratdemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RaiseExtraDemanddialog();
            }
        });


        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                running = false;
                Pausedbooking();

            }
        });
        btnmkcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookingCompleteapi();
            }
        });

        ivbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void RaiseExtraDemanddialog() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(Vendor_Booking_Confirm_Details_Inprogress.this);
        View view = LayoutInflater.from(Vendor_Booking_Confirm_Details_Inprogress.this).inflate(R.layout.dialog_raise_extra_demand, null);

        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final TextView txtclose = view.findViewById(R.id.txtclose);
        final TextView txttotal = view.findViewById(R.id.txttotal);
        final EditText edtamount = view.findViewById(R.id.edtamount);
        final Button btnsubmit = view.findViewById(R.id.btnsubmit);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalamount = edtamount.getText().toString();
                txttotal.setText(totalamount);

                Extraxchargeapi();
                alertDialog.dismiss();
            }
        });


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    //-----------------------------------------------------------------------
    private void Extraxchargeapi() {

        final ProgressDialog progressDialog = new ProgressDialog(Vendor_Booking_Confirm_Details_Inprogress.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Raiseextraamount_by_vendor;

        System.out.println("Extraxchargeapi vid  <><><<><><><   " + vid + "  id  " + id + " amount " + totalamount);
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vid)
                .addBodyParameter("booking_id", id)
                .addBodyParameter("extra_material", "extra charges")
                .addBodyParameter("extra_charge", totalamount)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {

                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("Extraxchargeapi-  ", jsonObject.toString());
                                progressDialog.dismiss();


                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(Vendor_Booking_Confirm_Details_Inprogress.this, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }


    //---------------------------------------------------------------Booking complete
    private void BookingCompleteapi() {

        final ProgressDialog progressDialog = new ProgressDialog(Vendor_Booking_Confirm_Details_Inprogress.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Booking_markcomplete_by_vendor;

        System.out.println("BookingCompleteapi   <><><><><<>  vid " + vid + "  id " + id + " currentTime " + currentTime);
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vid)
                .addBodyParameter("booking_id", id)
                .addBodyParameter("complete_time", currentTime)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {

                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("BookingCompleteapi-  ", jsonObject.toString());
                                progressDialog.dismiss();
                                ShowBillDetailsapi();
                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(Vendor_Booking_Confirm_Details_Inprogress.this, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }


//--------------------------------------------------------------------------Show Bill details


    private void ShowBillDetailsapi() {

        final ProgressDialog progressDialog = new ProgressDialog(Vendor_Booking_Confirm_Details_Inprogress.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + BillDetails_Beforecomplete_by_vendor;
        System.out.println("ShowBillDetailsapi   <><><><><<>  vid " + vid + "  id " + id);

        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vid)
                .addBodyParameter("booking_id", id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {

                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");
                            total = jsonObject.getString("total");


                            if (result.equalsIgnoreCase("true")) {
                                Log.e("Extraxchargeapi-  ", jsonObject.toString());
                                progressDialog.dismiss();
                                JSONObject obj1 = jsonObject.getJSONObject("data");

                                amount = obj1.getString("amount");
                                extra_charge = obj1.getString("extra_charge");
                                professional_change = obj1.getString("professional_change");

                                BillDetailsddialog();
                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(Vendor_Booking_Confirm_Details_Inprogress.this, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }


    public void BillDetailsddialog() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(Vendor_Booking_Confirm_Details_Inprogress.this);
        View view = LayoutInflater.from(Vendor_Booking_Confirm_Details_Inprogress.this).inflate(R.layout.dialog_vendorbilingdetails, null);

        builder.setCancelable(false);
        final TextView txtclose = view.findViewById(R.id.txtclose);
        final TextView txtextracharge = view.findViewById(R.id.txtextracharge);
        final TextView txtprocharge = view.findViewById(R.id.txtprocharge);
        final TextView ttxtrefund = view.findViewById(R.id.ttxtrefund);
        final TextView txttotal = view.findViewById(R.id.txttotal);

        txtprocharge.setText(professional_change);
        txtextracharge.setText(extra_charge);
        ttxtrefund.setText(total);
        txttotal.setText(amount);

        final Button btnsubmit = view.findViewById(R.id.btnsubmit);


        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Booking_Complete_GetOTP();
                alertDialog.dismiss();
            }
        });

    }


    public void BookingComplete_OTPdialog() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(Vendor_Booking_Confirm_Details_Inprogress.this);
        View view = LayoutInflater.from(Vendor_Booking_Confirm_Details_Inprogress.this).inflate(R.layout.dialog_vendorconfirmotp, null);

        builder.setCancelable(false);

        final TextView txtone = view.findViewById(R.id.txtone);
        final TextView txttwo = view.findViewById(R.id.txttwo);
        final TextView txtthree = view.findViewById(R.id.txtthree);
        final TextView txtfour = view.findViewById(R.id.txtfour);
        final TextView txtclose = view.findViewById(R.id.txtclose);


        txtone.setText(one);
        txttwo.setText(two);
        txtthree.setText(three);
        txtfour.setText(four);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }


    private void Pausedbooking() {

        final ProgressDialog progressDialog = new ProgressDialog(Vendor_Booking_Confirm_Details_Inprogress.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Booking_paused_by_vendor;
        System.out.println("param vid " + vid + "  bookid " + bookingid);
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vid)
                .addBodyParameter("booking_id", id)
                .addBodyParameter("paused_time", currentTime)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("Pausbookingresponce-  ", jsonObject.toString());
                                Toast.makeText(Vendor_Booking_Confirm_Details_Inprogress.this, msg, Toast.LENGTH_SHORT).show();

                                progressDialog.dismiss();
                            } else {


                                Toast.makeText(Vendor_Booking_Confirm_Details_Inprogress.this, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }


    private void Booking_Complete_GetOTP() {

        final ProgressDialog progressDialog = new ProgressDialog(Vendor_Booking_Confirm_Details_Inprogress.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Bookingcomplete_by_vendor_getOTP;
        System.out.println("Booking_Complete_GetOTP param vid " + vid + "  bookid " + id);
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vid)
                .addBodyParameter("booking_id", id)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");
                            String otp = jsonObject.getString("otp");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("BookingCompleteGetOTP  ", jsonObject.toString());
                                Toast.makeText(Vendor_Booking_Confirm_Details_Inprogress.this, msg, Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < otp.length(); i++) {
                                    System.out.println("otp----- c      " + otp.charAt(i));
                                }
                                System.out.println("!@#---  " + otp.charAt(0));
                                System.out.println("!@# --- " + otp.charAt(1));
                                System.out.println("!@# --- " + otp.charAt(2));
                                System.out.println("!@# ---" + otp.charAt(3));


                                one = Character.toString(otp.charAt(0));
                                two = Character.toString(otp.charAt(1));
                                three = Character.toString(otp.charAt(2));
                                four = Character.toString(otp.charAt(3));


                                BookingComplete_OTPdialog();

                                progressDialog.dismiss();
                            } else {


                                Toast.makeText(Vendor_Booking_Confirm_Details_Inprogress.this, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }


//    Stop Watch code------------------

    // Sets the NUmber of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    private void runTimer() {

        // Get the text view.


        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                txttimercounter.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }




     /*   private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                txttimercounter.setText(hms);//set text
            }

            public void onFinish() {

                txttimercounter.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
            }
        }.start();

    }
*/

}
