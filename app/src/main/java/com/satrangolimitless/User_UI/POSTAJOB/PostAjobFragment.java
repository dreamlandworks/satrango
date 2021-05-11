package com.satrangolimitless.User_UI.POSTAJOB;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;

public class PostAjobFragment extends Fragment {

CardView cd_online,cd_multi, cd_single;
Session session;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((LandingActivity) getActivity()).Layout_hader.setVisibility(View.GONE);
        View root =  inflater.inflate(R.layout.fragment_post_ajob, container, false);

        session = new Session(getActivity());
        cd_single = root.findViewById(R.id.cd_single);
        cd_multi = root.findViewById(R.id.cd_multi);
        cd_online = root.findViewById(R.id.cd_online);
        cd_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cd_single.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_multi.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_online.setCardBackgroundColor(Color.parseColor("#ffffff"));
                Intent intent = new Intent(getActivity(), Activity_PostJob_First.class);
                startActivity(intent);
            }
        });
        cd_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd_multi.setCardBackgroundColor(Color.parseColor("#fdc400"));
                cd_single.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_online.setCardBackgroundColor(Color.parseColor("#ffffff"));
                Intent intent = new Intent(getActivity(), Activity_Post_job_Multiplelocation_first.class);
                startActivity(intent);

            }
        });

        cd_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd_multi.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_single.setCardBackgroundColor(Color.parseColor("#ffffff"));
                cd_online.setCardBackgroundColor(Color.parseColor("#fdc400"));
                Intent intent = new Intent(getActivity(), Activity_PostJob_BlueCollarFirst.class);
                startActivity(intent);
            }
        });
        return root;
    }

}