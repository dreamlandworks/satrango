package com.satrangolimitless.User_UI.My_Job_Posts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;

import static com.satrangolimitless.Utils.Base_Url.Image_url;


public class MyJobPostsFragment extends Fragment {
    LinearLayout BooKingDetails;
    View root;
    TabLayout tabLayout2;
    ViewPager viewPager;
    String userid,get_user_id;
    ImageView imgbackb;
    TextView txtbacks;
    ImageView imgprojobs;
    Session session;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

 
      
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((LandingActivity)getActivity()).Layout_hader.setVisibility(View.GONE);
        root = inflater.inflate(R.layout.fragment_myjob_posts, container, false);

        session=new Session(getActivity());

        imgprojobs =  root.findViewById(R.id.imgprojobs);
        imgbackb =  root.findViewById(R.id.imgbackb);
        txtbacks =  root.findViewById(R.id.txtbacks);
        tabLayout2 =  root.findViewById(R.id.tabLayout2);
        viewPager =  root.findViewById(R.id.viewPager2);
        tabLayout2.addTab(tabLayout2.newTab().setText("Pending"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Awarded"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Expired"));
        tabLayout2.setTabGravity(TabLayout.GRAVITY_FILL);

        Glide.with(getActivity())
                .load(Image_url + session.getProfileimage())
                .into(imgprojobs);

        imgbackb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

        });


        txtbacks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

        });


        final Job_Posts_Adapter adapter = new Job_Posts_Adapter(getChildFragmentManager(), getActivity(), tabLayout2.getTabCount(),get_user_id);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout2));


        tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        return root;
    }
}

