package com.satrangolimitless.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.R;
import com.satrangolimitless.model.Transactionhistrymodel;
import com.satrangolimitless.model.UserAccountsmodel;
import com.satrangolimitless.session.Session;

import java.util.List;

public class Adapter_Transactionhistory extends RecyclerView.Adapter<Adapter_Transactionhistory.ViewHolder> {
private final List<Transactionhistrymodel> category_home_models;
private final Context context;
Session session1;


public Adapter_Transactionhistory(List<Transactionhistrymodel> category_home_models, Context context) {
    this.category_home_models = category_home_models;
    this.context = context;

}


@NonNull
@Override
public Adapter_Transactionhistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transactionhistry, parent, false);
    return new Adapter_Transactionhistory.ViewHolder(itemView);
}

@Override
public void onBindViewHolder(@NonNull final Adapter_Transactionhistory.ViewHolder holder, final int position) {
    final Transactionhistrymodel allCommunityModel = category_home_models.get(position);


    holder.txtamount.setText(category_home_models.get(position).getMoney());
    holder.txttype.setText(category_home_models.get(position).getTransfer_type());
    holder.txtdate.setText(category_home_models.get(position).getCreated_date());
if (category_home_models.get(position).getTransfer_type().equalsIgnoreCase("withdraw")){
    holder.txtamount.setTextColor(ContextCompat.getColor(context, R.color.red));
    holder.txttype.setTextColor(ContextCompat.getColor(context, R.color.red));
    holder.txtdate.setTextColor(ContextCompat.getColor(context, R.color.red));
    holder.txttype.setText("Funds Withdrawn");
    holder.txtamount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_removes, 0);
}

else {
    if (category_home_models.get(position).getTransfer_type().equalsIgnoreCase("deposit")) {
        holder.txttype.setText("Funds Deposited");
        holder.txtamount.setTextColor(ContextCompat.getColor(context, R.color.green));
        holder.txttype.setTextColor(ContextCompat.getColor(context, R.color.green));
        holder.txtdate.setTextColor(ContextCompat.getColor(context, R.color.green));
        holder.txtamount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_addmore, 0);

    }

}

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
    ImageView vimage, heart, fav2;
    TextView txtamount,txttype,txtdate;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        txtamount = itemView.findViewById(R.id.txtamount);
        txttype = itemView.findViewById(R.id.txttype);
        txtdate = itemView.findViewById(R.id.txtdate);
    }
}
}

//ic_removes    ic_addmore