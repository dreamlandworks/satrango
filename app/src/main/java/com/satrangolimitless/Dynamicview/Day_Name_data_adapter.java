package com.satrangolimitless.Dynamicview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.satrangolimitless.R;

import java.util.List;

public class Day_Name_data_adapter extends RecyclerView.Adapter<   Day_Name_data_adapter.HeroViewHolder> {

    private Context context;
    private List<String> arrayList;

    public Day_Name_data_adapter(Context mCtx, List<String> arrayList) {
        this.context = mCtx;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public    HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_item_slot, parent, false);
        return new   HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull   HeroViewHolder holder, final int position) {


        holder.mMedicine.setText(arrayList.get(position));

        holder.mMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                removeAt(position);

            }
        });


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

        ImageView imageView,imageView2;
        TextView mMedicine;

        public HeroViewHolder(View itemView) {
            super(itemView);
            mMedicine=itemView.findViewById(R.id.size);

        }
    }
}




