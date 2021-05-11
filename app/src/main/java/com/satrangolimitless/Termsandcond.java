package com.satrangolimitless;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.term_condition;

public class Termsandcond extends AppCompatActivity {
    TextView txt_terms;
    private RequestQueue rQueue;
    private Object Base_Url;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_terms_ans_conditinfragment);

        txt_terms= findViewById(R.id.txt_terms);
        iv_back= findViewById(R.id.iv_back);
        CallTermsApi();
    }



    private void CallTermsApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + term_condition,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String msg=jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
                                JSONObject userJson = jsonObject.getJSONObject("data");
                                String terms=userJson.getString("terms");
                                txt_terms.setText(Html.fromHtml(terms));

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
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "user");

                return params;
            }
        };

        rQueue = Volley.newRequestQueue(getApplicationContext());
        rQueue.add(stringRequest);

    }

}