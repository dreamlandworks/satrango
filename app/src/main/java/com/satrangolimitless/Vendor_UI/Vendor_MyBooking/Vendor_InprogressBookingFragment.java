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
import com.satrangolimitless.Adapter.Adapter_Vendor_InProgressBooking;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Vendor_InProgressBooking;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Get_InProgress_booking_Vendor;

public class Vendor_InprogressBookingFragment extends Fragment {
    View root;
    Session_vendor session_vendor;
    String vendor_id;
    String TAG = getClass().getName();
    RecyclerView rec_ven_inpgr;

    ArrayList<Vendor_InProgressBooking> vendor_inProgressBookings = new ArrayList<>();
    Adapter_Vendor_InProgressBooking adapter_vendor_inProgressBooking;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_inprogress_booking, container, false);
        session_vendor = new Session_vendor(getActivity());
        vendor_id = session_vendor.getUserId();
        rec_ven_inpgr = root.findViewById(R.id.rec_ven_inpgr);
        System.out.println("vendorid<><>><><>    " + TAG + " = " + vendor_id);
//        Intent intent = new Intent(getActivity(), ResheduledActivity.class);
//        startActivity(intent);

//        Intent intent = new Intent(getActivity(), BookingDetailsActivity.class);
//        startActivity(intent);

        Completed_rejected_Bookings();
        return root;
    }


//----------------------------------------Vendor Upcoming Bookings api

    private void Completed_rejected_Bookings() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Get_InProgress_booking_Vendor;
        vendor_inProgressBookings.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vendor_id)


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
                                System.out.println("Vendor_InprogressBookingFragment <>>   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_InProgressBooking allCommunityModel = new Vendor_InProgressBooking(
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
                                    vendor_inProgressBookings.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_vendor_inProgressBooking = new Adapter_Vendor_InProgressBooking(vendor_inProgressBookings, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_ven_inpgr.setLayoutManager(mLayoutManger);
                            rec_ven_inpgr.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_ven_inpgr.setItemAnimator(new DefaultItemAnimator());
                            rec_ven_inpgr.setAdapter(adapter_vendor_inProgressBooking);
                            adapter_vendor_inProgressBooking.notifyDataSetChanged();
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