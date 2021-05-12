package com.satrangolimitless;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.Utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Forgotpassword_Api;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button btn_resetpass;
    EditText edt_phone;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btn_resetpass = findViewById(R.id.btn_resetpass);
        edt_phone = findViewById(R.id.edt_phone);

        btn_resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = edt_phone.getText().toString();
                if (phone.length() < 10) {
                    edt_phone.setError("Enter 10 digit Phone Number");
                    edt_phone.requestFocus();
                    return;
                }
                ForgotPasswordApi();
            }
        });


    }


    private void ForgotPasswordApi() {
        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Forgotpassword_Api,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.v("<>Forgotpassword  ", response.toString());
                        System.out.println("Forgotpassword response ===== " + response.toString());
                        try {

                            progressDialog.dismiss();
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            if (result.equalsIgnoreCase("true")) {

//                                JSONObject obj1 = obj.getJSONObject("data");
//                                String id = obj1.getString("id");

                                Toast.makeText(getApplicationContext(), "Please check your mobile for OTP", Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(ForgotPasswordActivity.this, ForgotpasswordotpActivity.class);
                                intent.putExtra("phone", phone);
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
                params.put("phone", phone);
                System.out.println("params  forgot pass==    " + phone);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}