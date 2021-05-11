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
import com.satrangolimitless.model.Offers_expiringsoon_model;
import com.satrangolimitless.model.Vendor_rating;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Vendor_rating_reviews extends RecyclerView.Adapter<Adapter_Vendor_rating_reviews.ViewHolder> {
    Session session1;
    private final List<Vendor_rating> category_home_models;
    private final Context context;


    public Adapter_Vendor_rating_reviews(ArrayList<Vendor_rating> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vendor_rating_review, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Vendor_rating allCommunityModel = category_home_models.get(position);


        holder.txtname.setText(category_home_models.get(position).getUsername());
        holder.txtrating.setText(category_home_models.get(position).getOverall_rating());
        holder.  txtbookingid.setText("Booking ID: "+category_home_models.get(position).getBooking_id());
        holder.txtdescrip.setText(category_home_models.get(position).getMessage());
        holder.   txtprofratings.setText(category_home_models.get(position).getProfessionalism_rating());
        holder.txtbhvrratings.setText(category_home_models.get(position).getBehaviour_rating());
        holder.txtskilrating.setText(category_home_models.get(position).getSkills_rating());
        Glide.with(context)
                .load(mediimage_url + allCommunityModel.getUser_image())
                .into(holder.imgprof);

System.out.println("ratinng  review image  ---   "+mediimage_url + allCommunityModel.getUser_image());

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
        TextView txtname,txtrating ,txtbookingid,txtdescrip,txtprofratings,txtbhvrratings,txtskilrating ;
        CircleImageView imgprof;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.txtname);
            imgprof = itemView.findViewById(R.id.imgprof);
            txtrating = itemView.findViewById(R.id.txtrating);
            txtbookingid = itemView.findViewById(R.id.txtbookingid);
            txtdescrip = itemView.findViewById(R.id.txtdescrip);
            txtprofratings = itemView.findViewById(R.id.txtprofratings);
            txtbhvrratings = itemView.findViewById(R.id.txtbhvrratings);
            txtskilrating = itemView.findViewById(R.id.txtskilrating);
        }
    }
}
