package com.satrangolimitless.User_UI.Mybooking;

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
import com.satrangolimitless.Adapter.Adapter_User_Complete_or_rejectedBooking;
import com.satrangolimitless.R;
import com.satrangolimitless.model.User_Completed_or_rejectedBooking;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.ShowBookingcomplete_reject_bookingbothside;

public class CompletedBookingFragment extends Fragment {
    View root;

    Session session;
    RecyclerView rec_usr_complete;
    String user_id;
    ArrayList<User_Completed_or_rejectedBooking> user_completed_or_rejectedBookings = new ArrayList<>();
    Adapter_User_Complete_or_rejectedBooking adapter_user_complete_or_rejectedBooking;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_completed_booking, container, false);
        session=new Session(getActivity());
        user_id=session.getUserId();
        rec_usr_complete = root.findViewById(R.id.rec_usr_complete);

        CompleterejectBookingsApi();
        return root;
    }


    private void CompleterejectBookingsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + ShowBookingcomplete_reject_bookingbothside;

        user_completed_or_rejectedBookings.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("id", user_id)
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
                                    User_Completed_or_rejectedBooking allCommunityModel = new User_Completed_or_rejectedBooking(
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
                                    user_completed_or_rejectedBookings.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_user_complete_or_rejectedBooking = new Adapter_User_Complete_or_rejectedBooking(user_completed_or_rejectedBookings, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_usr_complete.setLayoutManager(mLayoutManger);
                            rec_usr_complete.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_usr_complete.setItemAnimator(new DefaultItemAnimator());
                            rec_usr_complete.setAdapter(adapter_user_complete_or_rejectedBooking);
                            adapter_user_complete_or_rejectedBooking.notifyDataSetChanged();

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




