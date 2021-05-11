package com.satrangolimitless.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

import com.bumptech.glide.Glide;
import com.satrangolimitless.R;
import com.satrangolimitless.model.UserChatmodel;
import com.satrangolimitless.session.Session;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.Image_url;


public class UsersideChat_Adapter extends RecyclerView.Adapter<UsersideChat_Adapter.ViewHolder> {
    Context mContext;
    List<UserChatmodel> chatList;
    Resources mRes;
    LayoutInflater mInflater;
    String userid;
    Session session;
    String chatgImage = "http://logicaltest.in/Taste/assets/images/gallery/";

    public UsersideChat_Adapter(List<UserChatmodel> chatList, Context context) {
        this.chatList = chatList;
        this.mContext = context;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupchatlayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        UserChatmodel chat = chatList.get(position);


        session = new Session(mContext);
        userid = session.getUserId();

        Log.e("-->", "onBindViewHolder: " + chat.getVendor_msg() + "-->>" + chat.getUser_msg());


        if (!chat.getVendor_msg().equalsIgnoreCase("")) {
            Log.e("'->>.first'", "onBindViewHolder: " + chat.getVendor_msg());
            holder.usercard.setVisibility(View.GONE);
            holder.usertime.setVisibility(View.GONE);
            holder.userTextSHow.setVisibility(View.GONE);
            // holder.userTextSHow.setText(chat.getUser_msg());
            holder.friendmessagelayout.setVisibility(View.VISIBLE);
            holder.usermessagelayout.setVisibility(View.GONE);
            holder.friendcard.setVisibility(View.GONE);
            holder.usersendimage.setVisibility(View.GONE);
            holder.recievesendimage.setVisibility(View.GONE);
            holder.friendTextShow.setVisibility(View.VISIBLE);
            holder.friendTextShow.setText(chat.getVendor_msg());



        } else {
            Log.e("'->>.second'", "onBindViewHolder: " + chat.getUser_msg());

            holder.usercard.setVisibility(View.GONE);
            holder.friendcard.setVisibility(View.GONE);
            holder.usersendimage.setVisibility(View.GONE);
            holder.recievesendimage.setVisibility(View.GONE);
            holder.userTextSHow.setText(chat.getUser_msg());
            holder.userTextSHow.setVisibility(View.VISIBLE);
            holder.friendmessagelayout.setVisibility(View.GONE);
            holder.friendTextShow.setVisibility(View.GONE);
            // holder.friendTextShow.setText(chat.getVendor_msg());

            System.out.println("userid" + chat.getUser_id());
//                holder.friendsenderimage.setVisibility(View.GONE);
//                holder.usersenderimage.setVisibility(View.VISIBLE);
/*
                Glide.with(mContext)
                        .load(Image_url + chatList.get(position).getSenderimage().trim())
                        .into(holder.usersenderimage);
*/


            holder.friendtime.setVisibility(View.GONE);
            holder.usertime.setVisibility(View.VISIBLE);
            holder.usertime.setText(chat.getDate_time());
        }

if (session.getUserId().equalsIgnoreCase(chat.getUser_id())){
    if (chat.getUser_msg().equalsIgnoreCase("")){
        System.out.println("filess-----     "+chatList.get(position).getFiles());

        if (chatList.get(position).getFiles() != null && !chatList.get(position).getFiles().isEmpty() && !chatList.get(position).getFiles().equals("null") && !chatList.get(position).getFiles().equals("0")) {

            holder.usercard.setVisibility(View.VISIBLE);
            holder.usersendimage.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(Image_url + chatList.get(position).getFiles().trim())
                    .into(holder.usersendimage);

        }


    }

}else {
    if (chat.getVendor_msg().equalsIgnoreCase("")){
        System.out.println("vendrrrrrr   -----     "+chatList.get(position).getFiles());
        if (chatList.get(position).getFiles() != null && !chatList.get(position).getFiles().isEmpty() && !chatList.get(position).getFiles().equals("null") && !chatList.get(position).getFiles().equals("0")) {

            holder.friendcard.setVisibility(View.VISIBLE);
            holder.recievesendimage.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(Image_url + chatList.get(position).getFiles().trim())
                    .into(holder.recievesendimage);
        }




    }


}










        // ChatListAdapter222.notifyDataSetChanged();
        try {
            UsersideChat_Adapter.this.notify();

        } catch (Exception e) {
            System.out.println("====" + e.fillInStackTrace());

        }


        holder.usercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });

        holder.friendcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void newDialog(final Context c) {

        final AlertDialog alertDialog;


    }

    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
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
        TextView userTextSHow, friendTextShow, usertime, friendtime;
        CardView usercard, friendcard;
        CircleImageView usersenderimage, friendsenderimage;
        LinearLayout usermessagelayout, friendmessagelayout;
        ImageView usersendimage, recievesendimage;
        ViewGroup viewGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usersenderimage = itemView.findViewById(R.id.usersenderimage);
            friendsenderimage = itemView.findViewById(R.id.friendsenderimage);
            usercard = itemView.findViewById(R.id.usercard);
            friendcard = itemView.findViewById(R.id.friendcard);
            userTextSHow = itemView.findViewById(R.id.userTextSHow);
            friendTextShow = itemView.findViewById(R.id.friendTextShow);
            usermessagelayout = itemView.findViewById(R.id.usermessagelayout);
            friendmessagelayout = itemView.findViewById(R.id.friendmessagelayout);
            usersendimage = itemView.findViewById(R.id.usersendimage);
            recievesendimage = itemView.findViewById(R.id.recievesendimage);
            usertime = itemView.findViewById(R.id.usertime);
            friendtime = itemView.findViewById(R.id.friendtime);

        }
    }

}