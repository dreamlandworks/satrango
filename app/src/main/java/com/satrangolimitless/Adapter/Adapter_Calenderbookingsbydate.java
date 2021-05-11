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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Calender_bookingbydate;
import com.satrangolimitless.model.Tailormadeoffers_model;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Calenderbookingsbydate extends RecyclerView.Adapter<Adapter_Calenderbookingsbydate.ViewHolder> {
    Session session1;
    private final List<Calender_bookingbydate> category_home_models;
    private final Context context;


    public Adapter_Calenderbookingsbydate(ArrayList<Calender_bookingbydate> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_calendar_timeslot, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Calender_bookingbydate allCommunityModel = category_home_models.get(position);


        holder.txtfrmtime.setText(category_home_models.get(position).getFrom_time());
        holder.txtbookiddesc.setText(category_home_models.get(position).getBookingId()+" "+category_home_models.get(position).getWork_description());
System.out.println("time >>>>>>>>>>>       "+category_home_models.get(position).getFrom_time());


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
        ImageView image3, heart, fav2;
        TextView txtfrmtime,txtbookiddesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtbookiddesc=itemView.findViewById(R.id.txtbookiddesc);
            txtfrmtime = itemView.findViewById(R.id.txtfrmtime);
        }
    }
}
