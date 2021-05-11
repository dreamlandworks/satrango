package com.satrangolimitless.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Search.SearchFragment;
import com.satrangolimitless.model.Services_Popularactivity_Model;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Popular_ServicesActivity extends RecyclerView.Adapter<Adapter_Popular_ServicesActivity.ViewHolder> {
    Session session1;
    private final List<Services_Popularactivity_Model> category_home_models;
    private final Context context;


    public Adapter_Popular_ServicesActivity(ArrayList<Services_Popularactivity_Model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_services_browse_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Services_Popularactivity_Model allCommunityModel = category_home_models.get(position);

        session1=new Session(context);
        holder.txtrowtext.setText(category_home_models.get(position).getName());

        Glide.with(context)
                .load(mediimage_url + allCommunityModel.getIcon())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .dontTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .skipMemoryCache(true)
                .apply(new RequestOptions().override(600, 200)).into(holder.img_rowsmal);

        holder.txtrowtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchFragment fragment = new SearchFragment();
                Bundle b = new Bundle();
                session1.setBookingcategory(category_home_models.get(position).getCat_name());
                session1.setKeyword(category_home_models.get(position).getName());
                b.putString("data", "search");
                  fragment.setArguments(b);

                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();

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
        CardView l_cardview;
        ImageView img_rowsmal, heart, fav2;
        TextView txtrowtext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_rowsmal = itemView.findViewById(R.id.img_rowsmal);
            txtrowtext = itemView.findViewById(R.id.txtrowtext);
        }
    }
}
