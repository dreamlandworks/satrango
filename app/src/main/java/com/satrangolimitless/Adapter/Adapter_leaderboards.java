package com.satrangolimitless.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Leaderboardsmodel;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_leaderboards extends RecyclerView.Adapter<Adapter_leaderboards.ViewHolder> {
    private final List<Leaderboardsmodel> category_home_models;
    private final Context context;
    Session session1;


    public Adapter_leaderboards(ArrayList<Leaderboardsmodel> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vendor_home_leaderboards, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Leaderboardsmodel allCommunityModel = category_home_models.get(position);


        holder.txtname.setText(category_home_models.get(position).getFname());
        holder.txtrank.setText(category_home_models.get(position).getRank());

        Glide.with(context)
                .load(mediimage_url + allCommunityModel.getImage())
                .into(holder.imgprofile);


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
        ImageView imgprofile, heart, fav2;
        TextView txtrank ,txtname   ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             txtrank = itemView.findViewById(R.id.txtrank);
            txtname = itemView.findViewById(R.id.txtname);
            imgprofile = itemView.findViewById(R.id.imgprofile);
        }

    }


}
