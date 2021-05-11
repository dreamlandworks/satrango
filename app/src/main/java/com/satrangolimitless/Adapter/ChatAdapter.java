package com.satrangolimitless.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.satrangolimitless.R;
import com.satrangolimitless.model.ChatModel;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter< ChatAdapter.MyViewHolder> {

    Context context ;
    ArrayList<ChatModel> chatModels;

    public ChatAdapter(Context context, ArrayList<ChatModel> chatModels) {
        this.context = context;
        this.chatModels = chatModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_userchatbubble,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ChatModel chatModel = chatModels.get(position);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;

        if(chatModel.getVendor_msg().equals("")){

            holder.chat_lay.setBackgroundResource(R.drawable.user_chat_back);
            params.gravity = Gravity.LEFT;
            holder.chat_msg.setText(chatModel.getUser_msg());

        }else {
            holder.chat_lay.setBackgroundResource(R.drawable.vendor_chat_back   );
            params.gravity = Gravity.RIGHT;
            holder.chat_msg.setText(chatModel.getVendor_msg());

        }

        holder.main_lay.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView chat_msg,date_tv;
        ImageView warning_img;
        LinearLayout chat_lay,main_lay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            main_lay = itemView.findViewById(R.id.main_lay);
            date_tv = itemView.findViewById(R.id.date_tv);
             chat_msg = itemView.findViewById(R.id.chat_msg_tv);
            chat_lay = itemView.findViewById(R.id.chat_lay);

        }
    }
}
