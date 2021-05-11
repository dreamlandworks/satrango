package com.satrangolimitless.Firebase_notification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.LoginActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.Vendor_UI.Activity_vendor_accept_newbookingrequest;
import com.satrangolimitless.session.Session;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    Session_vendor session_vendor;
    Session session;
    String bookingId = "", title = "",type="",body="";
    PendingIntent pendingIntent ;
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("token_fcm_service", s);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {

            Log.e(TAG, "Data_Payload: " + remoteMessage.getData().toString());
            try {
                handleDataMessage2(remoteMessage);

            } catch (Exception e) {

                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        if (remoteMessage.getNotification() != null) {

            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());

            title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            Log.e(TAG, "title_body: " + title + " " + body);
            handleDataMessage(remoteMessage, body, title);
             handleNotification(title,body);
        }

    }


    private void handleNotification(String title, String body) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent resultIntent = new Intent(getApplicationContext(), Activity_vendor_accept_newbookingrequest.class);
            SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
            String format = s.format(new Date());

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Log.e("rr22", "pppp22");
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Constants.CHANNEL_NAME);
                pushNotification.putExtra("message", body);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.showNotificationMessage(title, body, "", resultIntent);
                notificationUtils.playNotificationSound();


            } else {
                Log.e("rrrr", "pppp");
                sendNoti(title, body);
                // If the app is in background, firebase itself handles the notification
            }
        }

    }

    private void sendNoti(String title, String body) {
        System.out.println("---------sendNoti------------");
        Intent intent = new Intent(this, Activity_vendor_accept_newbookingrequest.class);
        intent.putExtra("NOTIFICATION", "NOTIFICATION");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        String channelId = "Default";

        b.setAutoCancel(false);
        b.setDefaults(Notification.DEFAULT_ALL);
        b.setWhen(System.currentTimeMillis());
        b.setSmallIcon(R.drawable.lunchoriogo);
        // b.setTicker("Hearty365");
        b.setContentTitle(title);
        b.setContentText(body);
        b.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
        b.setContentIntent(contentIntent);
        // b.setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, b.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleDataMessage2(RemoteMessage remoteMessage) {
        // Log.e(TAG, "push_json: " + json.toString());
        session_vendor = new Session_vendor(this);
        session = new Session(this);

        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String format = s.format(new Date());
        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);

            Log.e("JSON OBJECT USER APP", object.toString());
              title = object.getString("title");
              body = object.getString("body");
              type = object.getString("type");
            bookingId = object.getString("bookingId");

            System.out.println("check bookingId" + bookingId);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

//                not in backg check

                System.out.println("-------!NotificationUtils.isAppIsInBackground-----------");


                if (title.contains("Confirm booking")) {
                    session_vendor.setBookingid(bookingId);
                    if (session.isLoggedIn()) {

                        Intent intent = new Intent(this, LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    } else {

                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    }


                } else if (title.contains("Booking work start")) {

                    if (session.isLoggedIn()) {

                        Intent intent = new Intent(this, LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    } else {

                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    }

                }

                else {
                    if (session_vendor.isLoggedIn()) {
                        if (title.contains("New booking")){
                            session_vendor.setBookingid(bookingId);
                            Intent intent = new Intent(this, Activity_vendor_accept_newbookingrequest.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                        }

                    } else {

                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    }


                }

                Intent intent = new Intent(this, Activity_vendor_accept_newbookingrequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


                String channelId = "Default";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.lunchoriogo)
                        .setContentTitle(title)
                        .setContentText(body).setAutoCancel(true).setContentIntent(pendingIntent);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);
                }
                manager.notify(generateRandom(), builder.build());
            } else {


                System.out.println("-------!NotificationUtils.isAppIsInBackground------else-----");
                session_vendor.setBookingid(bookingId);
                session.setBookingid(bookingId);
                session.setXtrachrg(bookingId);

                if (type.contains("user")){
                    if (title.contains("Extra Demand charge")){
                        Intent intent = new Intent(this, LandingActivity.class);
                        intent.putExtra("id", bookingId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    }
                    else {

                        Intent intent = new Intent(this, LandingActivity.class);
                        intent.putExtra("id", bookingId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                    }
                }else{

                    Intent intent = new Intent(this, Activity_vendor_accept_newbookingrequest.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                }


                String channelId = "Default";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.lunchoriogo)
                        .setContentTitle(title)
                        .setContentText(body).setAutoCancel(true).setContentIntent(pendingIntent);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);
                }
                manager.notify(generateRandom(), builder.build());


            }

        } catch (Exception e) {
            Log.e("error", "Json Exception: " + e.getMessage());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleDataMessage(RemoteMessage remoteMessage, String body, String title) {
//         Log.e(TAG, "push_json: " + json.toString());
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String format = s.format(new Date());
        try {

            System.out.println("check title" + title);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

                System.out.println("---handleDataMessage----!NotificationUtils.isAppIsInBackground-----------");
                session_vendor.setBookingid(bookingId);
                Intent intent = new Intent(this, Activity_vendor_accept_newbookingrequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


                String channelId = "Default";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.lunchoriogo)
                        .setContentTitle(title)
                        .setContentText(body).setAutoCancel(true).setContentIntent(pendingIntent);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);
                }
                manager.notify(generateRandom(), builder.build());
            } else {

                System.out.println("---handleDataMessage----!NotificationUtils.isAppIsInBackground--------else---");
                session_vendor.setBookingid(bookingId);
                Intent intent = new Intent(this, Activity_vendor_accept_newbookingrequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                String channelId = "Default";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.lunchoriogo)
                        .setContentTitle(title)
                        .setContentText(body).setAutoCancel(true).setContentIntent(pendingIntent);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);
                }
                manager.notify(generateRandom(), builder.build());


            }

        } catch (Exception e) {
            Log.e("error", "Json Exception: " + e.getMessage());
        }
    }


    public int generateRandom() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }


}
