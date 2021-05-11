package com.satrangolimitless.User_UI.NotificationAlerts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.Adapter.Adapter_User_Notifications;
import com.satrangolimitless.ForgotPasswordActivity;
import com.satrangolimitless.ForgotpasswordotpActivity;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.model.User_Notification_model;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.UserNotifications;


public class User_Notifications extends Fragment {
        ArrayList<User_Notification_model> user_notification_models = new ArrayList<>();
        Adapter_User_Notifications adapter_user_notifications;
        RecyclerView recv_notification;
    Session session;
    ImageView icback;
        String name,user_id,image;

    CircleImageView imgprofile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((LandingActivity) getActivity()).Layout_hader.setVisibility(View.GONE);
        View root = inflater.inflate(R.layout.activity_user_notification, container, false);
        recv_notification = root.findViewById(R.id.recv_notification);
        icback = root.findViewById(R.id.icback);
        session=new Session(getActivity());

        imgprofile = root.findViewById(R.id.imgprofile);
        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity.class);
                startActivity(intent);
            }
        });

        image=session.getProfileimage();

        try {
            Glide.with(getActivity())
                    .load(Image_url + image)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .centerCrop()
                            .dontTransform())
                    .skipMemoryCache(true)
                    .into(imgprofile);

        }catch (Exception e){e.printStackTrace();}

        NotificationsApi();
        return root;
    }


    private void NotificationsApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + UserNotifications;
System.out.println("NotificationsApi ------  "+session.getUserId());
        user_notification_models.clear();
        AndroidNetworking.post(url)
                .addBodyParameter("uid", session.getUserId())

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
                                    User_Notification_model allCommunityModel = new User_Notification_model(
                                            dataObject.getString("receiver_id"),
                                            dataObject.getString("receiver_type"),
                                            dataObject.getString("message"),
                                            dataObject.getString("created_date"));
                                    user_notification_models.add(allCommunityModel);
                                }


                            } else {

                            }

                            adapter_user_notifications = new Adapter_User_Notifications(user_notification_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recv_notification.setLayoutManager(mLayoutManger);
                            recv_notification.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            recv_notification.setItemAnimator(new DefaultItemAnimator());
                            recv_notification.setAdapter(adapter_user_notifications);
                            adapter_user_notifications.notifyDataSetChanged();
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