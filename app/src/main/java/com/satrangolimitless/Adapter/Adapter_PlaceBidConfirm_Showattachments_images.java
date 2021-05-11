package com.satrangolimitless.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.R;
import com.satrangolimitless.model.SubjectData;
import com.satrangolimitless.session.Session;

import java.io.IOException;
import java.util.ArrayList;


public class Adapter_PlaceBidConfirm_Showattachments_images extends RecyclerView.Adapter<Adapter_PlaceBidConfirm_Showattachments_images.ViewHolder> {
    Session session1;
    Bitmap bm = null;
    private final ArrayList<SubjectData> category_home_models;
    private final Context context;


    public Adapter_PlaceBidConfirm_Showattachments_images(ArrayList<SubjectData> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public Adapter_PlaceBidConfirm_Showattachments_images.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attachment_images, parent, false);
        return new Adapter_PlaceBidConfirm_Showattachments_images.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_PlaceBidConfirm_Showattachments_images.ViewHolder holder, final int position) {
        final SubjectData allCommunityModel = category_home_models.get(position);


        Uri myUri = Uri.parse(category_home_models.get(position).getImage());
        System.out.println("IMAGES------     " + category_home_models.get(position).getImage());

        try {
            bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.img_cat.setImageBitmap(bm);


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
        ImageView img_cat, heart, fav2;
        TextView txtcat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cat = itemView.findViewById(R.id.list_image);

        }
    }
}
