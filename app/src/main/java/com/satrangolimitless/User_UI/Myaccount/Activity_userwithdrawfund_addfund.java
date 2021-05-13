package com.satrangolimitless.User_UI.Myaccount;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_Expiringsoon_offers;
import com.satrangolimitless.Adapter.Adapter_view_bankaccounts;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.model.Latest_offers_model;
import com.satrangolimitless.model.Offers_expiringsoon_model;
import com.satrangolimitless.model.UserAccountsmodel;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.Add_Bank_account;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.UserAddfunds;
import static com.satrangolimitless.Utils.Base_Url.UserBank_account;
import static com.satrangolimitless.Utils.Base_Url.Userwithdrawfunds;
import static com.satrangolimitless.Utils.Base_Url.get_expiry_soon;

public class Activity_userwithdrawfund_addfund extends AppCompatActivity {

    CardView cd_h,cd_f,cd_onths,cd_tws;
RecyclerView recvaccunts;
Session session;
String userid,money="",withdrawmoney,transid;
EditText edt_depamount,edtwithdrwmoney;
    TextView txtadacount;
    Button btnadfund,btnwithdfunds;
    Dialog dialogpleasewait;
    ArrayList<UserAccountsmodel> userAccountsmodels = new ArrayList<>();
    Adapter_view_bankaccounts adapter_view_bankaccounts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_withdraw);
        session=new Session(getApplicationContext());
        cd_h=findViewById(R.id.cd_h);
        cd_f=findViewById(R.id.cd_f);
        cd_onths=findViewById(R.id.cd_onths);
        cd_tws=findViewById(R.id.cd_tws);

        recvaccunts=findViewById(R.id.recvaccunts);
        userid=session.getUserId();
        txtadacount=findViewById(R.id.txtadacount);
        edt_depamount=findViewById(R.id.edt_depamount);
        edtwithdrwmoney=findViewById(R.id.edtwithdrwmoney);
        btnadfund=findViewById(R.id.btnadfund);
        btnwithdfunds=findViewById(R.id.btnwithdfunds);

        findViewById(R.id.imgback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.txtback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        UserBank_account();
        txtadacount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_userwithdrawfund_addfund.this, AddBankAccount.class);
                startActivity(intent);

            }
        });


        cd_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money="100";
                cd_h.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_f.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_onths.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_tws.setCardBackgroundColor(Color.parseColor("#ffffff"));

            }
        });

        cd_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                money="500";
                cd_f.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_h.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_onths.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_tws.setCardBackgroundColor(Color.parseColor("#ffffff"));


            }
        });
        cd_onths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                money="1000";
                cd_onths .setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_h.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_f.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_tws.setCardBackgroundColor(Color.parseColor("#ffffff"));

            }
        });

        cd_tws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money="2000";
                cd_tws    .setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_h.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_f.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_onths.setCardBackgroundColor(Color.parseColor("#ffffff"));

            }
        });

        btnadfund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_userwithdrawfund_addfund.this, UseraddfundsPaymentActivity.class);
                startActivity(intent);

/*if (money.equalsIgnoreCase("")){
  money=  edt_depamount.getText().toString();
    Addfund();
}else{
    Addfund();
}*/




            }
        });

        btnwithdfunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawmoney=edtwithdrwmoney.getText().toString();
                Withdrawfund();
            }
        });


        edt_depamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money="";
                cd_h.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_f.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_onths.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_tws.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        });



    }



    private void UserBank_account() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_userwithdrawfund_addfund.this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + UserBank_account;
        userAccountsmodels.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", userid)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                System.out.println("UserBank_accountApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    UserAccountsmodel allCommunityModel = new UserAccountsmodel(
                                            dataObject.getString("id"),
                                            dataObject.getString("user_id"),
                                            dataObject.getString("holder_name"),
                                            dataObject.getString("account_number"),
                                            dataObject.getString("ifsc_code"),
                                            dataObject.getString("created_date"));
                                    userAccountsmodels.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_view_bankaccounts = new Adapter_view_bankaccounts(userAccountsmodels, getApplicationContext());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
                            recvaccunts.setLayoutManager(mLayoutManger);
                            recvaccunts.setLayoutManager(new LinearLayoutManager(Activity_userwithdrawfund_addfund.this, RecyclerView.HORIZONTAL, false));
                            recvaccunts.setItemAnimator(new DefaultItemAnimator());
                            recvaccunts.setAdapter(adapter_view_bankaccounts);
                            adapter_view_bankaccounts.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_my_join", anError.toString());
                    }
                });
    }





    private void Addfund() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_userwithdrawfund_addfund.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + UserAddfunds
                ,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("UserAddfunds data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");

                            if (result.equalsIgnoreCase("true")) {


                            } else {
                                Toast.makeText(Activity_userwithdrawfund_addfund.this, msg, Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                            Toast.makeText(Activity_userwithdrawfund_addfund.this, e.toString(), Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();

                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Activity_userwithdrawfund_addfund.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userid);
                params.put("deposit_money", money);


                System.out.println(" Activity_userwithdrawfund_addfund param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void Withdrawfund() {
        final ProgressDialog progressDialog = new ProgressDialog(Activity_userwithdrawfund_addfund.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + Userwithdrawfunds,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            //converting response to json object
                            System.out.println("Userwithdrawfunds data----  " + response);
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            transid = obj.getString("data");
                            if (result.equalsIgnoreCase("true")) {

                                showreqsuccessdialogue();
                            } else {
                                Toast.makeText(Activity_userwithdrawfund_addfund.this, msg, Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {

                            Toast.makeText(Activity_userwithdrawfund_addfund.this, e.toString(), Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();

                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Activity_userwithdrawfund_addfund.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userid);
                params.put("withdarw_amount", withdrawmoney);


                System.out.println(" Userwithdrawfunds param-      " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }



    public void showreqsuccessdialogue() {


        dialogpleasewait= new Dialog(Activity_userwithdrawfund_addfund.this);
        dialogpleasewait.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogpleasewait.setContentView(R.layout.dialogwithdrawlrequestsuccess);
        dialogpleasewait.setCancelable(true);
        dialogpleasewait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogpleasewait.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;


        final Button btn_yes = (Button) dialogpleasewait.findViewById(R.id.btn_yes);
        final TextView txtdiasucc = (TextView) dialogpleasewait.findViewById(R.id.txtdiasucc);

            txtdiasucc.setText("Your Withdrawl Request Submitted with No. "+transid);
        
        
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogpleasewait.dismiss();
            }
        });
        dialogpleasewait.getWindow().setAttributes(lp);
        dialogpleasewait.show();
    }



}
