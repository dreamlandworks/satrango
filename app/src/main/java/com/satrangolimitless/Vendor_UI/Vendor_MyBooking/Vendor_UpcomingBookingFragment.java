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
import com.satrangolimitless.Adapter.Adapter_Vendor_UpcomingBooking;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Confirm_Bid;
import com.satrangolimitless.model.Vendor_UpcomingBooking;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Get_upcoming_booking_Vendor;


public class Vendor_UpcomingBookingFragment extends Fragment {
    View root;
    Session_vendor session_vendor;
    RecyclerView rec_ven_upc;
    String vendor_id;
    ArrayList<Vendor_UpcomingBooking> vendor_upcomingBookings = new ArrayList<>();
    ArrayList<Confirm_Bid> confirm_bidArrayList = new ArrayList<>();
    Adapter_Vendor_UpcomingBooking adapter_vendor_upcomingBooking;

    public static Vendor_UpcomingBookingFragment newInstance(String param1, String param2) {
        Vendor_UpcomingBookingFragment fragment = new Vendor_UpcomingBookingFragment();

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_vendorupcoming_booking, container, false);
        session_vendor = new Session_vendor(getActivity());
        vendor_id = session_vendor.getUserId();

        rec_ven_upc = root.findViewById(R.id.rec_ven_upc);

//        Intent intent = new Intent(getActivity(), ResheduledActivity.class);
//        startActivity(intent);

//        Intent intent = new Intent(getActivity(), BookingDetailsActivity.class);
//        startActivity(intent);

        UpcomingBookingsApi();
        return root;
    }


//----------------------------------------Vendor Upcoming Bookings api


    private void UpcomingBookingsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Get_upcoming_booking_Vendor;
        vendor_upcomingBookings.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vendor_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {

                            System.out.println("REsponse---------  "+jsonObject.toString());
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("new_booking");
                                JSONArray jsonArray2 = jsonObject.getJSONArray("confirm_bid");
                                System.out.println("UpcomingBookingsApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_UpcomingBooking allCommunityModel = new Vendor_UpcomingBooking(
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
                                            dataObject.getString("min_charge"),
                                            dataObject.getString("user_name"),
                                            dataObject.getString("user_contact"),
                                            dataObject.getString("address"),
                                            dataObject.getString("user_image"),
                                            dataObject.getString("user_rating")
                                    );
                                    vendor_upcomingBookings.add(allCommunityModel);
                                }

                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    Log.e("jsonarray2", jsonArray2.toString());
                                    JSONObject dataObject2 = jsonArray2.getJSONObject(i);
                                    Confirm_Bid confirm_bid = new Confirm_Bid(
                                            dataObject2.getString("id"),
                                            dataObject2.getString("job_id"),
                                            dataObject2.getString("vendor_id"),
                                            dataObject2.getString("bid_amount"),
                                            dataObject2.getString("proposal"),
                                            dataObject2.getString("estimated_time"),
                                            dataObject2.getString("attachment"),
                                            dataObject2.getString("submission_type"),
                                            dataObject2.getString("accepted_by"),
                                            dataObject2.getString("reject_by"),
                                            dataObject2.getString("status"),
                                            dataObject2.getString("modify_date"),
                                            dataObject2.getString("user_name"),
                                            dataObject2.getString("phone")
                                    );
                                    confirm_bidArrayList.add(confirm_bid);

                                }



                            } else {

                            }
                            adapter_vendor_upcomingBooking = new Adapter_Vendor_UpcomingBooking(vendor_upcomingBookings, confirm_bidArrayList,getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_ven_upc.setLayoutManager(mLayoutManger);
                            rec_ven_upc.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_ven_upc.setItemAnimator(new DefaultItemAnimator());
                            rec_ven_upc.setAdapter(adapter_vendor_upcomingBooking);
                            adapter_vendor_upcomingBooking.notifyDataSetChanged();
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








    /*"id": "39",
      "bookingId": "10000039",
      "user_id": "55",
      "vendor_id": "66",
      "service_id": "0",
      "booking_date": "2021-04-30",
      "from_time": "2:6PM",
      "to_time": "4:6PM",
      "start_location1": "",
      "to_location1": "",
      "end_location1": "",
      "to_loaction2": "",
      "to_location3": "",
      "work_description": "test notif",
      "upload_doc": "f36d68afe537df6a55ec7ea15d4f5342.jpg",
      "job_estimate": "2Hours",
      "weight": "",
      "date_time": "2021-04-12 04:09:42",
      "job_status": "Upcoming",
      "cancel_by": "",
      "reason": "",
      "type": "blue collar",
      "booking_type": "Book for now",
      "otp": "5165",
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
      "min_charge": "150.00",
      "user_name": "Pinkee",
      "user_contact": "9074652551",
      "address": "Bombay Hospital Square",
      "user_image": "users.png",
      "user_rating": "3.00"*/

}