package com.satrangolimitless.Vendor_UI.Vendor_Settings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_Complaintrequests;
import com.satrangolimitless.Adapter.Adapter_Expiringsoon_offers;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Complaintsrequestmodel;
import com.satrangolimitless.model.Offers_expiringsoon_model;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.get_expiry_soon;
import static com.satrangolimitless.Utils.Base_Url.getcomplaints;

public class Vendor_mycomplaintsandrequest extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Session_vendor session;
    String userid;
    Button btnsbmit, btnbk;
    ImageView img_back;
    Fragment fragment;
    RecyclerView reccomplaints;
    ArrayList<Complaintsrequestmodel> complaintsrequestmodels = new ArrayList<>();
    Adapter_Complaintrequests adapter_complaintrequests;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue rQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_vendor_mycomplaints, container, false);
        session = new Session_vendor(getActivity());
userid=session.getUserId();

        img_back = root.findViewById(R.id.img_back);
        reccomplaints = root.findViewById(R.id.reccomplaints);

//        CallComplaintsApi();

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


        Complaintrequest();
        return root;

    }


    private void Complaintrequest() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + getcomplaints;
        System.out.println("Complaintrequest-------   "   );
        complaintsrequestmodels.clear();
        AndroidNetworking.post(url)
            .addBodyParameter("user_id",userid)
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
                                System.out.println("Complaintrequest-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Complaintsrequestmodel allCommunityModel = new Complaintsrequestmodel(
                                            dataObject.getString("id"),
                                            dataObject.getString("user_id"),
                                            dataObject.getString("request_id"),
                                            dataObject.getString("reason"),
                                            dataObject.getString("message"),
                                            dataObject.getString("type"),
                                            dataObject.getString("status"),
                                            dataObject.getString("created_at"));
                                    complaintsrequestmodels.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_complaintrequests = new Adapter_Complaintrequests(complaintsrequestmodels, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            reccomplaints.setLayoutManager(mLayoutManger);
                            reccomplaints.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

                            reccomplaints.setItemAnimator(new DefaultItemAnimator());
                            reccomplaints.setAdapter(adapter_complaintrequests);
                            adapter_complaintrequests.notifyDataSetChanged();
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


}
