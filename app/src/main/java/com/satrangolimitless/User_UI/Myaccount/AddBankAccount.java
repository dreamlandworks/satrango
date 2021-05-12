package com.satrangolimitless.User_UI.Myaccount;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.Add_Bank_account;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;

public class AddBankAccount extends AppCompatActivity {

EditText edt_name,edtaccno,edtcnfaccno,edtifsc;
Button bnsubmit;
String ifsc,cnfaccno,accno,name,userid,id;
Session session;
    Dialog dialogpleasewait;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbank_account);
        edt_name=findViewById(R.id.edt_name);
        edtaccno=findViewById(R.id.edtaccno);
        edtcnfaccno=findViewById(R.id.edtcnfaccno);
        edtifsc=findViewById(R.id.edtifsc);
        bnsubmit=findViewById(R.id.bnsubmit);

        session=new Session(getApplicationContext());
        userid=session.getUserId();

        bnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    name = edt_name.getText().toString();
                    accno = edtaccno.getText().toString();
                    cnfaccno = edtcnfaccno.getText().toString();
                    ifsc = edtifsc.getText().toString();

                if (name.isEmpty()) {
                    edt_name.setError("Enter Name");
                    edt_name.requestFocus();
                    return;
                }
               else if (accno.isEmpty()) {
                    edtaccno.setError("Enter Account no.");
                    edtaccno.requestFocus();
                    return;
                }
               else if (accno.length() < 9) {
                    edtaccno.setError("Account no. must min. 9 digit");
                    edtaccno.requestFocus();
                    return;
                }
                else  if (cnfaccno.isEmpty()) {
                    edtcnfaccno.setError("Enter Confirm Account no.");
                    edtcnfaccno.requestFocus();
                    return;
                } else if (cnfaccno.length() < 9) {
                    edtaccno.setError("Account no. must min. 9 digit");
                    edtaccno.requestFocus();
                    return;
                } else  if (!accno.matches(cnfaccno)) {
                    edtcnfaccno.setError("Enter same account no. and confirm acc. no.");
                    edtcnfaccno.requestFocus();
                    return;
                } else  if (ifsc.isEmpty()) {
                    edtifsc.setError("Enter IFSC no.");
                    edtifsc.requestFocus();
                    return;
                } else {
                    AddBankaccount();
                }


            }
        });

    }


    private void AddBankaccount() {
        final ProgressDialog progressDialog = new ProgressDialog(AddBankAccount.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Add_Bank_account, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            System.out.println("AddBankaccount data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            if (result.equalsIgnoreCase("true")) {
                                showreqsuccessdialogue();
                            } else {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
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
                params.put("user_id", userid);
                params.put("holder_name", name);
                params.put("account_number", accno);
                params.put("ifsc_code", ifsc);

                System.out.println(" AddBankaccount param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }



    public void showreqsuccessdialogue() {


        dialogpleasewait= new Dialog(AddBankAccount.this);
        dialogpleasewait.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogpleasewait.setContentView(R.layout.dialogaccount_add_sucess);
        dialogpleasewait.setCancelable(true);
        dialogpleasewait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogpleasewait.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;







        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBackPressed();



            }

        }, 5000);

        dialogpleasewait.show();
         dialogpleasewait.getWindow().setAttributes(lp);

    }




}


/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {





            }

        }, SPLASH_SCREEN_TIME_OUT);

*/