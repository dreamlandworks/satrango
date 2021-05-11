package com.satrangolimitless.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Complaintsrequestmodel;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Complaintrequests extends RecyclerView.Adapter<Adapter_Complaintrequests.ViewHolder> {
    private final List<Complaintsrequestmodel> category_home_models;
    private final Context context;
    Session session1;


    public Adapter_Complaintrequests(ArrayList<Complaintsrequestmodel> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_complaint_requests, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Complaintsrequestmodel allCommunityModel = category_home_models.get(position);


        holder.txtmessages.setText(category_home_models.get(position).getMessage());




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
        CardView l_cardview;
        ImageView imagess, heart, fav2;
        TextView txtdate,txtrequestid,txtstats,txtmessages;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtrequestid=itemView.findViewById(R.id.txtrequestid);
            txtstats=itemView.findViewById(R.id.txtstats);
            txtmessages=itemView.findViewById(R.id.txtmessages);

        }
    }
}
