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
import com.satrangolimitless.Adapter.Adapter_User_PendingBooking;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Confirm_Bid;
import com.satrangolimitless.model.User_UpcomingBooking;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.GetBookings_pending_user;


public class PendingBookingFragment extends Fragment {
    View root;


    Session session;
    RecyclerView rec_usr_upc;
    String userid;
    ArrayList<User_UpcomingBooking> user_upcomingBookings = new ArrayList<>();
    ArrayList<Confirm_Bid> confirm_bidArrayList = new ArrayList<>();
    Adapter_User_PendingBooking adapter_user_pendingBooking;

    public static PendingBookingFragment newInstance(String param1, String param2) {
        PendingBookingFragment fragment = new PendingBookingFragment();

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
        root= inflater.inflate(R.layout.fragment_upcoming_booking, container, false);


        session = new Session(getActivity());
        userid = session.getUserId();

        rec_usr_upc = root.findViewById(R.id.rec_usr_upc);


        pendingBookingsApi();


        return root;
    }


    private void pendingBookingsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + GetBookings_pending_user;
        user_upcomingBookings.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", userid)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            System.out.println("  user upcoming bookingresponse--------        "+jsonObject.toString());
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("new_booking");
                                JSONArray jsonArray2 = jsonObject.getJSONArray("job");
                                System.out.println("UpcomingBookingsApiuserside-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    User_UpcomingBooking allCommunityModel = new User_UpcomingBooking(
                                            dataObject.getString("id"),
                                            dataObject.getString("bookingId"),
                                            dataObject.getString("user_id"),
                                            dataObject.getString("vendor_id"),
                                            dataObject.getString("booking_date"),
                                            dataObject.getString("from_time"),
                                            dataObject.getString("to_time"),
                                            dataObject.getString("start_location1"),
                                            dataObject.getString("work_description"),
                                            dataObject.getString("type"),
                                            dataObject.getString("job_status"),
                                            dataObject.getString("otp"),
                                            dataObject.getString("v_name"),
                                            dataObject.getString("v_contact"),
                                            dataObject.getString("v_address"),
                                            dataObject.getString("upload_doc"),
                                            dataObject.getString("min_charge"),
                                            dataObject.getString("v_image"),
                                            dataObject.getString("user_rating")

                                    );
                                    user_upcomingBookings.add(allCommunityModel);


                                }

                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    Log.e("jsonarray2 JOB----     ", jsonArray2.toString());
                                    JSONObject dataObject2 = jsonArray2.getJSONObject(i);
                                System.out.println("ididididiid<><><><>   "+dataObject2.getString("id"));
                                if (dataObject2.has("id")) {
                                    Confirm_Bid confirm_bid = new Confirm_Bid(dataObject2.getString("id"), dataObject2.getString("job_id"), dataObject2.getString("vendor_id"), dataObject2.getString("bid_amount"), dataObject2.getString("proposal"), dataObject2.getString("estimated_time"), dataObject2.getString("attachment"), dataObject2.getString("submission_type"), dataObject2.getString("accepted_by"), dataObject2.getString("reject_by"), dataObject2.getString("status"), dataObject2.getString("modify_date"), dataObject2.getString("user_name"), dataObject2.getString("phone"));

                                    confirm_bidArrayList.add(confirm_bid);

                                }


                                }

                            } else {

                            }

                            adapter_user_pendingBooking = new Adapter_User_PendingBooking(user_upcomingBookings,confirm_bidArrayList, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_usr_upc.setLayoutManager(mLayoutManger);
                            rec_usr_upc.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            rec_usr_upc.setItemAnimator(new DefaultItemAnimator());
                            rec_usr_upc.setAdapter(adapter_user_pendingBooking);
                            adapter_user_pendingBooking.notifyDataSetChanged();

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