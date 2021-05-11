package com.satrangolimitless.Vendor_UI.Vendor_MyBooking;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.Adapter.Adapter_Vendor_CompletedBooking;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Vendor_CompletedBooking;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.ShowBookingcomplete_reject_bookingbothside;
import static com.satrangolimitless.Utils.Base_Url.View_Vendor_reject_bookings;

public class Vendor_CompletedrejectedBookingFragment extends Fragment {
    View root;
    Session_vendor session_vendor;
    RecyclerView rec_ven_complete;
    String vendor_id;
    ArrayList<Vendor_CompletedBooking> vendor_completedBookings = new ArrayList<>();
    Adapter_Vendor_CompletedBooking adapter_vendor_completedBooking;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_vendorcompleted_booking, container, false);
        session_vendor = new Session_vendor(getActivity());
        vendor_id = session_vendor.getUserId();

        rec_ven_complete = root.findViewById(R.id.rec_ven_complete);

//        Intent intent = new Intent(getActivity(), ResheduledActivity.class);
//        startActivity(intent);

//        Intent intent = new Intent(getActivity(), BookingDetailsActivity.class);
//        startActivity(intent);

        CompleterejectBookingsApi();
        return root;
    }


//----------------------------------------Vendor Upcoming Bookings api


    private void CompleterejectBookingsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + ShowBookingcomplete_reject_bookingbothside;
        vendor_completedBookings.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("id", vendor_id)
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
                                System.out.println("CompleterejectBookingsApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_CompletedBooking allCommunityModel = new Vendor_CompletedBooking(
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
                                            dataObject.getString("vendor_doc"),
                                            dataObject.getString("appropriate_status"),
                                            dataObject.getString("extra_charge_status"),
                                            dataObject.getString("min_charge"),
                                            dataObject.getString("v_name"),
                                            dataObject.getString("v_contact"),
                                            dataObject.getString("v_address"),
                                            dataObject.getString("v_image"),
                                            dataObject.getString("user_name"),
                                            dataObject.getString("user_contact"),
                                            dataObject.getString("address"),
                                            dataObject.getString("user_image"),
                                            dataObject.getString("user_rating")

                                    );
                                    vendor_completedBookings.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_vendor_completedBooking = new Adapter_Vendor_CompletedBooking(vendor_completedBookings, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_ven_complete.setLayoutManager(mLayoutManger);
                            rec_ven_complete.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_ven_complete.setItemAnimator(new DefaultItemAnimator());
                            rec_ven_complete.setAdapter(adapter_vendor_completedBooking);
                            adapter_vendor_completedBooking.notifyDataSetChanged();

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


/*  "id": "1",
    "bookingId": "10000001",
    "user_id": "14",
    "vendor_id": "66",
    "service_id": "0",
    "booking_date": "2021-03-30",
    "from_time": "11:42AM",
    "to_time": "3:42PM",
    "start_location1": "Bombay Hospital Square",
    "to_location1": "",
    "end_location1": "",
    "to_loaction2": "",
    "to_location3": "",
    "work_description": "single hi ",
    "upload_doc": "ff494bf2faa4d4fcbad49f2e22697ae7.jpg",
    "job_estimate": "",
    "weight": "",
    "date_time": "2021-03-29 22:38:54",
    "job_status": "Cancel",
    "cancel_by": "vendor",
    "reason": "User not responding properly",
    "type": "single move",
    "booking_type": "Book for now",
    "otp": "7894",
    "verify_otp": "0",
    "start_time": "00:00:00",
    "paused_time": "00:00:00",
    "resume_time": "00:00:00",
    "complete_time": "00:00:00",
    "modified_date": "0000-00-00 00:00:00",
    "amount": "0.00",
    "extra_charge": "0.00",
    "extra_material": "",
    "payment_status": "Unpaid",
    "vendor_doc": "",
    "appropriate_status": "0",
    "extra_charge_status": "0",
    "min_charge": "150.00",
    "v_name": "sanjay",
    "v_contact": "7000641818",
    "v_address": "Bombay Hospital Square",
    "v_image": "users.png",
    "user_name": "anish",
    "user_contact": "9406683232",
    "address": "Bombay Hospital Square",
    "user_image": "Screenshot_20210216_210513_com_instagram_android.jpg",
    "user_rating": "3.00"*/