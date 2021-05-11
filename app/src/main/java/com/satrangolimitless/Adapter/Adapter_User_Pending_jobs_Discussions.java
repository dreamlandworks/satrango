package com.satrangolimitless.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.UserchatdetailsActivity;
import com.satrangolimitless.model.User_job_discussions;
import com.satrangolimitless.session.Session_vendor;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_User_Pending_jobs_Discussions extends RecyclerView.Adapter<Adapter_User_Pending_jobs_Discussions.ViewHolder> {
    private final List<User_job_discussions> category_home_models;
    private final Context context;
    Session_vendor session1;
    String e1 = "", e2 = "", e3 = "", e4 = "", otp = "";
    String vid, bookingid;
    private EditText[] editTexts;

    public Adapter_User_Pending_jobs_Discussions(List<User_job_discussions> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_bid_discussion, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User_job_discussions allCommunityModel = category_home_models.get(position);

        session1 = new Session_vendor(context);

        holder.txtproposal.setText(category_home_models.get(position).getVendor_msg());
        holder.txtvname.setText(category_home_models.get(position).getV_name());
        holder.ll_discussuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, UserchatdetailsActivity.class);
                intent.putExtra("vendorid", category_home_models.get(position).getVendor_id());
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
        LinearLayout ll_discussuser, ll_statusbacg;
        ImageView img_call, heart, fav2;
        TextView txtestimatetime, txtproposal, txtvname;
        CircleImageView imageprofile;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtestimatetime = itemView.findViewById(R.id.txtestimatetime);
            txtproposal = itemView.findViewById(R.id.txtproposal);
            txtvname = itemView.findViewById(R.id.txtvname);
            ll_discussuser = itemView.findViewById(R.id.ll_discussuser);
        }
    }


}
