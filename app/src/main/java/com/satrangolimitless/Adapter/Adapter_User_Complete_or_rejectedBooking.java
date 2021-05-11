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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Mybooking.Activity_User_BookingCompleted_Details;
import com.satrangolimitless.User_UI.UserchatBookingdetailsActivity;
import com.satrangolimitless.User_UI.UserchatdetailsActivity;
import com.satrangolimitless.model.User_Completed_or_rejectedBooking;
import com.satrangolimitless.session.Session_vendor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_User_Complete_or_rejectedBooking extends RecyclerView.Adapter<Adapter_User_Complete_or_rejectedBooking.ViewHolder> {
    private final List<User_Completed_or_rejectedBooking> category_home_models;
    private final Context context;
    Session_vendor session1;
    String e1 = "", e2 = "", e3 = "", e4 = "", otp = "";
    String vid, bookingid;
    private EditText[] editTexts;

    public Adapter_User_Complete_or_rejectedBooking(List<User_Completed_or_rejectedBooking> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_booking_completed_or_rejected, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User_Completed_or_rejectedBooking allCommunityModel = category_home_models.get(position);

        session1 = new Session_vendor(context);
        holder.txtname.setText(category_home_models.get(position).getV_name());
        holder.txtrating.setText(category_home_models.get(position).getUser_rating());
        holder.txt_desc.setText(category_home_models.get(position).getWork_description());
        holder.txtfromtime.setText(category_home_models.get(position).getBooking_date());
        holder.txtamount.setText("₹ " + category_home_models.get(position).getMin_charge());

        holder.txtlocation.setText(category_home_models.get(position).getStart_location1());

        holder.imgcallcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + category_home_models.get(position).getV_contact()));
                context.startActivity(intent);
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



        try {

            String booking_date = category_home_models.get(position).getBooking_date();
            String start_dt = booking_date;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
            Date date = formatter.parse(start_dt);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String finalString = newFormat.format(date);
            System.out.println("User_Complete_or_rejectedBooking string------     " + finalString);
            holder.txtdate.setText(finalString);


        } catch (Exception e) {
            e.printStackTrace();
        }


        Glide.with(context)
                .load(Image_url + allCommunityModel.getV_image())
                .into(holder.imgprocr);





        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Activity_User_BookingCompleted_Details.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("name", category_home_models.get(position).getV_name());
                intent.putExtra("date", category_home_models.get(position).getBooking_date());
                intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
                intent.putExtra("reting", category_home_models.get(position).getUser_rating());
                intent.putExtra("price", category_home_models.get(position).getMin_charge());
                intent.putExtra("image", category_home_models.get(position).getV_image());
                intent.putExtra("desc", category_home_models.get(position).getWork_description());
                intent.putExtra("otp", category_home_models.get(position).getOtp());
                intent.putExtra("frmtime", category_home_models.get(position).getFrom_time());
                intent.putExtra("totime", category_home_models.get(position).getTo_time());

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
        LinearLayout ll_details;
        ImageView imgcallcr, imgmess, fav2;
        TextView txtraiseticket ,txtdate, txtname, txt_desc, txtbookagain, txtfromtime, txttotime, txtlocation, txtamount, txtrating, txtstart, txtcancelbooking;
        CircleImageView imgprocr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            txtname = itemView.findViewById(R.id.txtname);
            ll_details = itemView.findViewById(R.id.ll_details);
            txtrating = itemView.findViewById(R.id.txtrating);
            txtlocation = itemView.findViewById(R.id.txtlocation);
            txt_desc = itemView.findViewById(R.id.txtdesc);
            txtfromtime = itemView.findViewById(R.id.txttime);
            txtamount = itemView.findViewById(R.id.txtcharge);
            txtdate = itemView.findViewById(R.id.txtdate);
            txtbookagain = itemView.findViewById(R.id.txtbookagain);
            txtraiseticket = itemView.findViewById(R.id.txtraiseticket);
            imgprocr = itemView.findViewById(R.id.imgprocr);
            imgcallcr = itemView.findViewById(R.id.imgcallcr);
            imgmess = itemView.findViewById(R.id.imgmess);


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
