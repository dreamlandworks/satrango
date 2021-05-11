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
import com.satrangolimitless.Vendor_UI.My_Bids.Activity_Vendor_PlaceBidFulldetails;
import com.satrangolimitless.model.Vendor_Bidsawarded_model;
import com.satrangolimitless.model.Vendor_Bidsplaced_model;
import com.satrangolimitless.session.Session;

import java.util.List;

    public class Adapter_VendorBidsplaced extends RecyclerView.Adapter<Adapter_VendorBidsplaced.ViewHolder> {
    private final List<Vendor_Bidsplaced_model> category_home_models;
    private final Context context;
    Session session1;


    public Adapter_VendorBidsplaced(List<Vendor_Bidsplaced_model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vendor_bids_placed, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Vendor_Bidsplaced_model allCommunityModel = category_home_models.get(position);


        holder.txtaverage.setText(category_home_models.get(position).getAvg_range());
        holder.txtbids.setText(category_home_models.get(position).getTotal_bid());
        holder.txtminrange.setText("₹ " + category_home_models.get(position).getBid_min_range());
        holder.txtmaxrange.setText("- " + category_home_models.get(position).getBid_max_range());
//        holder.txtexpires.setText(category_home_models.get(position).getAccept_bid_for());
        holder.txtdesc.setText(category_home_models.get(position).getDescription());


        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, Activity_Vendor_PlaceBidFulldetails.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("jobid", category_home_models.get(position).getJob_id());
                intent.putExtra("avgeragebid", category_home_models.get(position).getAvg_range());
                intent.putExtra("date", category_home_models.get(position).getCreated_date());
                intent.putExtra("attachment", category_home_models.get(position).getAttachment());
                intent.putExtra("est_time", category_home_models.get(position).getEstimated_time());
                intent.putExtra("desx", category_home_models.get(position).getDescription());
                intent.putExtra("proposal", category_home_models.get(position).getProposal());
                intent.putExtra("bid_amount", category_home_models.get(position).getBid_amount());
                intent.putExtra("submissiontype", category_home_models.get(position).getSubmission_type());

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
