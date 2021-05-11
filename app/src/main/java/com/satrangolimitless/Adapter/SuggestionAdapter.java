package com.satrangolimitless.Adapter;

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

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.HeroViewHolder> {

    private final Context context;
    private final List<String> arrayList;

    public SuggestionAdapter(Context mCtx, List<String> arrayList) {
        this.context = mCtx;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public SuggestionAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_suggestion, parent, false);
        return new SuggestionAdapter.HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionAdapter.HeroViewHolder holder, final int position) {


        holder.mMedicine.setText(arrayList.get(position));

        holder.mMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);

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

        ImageView imageView, imageView2;
        TextView mMedicine;

        public HeroViewHolder(View itemView) {
            super(itemView);
            mMedicine = itemView.findViewById(R.id.mMedicine);

        }
    }
}




