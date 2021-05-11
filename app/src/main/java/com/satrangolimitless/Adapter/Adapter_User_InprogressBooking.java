package com.satrangolimitless.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Mybooking.Activity_User_BookingInprogress_details;
import com.satrangolimitless.User_UI.UserchatBookingdetailsActivity;
import com.satrangolimitless.User_UI.UserchatdetailsActivity;
import com.satrangolimitless.model.User_InProgressBooking;
import com.satrangolimitless.session.Session_vendor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;
import static com.satrangolimitless.Utils.Base_Url.user_side_chat;

public class Adapter_User_InprogressBooking extends RecyclerView.Adapter<Adapter_User_InprogressBooking.ViewHolder> {
    private final List<User_InProgressBooking> category_home_models;
    private final Context context;
    Session_vendor session1;
    String e1 = "", e2 = "", e3 = "", e4 = "", otp = "";
    String vid, bookingid;
    private EditText[] editTexts;

    public Adapter_User_InprogressBooking(List<User_InProgressBooking> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_booking_inprogress, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User_InProgressBooking allCommunityModel = category_home_models.get(position);

        session1 = new Session_vendor(context);
        holder.txtname.setText(category_home_models.get(position).getV_name());
        holder.txtrating.setText(category_home_models.get(position).getUser_rating());
        holder.txt_desc.setText(category_home_models.get(position).getWork_description());
        holder.txtlocation.setText(category_home_models.get(position).getStart_location1());
        holder.txtamount.setText("₹ " + category_home_models.get(position).getMin_charge());

        String status = category_home_models.get(position).getJob_status();
        System.out.println(" status  ------     " + status);
        System.out.println(" category_home_models.get(position).getAmount()  ------     " + category_home_models.get(position).getAmount());

        holder.imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(category_home_models.get(position).getV_contact()));
                context. startActivity(callIntent);
            }
        });


        holder.imgmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, UserchatBookingdetailsActivity.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("vendorid", category_home_models.get(position).getVendor_id());

                context.startActivity(intent);

            }
        });

        if (status.contains("Paused")) {

            holder.txtstatus.setText("Paused");
            holder.txtstatusdes.setText("Service Provider Paused your Work");
            holder.ll_statusbacg.setBackgroundResource(R.drawable.ic_bookinginprogress);

        } else {

            holder.txtstatus.setText("In-Progress");
            holder.txtstatusdes.setText("Service Provider Started to your location");
            holder.ll_statusbacg.setBackgroundResource(R.drawable.ic_bookingpending_svg);
            holder.txtstatus.setTextColor(context.getResources().getColor(R.color.white));
        }

//        holder.txtfromtime.setText(category_home_models.get(position).getFrom_time());
//        holder.txttotime.setText("to " + category_home_models.get(position).getTo_time());

        try {

            String booking_date = category_home_models.get(position).getBooking_date();
            String start_dt = booking_date;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
            Date date = formatter.parse(start_dt);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String finalString = newFormat.format(date);
            System.out.println(" inprogres date string------     " + finalString);
            holder.txtdate.setText(finalString);


        } catch (Exception e) {
            e.printStackTrace();
        }


        Glide.with(context).load(Image_url + allCommunityModel.getV_image()).into(holder.imageprofile);




//        holder.txtreshedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, Vendor_ResheduleBooking_activity.class);
//                intent.putExtra("id", category_home_models.get(position).getId());
//                intent.putExtra("name", category_home_models.get(position).getUser_name());
//                intent.putExtra("date", category_home_models.get(position).getDate_time());
//                intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
//                intent.putExtra("reting", category_home_models.get(position).getUser_rating());
//                intent.putExtra("price", category_home_models.get(position).getMin_charge());
//                intent.putExtra("image", category_home_models.get(position).getUser_image());
//
//                context.startActivity(intent);
//            }
//        });

        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Activity_User_BookingInprogress_details.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("name", category_home_models.get(position).getUser_name());
                intent.putExtra("date", category_home_models.get(position).getBooking_date());
                intent.putExtra("frmtime", category_home_models.get(position).getFrom_time());
                intent.putExtra("totime", category_home_models.get(position).getTo_time());
                intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
                intent.putExtra("reting", category_home_models.get(position).getUser_rating());
                intent.putExtra("price", category_home_models.get(position).getMin_charge());
                intent.putExtra("vendorid", category_home_models.get(position).getVendor_id());
                intent.putExtra("desc", category_home_models.get(position).getWork_description());
                intent.putExtra("otp", category_home_models.get(position).getOtp());
                intent.putExtra("votp", category_home_models.get(position).getVerify_otp());
                intent.putExtra("attachm", category_home_models.get(position).getUpload_doc());
                intent.putExtra("job_status", category_home_models.get(position).getJob_status());
                intent.putExtra("extra_charge_status", category_home_models.get(position).getExtra_charge_status());
                intent.putExtra("vendordoc", category_home_models.get(position).getVendor_doc());
                intent.putExtra("date_time", category_home_models.get(position).getDate_time());
                intent.putExtra("appropriate_status", category_home_models.get(position).getAppropriate_status());
                intent.putExtra("mrkcomplete", category_home_models.get(position).getMark_complete());
                intent.putExtra("usrimage", category_home_models.get(position).getUser_image());
                intent.putExtra("vdrimage", category_home_models.get(position).getV_image());

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


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_details, ll_statusbacg;
        ImageView imgcall, imgmess, fav2;
        TextView txtstatus, txtname, txt_desc, txtstatusdes, txtdate, txttotime, txtlocation, txtamount, txtrating, txtstart, txtcancelbooking;
        CircleImageView imageprofile;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageprofile = itemView.findViewById(R.id.imageprofile);
            txtname = itemView.findViewById(R.id.txtname);
            txt_desc = itemView.findViewById(R.id.txtdesc);
            txtlocation = itemView.findViewById(R.id.txtlocation);
            txtamount = itemView.findViewById(R.id.txtcharge);
            txtdate = itemView.findViewById(R.id.txtdate);
            txtrating = itemView.findViewById(R.id.txtrating);
            imgcall = itemView.findViewById(R.id.imgcall);
            imgmess = itemView.findViewById(R.id.imgmess);
            ll_details = itemView.findViewById(R.id.ll_details);
            ll_statusbacg = itemView.findViewById(R.id.ll_statusbacg);
            txtstatus = itemView.findViewById(R.id.txtstatus);
            txtstatusdes = itemView.findViewById(R.id.txtstatusdes);
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
