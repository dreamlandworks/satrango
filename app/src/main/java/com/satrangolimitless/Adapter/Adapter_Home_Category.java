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
import com.satrangolimitless.model.Category_Home_Model;
import com.satrangolimitless.session.Session;

import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Home_Category extends RecyclerView.Adapter<Adapter_Home_Category.ViewHolder> {
    private final List<Category_Home_Model> category_home_models;
    private final Context context;
    Session session1;


    public Adapter_Home_Category(List<Category_Home_Model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public Adapter_Home_Category.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_home, parent, false);
        return new Adapter_Home_Category.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_Home_Category.ViewHolder holder, final int position) {
        final Category_Home_Model allCommunityModel = category_home_models.get(position);


        holder.txtcat.setText(category_home_models.get(position).getName());

        Glide.with(context)
                .load(mediimage_url + allCommunityModel.getIcon())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .dontTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .skipMemoryCache(true)
                .apply(new RequestOptions().override(600, 200)).into(holder.img_cat);


        holder.ll_rowcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PopularcategoriesActivity.class);
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
        CardView l_cardview;
        LinearLayout ll_rowcat;
        ImageView img_cat, heart, fav2;
        TextView txtcat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cat = itemView.findViewById(R.id.img_cat);
            txtcat = itemView.findViewById(R.id.txtcat);
            ll_rowcat = itemView.findViewById(R.id.ll_rowcat);
        }
    }
}
