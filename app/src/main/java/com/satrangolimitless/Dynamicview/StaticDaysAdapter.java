package com.satrangolimitless.Dynamicview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.R;

import java.util.ArrayList;
import java.util.List;

public class StaticDaysAdapter extends RecyclerView.Adapter<StaticDaysAdapter.ViewHolder> {
    private List<Days_Model> allCommunityModels;
    private Context context;

    private String user_id;
    String Community;
    int row_index=-1;
    String get_select_size;


    public Size_Click mCallback;

    public static ArrayList<String> selected_size=new ArrayList<>();

    public StaticDaysAdapter(List<Days_Model> allCommunityModels1, Context context, Size_Click product_click) {
        this.allCommunityModels = allCommunityModels1;
        this.context = context;
        this.mCallback = ((Size_Click) context);
        this.mCallback = product_click;

    }

    public StaticDaysAdapter(List<Days_Model> allCommunityModels1, Context context) {
        this.allCommunityModels = allCommunityModels1;
        this.context = context;

    }


    @NonNull
    @Override
    public StaticDaysAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);

        return new StaticDaysAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StaticDaysAdapter.ViewHolder holder, final int position) {
        if (allCommunityModels.size() > 0) {
            Days_Model allCommunityModel=allCommunityModels.get(position);
            holder.size.setText(allCommunityModel.getSize());


            try {
                this.mCallback = ((Size_Click) context);
            } catch (ClassCastException e) {
                throw new ClassCastException("Activity must implement AdapterCallback.");
            }


            selected_size.clear();



            holder.size.setOnClickListener(new View.OnClickListener() {
                boolean clicked = true;

                @Override
                public void onClick(View v) {


                    if (!clicked) {



                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            holder.linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.greenborderbutton) );
                            holder.size.setTextColor(context.getResources().getColor(R.color.green));
                        } else {
                            holder.linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.greenborderbutton));
                            holder.size.setTextColor(context.getResources().getColor(R.color.green));
                        }




                        get_select_size = allCommunityModels.get(position).getSize_name();

                        selected_size.remove(get_select_size);


                        clicked = true;

                    } else {





                     final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        holder.linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.greenbutton) );
                        holder.size.setTextColor(context.getResources().getColor(R.color.white));

                    } else {
                        holder.linearLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.greenbutton));
                        holder.size.setTextColor(context.getResources().getColor(R.color.white));
                    }
                        get_select_size = allCommunityModels.get(position).getSize_name();

                        selected_size.add(get_select_size);

                        if(mCallback != null) {
                            mCallback.onsizeClick(selected_size);
                        }

                        clicked = false;
                    }
                }
            });






        }
    }


    @Override
    public int getItemCount() {
        return allCommunityModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView size;
        LinearLayout linearLayout;
        public ViewHolder(View parent) {
            super(parent);
            linearLayout=parent.findViewById(R.id.linear);
            size=parent.findViewById(R.id.size);
        }
    }

}

