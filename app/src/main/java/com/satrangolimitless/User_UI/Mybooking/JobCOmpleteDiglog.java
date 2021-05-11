package com.satrangolimitless.User_UI.Mybooking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.satrangolimitless.Booknow.Activity_booking_timer_show_dialogue;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_Booking_Confirm_Details_Inprogress;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Bookingmarkcomplete_by_user_matchOTPvendor;
import static com.satrangolimitless.Utils.Base_Url.app_feedback;
import static com.satrangolimitless.Utils.Base_Url.rate_booking_userside;
import static com.satrangolimitless.Utils.Base_Url.verify_otp_signup_Api;

public class JobCOmpleteDiglog extends AppCompatActivity {
    String otp, bookingid, userid,vendorid,service_id="3";
    Session session;
    Button btnverify;
    EditText edt_one, edt_two, edt_three, edt_four;
    private EditText[] editTexts;
    private RequestQueue rQueue;

    String overall,professional,skillrating,behaviourrating,satisfactionrating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_c_omplete_diglog);
        session = new Session(getApplicationContext());
        userid = session.getUserId();
        if (getIntent() != null) {
            bookingid = getIntent().getStringExtra("id");
            vendorid = getIntent().getStringExtra("vendorid");

        }
        btnverify = findViewById(R.id.btnverify);
        edt_one = findViewById(R.id.edt_one);
        edt_two = findViewById(R.id.edt_two);
        edt_three = findViewById(R.id.edt_three);
        edt_four = findViewById(R.id.edt_four);
        editTexts = new EditText[]{edt_one, edt_two, edt_three, edt_four};


        edt_one.addTextChangedListener(new PinTextWatcher(0));
        edt_two.addTextChangedListener(new PinTextWatcher(1));
        edt_three.addTextChangedListener(new PinTextWatcher(2));
        edt_four.addTextChangedListener(new PinTextWatcher(3));

        edt_one.setOnKeyListener(new PinOnKeyListener(0));
        edt_two.setOnKeyListener(new PinOnKeyListener(1));
        edt_three.setOnKeyListener(new PinOnKeyListener(2));
        edt_four.setOnKeyListener(new PinOnKeyListener(3));


        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Otpverify();
            }
        });
    }

    private void Otpverify() {
        final String pin_One = edt_one.getText().toString().trim();
        final String pin_Two = edt_two.getText().toString().trim();
        final String pin_three = edt_three.getText().toString().trim();
        final String pin_four = edt_four.getText().toString().trim();

        //  final String pin_Six = edt_pinSix.getText().toString().trim();

        otp = pin_One + pin_Two + pin_three + pin_four;

        if (edt_one.getText().toString().length() == 1) {
            if (edt_two.getText().toString().length() == 1) {
                if (edt_three.getText().toString().length() == 1) {
                    if (edt_four.getText().toString().length() == 1) {


                        String url = BaseUrl + verify_otp_signup_Api;

                        OTP_Matchapi();
                    } else {
                        Toast.makeText(this, "Please enter correct otp.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Please enter correct otp.", Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(this, "Please enter currect otp.", Toast.LENGTH_SHORT).show();

            }

        } else {

            Toast.makeText(this, "Please enter currect otp.", Toast.LENGTH_SHORT).show();

        }
    }

    private void OTP_Matchapi() {

        final ProgressDialog progressDialog = new ProgressDialog(JobCOmpleteDiglog.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Bookingmarkcomplete_by_user_matchOTPvendor;
        AndroidNetworking.post(url)
                .addBodyParameter("otp", otp)
                .addBodyParameter("booking_id", bookingid)
                .addBodyParameter("user_id", userid)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        System.out.println("Bookingmarkcomplete_by_userresponce-  " + jsonObject.toString());
                        progressDialog.dismiss();
                        try {

                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {

                                Toast.makeText(JobCOmpleteDiglog.this, msg, Toast.LENGTH_SHORT).show();

                                BillDetailsddialog();

                            } else {

                                Toast.makeText(JobCOmpleteDiglog.this, msg, Toast.LENGTH_SHORT).show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }

    public class PinTextWatcher implements TextWatcher {

        private final int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }


//    Bookingmarkcomplete_by_user_matchOTPvendor

    public class PinOnKeyListener implements View.OnKeyListener {

        private final int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }





    private void RatingreviewApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + rate_booking_userside,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String result=jsonObject.getString("result");
                            String msg=jsonObject.getString("msg");

                            System.out.println("RatingreviewApi---------    "+jsonObject.toString());
                            Log.e("<><><> ",jsonObject.toString());

                            if (result.equals("true")) {
                                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                                startActivity(intent);
                                finish();

                            } else {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        System.out.println("gkgkgkgkgk"+error);

                     }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",session.getUserId());
                params.put("service_id",service_id);
                params.put("vendor_id",vendorid);
                params.put("Professionalism_rating",professional);
                params.put("Behaviour_rating",behaviourrating);
                params.put("Satisfaction_rating",satisfactionrating);
                params.put("Skills_rating",skillrating);
                params.put("messag","user");


                System.out.println("rating review api ----         "+params);
                return params;





            }
        };

        rQueue = Volley.newRequestQueue(getApplicationContext());
        rQueue.add(stringRequest);

    }



    public void BillDetailsddialog() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(JobCOmpleteDiglog.this);
        View view = LayoutInflater.from(JobCOmpleteDiglog.this).inflate(R.layout.dialog_bookingrating_review, null);

        builder.setCancelable(false);


        final Button btnsubmit = view.findViewById(R.id.btnsubmit);
        final TextView overrating =   view.findViewById(R.id.overrating);
        final  TextView profRatig =   view.findViewById(R.id.profRatig);
        final  TextView servi_rating =   view.findViewById(R.id.servi_rating);
        final  TextView app_inter_rating =   view.findViewById(R.id.app_inter_rating);
        final TextView customer_s_rating =   view.findViewById(R.id.customer_s_rating);


        RatingBar over_rating=view.findViewById(R.id.over_rating);
        RatingBar pro_rating=view.findViewById(R.id.pro_rating);
        RatingBar services_rating=view.findViewById(R.id.services_rating);
        RatingBar app_interface_rating=view.findViewById(R.id.app_interface_rating);
        RatingBar customer_support_rating=view.findViewById(R.id.customer_support_rating);




        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        over_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int noofstars = ratingBar.getNumStars();
                float  pro_getrating = ratingBar.getRating();
                overrating.setText(pro_getrating+"/"+noofstars);

                String   over_rating;


            }
        });
        pro_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                int noofstars = ratingBar.getNumStars();
                float  pro_rating = ratingBar.getRating();
                profRatig.setText(pro_rating+"/"+noofstars);


                professional=String.valueOf(pro_rating);
            }
        });

        services_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int noofstars = ratingBar.getNumStars();
                float  pro_rating = ratingBar.getRating();
                servi_rating.setText(pro_rating+"/"+noofstars);
                skillrating=String.valueOf(pro_rating);
            }
        });


        app_interface_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                int noofstars = ratingBar.getNumStars();
                float  pro_rating = ratingBar.getRating();
                app_inter_rating.setText(pro_rating+"/"+noofstars);

                behaviourrating=String.valueOf(pro_rating);

            }
        });


        customer_support_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int noofstars = ratingBar.getNumStars();
                float  pro_rating = ratingBar.getRating();
                customer_s_rating.setText(pro_rating+"/"+noofstars);

                satisfactionrating=String.valueOf(pro_rating);
            }
        });



        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RatingreviewApi();
            }
        });

    }


}