package com.satrangolimitless.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Dynamicview.Choose_From_Time;
import com.satrangolimitless.Dynamicview.Choose_To_Time;
import com.satrangolimitless.Dynamicview.Day_Name_data_adapter;
import com.satrangolimitless.Interface.onFrom_timeclick;
import com.satrangolimitless.Interface.onTo_timeclick;
import com.satrangolimitless.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Vendor_Availibility_time extends RecyclerView.Adapter<Adapter_Vendor_Availibility_time.ViewHolder> {
    private final List<Choose_From_Time> allFromTimelist;
    private final List<Choose_To_Time> allToTimelist;
    private final Context context;
    onFrom_timeclick onFrom_timeclick;
    onTo_timeclick onTo_timeclick;
    String Community;
    ArrayList<String> SuggestionList = new ArrayList<>();
    Day_Name_data_adapter suggestionAdapter;
    private String user_id;

    int selectedPosition = -1;
    public Adapter_Vendor_Availibility_time(List<Choose_From_Time> allFromTimelist, List<Choose_To_Time> allToTimelist, Context context, onFrom_timeclick onFrom_timeclick, onTo_timeclick onTo_timeclick) {
        this.allFromTimelist = allFromTimelist;
        this.allToTimelist = allToTimelist;
        this.context = context;
        this.onFrom_timeclick = onFrom_timeclick;
        this.onTo_timeclick = onTo_timeclick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vendor_availibility_frmtime_totime, parent, false);

        return new ViewHolder(itemView);
    }


    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.txt_from.setText(allFromTimelist.get(position).getFromTime());
        holder.txt_to.setText(allToTimelist.get(position).getToTime());
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

                onFrom_timeclick.onitemclick(allFromTimelist.get(position).getFromTime());
                onTo_timeclick.ontotimeclick(allToTimelist.get(position).getToTime());


            }
        });

    }


    @Override
    public int getItemCount() {

        return allFromTimelist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_from, txt_to;
        RecyclerView recydays;
        Button btn_wekend, btn_weekday, btn_everyday;
        LinearLayout llbacg;
        CardView card;

        public ViewHolder(View parent) {
            super(parent);
            txt_from = parent.findViewById(R.id.txtfrmtime);
            txt_to = parent.findViewById(R.id.txttotime);
            card = parent.findViewById(R.id.card);
            llbacg = parent.findViewById(R.id.llbacg);

        }
    }
}
