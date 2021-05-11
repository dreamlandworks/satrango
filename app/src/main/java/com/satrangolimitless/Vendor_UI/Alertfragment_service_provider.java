package com.satrangolimitless.Vendor_UI;

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
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.satrangolimitless.Adapter.Adapter_User_Notifications;
import com.satrangolimitless.Adapter.Adapter_Vendor_Notifications;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.model.User_Notification_model;
import com.satrangolimitless.model.Vendor_Notification_model;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.UserNotifications;
import static com.satrangolimitless.Utils.Base_Url.Vendor_Notifications;


public class Alertfragment_service_provider extends Fragment {

    Session_vendor session_vendor;
    Session session;
CircleImageView imgproalerts;

    ArrayList<Vendor_Notification_model> vendor_notification_models = new ArrayList<>();
    Adapter_Vendor_Notifications adapter_vendor_notifications;
    RecyclerView recv_notification;

    ImageView icback;
    String name,user_id,image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((LandingActivity_Service_provider)getActivity()).Layout_hader.setVisibility(View.GONE);

        View root = inflater.inflate(R.layout.fragment_alertfragment, container, false);
        session = new Session(getActivity());
        session_vendor = new Session_vendor(getActivity());
        imgproalerts = root.findViewById(R.id.imgproalerts);
        recv_notification = root.findViewById(R.id.recv_notification);
        Glide.with(getActivity())
                .load(Image_url + session.getProfileimage())
                .into(imgproalerts);

        NotificationsApi();
        return root;

    }





    private void NotificationsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Vendor_Notifications;
        System.out.println("NotificationsApi ------  "+session.getUserId());
        vendor_notification_models.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("vid", session_vendor.getUserId())

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
                                System.out.println("NotificationsApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Vendor_Notification_model allCommunityModel = new Vendor_Notification_model(
                                            dataObject.getString("bookingId"),
                                            dataObject.getString("sender_id"),
                                            dataObject.getString("receiver_id"),
                                            dataObject.getString("receiver_type"),
                                            dataObject.getString("message"),
                                            dataObject.getString("created_date"),
                                            dataObject.getString("fname"),
                                            dataObject.getString("image"),
                                            dataObject.getString("type"));
                                    vendor_notification_models.add(allCommunityModel);
                                }


                            } else {

                            }

                            adapter_vendor_notifications = new Adapter_Vendor_Notifications(vendor_notification_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recv_notification.setLayoutManager(mLayoutManger);
                            recv_notification.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            recv_notification.setItemAnimator(new DefaultItemAnimator());
                            recv_notification.setAdapter(adapter_vendor_notifications);
                            adapter_vendor_notifications.notifyDataSetChanged();
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