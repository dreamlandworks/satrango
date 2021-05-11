package com.satrangolimitless.Setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Settings.SettingsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.privacy_policy;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Privacyfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Privacyfragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txt_privacypolicy;
    private RequestQueue rQueue;
    ImageView iv_back;
    Fragment fragment;

    public Privacyfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Privacyfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Privacyfragment newInstance(String param1, String param2) {
        Privacyfragment fragment = new Privacyfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View  root=   inflater.inflate(R.layout.fragment_privacyfragment, container, false);
        txt_privacypolicy=root.findViewById(R.id.txt_privacypolicy);
        iv_back=root.findViewById(R.id.iv_back);
        LandingActivity.Layout_hader.setVisibility(View.GONE);
        CallPrivacyApi();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new SettingsFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });
        return root;

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
                                String content=userJson.getString("content");
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
                params.put("type", "user");
                return params;
            }
        };

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);

    }


}