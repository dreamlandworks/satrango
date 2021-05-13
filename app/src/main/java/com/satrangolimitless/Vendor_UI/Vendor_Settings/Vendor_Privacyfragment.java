package com.satrangolimitless.Vendor_UI.Vendor_Settings;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.Setting.Privacyfragment;
import com.satrangolimitless.User_UI.Settings.SettingsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.privacy_policy;

public class Vendor_Privacyfragment extends Fragment {


    private String mParam1;
    private String mParam2;
    TextView txt_privacypolicy;
    private RequestQueue rQueue;
    ImageView iv_back;
    Fragment fragment;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  root=   inflater.inflate(R.layout.fragment_vendor_privacyfragment, container, false);
        txt_privacypolicy=root.findViewById(R.id.txt_privacypolicy);
        iv_back=root.findViewById(R.id.img_back);
        LandingActivity_Service_provider.Layout_hader.setVisibility(View.GONE);
        CallPrivacyApi();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performBackAction();
            }
        });
        root.findViewById(R.id.txt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performBackAction();
            }
        });
        return root;

    }

    private void performBackAction() {
        fragment = new Vendor_SettingsFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame, fragment)
                .addToBackStack("csds").commit();
    }

    private void CallPrivacyApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + privacy_policy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String msg=jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
                                JSONObject userJson = jsonObject.getJSONObject("data");
                                String content=userJson.getString("terms");
                                txt_privacypolicy.setText(Html.fromHtml(content));

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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "vendor");
                return params;
            }
        };

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);

    }


}