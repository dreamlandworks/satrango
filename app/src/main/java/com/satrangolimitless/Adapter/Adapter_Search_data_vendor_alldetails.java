package com.satrangolimitless.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Booknow.BooknowSingleMoveActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Search.SearchFragment;
import com.satrangolimitless.model.Search_Data_Model_Vendor_Alldetails;
import com.satrangolimitless.session.Session;

import java.util.List;


public class Adapter_Search_data_vendor_alldetails extends RecyclerView.Adapter<Adapter_Search_data_vendor_alldetails.ViewHolder> {
    Session session;
    Fragment fragment;
    private final List<Search_Data_Model_Vendor_Alldetails> search_data_models;
    private final Context context;

    public Adapter_Search_data_vendor_alldetails(List<Search_Data_Model_Vendor_Alldetails> search_data_models, Context context) {
        this.search_data_models = search_data_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vendor_viewall_details, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Search_Data_Model_Vendor_Alldetails allCommunityModel = search_data_models.get(position);

        session = new Session(context);


//        Glide.with(context)
//                .load(mediimage_url+allCommunityModel.getIcon())
//                .apply(new RequestOptions()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .dontAnimate()
//                        .centerCrop()
//                        .dontTransform())
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
//                .skipMemoryCache(true)
//                .apply(new RequestOptions().override(600, 200)). into(holder.img_cat);

        //holder.img_recomend.setVisibility(View.GONE);
//        search_data_models.get(position).getCat_id()

        holder.txt_uname.setText(search_data_models.get(position).getName());
        holder.txtlanguage.setText(search_data_models.get(position).getLanguage());
        holder.txtminamount.setText(search_data_models.get(position).getMin_amount());

        holder.txtamount.setText(search_data_models.get(position).getMax_amount());
        holder.txtamount.setPaintFlags(holder.txtamount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.txtskill.setText(search_data_models.get(position).getService_name());
        holder.txtjobskill.setText(search_data_models.get(position).getService_name());


        String myString = search_data_models.get(position).getDistance();
        Float foo = Float.parseFloat(myString);
        Float i2 = foo / 1000;

//        holder.txt_diskms.setText(new DecimalFormat("##.##").format(i2)+" kms away");

        holder.txtabout.setText(search_data_models.get(position).getAbout());
        try {

            holder.txtjobs.setText(search_data_models.get(position).getJobs());
            holder.txt_rating.setText(search_data_models.get(position).getRating());
            holder.ratingBar.setRating(Float.parseFloat(search_data_models.get(position).getProfessionalism_rating()));
            holder.rating_equisite_skills.setRating(Float.parseFloat(search_data_models.get(position).getSkills_rating()));
            holder.rating_satisfaction.setRating(Float.parseFloat(search_data_models.get(position).getSatisfaction_rating()));
            holder.rating_behaviour.setRating(Float.parseFloat(search_data_models.get(position).getBehaviour_rating()));

            holder.txtrating_professionalism.setText(search_data_models.get(position).getProfessionalism_rating());
            holder.txtrating_behaviour.setText(search_data_models.get(position).getBehaviour_rating());
            holder.txtrating_satisfaction.setText(search_data_models.get(position).getSatisfaction_rating());
            holder.txtrating_equisite_skills.setText(search_data_models.get(position).getSkills_rating());

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.AG_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                SearchFragment fragment = new SearchFragment();
                Bundle b = new Bundle();

                b.putString("data", "search");
                fragment.setArguments(b);

                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();



            }

        });
        holder.AG_BOOKNOW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.setBookvid(search_data_models.get(position).getId());
                session.setBookvname(search_data_models.get(position).getName());
                session.setBookminam(search_data_models.get(position).getMin_amount());
                session.setBookmaxam(search_data_models.get(position).getMax_amount());
                session.setBookrating(search_data_models.get(position).getRating());
                session.setBookservice(search_data_models.get(position).getService_name());
                session.setBookdis(search_data_models.get(position).getDistance());
                Intent intent = new Intent(context, BooknowSingleMoveActivity.class);
                context.startActivity(intent);
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
        TextView txt_uname, txt_u_rank, txtskill, txtlanguage, txtjobskill,
                txt_rating, txtjobs, txt_diskms, txtminamount, txtamount,
                txtknowmore, txt_booknow, AG_Close, txtabout, AG_BOOKNOW;

        TextView txtrating_professionalism, txtrating_behaviour, txtrating_satisfaction, txtrating_equisite_skills;
        AppCompatRatingBar ratingBar, rating_equisite_skills, rating_satisfaction,
                rating_behaviour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.img_user);
            img_recomend = itemView.findViewById(R.id.img_recomend);
            txt_uname = itemView.findViewById(R.id.txt_uname);
            txt_u_rank = itemView.findViewById(R.id.txt_u_rank);
            txtskill = itemView.findViewById(R.id.txtskill);
            txtlanguage = itemView.findViewById(R.id.txtlanguage);
            txtjobskill = itemView.findViewById(R.id.txtjobskill);
            txt_rating = itemView.findViewById(R.id.txt_rating);
            txtjobs = itemView.findViewById(R.id.txtjobs);
            txt_diskms = itemView.findViewById(R.id.txt_diskms);
            txtminamount = itemView.findViewById(R.id.txtminamount);
            txtamount = itemView.findViewById(R.id.txtamount);
            txtknowmore = itemView.findViewById(R.id.txtknowmore);
            txt_booknow = itemView.findViewById(R.id.txt_booknow);
            ratingBar = itemView.findViewById(R.id.rating_professionalism);
            rating_equisite_skills = itemView.findViewById(R.id.rating_equisite_skills);
            rating_satisfaction = itemView.findViewById(R.id.rating_satisfaction);
            rating_behaviour = itemView.findViewById(R.id.rating_behaviour);
            txtrating_professionalism = itemView.findViewById(R.id.txtrating_professionalism);
            txtrating_behaviour = itemView.findViewById(R.id.txtrating_behaviour);
            txtrating_satisfaction = itemView.findViewById(R.id.txtrating_satisfaction);
            txtrating_equisite_skills = itemView.findViewById(R.id.txtrating_equisite_skills);
            txtabout = itemView.findViewById(R.id.txtabout);
            AG_Close = itemView.findViewById(R.id.AG_Close);
            AG_BOOKNOW = itemView.findViewById(R.id.AG_BOOKNOW);

        }
    }
}
