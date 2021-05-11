package com.satrangolimitless.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.PopularcategoriesActivity;
import com.satrangolimitless.R;

import com.satrangolimitless.model.toppicksHome_Model;
import com.satrangolimitless.session.Session;

import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Home_Toppicks extends RecyclerView.Adapter<Adapter_Home_Toppicks.ViewHolder> {
    private final List<toppicksHome_Model> category_home_models;
    private final Context context;
    Session session1;


    public Adapter_Home_Toppicks(List<toppicksHome_Model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public Adapter_Home_Toppicks.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tophired_homeuser, parent, false);
        return new Adapter_Home_Toppicks.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_Home_Toppicks.ViewHolder holder, final int position) {
        final toppicksHome_Model allCommunityModel = category_home_models.get(position);


        holder.txtname.setText(category_home_models.get(position).getName());
        holder.txtrating.setText(category_home_models.get(position).getSkills());
        holder.txttotaljob.setText(category_home_models.get(position).getTotal_job());

        Glide.with(context)
                .load(Image_url + allCommunityModel.getIcon())
              .into(holder.vimage);



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
        LinearLayout ll_rowcat;
        ImageView vimage, heart, fav2;
        TextView txtname,txtrating,txttotaljob;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vimage = itemView.findViewById(R.id.vimage);
            txtname = itemView.findViewById(R.id.txtname);
            txtrating = itemView.findViewById(R.id.txtrating);
            txttotaljob = itemView.findViewById(R.id.txttotaljob);

        }
    }
}
