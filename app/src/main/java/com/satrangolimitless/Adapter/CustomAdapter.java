package com.satrangolimitless.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.satrangolimitless.R;
import com.satrangolimitless.model.SubjectData;

import java.io.IOException;
import java.util.ArrayList;

public class CustomAdapter implements ListAdapter {
    ArrayList<SubjectData> arrayList;
    Context context;
    Bitmap bm = null;

    public CustomAdapter(Context context, ArrayList<SubjectData> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubjectData subjectData = arrayList.get(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });


            System.out.println("getimageeeee      " + subjectData.getImage());
            ImageView imag = convertView.findViewById(R.id.list_image);

            Uri myUri = Uri.parse(subjectData.getImage());

            try {
                bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), myUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imag.setImageBitmap(bm);


        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}