package com.satrangolimitless.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.R;

import com.satrangolimitless.User_UI.Search.SearchFragment;
import com.satrangolimitless.model.Services_Home_Model;
import com.satrangolimitless.session.Session;

import java.util.ArrayList;
import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.mediimage_url;

public class Adapter_Home_Services extends RecyclerView.Adapter< Adapter_Home_Services.ViewHolder> {
    private List<Services_Home_Model> category_home_models;
    private Context context;
    Session session1;
   Fragment fragment;


    public Adapter_Home_Services(ArrayList<Services_Home_Model> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public  Adapter_Home_Services.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_services_home, parent, false);
        return new  Adapter_Home_Services.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final  Adapter_Home_Services.ViewHolder holder, final int position) {
        final Services_Home_Model allCommunityModel = category_home_models.get(position);

        session1=new Session(context);
        holder.txtcat.setText(category_home_models.get(position).getName());

        Glide.with(context)
                .load(mediimage_url+allCommunityModel.getIcon()). into(holder.img_cat);

holder.ll_row.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        SearchFragment fragment = new SearchFragment();
        Bundle b = new Bundle();
        session1.setBookingcategory(category_home_models.get(position).getCat_name());
        session1.setKeyword(category_home_models.get(position).getName());
        b.putString("data", "search");


        fragment.setArguments(b);

        ((FragmentActivity) context ).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();


    }
});



    }
    


    @Override
    public int getItemCount() {
        return category_home_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_row;
        CardView l_cardview;
        ImageView img_cat,heart,fav2;
        TextView txtcat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cat=itemView.findViewById(R.id.img_cat);
            txtcat=itemView.findViewById(R.id.txtcat);
            ll_row=itemView.findViewById(R.id.ll_row);
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
