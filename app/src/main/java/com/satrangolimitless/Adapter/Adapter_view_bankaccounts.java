package com.satrangolimitless.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.R;
import com.satrangolimitless.model.UserAccountsmodel;
import com.satrangolimitless.session.Session;

import java.util.List;

    public class Adapter_view_bankaccounts extends RecyclerView.Adapter<Adapter_view_bankaccounts.ViewHolder> {
    private final List<UserAccountsmodel> category_home_models;
    private final Context context;
    Session session1;
    int selectedPosition = -1;

    public Adapter_view_bankaccounts(List<UserAccountsmodel> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public Adapter_view_bankaccounts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bnkaccountuser, parent, false);
        return new Adapter_view_bankaccounts.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_view_bankaccounts.ViewHolder holder, final int position) {
        final UserAccountsmodel allCommunityModel = category_home_models.get(position);


        holder.txtacnumber.setText("A/c No. "+category_home_models.get(position).getAccount_number());
        holder.txtifsc.setText("IFSC: "+category_home_models.get(position).getIfsc_code());


        if (selectedPosition == position) {
            System.out.println("select pos  -----   ");
            holder.card.setCardBackgroundColor(Color.parseColor("#fdc400"));
            holder.llbacg.setBackgroundColor(Color.parseColor("#fdc400"));

        }else {
            System.out.println("deselect pos  -----   ");
            holder.card.setCardBackgroundColor(Color.parseColor("#ffffff"));
            holder.llbacg.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedPosition = position;
                notifyDataSetChanged();


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
        LinearLayout llbacg;
        ImageView vimage, heart, fav2;
        TextView txtacnumber, txtifsc, txttotaljob;
CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtacnumber = itemView.findViewById(R.id.txtacnumber);
            txtifsc = itemView.findViewById(R.id.txtifsc);
            card = itemView.findViewById(R.id.card);
            llbacg = itemView.findViewById(R.id.llbacg);
        }
    }
}
