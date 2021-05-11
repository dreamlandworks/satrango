package com.satrangolimitless.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
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
import com.satrangolimitless.Booknow.Activity_Blue_Collar_booking;
import com.satrangolimitless.Booknow.Activity_Multimove_booking;
import com.satrangolimitless.Booknow.BooknowSingleMoveActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Search.SearchFragment_vendor_alldetails;
import com.satrangolimitless.model.Search_Data_Model;
import com.satrangolimitless.model.Search_Data_Model1;
import com.satrangolimitless.session.Session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.satrangolimitless.Utils.Base_Url.mediimage_url;


public class Adapter_Search_dataonly extends RecyclerView.Adapter<Adapter_Search_dataonly.ViewHolder> {
    Session session;
    private final List<Search_Data_Model1> search_data_models;
    private final Context context;


    public Adapter_Search_dataonly(List<Search_Data_Model1> search_data_models, Context context) {
        this.search_data_models = search_data_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_data, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Search_Data_Model1 allCommunityModel = search_data_models.get(position);


        session = new Session(context);

        Glide.with(context)
                .load(mediimage_url+allCommunityModel.getImage()). into(holder.img_user);


        System.out.println("Adapter_Search_dataonly ---    "+mediimage_url+allCommunityModel.getImage());
        System.out.println("Adapter_Search_dataonly rating ---    " +allCommunityModel.getRating());
        //holder.img_recomend.setVisibility(View.GONE);
//        search_data_models.get(position).getCat_id()


        String myString = search_data_models.get(position).getDistance();
        Float foo = Float.parseFloat(myString);
        Float i2 = foo / 1000;




        double a = Double.valueOf(myString);
        BigDecimal bd = new BigDecimal(a);
        BigDecimal res = bd.setScale(2, RoundingMode.DOWN);
        System.out.println("kkkk   ===     " + res.toPlainString());
        System.out.println("kmmmmDISTANCE        " + i2);
        holder.txt_diskms.setText(res.toPlainString()+"kms");

        holder.txt_uname.setText(search_data_models.get(position).getName());
        holder.txtlanguage.setText(search_data_models.get(position).getLanguage());
        holder.txtminamount.setText("₹ " + search_data_models.get(position).getMin_amount());
        holder.txtamount.setText("₹ " + search_data_models.get(position).getMax_amount());
        holder.txtamount.setPaintFlags(holder.txtamount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtskill.setText(search_data_models.get(position).getService_name());
        holder.txtspecialist.setText(search_data_models.get(position).getSubservice());
        holder.txtjobs.setText(search_data_models.get(position).getJobs());

        holder.txt_rating.setText(search_data_models.get(position).getRating());
        final String category = session.getBookingcategory();

        System.out.println("CATEGORY-----        " + category);

        holder.txt_booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (category.contains("Single Move")) {

                    session.setBookvid(search_data_models.get(position).getId());
                    session.setBookvname(search_data_models.get(position).getName());
                    session.setBookminam(search_data_models.get(position).getMin_amount());
                    session.setBookmaxam(search_data_models.get(position).getMax_amount());
                    session.setBookrating(search_data_models.get(position).getRating());
                    session.setBookservice(search_data_models.get(position).getService_name());
                    session.setBookdis(search_data_models.get(position).getDistance());
                    session.setBookcat(search_data_models.get(position).getCat_id());

                    Intent intent = new Intent(context, BooknowSingleMoveActivity.class);
                    context.startActivity(intent);
                } else if (category.contains("Blue collar")) {

                    session.setBookvid(search_data_models.get(position).getId());
                    session.setBookvname(search_data_models.get(position).getName());
                    session.setBookminam(search_data_models.get(position).getMin_amount());
                    session.setBookmaxam(search_data_models.get(position).getMax_amount());
                    session.setBookrating(search_data_models.get(position).getRating());
                    session.setBookservice(search_data_models.get(position).getService_name());
                    session.setBookdis(search_data_models.get(position).getDistance());
                    session.setBookcat(search_data_models.get(position).getCat_id());
                    Intent intent = new Intent(context, Activity_Blue_Collar_booking.class);
                    context.startActivity(intent);

                } else if (category.contains("Multi Move")) {

                    session.setBookvid(search_data_models.get(position).getId());
                    session.setBookvname(search_data_models.get(position).getName());
                    session.setBookminam(search_data_models.get(position).getMin_amount());
                    session.setBookmaxam(search_data_models.get(position).getMax_amount());
                    session.setBookrating(search_data_models.get(position).getRating());
                    session.setBookservice(search_data_models.get(position).getService_name());
                    session.setBookdis(search_data_models.get(position).getDistance());
                    session.setBookcat(search_data_models.get(position).getCat_id());
                    Intent intent = new Intent(context, Activity_Multimove_booking.class);
                    context.startActivity(intent);

                } else {

                }


            }
        });


        holder.txtknowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchFragment_vendor_alldetails fragment = new SearchFragment_vendor_alldetails();

                Bundle bundle = new Bundle();

                bundle.putString("data", "search");
                fragment.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return search_data_models.size();
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
        ImageView img_user, img_recomend;
        TextView txt_uname, txt_u_rank, txtskill, txtlanguage, txtspecialist,
                txt_rating, txtjobs, txt_diskms, txtminamount, txtamount,
                txtknowmore, txt_booknow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.img_user);
            img_recomend = itemView.findViewById(R.id.img_recomend);
            txt_uname = itemView.findViewById(R.id.txt_uname);
            txt_u_rank = itemView.findViewById(R.id.txt_u_rank);
            txtskill = itemView.findViewById(R.id.txtskill);
            txtlanguage = itemView.findViewById(R.id.txtlanguage);
            txtspecialist = itemView.findViewById(R.id.txtspecialist);
            txt_rating = itemView.findViewById(R.id.txt_rating);
            txtjobs = itemView.findViewById(R.id.txtjobs);
            txt_diskms = itemView.findViewById(R.id.txt_diskms);
            txtminamount = itemView.findViewById(R.id.txtminamount);
            txtamount = itemView.findViewById(R.id.txtamount);
            txtknowmore = itemView.findViewById(R.id.txtknowmore);
            txt_booknow = itemView.findViewById(R.id.txt_booknow);
        }
    }
}
