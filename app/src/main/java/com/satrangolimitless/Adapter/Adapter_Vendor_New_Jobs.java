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
import com.satrangolimitless.Vendor_UI.My_Bids.Activity_Vendor_PlaceBid;
import com.satrangolimitless.Vendor_UI.My_Bids.Activity_view_job_details_bids_discussions_vendr_side;
import com.satrangolimitless.model.Vendor_New_Jobs_model;
import com.satrangolimitless.session.Session_vendor;

import java.util.List;

public class Adapter_Vendor_New_Jobs extends RecyclerView.Adapter<Adapter_Vendor_New_Jobs.ViewHolder> {
    private final List<Vendor_New_Jobs_model> category_home_models;
    private final Context context;
    Session_vendor session1;


    public Adapter_Vendor_New_Jobs(List<Vendor_New_Jobs_model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_upcoming_job, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Vendor_New_Jobs_model allCommunityModel = category_home_models.get(position);

session1=new Session_vendor(context);

        holder.txtbids.setText(category_home_models.get(position).getTotal_bid());
        holder.txtminrange.setText("₹ " + category_home_models.get(position).getBid_min_range());
        holder.txtmaxrange.setText("-" + category_home_models.get(position).getBid_max_range());
        holder.txtexpires.setText(category_home_models.get(position).getAccept_bid_for());
        holder.txtdesc.setText(category_home_models.get(position).getDescription());

        if (category_home_models.get(position).getAvg_range() != null && !category_home_models.get(position).getAvg_range().isEmpty() && !category_home_models.get(position).getAvg_range().equals("null") && !category_home_models.get(position).getAvg_range().equals("0")) {

            holder.txtaverage.setText(category_home_models.get(position).getAvg_range());
        }else{
            holder.txtaverage.setText("0");
        }



        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, Activity_view_job_details_bids_discussions_vendr_side.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("jobid", category_home_models.get(position).getJob_id());
                intent.putExtra("avgeragebid", category_home_models.get(position).getAvg_range());
                intent.putExtra("date", category_home_models.get(position).getDate());
                intent.putExtra("time", category_home_models.get(position).getTime());
                intent.putExtra("est_time", category_home_models.get(position).getEstimate_time());
                intent.putExtra("desx", category_home_models.get(position).getDescription());
                intent.putExtra("min", category_home_models.get(position).getBid_min_range());
                intent.putExtra("max", category_home_models.get(position).getBid_max_range());
                intent.putExtra("totalbids", category_home_models.get(position).getTotal_bid());
                intent.putExtra("lang", category_home_models.get(position).getLanguage());
                intent.putExtra("attach", category_home_models.get(position).getAttachment());
                intent.putExtra("bid_per", category_home_models.get(position).getBid_per());

                session1.setPendongjob_id(category_home_models.get(position).getId());
System.out.println("vendor jobs list------   "+category_home_models.get(position).getId());
System.out.println("vendor jobs list@@@------   "+category_home_models.get(position).getJob_id());
                context.startActivity(intent);
            }
        });
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
            ll_placebid = itemView.findViewById(R.id.ll_placebid);

        }
    }
}
