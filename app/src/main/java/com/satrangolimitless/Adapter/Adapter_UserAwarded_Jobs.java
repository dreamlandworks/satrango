package com.satrangolimitless.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.My_Job_Posts.Activity_User_jobs_awarded_details;
import com.satrangolimitless.User_UI.My_Job_Posts.Activity_User_jobs_pending_details;
import com.satrangolimitless.model.UserAwardedJobs_model;
import com.satrangolimitless.model.Vendor_Bidsawarded_model;
import com.satrangolimitless.session.Session;

import java.util.List;

public class Adapter_UserAwarded_Jobs extends RecyclerView.Adapter<Adapter_UserAwarded_Jobs.ViewHolder> {
    private final List<UserAwardedJobs_model> category_home_models;
    private final Context context;
    Session session1;


    public  Adapter_UserAwarded_Jobs(List<UserAwardedJobs_model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_userjobawarded, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final UserAwardedJobs_model allCommunityModel = category_home_models.get(position);

session1=new Session(context);
//        holder.txtaverage.setText(category_home_models.get(position).getAvg_range());
        holder.txtbid_amount.setText("₹ "+category_home_models.get(position).getBid_amount());
        holder.txtbid_min_range.setText("₹ " + category_home_models.get(position).getBid_min_range());
        holder.txtbid_max_range.setText("- " + category_home_models.get(position).getBid_max_range());
        holder.txtv_name.setText(category_home_models.get(position).getV_name());
        holder.txtdescription.setText(category_home_models.get(position).getDescription());


        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_User_jobs_awarded_details.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("lang", category_home_models.get(position).getLanguage());
                intent.putExtra("desc", category_home_models.get(position).getDescription());
                intent.putExtra("date", category_home_models.get(position).getCreated_date());
//                intent.putExtra("time", category_home_models.get(position).getTime());
                intent.putExtra("esttime", category_home_models.get(position).getEstimated_time());
                intent.putExtra("bidper", category_home_models.get(position).getBid_per());
                intent.putExtra("avgrange", category_home_models.get(position).getAvg_range());
                intent.putExtra("bid", category_home_models.get(position).getTotal_bid());
                intent.putExtra("attachment", category_home_models.get(position).getAttachment());
                intent.putExtra("aceptbidfor", category_home_models.get(position).getAccept_bid_for());
                intent.putExtra("minrange", category_home_models.get(position).getBid_min_range());
                intent.putExtra("maxrange", category_home_models.get(position).getBid_max_range());


                session1.setAwardedsecjob_id_bids(category_home_models.get(position).getJob_id());
                context.startActivity(intent);
            }
        });


/*
        holder.ll_placebid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Activity_Vendor_PlaceBid.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("jobid", category_home_models.get(position).getJob_id());
                intent.putExtra("avgeragebid", category_home_models.get(position).getAvg_range());
                intent.putExtra("date", category_home_models.get(position).getDate());
                intent.putExtra("time", category_home_models.get(position).getTime());
                intent.putExtra("est_time", category_home_models.get(position).getEstimate_time());
                intent.putExtra("desx", category_home_models.get(position).getDescription());

                context.startActivity(intent);
            }
        });

*/

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
        LinearLayout ll_details, ll_placebid;
        TextView txtbid_amount,txtv_name,txtdescription,txtbid_max_range,txtbid_min_range;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtbid_amount = itemView.findViewById(R.id.txtbid_amount);
            txtv_name = itemView.findViewById(R.id.txtv_name);
             txtdescription = itemView.findViewById(R.id.txtdescription);
            txtbid_max_range = itemView.findViewById(R.id.txtbid_max_range);
            txtbid_min_range = itemView.findViewById(R.id.txtbid_min_range);

            ll_details = itemView.findViewById(R.id.ll_details);


        }
    }
}
