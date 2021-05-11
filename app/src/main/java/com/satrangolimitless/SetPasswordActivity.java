package com.satrangolimitless;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.satrangolimitless.Utils.Utils;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Setpassword_Api;

public class SetPasswordActivity extends AppCompatActivity {
Button UserTypes, Loginsucess;
String password,user_id,cnfpassword;
EditText edt_pasone,edt_pastwo;
Session session;
    private RequestQueue rQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        edt_pasone= findViewById(R.id.edt_pasone);
        edt_pastwo= findViewById(R.id.edt_pastwo);
        session=new Session(getApplicationContext());
        user_id=session.getUserId();


        edt_pasone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                edt_pasone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edt_pastwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                edt_pastwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_greencheck, 0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
         findViewById(R.id.submit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edt_pasone.getText().toString().length()<6)
                {
                    edt_pasone.setError("Password must be six digit.");
                    edt_pasone.requestFocus();
                    return;
                }
               else if (edt_pasone.getText().toString().equals(""))
                {
                    edt_pasone.setError("Enter Password.");
                    edt_pasone.requestFocus();
                    return;
                }
                else if (edt_pastwo.getText().toString().equals(""))
                {
                    edt_pastwo.setError("Enter confirm Password.");
                    edt_pastwo.requestFocus();
                    return;
                }
                else if (!edt_pasone.getText().toString().equals(edt_pastwo.getText().toString()))
                {
                    edt_pastwo.setError("please enter same password.");
                    edt_pastwo.requestFocus();
                    return;
                }

                password=edt_pasone.getText().toString();
                cnfpassword=edt_pastwo.getText().toString();

                if (Utils.isInternetConnected(SetPasswordActivity.this)) {

                    CallApi();
                } else {
                    Toast.makeText(SetPasswordActivity.this,  getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

/*
Set password api-------------------
 */
    public void CallApi(){

        final ProgressDialog progressDialog = new ProgressDialog(SetPasswordActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Setpassword_Api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject= new JSONObject(response);
                            System.out.println("Setpassword_Api-------    "+jsonObject.toString());
                            String msg=jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                String id=jsonObject1.getString("id");
                                String name=jsonObject1.getString("fname");


                                session.setLogin(true);


                                showCustomDialog();


                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SetPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SetPasswordActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("password", password);
                params.put("user_id",user_id);
                params.put("cnfpassword", cnfpassword);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(SetPasswordActivity.this);
        rQueue.add(stringRequest);

    }



    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.signupsucessdilog, viewGroup, false);

        Button SC_Login=dialogView.findViewById(R.id.SC_Login);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        SC_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}