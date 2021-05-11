package com.satrangolimitless.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_Booking_Confirm_Details_Inprogress;
import com.satrangolimitless.Vendor_UI.VendorchatBookingdetailsActivity;
import com.satrangolimitless.Vendor_UI.VendorchatdetailsActivity;
import com.satrangolimitless.model.Vendor_InProgressBooking;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Booking_resumed_by_vendor;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Vendor_InProgressBooking extends RecyclerView.Adapter<Adapter_Vendor_InProgressBooking.ViewHolder> {
    private final List<Vendor_InProgressBooking> category_home_models;
    private final Context context;
    Session_vendor session1;
    String e1 = "", e2 = "", e3 = "", e4 = "", otp = "";
    String vid, bookingid, currentTime;
    private EditText[] editTexts;

    public Adapter_Vendor_InProgressBooking(List<Vendor_InProgressBooking> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vendor_booking_inprogress, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Vendor_InProgressBooking allCommunityModel = category_home_models.get(position);

        session1 = new Session_vendor(context);
        currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        holder.txtname.setText(category_home_models.get(position).getUser_name());

        holder.txtlocation.setText(category_home_models.get(position).getStart_location1());
        holder.txt_desc.setText(category_home_models.get(position).getWork_description());
        vid = session1.getUserId();
        holder.txtamount.setText("₹ " + category_home_models.get(position).getMin_charge());
        holder.txtrating.setText(category_home_models.get(position).getUser_rating());

        try {
            String booking_date = category_home_models.get(position).getBooking_date();
            String start_dt = booking_date;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
            Date date = formatter.parse(start_dt);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String finalString = newFormat.format(date);
            System.out.println("final string------     " + finalString);
            holder.txtsheduleon.setText(finalString);


        } catch (Exception e) {
            e.printStackTrace();
        }


        Glide.with(context)
                .load(mediimage_url + allCommunityModel.getUser_image()).into(holder.img);


        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Vendor_Booking_Confirm_Details_Inprogress.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
                intent.putExtra("name", category_home_models.get(position).getUser_name());
                intent.putExtra("date", category_home_models.get(position).getBooking_date());
                intent.putExtra("frmtime", category_home_models.get(position).getFrom_time());
                intent.putExtra("totime", category_home_models.get(position).getTo_time());
                intent.putExtra("reting", category_home_models.get(position).getUser_rating());
                intent.putExtra("price", category_home_models.get(position).getMin_charge());
                intent.putExtra("status", category_home_models.get(position).getJob_status());
                intent.putExtra("job_status", category_home_models.get(position).getJob_status());
                intent.putExtra("verify_otp", category_home_models.get(position).getVerify_otp());
                intent.putExtra("extra_charge_status", category_home_models.get(position).getExtra_charge_status());
                intent.putExtra("date_time", category_home_models.get(position).getDate_time());
                intent.putExtra("vimage", category_home_models.get(position).getV_image());
                intent.putExtra("image", category_home_models.get(position).getUser_image());
                intent.putExtra("mrkcomplete", category_home_models.get(position).getMark_complete());

                context.startActivity(intent);

            }
        });


        String status = category_home_models.get(position).getJob_status();
        System.out.println("status ><><><Inprogress><><><       " + status);
        if (status.contains("Inprogress")) {

            holder.ll_bookingstatuscolr.setBackgroundResource(R.drawable.ic_greentrabgle);
            holder.txtstatus.setText("Started");
            holder.txtstatus1.setText("You should start to user location");
        } else if (status.contains("Paused")) {
            holder.ll_bookingstatuscolr.setBackgroundResource(R.drawable.ic_bookinginprogress);
            holder.txtstatus.setText("Resume");
            holder.txtstatus1.setText("Work is paused by you");

        } else if (status.contains("Resume")) {
            holder.ll_bookingstatuscolr.setBackgroundResource(R.drawable.ic_greentrabgle);
            holder.txtstatus.setText("Started");

        }


        holder.txtstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookingid = category_home_models.get(position).getId();

                Resumebooking(holder.txtstatus);
            }
        });


        holder.imgacall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + category_home_models.get(position).getUser_contact()));
                context. startActivity(intent);
            }
        });

        holder.imchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(context, VendorchatBookingdetailsActivity.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("userid", category_home_models.get(position).getUser_id());
                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return category_home_models.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void Resumebooking(final TextView txtstatus) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Booking_resumed_by_vendor;
        System.out.println("param vid " + vid + "  bookid " + bookingid + " time  " + currentTime);
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vid)
                .addBodyParameter("booking_id", bookingid)
                .addBodyParameter("resume_time", currentTime)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("Resumebooking-  ", jsonObject.toString());
                                System.out.println("Resumebooking    " + jsonObject.toString());

                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                txtstatus.setText("Started");
                                notifyDataSetChanged();
                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }


//Start booking by vendor api

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_details, ll_bookingstatuscolr;
        ImageView img_call, heart, fav2;
        TextView txtname, txt_desc, txtsheduleon, txtlocation, txtamount, txtrating, txtstatus,txtstatus1;
        ImageView img,imgacall,imchat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imgacall = itemView.findViewById(R.id.imgacall);
            imchat = itemView.findViewById(R.id.imchat);
            img = itemView.findViewById(R.id.img);
            txtname = itemView.findViewById(R.id.txtname);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txtsheduleon = itemView.findViewById(R.id.txtsheduleon);

            txtlocation = itemView.findViewById(R.id.txtlocation);
            txtamount = itemView.findViewById(R.id.txtamount);
            txtrating = itemView.findViewById(R.id.txtrating);
            txtstatus = itemView.findViewById(R.id.txtstatus);
            txtstatus1 = itemView.findViewById(R.id.txtstatus1);
            ll_details = itemView.findViewById(R.id.ll_details);
            ll_bookingstatuscolr = itemView.findViewById(R.id.ll_bookingstatuscolr);
        }
    }



    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), 101);
            return false;
        } else {
        }

        return true;
    }

}
