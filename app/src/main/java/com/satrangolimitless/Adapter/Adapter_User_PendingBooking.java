package com.satrangolimitless.Adapter;

import android.Manifest;
import android.app.Activity;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Mybooking.Actvity_Userbooking_details_;
import com.satrangolimitless.User_UI.Mybooking.User_Cancel_Booking_Activity;
import com.satrangolimitless.User_UI.Mybooking.User_Reshedule_BookingActivity;
import com.satrangolimitless.User_UI.UserchatBookingdetailsActivity;
import com.satrangolimitless.Vendor_UI.VendorchatdetailsActivity;
import com.satrangolimitless.model.Confirm_Bid;
import com.satrangolimitless.model.User_UpcomingBooking;
import com.satrangolimitless.session.Session_vendor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_User_PendingBooking extends RecyclerView.Adapter<Adapter_User_PendingBooking.ViewHolder> {
    private final List<User_UpcomingBooking> category_home_models;
    private final List<Confirm_Bid> confirm_bids;
    private final Context context;
    Session_vendor session1;
    String e1 = "", e2 = "", e3 = "", e4 = "", otp = "";
    String vid, bookingid, finalString;
    private EditText[] editTexts;

    public Adapter_User_PendingBooking(List<User_UpcomingBooking> category_home_models, List<Confirm_Bid> confirm_bids, Context context) {
        this.category_home_models = category_home_models;
        this.confirm_bids = confirm_bids;

        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_upcoming_booking, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User_UpcomingBooking allCommunityModel = category_home_models.get(position);

        session1 = new Session_vendor(context);



        holder.txtname.setText(category_home_models.get(position).getUser_name());
        holder.txtrating.setText(category_home_models.get(position).getUser_rating());
        holder.txtamount.setText("₹ " + category_home_models.get(position).getMin_charge());
        holder.txtfromtime.setText(category_home_models.get(position).getFrom_time());
        holder.txttotime.setText("to " + category_home_models.get(position).getTo_time());
        holder.txtlocation.setText(category_home_models.get(position).getStart_location1());
        holder.txt_desc.setText(category_home_models.get(position).getWork_description());




try {
    if (confirm_bids.get(position).getId()!= null && confirm_bids.size() > 0){
        final Confirm_Bid allCommunityModel2 = confirm_bids.get(position);
        String idj = confirm_bids.get(position).getId();
        System.out.println("idj<><>><><><>-- " + idj);
        if (confirm_bids.isEmpty())
        {

            holder.ll_jobpost.setVisibility(View.GONE);
        }else {


            System.out.println("job name<><><><>     "+confirm_bids.get(position).getUser_name());
            holder.ll_jobpost.setVisibility(View.VISIBLE);
            holder.txtname2.setText(confirm_bids.get(position).getUser_name());
            holder.txtrating2.setVisibility(View.GONE);
            holder.txtamount2.setText("₹ " + confirm_bids.get(position).getBid_amount());
            holder.txtfromtime2.setText(confirm_bids.get(position).getModify_date());

            holder.txtlocation2.setVisibility(View.GONE);
            holder.txt_desc2.setText(confirm_bids.get(position).getProposal());


        }




    }

}catch (IndexOutOfBoundsException e) {
    e.printStackTrace();

}
        holder.ll_jobpost.setVisibility(View.GONE);
        System.out.println("getEnd_location3!@#@$#%  --  "+category_home_models.get(position).getTo_location3());

//        vid = session1.getUserId();
//
        try {
            String booking_date = category_home_models.get(position).getBooking_date();
            String start_dt = booking_date;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
            Date date = formatter.parse(start_dt);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
              finalString = newFormat.format(date);
            System.out.println("final string------     " + finalString);
            holder.txtsheduleon.setText(finalString);


        } catch (Exception e) {
            e.printStackTrace();
        }

//

        Glide.with(context)
                .load(Image_url + allCommunityModel.getUser_image()).into(holder.imageprofile);




        holder.txtreshedule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    Intent intent = new Intent(context, User_Reshedule_BookingActivity.class);
    intent.putExtra("id", confirm_bids.get(position).getId());
    intent.putExtra("name", confirm_bids.get(position).getUser_name());
    intent.putExtra("date", confirm_bids.get(position).getModify_date());
    intent.putExtra("bookingid", confirm_bids.get(position).getJob_id());
    intent.putExtra("reting", "");
    intent.putExtra("price", confirm_bids.get(position).getBid_amount());
    intent.putExtra("image", "");
    intent.putExtra("vendorid", category_home_models.get(position).getVendor_id());

                context.startActivity(intent);
            }
        });

        holder.txtreshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, User_Reshedule_BookingActivity.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("name", category_home_models.get(position).getUser_name());
                intent.putExtra("date", category_home_models.get(position).getDate_time());
                intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
                intent.putExtra("reting", category_home_models.get(position).getUser_rating());
                intent.putExtra("price", category_home_models.get(position).getMin_charge());
                intent.putExtra("image", category_home_models.get(position).getUser_image());
                intent.putExtra("vendorid", category_home_models.get(position).getVendor_id());

                context.startActivity(intent);

            }
        });


        holder.txtcancelbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, User_Cancel_Booking_Activity.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("name", category_home_models.get(position).getUser_name());
                intent.putExtra("date", category_home_models.get(position).getDate_time());
                intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
                intent.putExtra("reting", category_home_models.get(position).getUser_rating());
                intent.putExtra("price", category_home_models.get(position).getMin_charge());
                intent.putExtra("image", category_home_models.get(position).getUpload_doc());
            }
        });




        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    Intent intent = new Intent(context, Actvity_Userbooking_details_.class);
                    intent.putExtra("id", category_home_models.get(position).getId());
                    intent.putExtra("name", category_home_models.get(position).getUser_name());
                    intent.putExtra("date", finalString);
                    intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
                    intent.putExtra("reting", category_home_models.get(position).getUser_rating());
                    intent.putExtra("price", category_home_models.get(position).getMin_charge());
                    intent.putExtra("image", category_home_models.get(position).getUser_image());
                    intent.putExtra("desc", category_home_models.get(position).getWork_description());
                    intent.putExtra("otp", category_home_models.get(position).getTo_location3());
                    intent.putExtra("upload_doc", category_home_models.get(position).getImage());
                    intent.putExtra("frmtime", category_home_models.get(position).getFrom_time());
                    intent.putExtra("totime", category_home_models.get(position).getTo_time());
                    intent.putExtra("vendorid", category_home_models.get(position).getVendor_id());


                    context.startActivity(intent);






            }
        });


        holder.ll_details2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Actvity_Userbooking_details_.class);
                intent.putExtra("id", confirm_bids.get(position).getId());
                intent.putExtra("name", confirm_bids.get(position).getUser_name());
                intent.putExtra("date", confirm_bids.get(position).getModify_date());
                intent.putExtra("bookingid", confirm_bids.get(position).getJob_id());
                intent.putExtra("reting", "");
                intent.putExtra("price", confirm_bids.get(position).getBid_amount());
                intent.putExtra("image", "");
                intent.putExtra("desc", confirm_bids.get(position).getProposal());
                intent.putExtra("otp", "");
                context.startActivity(intent);
            }
        });


        holder.ivphn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + category_home_models.get(position).getUser_contact()));
               context. startActivity(intent);
            }
        });


        holder.ivmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(context, UserchatBookingdetailsActivity.class);
                intent.putExtra("id", confirm_bids.get(position).getId());
                intent.putExtra("vendorid", confirm_bids.get(position).getVendor_id());
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
        LinearLayout ll_details,ll_jobpost;
        LinearLayout ll_details2;
        ImageView ivphn, ivmess, fav2;
        TextView txtreshedule, txtname, txt_desc, txtsheduleon, txtfromtime, txttotime, txtlocation, txtamount, txtrating, txtstart, txtcancelbooking;
        TextView txtreshedule2, txtname2, txt_desc2, txtsheduleon2, txtfromtime2, txttotime2, txtlocation2, txtamount2, txtrating2, txtstart2, txtcancelbooking2;
        CircleImageView imageprofile;
        CircleImageView imageprofile2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageprofile = itemView.findViewById(R.id.imageprofile);
            ivphn = itemView.findViewById(R.id.ivphn);
            ivmess = itemView.findViewById(R.id.ivmess);
            txtfromtime = itemView.findViewById(R.id.txtfrmtime);
            txttotime = itemView.findViewById(R.id.txttotime);
            txtname = itemView.findViewById(R.id.txtname);
            txtreshedule = itemView.findViewById(R.id.txtreshedule);
            txtcancelbooking = itemView.findViewById(R.id.txtcancelbooking);
            txt_desc = itemView.findViewById(R.id.txtdesc);
            txtsheduleon = itemView.findViewById(R.id.txtsheduleon);
            txtlocation = itemView.findViewById(R.id.txtlocation);
            txtamount = itemView.findViewById(R.id.txtamut);
            txtrating = itemView.findViewById(R.id.txtrating);
            ll_details = itemView.findViewById(R.id.ll_details);
            ll_jobpost = itemView.findViewById(R.id.ll_jobpost);


            imageprofile2 = itemView.findViewById(R.id.imageprofile2);
            txtfromtime2 = itemView.findViewById(R.id.txtfrmtime2);
            txttotime2 = itemView.findViewById(R.id.txttotime2);
            txtname2 = itemView.findViewById(R.id.txtname2);
            txtreshedule2 = itemView.findViewById(R.id.txtreshedule2);
            txtcancelbooking2 = itemView.findViewById(R.id.txtcancelbooking2);
            txt_desc2 = itemView.findViewById(R.id.txtdesc2);
            txtsheduleon2 = itemView.findViewById(R.id.txtsheduleon2);
            txtlocation2 = itemView.findViewById(R.id.txtlocation2);
            txtamount2 = itemView.findViewById(R.id.txtamut2);
            txtrating2 = itemView.findViewById(R.id.txtrating2);
            ll_details2 = itemView.findViewById(R.id.ll_details2);


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
