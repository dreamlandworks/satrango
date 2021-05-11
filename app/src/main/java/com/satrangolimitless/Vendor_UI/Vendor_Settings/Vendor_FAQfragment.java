package com.satrangolimitless.Vendor_UI.Vendor_Settings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.FAQ.FaqAdapter;
import com.satrangolimitless.User_UI.FAQ.FaqFragment;
import com.satrangolimitless.User_UI.Settings.SettingsFragment;
import com.satrangolimitless.Utils.VolleySingleton;
import com.satrangolimitless.model.GetFaqModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.faq;

public class Vendor_FAQfragment extends Fragment {
    View view;
    TextView question1,ans1,question3,ans3,ans4,question4,question5,question6;
    LinearLayout ans5,ans6;
    CardView downloadapp;
    TextView tv1,tv_11;
    private boolean isclick1 = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<GetFaqModel> getFaqModels=new ArrayList<>();
    RecyclerView recyclerview;
    FaqAdapter adapter;
    ImageView img_back;
    Fragment fragment;


    public static FaqFragment newInstance(String param1, String param2) {
        FaqFragment fragment = new FaqFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);


        view = inflater.inflate(R.layout.fragment_vendor_faq, container, false);
        question1= view.findViewById(R.id.question1);
        ans1= view.findViewById(R.id.ans1);
        LandingActivity_Service_provider.Layout_hader.setVisibility(View.GONE);
        recyclerview= view.findViewById(R.id.recyclerview);
        img_back= view.findViewById(R.id.img_back);



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new Vendor_SettingsFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });

        CallTxtfaqApi();



        /* question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isclick1) {
                    ans1.setVisibility(View.GONE);

                    question1.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand, //left
                            0);

                    isclick1 = false;
                } else {
                    ans1.setVisibility(View.VISIBLE);

                    question1.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand_less, //left
                            0);
                    isclick1 = true;
                }
            }
        });*/


       /* tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isclick1) {
                    tv_11.setVisibility(View.GONE);

                    tv1.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand, //left
                            0);

                    isclick1 = false;
                } else {
                    tv_11.setVisibility(View.VISIBLE);

                    tv1.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand_less, //left
                            0);
                    isclick1 = true;
                }
            }
        });*/

       /* question3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isclick1) {
                    ans3.setVisibility(View.GONE);

                    question3.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand, //left
                            0);

                    isclick1 = false;
                } else {
                    ans3.setVisibility(View.VISIBLE);

                    question3.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand_less, //left
                            0);
                    isclick1 = true;
                }
            }
        });*/

       /* question4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isclick1) {
                    ans4.setVisibility(View.GONE);

                    question4.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand, //left
                            0);

                    isclick1 = false;
                } else {
                    ans4.setVisibility(View.VISIBLE);

                    question4.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand_less, //left
                            0);
                    isclick1 = true;
                }
            }
        });*/

       /* question5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isclick1) {
                    ans5.setVisibility(View.GONE);

                    question5.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand, //left
                            0);

                    isclick1 = false;
                } else {
                    ans5.setVisibility(View.VISIBLE);

                    question5.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand_less, //left
                            0);
                    isclick1 = true;
                }
            }
        });*/
        return view;

    }

    private void CallTxtfaqApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait..");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + faq,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            String msg = obj.getString("msg");
                            System.out.println("hkhkhkkhkkh"+obj.toString());
                            if (result.equals("true")) {
                                JSONArray jsonArray = obj.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    GetFaqModel hero = new GetFaqModel(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("question"),
                                            jsonObject.getString("answer"),
                                            jsonObject.getString("status")
                                    );
                                    getFaqModels.add(hero);
                                    adapter = new FaqAdapter(getFaqModels, getActivity());
                                    RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                    recyclerview.setLayoutManager(mLayoutManger);
                                    recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, true));
                                    recyclerview.setItemAnimator(new DefaultItemAnimator());
                                    recyclerview.setAdapter(adapter);
                                }
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
                        progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", "vendor");
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest); }
}