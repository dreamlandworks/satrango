package com.satrangolimitless;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.ResendOTP_Api;
import static com.satrangolimitless.Utils.Base_Url.verify_otp_signup_Api;

public class Otpverification_onLoginActivity extends AppCompatActivity {
Button signIn_OTPButton;
    EditText edt_one,edt_two,edt_three,edt_four;
    String id,otp,phone;
    private EditText[] editTexts;
    TextView txt_resend;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        signIn_OTPButton= findViewById(R.id.signIn_OTPButton);
        edt_one= findViewById(R.id.edt_one);
        edt_two= findViewById(R.id.edt_two);
        edt_three= findViewById(R.id.edt_three);
        edt_four= findViewById(R.id.edt_four);
        txt_resend= findViewById(R.id.txt_resend);

        session=new Session(getApplicationContext());
        id=session.getUserId();
        phone=session.getMobile();

        System.out.println("otp verify-------       "+id);
        editTexts = new EditText[]{edt_one,edt_two,edt_three,edt_four};

        txt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resend_OTP_Api(phone);
            }
        });

        signIn_OTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Otpverify(id);
            }
        });


        edt_one.addTextChangedListener(new PinTextWatcher(0));
        edt_two.addTextChangedListener(new PinTextWatcher(1));
        edt_three.addTextChangedListener(new PinTextWatcher(2));
        edt_four.addTextChangedListener(new PinTextWatcher(3));

        edt_one.setOnKeyListener(new PinOnKeyListener(0));
        edt_two.setOnKeyListener(new PinOnKeyListener(1));
        edt_three.setOnKeyListener(new PinOnKeyListener(2));
        edt_four.setOnKeyListener(new PinOnKeyListener(3));

    }

    private void Otpverify(String id) {
        final String pin_One = edt_one.getText().toString().trim();
        final String pin_Two = edt_two.getText().toString().trim();
        final String pin_three = edt_three.getText().toString().trim();
        final String pin_four = edt_four.getText().toString().trim();

        //  final String pin_Six = edt_pinSix.getText().toString().trim();

        otp = new String(pin_One + pin_Two + pin_three + pin_four  );

        if (edt_one.getText().toString().length() == 1) {
            if (edt_two.getText().toString().length() == 1) {
                if (edt_three.getText().toString().length() == 1) {
                    if (edt_four.getText().toString().length() == 1) {


                        String url = BaseUrl + verify_otp_signup_Api;

                        OTPVerifyApi(url, otp,id);
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

//------------------------------------------------------------------------------------------------------------------------

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
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


    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

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

    //OTPApi-----------------------------------------------------------------------------------

    private void OTPVerifyApi(String url, final String otp, final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(Otpverification_onLoginActivity.this);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v("<>verify_otp  ", response.toString());
                        System.out.println("otp response ===== "+response.toString());
                        try {

                            progressDialog.dismiss();
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            if (result.equalsIgnoreCase("true")) {


                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();



                                Intent intent = new Intent(Otpverification_onLoginActivity.this, Activity_login_type.class);
                                startActivity(intent);
                                finish();

                            } else {

                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("otp", otp);
                params.put("user_id", id);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }



//    Resend OTP---------------------------------------------------------------------------------------------



    private void Resend_OTP_Api(final String phone) {
        final ProgressDialog progressDialog = new ProgressDialog(Otpverification_onLoginActivity.this);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl+ResendOTP_Api,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v("<>verify_otp  ", response.toString());
                        System.out.println("otp response ===== "+response.toString());
                        try {

                            progressDialog.dismiss();
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            if (result.equalsIgnoreCase("true")) {

//                                JSONObject obj1 = obj.getJSONObject("data");
//                                String id = obj1.getString("id");
//                                String fname = obj1.getString("fname");
//                                String lname = obj1.getString("lname");
//                                String email = obj1.getString("email");
//                                String phone = obj1.getString("phone");
//                                String password = obj1.getString("password");
//                                String address = obj1.getString("address");
//                                String roll = obj1.getString("roll");
//                                String verify_otp = obj1.getString("verify_otp");
//                                String lat = obj1.getString("lat");
//                                String lang = obj1.getString("lang");
//                                String status = obj1.getString("status");
//                                String otp = obj1.getString("otp");
//                                String image = obj1.getString("image");
//                                String service_provider = obj1.getString("service_provider");
//                                String email_status = obj1.getString("email_status");
//                                String phone_status = obj1.getString("phone_status");
//                                String created_date = obj1.getString("created_date");
//                                String fcm_id = obj1.getString("fcm_id");
//

                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

//                                session.setLogin(true);
//                                session.setUserId(id);
//                                session.setMobile(phone,email);
//                                session.setEmail(email);
//                                session.setUser_name(fname+lname);
//                                session.setPro_Image(image);
//                                session.setUser_Status(status);
//                                session.setUser_Type(roll);
//                                session.setOtp_Verify(verify_otp);
//                                session.setFCM_Id(fcm_id);




                            } else {

                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);
System.out.println("resend otp ----      "+phone);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}