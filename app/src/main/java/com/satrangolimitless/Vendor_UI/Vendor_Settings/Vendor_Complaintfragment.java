package com.satrangolimitless.Vendor_UI.Vendor_Settings;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.complaints;

public class Vendor_Complaintfragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue rQueue;
    Session_vendor session;
    String message = "", reason = "";
    Button btnsbmit, btnbk;
    ImageView img_back;
    Fragment fragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_vendor_complaint, container, false);
        session = new Session_vendor(getActivity());
        btnsbmit = root.findViewById(R.id.btnsbmit);
        btnbk = root.findViewById(R.id.btnbk);
        img_back = root.findViewById(R.id.img_back);

        final Button bookingBtn = root.findViewById(R.id.bookingBtn);
        final Button fundsTransferBtn = root.findViewById(R.id.fundTransferBtn);
        final Button bugsBtn = root.findViewById(R.id.bugsBtn);
        final Button userBtn = root.findViewById(R.id.userBtn);
        final Button othersBtn = root.findViewById(R.id.othersBtn);

        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = bookingBtn.getText().toString().trim();
                bookingBtn.setBackgroundResource(R.drawable.acceptbackground);
                bookingBtn.setTextColor(Color.parseColor("#ffffff"));
                fundsTransferBtn.setTextColor(Color.parseColor("#55b537"));
                fundsTransferBtn.setBackgroundResource(R.drawable.greenborderbutton);
                bugsBtn.setTextColor(Color.parseColor("#55b537"));
                bugsBtn.setBackgroundResource(R.drawable.greenborderbutton);
                userBtn.setTextColor(Color.parseColor("#55b537"));
                userBtn.setBackgroundResource(R.drawable.greenborderbutton);
                othersBtn.setTextColor(Color.parseColor("#55b537"));
                othersBtn.setBackgroundResource(R.drawable.greenborderbutton);
            }
        });
        fundsTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = fundsTransferBtn.getText().toString().trim();
                fundsTransferBtn.setBackgroundResource(R.drawable.acceptbackground);
                fundsTransferBtn.setTextColor(Color.parseColor("#ffffff"));
                bookingBtn.setTextColor(Color.parseColor("#55b537"));
                bookingBtn.setBackgroundResource(R.drawable.greenborderbutton);
                bugsBtn.setTextColor(Color.parseColor("#55b537"));
                bugsBtn.setBackgroundResource(R.drawable.greenborderbutton);
                userBtn.setTextColor(Color.parseColor("#55b537"));
                userBtn.setBackgroundResource(R.drawable.greenborderbutton);
                othersBtn.setTextColor(Color.parseColor("#55b537"));
                othersBtn.setBackgroundResource(R.drawable.greenborderbutton);
            }
        });
        bugsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = bugsBtn.getText().toString().trim();
                bugsBtn.setBackgroundResource(R.drawable.acceptbackground);
                bugsBtn.setTextColor(Color.parseColor("#ffffff"));
                fundsTransferBtn.setTextColor(Color.parseColor("#55b537"));
                fundsTransferBtn.setBackgroundResource(R.drawable.greenborderbutton);
                bookingBtn.setTextColor(Color.parseColor("#55b537"));
                bookingBtn.setBackgroundResource(R.drawable.greenborderbutton);
                userBtn.setTextColor(Color.parseColor("#55b537"));
                userBtn.setBackgroundResource(R.drawable.greenborderbutton);
                othersBtn.setTextColor(Color.parseColor("#55b537"));
                othersBtn.setBackgroundResource(R.drawable.greenborderbutton);
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = userBtn.getText().toString().trim();
                userBtn.setBackgroundResource(R.drawable.acceptbackground);
                userBtn.setTextColor(Color.parseColor("#ffffff"));
                fundsTransferBtn.setTextColor(Color.parseColor("#55b537"));
                fundsTransferBtn.setBackgroundResource(R.drawable.greenborderbutton);
                bugsBtn.setTextColor(Color.parseColor("#55b537"));
                bugsBtn.setBackgroundResource(R.drawable.greenborderbutton);
                bookingBtn.setTextColor(Color.parseColor("#55b537"));
                bookingBtn.setBackgroundResource(R.drawable.greenborderbutton);
                othersBtn.setTextColor(Color.parseColor("#55b537"));
                othersBtn.setBackgroundResource(R.drawable.greenborderbutton);
            }
        });
        othersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = othersBtn.getText().toString().trim();
                othersBtn.setBackgroundResource(R.drawable.acceptbackground);
                othersBtn.setTextColor(Color.parseColor("#ffffff"));
                fundsTransferBtn.setTextColor(Color.parseColor("#55b537"));
                fundsTransferBtn.setBackgroundResource(R.drawable.greenborderbutton);
                bugsBtn.setTextColor(Color.parseColor("#55b537"));
                bugsBtn.setBackgroundResource(R.drawable.greenborderbutton);
                userBtn.setTextColor(Color.parseColor("#55b537"));
                userBtn.setBackgroundResource(R.drawable.greenborderbutton);
                bookingBtn.setTextColor(Color.parseColor("#55b537"));
                bookingBtn.setBackgroundResource(R.drawable.greenborderbutton);
            }
        });
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

        btnbk.setOnClickListener(new View.OnClickListener() {
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

        btnsbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView messageTV = root.findViewById(R.id.et_suggestionmsg);
                message = messageTV.getText().toString().trim();
                CallComplaintsApi();
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
                            if (result.equals("true")) {
                                final Dialog dialog = new Dialog(requireContext());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_complaint_success);
                                Window window = dialog.getWindow();
                                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                ImageView closeIvBtn = dialog.findViewById(R.id.closeBtn);
                                Button closeBtn = dialog.findViewById(R.id.btn_no);
                                closeBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                closeIvBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
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
                params.put("reason", reason);
                params.put("message", message);
                params.put("type", "vendor");
                return params;
            }
        };

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);

    }

}
