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
import com.satrangolimitless.model.User_Notification_model;
import com.satrangolimitless.model.Vendor_Notification_model;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Vendor_Notifications extends RecyclerView.Adapter<Adapter_Vendor_Notifications.ViewHolder> {
    Session session1;
    private final List<Vendor_Notification_model> category_home_models;
    private final Context context;


    public Adapter_Vendor_Notifications(ArrayList<Vendor_Notification_model> category_home_models, Context context) {
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
        final Vendor_Notification_model allCommunityModel = category_home_models.get(position);


        holder.txtrowtext.setText(category_home_models.get(position).getMessage());
        holder.txtdate.setText(category_home_models.get(position).getCreated_date());

         Glide.with(context)
                .load(Image_url + category_home_models.get(position).getImage())
                .into(holder.imgpro);

System.out.println("notificatoion image-------     "+category_home_models.get(position).getImage());
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
        CircleImageView imgpro;
        ImageView img_rowsmal, heart, fav2;
        TextView txtrowtext,txtdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtrowtext = itemView.findViewById(R.id.txtmessage);
            txtdate = itemView.findViewById(R.id.txtdate);
            imgpro = itemView.findViewById(R.id.imgpro);
        }
    }
}
