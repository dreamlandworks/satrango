package com.satrangolimitless.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.R;

import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class AtachmentshowAdapter extends RecyclerView.Adapter<AtachmentshowAdapter.HeroViewHolder> {

    private final Context context;
    private final List<String> arrayList;

    public AtachmentshowAdapter(Context mCtx, List<String> arrayList) {
        this.context = mCtx;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public AtachmentshowAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_show_attachmentsmultijobpost, parent, false);
        return new AtachmentshowAdapter.HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AtachmentshowAdapter.HeroViewHolder holder, final int position) {



try {
System.out.println("image sdapter-----  "+arrayList.get(position));
    Glide.with(context)
            .load(Image_url+arrayList.get(position))
            .into(holder.imgatch);

}catch (Exception e ){e .printStackTrace();}


    }

    public void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView imgatch, imageView2;
        TextView mMedicine;

        public HeroViewHolder(View itemView) {
            super(itemView);
            imgatch = itemView.findViewById(R.id.imgatch);

        }
    }
}




