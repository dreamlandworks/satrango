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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.UserchatdetailsActivity;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_Bookking_Completed_Details;
import com.satrangolimitless.Vendor_UI.VendorchatBookingdetailsActivity;
import com.satrangolimitless.Vendor_UI.VendorchatdetailsActivity;
import com.satrangolimitless.model.Vendor_CompletedBooking;
import com.satrangolimitless.session.Session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Vendor_CompletedBooking extends RecyclerView.Adapter<Adapter_Vendor_CompletedBooking.ViewHolder> {
    private final List<Vendor_CompletedBooking> category_home_models;
    private final Context context;


    public Adapter_Vendor_CompletedBooking(List<Vendor_CompletedBooking> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vendor_booking_completed, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Vendor_CompletedBooking allCommunityModel = category_home_models.get(position);


        holder.txtname.setText(category_home_models.get(position).getUser_name());
        holder.txtfromtime.setText(category_home_models.get(position).getFrom_time() + " to " + category_home_models.get(position).getTo_time());
//      holder.txttotime.setText("to "+category_home_models.get(position).getTo_time());
        holder.txtlocation.setText(category_home_models.get(position).getStart_location1());
        holder.txt_desc.setText(category_home_models.get(position).getWork_description());
        holder.txtamount.setText("₹ " + category_home_models.get(position).getMin_charge());
        holder.txtrating.setText(category_home_models.get(position).getUser_rating());

        System.out.println("rating vendr complted -----      " + category_home_models.get(position).getUser_rating());
        try {
            String booking_date = category_home_models.get(position).getBooking_date();
            holder.txtsheduleon.setText(booking_date);
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
                .load(mediimage_url + allCommunityModel.getV_image())
                .into(holder.img_cat);

        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (category_home_models.get(position).getJob_status().contains("Cancel"))
                {


                }else{

                    Intent intent = new Intent(context, Vendor_Bookking_Completed_Details.class);
                    intent.putExtra("id", category_home_models.get(position).getId());
                    intent.putExtra("name", category_home_models.get(position).getUser_name());
                    intent.putExtra("rating", category_home_models.get(position).getUser_rating());
                    intent.putExtra("time", category_home_models.get(position).getFrom_time());
                    intent.putExtra("To_time", category_home_models.get(position).getTo_time());
                    intent.putExtra("date", category_home_models.get(position).getBooking_date());
                    intent.putExtra("price", category_home_models.get(position).getMin_charge());
                    intent.putExtra("image", category_home_models.get(position).getUser_image());
                    intent.putExtra("bookid", category_home_models.get(position).getBookingId());
                    context.startActivity(intent);
                }


            }
        });


        holder.imgcallvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + category_home_models.get(position).getUser_contact()));
                context. startActivity(intent);
            }
        });
        holder.imgmesvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(context, VendorchatBookingdetailsActivity.class);
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
        LinearLayout ll_details,ll_statscolor;
        ImageView img_cat, imgmesvc,
        imgcallvc;
        TextView txtraisesuport,txtstatus ,txtname, txt_desc, txtsheduleon, txtfromtime, txttotime, txtlocation, txtamount, txtrating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_statscolor = itemView.findViewById(R.id.ll_statscolor);
            txtstatus = itemView.findViewById(R.id.txtstatus);
            txtraisesuport = itemView.findViewById(R.id.txtraisesuport);
            txtname = itemView.findViewById(R.id.txtname);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txtsheduleon = itemView.findViewById(R.id.txtsheduleon);
            txtfromtime = itemView.findViewById(R.id.txtfromtime);
//            txttotime = itemView.findViewById(R.id.txttotime);
            txtlocation = itemView.findViewById(R.id.txtlocation);
            txtamount = itemView.findViewById(R.id.txtamount);
            txtrating = itemView.findViewById(R.id.txtrating);
            img_cat = itemView.findViewById(R.id.img_cat);
            imgmesvc = itemView.findViewById(R.id.imgmesvc);
            imgcallvc = itemView.findViewById(R.id.imgcallvc);
            ll_details = itemView.findViewById(R.id.ll_details);
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
