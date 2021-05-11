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

import com.satrangolimitless.R;
import com.satrangolimitless.model.User_Notification_model;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.List;

public class Adapter_User_Notifications extends RecyclerView.Adapter<Adapter_User_Notifications.ViewHolder> {
    Session session1;
    private final List<User_Notification_model> category_home_models;
    private final Context context;


    public Adapter_User_Notifications(ArrayList<User_Notification_model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_notification_alerts, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User_Notification_model allCommunityModel = category_home_models.get(position);


        holder.txtrowtext.setText(category_home_models.get(position).getMessage());

/*
        Glide.with(context)
                .load(mediimage_url+allCommunityModel.getIcon())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .dontTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .skipMemoryCache(true)
                .apply(new RequestOptions().override(600, 200)). into(holder.img_rowsmal);
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
        CardView l_cardview;
        ImageView img_rowsmal, heart, fav2;
        TextView txtrowtext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtrowtext = itemView.findViewById(R.id.txtmessage);
        }
    }
}
