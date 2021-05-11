package com.satrangolimitless.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.Dynamicview.Choose_Day_Name;
import com.satrangolimitless.Dynamicview.Choose_Day_Type;
import com.satrangolimitless.Dynamicview.Choose_From_Time;
import com.satrangolimitless.Dynamicview.Choose_To_Time;
import com.satrangolimitless.Dynamicview.Day_Name_data_adapter;
import com.satrangolimitless.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Tarrif_adapter extends RecyclerView.Adapter<Tarrif_adapter.ViewHolder> {
    String Community;
    ArrayList<String> SuggestionList = new ArrayList<>();
    Day_Name_data_adapter suggestionAdapter;
    private final List<Choose_From_Time> allFromTimelist;
    private final List<Choose_To_Time> allToTimelist;
    private final List<Choose_Day_Name> allDaynamelist;
    private final List<Choose_Day_Type> allDaytypelist;
    private final Context context;
    private String user_id;

    public Tarrif_adapter(List<Choose_From_Time> allFromTimelist, List<Choose_To_Time> allToTimelist, List<Choose_Day_Type> allDaytypelist, List<Choose_Day_Name> allDaynamelist, Context context) {
        this.allFromTimelist = allFromTimelist;
        this.allToTimelist = allToTimelist;
        this.allDaynamelist = allDaynamelist;
        this.allDaytypelist = allDaytypelist;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_slot_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.txt_from.setText(allFromTimelist.get(position).getFromTime());
        holder.txt_to.setText(allToTimelist.get(position).getToTime());

        System.out.println("day name========      " + allDaynamelist.get(position).getDay_Name());
        String str = allDaynamelist.get(position).getDay_Name();
        String day_type = allDaytypelist.get(position).getDay_Type();
        System.out.println("day_type   " + day_type);


        if (day_type.contains("Weekend")) {

            holder.btn_wekend.setBackgroundResource(R.drawable.greenbutton);
            holder.btn_wekend.setTextColor(ContextCompat.getColor(context, R.color.white));

        }   if (day_type.contains("Everyday")) {

            holder.btn_everyday.setBackgroundResource(R.drawable.greenbutton);
            holder.btn_everyday.setTextColor(ContextCompat.getColor(context, R.color.white));

        }   if (day_type.contains("Weekday")) {

            holder.btn_weekday.setBackgroundResource(R.drawable.greenbutton);
            holder.btn_weekday.setTextColor(ContextCompat.getColor(context, R.color.white));

        }



        ArrayList aList = new ArrayList(Arrays.asList(str.split(",")));
        for (int i = 0; i < aList.size(); i++) {
            System.out.println(" -->       " + aList.get(i));
            System.out.println(" size-->       " + aList.size());


            SuggestionList.add(aList.get(i).toString());



        }

        //Deleting Same entries

        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(SuggestionList);
        SuggestionList.clear();
        SuggestionList.addAll(hashSet);

        //Alphabetic sorting.
        Collections.sort(SuggestionList);

        suggestionAdapter = new Day_Name_data_adapter(context, SuggestionList);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(context);
        holder.recydays.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        holder.recydays.setAdapter(suggestionAdapter);
        suggestionAdapter.notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return allFromTimelist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_from, txt_to;
        RecyclerView recydays;
        Button btn_wekend, btn_weekday, btn_everyday;

        public ViewHolder(View parent) {
            super(parent);
            txt_from = parent.findViewById(R.id.txt_from);
            txt_to = parent.findViewById(R.id.txt_to);
            recydays = parent.findViewById(R.id.recydays);
            btn_wekend = parent.findViewById(R.id.btn_wekend);
            btn_weekday = parent.findViewById(R.id.btn_weekday);
            btn_everyday = parent.findViewById(R.id.btn_everyday);
        }
    }
}
