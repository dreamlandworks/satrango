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
import com.satrangolimitless.User_UI.My_Job_Posts.Activity_User_jobs_pending_details;
import com.satrangolimitless.model.User_PendingJobs_model;
import com.satrangolimitless.session.Session;

import java.util.List;

public class Adapter_User_Pending_Jobs extends RecyclerView.Adapter<Adapter_User_Pending_Jobs.ViewHolder> {
    private final List<User_PendingJobs_model> category_home_models;
    private final Context context;
    Session session1;


    public Adapter_User_Pending_Jobs(List<User_PendingJobs_model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_userjobpostpending, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User_PendingJobs_model allCommunityModel = category_home_models.get(position);

        session1=new Session(context);
        holder.txtbids.setText(category_home_models.get(position).getTotal_bid());
        holder.txtminrange.setText("₹ " + category_home_models.get(position).getBid_min_range()+"-");
        holder.txtmaxrange.setText("₹ " + category_home_models.get(position).getBid_max_range());
        holder.txtexpires.setText(category_home_models.get(position).getAccept_bid_for());
        holder.txtdesc.setText(category_home_models.get(position).getDescription());
        if (category_home_models.get(position).getAvg_range() != null && !category_home_models.get(position).getAvg_range().isEmpty() && !category_home_models.get(position).getAvg_range().equals("null") && !category_home_models.get(position).getAvg_range().equals("0")) {
            holder.txtaverage.setText(category_home_models.get(position).getAvg_range());


        }else {
            holder.txtaverage.setText("0");

        }


        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, Activity_User_jobs_pending_details.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("lang", category_home_models.get(position).getLanguage());
                intent.putExtra("desc", category_home_models.get(position).getDescription());
                intent.putExtra("date", category_home_models.get(position).getDate());
                intent.putExtra("time", category_home_models.get(position).getTime());
                intent.putExtra("esttime", category_home_models.get(position).getEstimate_time());
                intent.putExtra("bidper", category_home_models.get(position).getBid_per());
                intent.putExtra("avgrange", category_home_models.get(position).getAvg_range());
                intent.putExtra("bid", category_home_models.get(position).getTotal_bid());
                intent.putExtra("attachment", category_home_models.get(position).getAttachment());
                intent.putExtra("aceptbidfor", category_home_models.get(position).getAccept_bid_for());
                intent.putExtra("minrange", category_home_models.get(position).getBid_min_range());
                intent.putExtra("maxrange", category_home_models.get(position).getBid_max_range());


                session1.setPendongjob_id(category_home_models.get(position).getId());
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
        TextView txtaverage, txtbids, txtdesc, txtexpires, txtmaxrange, txtminrange;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtaverage = itemView.findViewById(R.id.txtaverage);
            txtbids = itemView.findViewById(R.id.txtbids);
            txtdesc = itemView.findViewById(R.id.txtdesc);
            txtexpires = itemView.findViewById(R.id.txtexpires);
            txtmaxrange = itemView.findViewById(R.id.txtmaxrange);
            txtminrange = itemView.findViewById(R.id.txtminrange);
            ll_details = itemView.findViewById(R.id.ll_details);

        }
    }
}
