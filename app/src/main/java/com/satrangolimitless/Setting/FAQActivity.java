package com.satrangolimitless.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.R;
import com.satrangolimitless.Utils.Base_Url;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.model.GetFaqModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





public class FAQActivity extends AppCompatActivity {
TextView txt_faq;
List<GetFaqModel>getFaqModels=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);
        txt_faq=findViewById(R.id.txt_faq);

    }





}