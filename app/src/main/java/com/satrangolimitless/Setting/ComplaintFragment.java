package com.satrangolimitless.Setting;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Settings.SettingsFragment;
import com.satrangolimitless.session.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.complaints;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Session session;
    Button btnsubmit, btnback, idbtn1, idbtn2, idbtn3, idbtn4, idbtn5;
    ImageView iv_back;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue rQueue;
    Fragment fragment;
    String reson,message="";
    EditText et_suggestionmsg;


    public ComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintFragment newInstance(String param1, String param2) {
        ComplaintFragment fragment = new ComplaintFragment();
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
        View root = inflater.inflate(R.layout.fragment_complaint, container, false);
        LandingActivity.Layout_hader.setVisibility(View.GONE);

        session = new Session(getActivity());

        btnsubmit = root.findViewById(R.id.btnsubmit);
        btnback = root.findViewById(R.id.btnback);
        iv_back = root.findViewById(R.id.iv_back);
        et_suggestionmsg = root.findViewById(R.id.et_suggestionmsg);
        idbtn1 = root.findViewById(R.id.idbtn1);
        idbtn2 = root.findViewById(R.id.idbtn2);
        idbtn3 = root.findViewById(R.id.idbtn3);
        idbtn4 = root.findViewById(R.id.idbtn4);
        idbtn5 = root.findViewById(R.id.idbtn5);

//
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


        btnback.setOnClickListener(new View.OnClickListener() {
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

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=et_suggestionmsg.getText().toString();
                CallComplaintsApi();
            }
        });


        idbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn1.getText().toString();
                idbtn1.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn2.setBackgroundResource(R.color.white);

                idbtn3.setBackgroundResource(R.color.white);

                idbtn4.setBackgroundResource(R.color.white);
                idbtn5.setBackgroundResource(R.color.white);

            }
        });
        idbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn2.getText().toString();
                idbtn2.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn1.setBackgroundResource(R.color.white);

                idbtn3.setBackgroundResource(R.color.white);

                idbtn4.setBackgroundResource(R.color.white);
                idbtn5.setBackgroundResource(R.color.white);

            }
        });
        idbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn3.getText().toString();
                idbtn3.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn1.setBackgroundResource(R.color.white);

                idbtn2.setBackgroundResource(R.color.white);

                idbtn4.setBackgroundResource(R.color.white);
                idbtn5.setBackgroundResource(R.color.white);
            }
        });
        idbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn4.getText().toString();
                idbtn4.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn1.setBackgroundResource(R.color.white);

                idbtn3.setBackgroundResource(R.color.white);

                idbtn2.setBackgroundResource(R.color.white);
                idbtn5.setBackgroundResource(R.color.white);
            }
        });
        idbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reson=idbtn5.getText().toString();
                idbtn5.setBackgroundResource(R.color.yellow);
//                idbtn1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                idbtn1.setBackgroundResource(R.color.white);

                idbtn3.setBackgroundResource(R.color.white);

                idbtn4.setBackgroundResource(R.color.white);
                idbtn2.setBackgroundResource(R.color.white);
            }
        });

        return root;

    }

    private void CallComplaintsApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + complaints,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");
                            Log.e("<><><>namiikdnjcdshnj", jsonObject.toString());
                            if (result.equals(true)) {

                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();

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
                params.put("user_id", session.getUserId());
                params.put("reason", reson);
                params.put("message", message);
                params.put("type", "user");
                return params;
            }
        };

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);

    }

}
