package com.satrangolimitless.User_UI.Mybooking;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_User_InprogressBooking;
import com.satrangolimitless.R;
import com.satrangolimitless.model.User_InProgressBooking;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Get_InProgress_booking_USER;

public class InprogressBookingFragment extends Fragment {
     View root;
    Session session;
    RecyclerView rec_usr_inpgr;
    String userid;
    ArrayList<User_InProgressBooking> user_inProgressBookings = new ArrayList<>();
    Adapter_User_InprogressBooking adapterUserInprogressBooking;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_user_inprogress_booking, container, false);

        session = new Session(getActivity());
        userid = session.getUserId();

        rec_usr_inpgr = root.findViewById(R.id.rec_usr_inpgr);


        InprogressBookingsApi();

        return root;
    }

    private void InprogressBookingsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Get_InProgress_booking_USER;
        user_inProgressBookings.clear();
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
                                JSONArray jsonArray = jsonObject.getJSONArray("new_booking");
                                System.out.println("User InprogressBookingsApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    User_InProgressBooking allCommunityModel = new User_InProgressBooking(
                                            dataObject.getString("id"),
                                            dataObject.getString("bookingId"),
                                            dataObject.getString("user_id"),
                                            dataObject.getString("vendor_id"),
                                            dataObject.getString("service_id"),
                                            dataObject.getString("booking_date"),
                                            dataObject.getString("from_time"),
                                            dataObject.getString("to_time"),
                                            dataObject.getString("start_location1"),
                                            dataObject.getString("to_location1"),
                                            dataObject.getString("end_location1"),
                                            dataObject.getString("to_loaction2"),
                                            dataObject.getString("to_location3"),
                                            dataObject.getString("work_description"),
                                            dataObject.getString("upload_doc"),
                                            dataObject.getString("job_estimate"),
                                            dataObject.getString("weight"),
                                            dataObject.getString("date_time"),
                                            dataObject.getString("job_status"),
                                            dataObject.getString("cancel_by"),
                                            dataObject.getString("reason"),
                                            dataObject.getString("type"),
                                            dataObject.getString("booking_type"),
                                            dataObject.getString("otp"),
                                            dataObject.getString("verify_otp"),
                                            dataObject.getString("start_time"),
                                            dataObject.getString("paused_time"),
                                            dataObject.getString("resume_time"),
                                            dataObject.getString("complete_time"),
                                            dataObject.getString("modified_date"),
                                            dataObject.getString("amount"),
                                            dataObject.getString("extra_charge"),
                                            dataObject.getString("extra_material"),
                                            dataObject.getString("payment_status"),
                                            dataObject.getString("min_charge"),
                                            dataObject.getString("v_name"),
                                            dataObject.getString("v_contact"),
                                            dataObject.getString("v_address"),
                                            dataObject.getString("v_image"),
                                            dataObject.getString("user_name"),
                                            dataObject.getString("user_contact"),
                                            dataObject.getString("address"),
                                            dataObject.getString("user_image"),
                                            dataObject.getString("user_rating"),
                                            dataObject.getString("extra_charge_status"),
                                            dataObject.getString("appropriate_status"),
                                            dataObject.getString("mark_complete"),
                                            dataObject.getString("vendor_doc")

                                    );
                                    user_inProgressBookings.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapterUserInprogressBooking = new Adapter_User_InprogressBooking(user_inProgressBookings, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_usr_inpgr.setLayoutManager(mLayoutManger);
                            rec_usr_inpgr.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_usr_inpgr.setItemAnimator(new DefaultItemAnimator());
                            rec_usr_inpgr.setAdapter(adapterUserInprogressBooking);
                            adapterUserInprogressBooking.notifyDataSetChanged();
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





/*
*   "id": "6",
    "bookingId": "10000006",
    "user_id": "55",
    "vendor_id": "66",
    "service_id": "0",
    "booking_date": "2021-04-05",
    "from_time": "2:6PM",
    "to_time": "4:6PM",
    "start_location1": "",
    "to_location1": "",
    "end_location1": "",
    "to_loaction2": "",
    "to_location3": "",
    "work_description": "hi i want ac repair please belp",
    "upload_doc": "d4b6799862c4c7b0be9b5bfd2814abdb.jpeg",
    "job_estimate": "3Days",
    "weight": "",
    "date_time": "2021-04-05 04:39:05",
    "job_status": "Inprogress",
    "cancel_by": "",
    "reason": "",
    "type": "blue collar",
    "booking_type": "Book for now",
    "otp": "4828",
    "verify_otp": "0",
    "start_time": "17:53:00",
    "paused_time": "00:00:00",
    "resume_time": "00:00:00",
    "complete_time": "19:51:00",
    "modified_date": "0000-00-00 00:00:00",
    "amount": "0.00",
    "extra_charge": "0.00",
    "extra_material": "",
    "payment_status": "Unpaid",
    "min_charge": "150.00",
    "v_name": "sanjay",
    "v_contact": "7000641818",
    "v_address": "Bombay Hospital Square",
    "v_image": "users.png",
    "user_name": "Pinkee",
    "user_contact": "9074652551",
    "address": "Bombay Hospital Square",
    "user_image": "users.png",
    "user_rating": "3.00"*/