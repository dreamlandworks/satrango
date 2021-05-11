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
import com.satrangolimitless.model.Categories_Popularactivity_Model;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Popular_categoriesActivity extends RecyclerView.Adapter<Adapter_Popular_categoriesActivity.ViewHolder> {
    Session session1;
    private final List<Categories_Popularactivity_Model> category_home_models;
    private final Context context;


    public Adapter_Popular_categoriesActivity(ArrayList<Categories_Popularactivity_Model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_small_popular_services, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Categories_Popularactivity_Model allCommunityModel = category_home_models.get(position);


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
