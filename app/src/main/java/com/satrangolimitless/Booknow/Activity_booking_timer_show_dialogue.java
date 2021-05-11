package com.satrangolimitless.Booknow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Getbooking_status_during_timer;

public class Activity_booking_timer_show_dialogue extends AppCompatActivity {


    int myProgress = 0;
    ProgressBar progressBarView;
    Button btn_start;
    TextView tv_time;
    EditText et_timer;
    int progress;
    CountDownTimer countDownTimer;
    int endTime = 250;
    Session session;
    String User_id, bookid;

    public static String data;
    Handler ha = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_booking_timer);
        session = new Session(Activity_booking_timer_show_dialogue.this);
        progressBarView = (ProgressBar) findViewById(R.id.view_progress_bar);
        tv_time = (TextView) findViewById(R.id.tv_timer);
        User_id = session.getUserId();

        if (getIntent() != null) {
            bookid = getIntent().getStringExtra("id");
            Log.e("bookid", "" + bookid);
        }
        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        progressBarView.startAnimation(makeVertical);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(0);


        fn_countdown();
        handlerfun();









    }



    public void setProgress(int startTime, int endTime) {
        progressBarView.setMax(endTime);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(startTime);

    }


    @Override
    public void onBackPressed() {
         this.finish();
        super.onBackPressed();
    }


    public void handlerfun() {


        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function
                System.out.println(" ----------*******--------- ");

                GetBookingStatususerside();

                ha.postDelayed(this, 15000);
            }
        }, 15000);


    }




//GET STATUS OF BOOKING AFTER EVERY 30 SECONDS


    public void GetBookingStatususerside() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Getbooking_status_during_timer,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v("<>GetBookingStatus   ", response);
                        System.out.println("GetBookingStatususerside response ===== " + response);

                        try {


                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                              data = obj.getString("data");
                            if (result.equalsIgnoreCase("true")) {


                                if (data.contains("Upcoming")){
                                    ha.removeCallbacksAndMessages(null);

                                    alertdialogbox();
                                }else if (data.contains("Cancel")) {
                                    ha.removeCallbacksAndMessages(null);

                                    alertdialogboxcancel();

                                }else {
                                    handlerfun();
                                }


                            } else {

                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", User_id);
                params.put("bookingId", bookid);
                System.out.println("GetBookingStatususerside==========        " + params);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }





    public void alertdialogboxcancel() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Activity_booking_timer_show_dialogue.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_waiting_user_dialog, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        Button btnbookforme = (Button) dialogView.findViewById(R.id.btnbookforme);
        Button btnnothanks = (Button) dialogView.findViewById(R.id.btnnothanks);

        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        btnbookforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                startActivity(intent);
                finish();

            }
        });
        btnnothanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                startActivity(intent);
                finish();
            }
        });

try {



    if(!isFinishing())
    {
        dialog.show();
    }
}
     catch (Exception e)  {
    e.printStackTrace();
     }

    }


//activity_postjob_user_dialog

    void alertdialogbox() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Activity_booking_timer_show_dialogue.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_my_dialog, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        Button MakePayment = (Button) dialogView.findViewById(R.id.MakePayment);

        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        MakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent in = new Intent(Activity_booking_timer_show_dialogue.this, PaymentActivity.class);
                startActivity(in);
                finish();

            }
        });
        try {

            if(!isFinishing())
            {
                dialog.show();
            }

        }catch (Exception e){e.printStackTrace();}

    }





    private void fn_countdown() {

        if (5 > 0) {
            myProgress = 0;

            try {
                countDownTimer.cancel();

            } catch (Exception e) {

            }


            String timeInterval = "181";
            progress = 1;
            endTime = Integer.parseInt(timeInterval); // up to finish time

            countDownTimer = new CountDownTimer(endTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setProgress(progress, endTime);
                    progress = progress + 1;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                    String newtime = hours + ":" + minutes + ":" + seconds;

                    if (newtime.equals("0:0:0")) {
                        tv_time.setText("00:00:00");
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + hours + ":0" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1)) {
                        tv_time.setText("0" + hours + ":0" + minutes + ":" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + hours + ":" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText(hours + ":0" + minutes + ":0" + seconds);
                    } else if (String.valueOf(hours).length() == 1) {
                        tv_time.setText("0" + hours + ":" + minutes + ":" + seconds);
                    } else if (String.valueOf(minutes).length() == 1) {
                        tv_time.setText(hours + ":0" + minutes + ":" + seconds);
                    } else if (String.valueOf(seconds).length() == 1) {
                        tv_time.setText(hours + ":" + minutes + ":0" + seconds);
                    } else {
                        tv_time.setText(hours + ":" + minutes + ":" + seconds);
                    }

                }

                @Override
                public void onFinish() {
                    setProgress(progress, endTime);


                    alertdialogboxcancel();




                }
            };
            countDownTimer.start();
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the value", Toast.LENGTH_LONG).show();
        }


    }


}