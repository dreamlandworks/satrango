package com.satrangolimitless;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import static com.satrangolimitless.Utils.Base_Url.Forgotpassword_Api;
import static com.satrangolimitless.Utils.Base_Url.Resetpassword_Api;

public class Activity_reset_password extends AppCompatActivity {
    Button btn_resetpass, Loginsucess;
    String password, user_id, cnfpassword,otp;
    EditText  edt_pass, edt_cnfpass;
    Session session;
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        edt_pass = findViewById(R.id.edt_pass);
        edt_cnfpass = findViewById(R.id.edt_cnfpass);
        session = new Session(getApplicationContext());
        user_id = session.getUserId();
if (getIntent()!=null){
    otp = getIntent().getStringExtra("otp");

}

        findViewById(R.id.btn_resetpass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               password = edt_pass.getText().toString();
                cnfpassword = edt_cnfpass.getText().toString();




                if (Utils.isInternetConnected(Activity_reset_password.this)) {

                    CallApi();
                } else {
                    Toast.makeText(Activity_reset_password.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /*
    Set password api-------------------
     */
    public void CallApi() {

        final ProgressDialog progressDialog = new ProgressDialog(Activity_reset_password.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Resetpassword_Api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println("Forgotpassword_Api-------    " + jsonObject.toString());
                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                Toast.makeText(Activity_reset_password.this, msg, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Activity_reset_password.this, LoginActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Activity_reset_password.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_reset_password.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", otp);
                params.put("new_password", password);
                params.put("confim_password", cnfpassword);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(Activity_reset_password.this);
        rQueue.add(stringRequest);

    }


}