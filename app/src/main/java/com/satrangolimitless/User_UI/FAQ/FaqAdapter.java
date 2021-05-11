package com.satrangolimitless.User_UI.FAQ;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.R;
import com.satrangolimitless.model.GetFaqModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {
    private List<GetFaqModel> allCommunityModels;
    private Context context;
    String user_id,product_id;
    private boolean isclick1 = false;


    public FaqAdapter(List<GetFaqModel> getFaqModels, Context context) {
        this.allCommunityModels = getFaqModels;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.faqlist, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
         GetFaqModel getFaqModel = allCommunityModels.get(position);
         holder.question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isclick1) {
                    holder.ans1.setVisibility(View.GONE);
                    holder.question1.setText(allCommunityModels.get(position).getQuestion());

                    holder.question1.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand, //left
                            0);

                    isclick1 = false;
                } else {
                    holder.ans1.setVisibility(View.VISIBLE);
                    holder.ans1.setText(allCommunityModels.get(position).getAnswer());
                    holder.ans1.setText(Html.fromHtml(Html.fromHtml(allCommunityModels.get(position).getAnswer()).toString()));
                    holder.question1.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_expand_less, //left
                            0);
                    isclick1 = true;
                }
            }
        });





    }

    @Override
    public int getItemCount() {
        return allCommunityModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question1,ans1;
        public ViewHolder(View parent) {
            super(parent);
            question1=parent.findViewById(R.id.question1);
            ans1=parent.findViewById(R.id.ans1);

        }
    }



}


