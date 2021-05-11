package com.satrangolimitless.Vendor_UI.My_Bids;

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

import com.google.android.material.tabs.TabLayout;
import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.Mybooking.MybookingFragment;
import com.satrangolimitless.session.Session_vendor;

public class Fragment_MyBids_Vendor extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout BooKingDetails;
    View root;
    TabLayout tabLayout2;
    ViewPager viewPager;
    String userid, get_user_id;
    Session_vendor session_vendor;
    ImageView imgarbak;
    TextView txtback;

    public static MybookingFragment newInstance(String param1, String param2) {
        MybookingFragment fragment = new MybookingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_vendor_my_bids, container, false);
        LandingActivity_Service_provider.Layout_hader.setVisibility(View.GONE);

        txtback = root.findViewById(R.id.txtback);
        imgarbak = root.findViewById(R.id.imgarbak);
        tabLayout2 = root.findViewById(R.id.tabLayout2);
        viewPager = root.findViewById(R.id.viewPager2);
        tabLayout2.addTab(tabLayout2.newTab().setText("New Jobs"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Bids Placed"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Awarded/Expired"));
        tabLayout2.setTabGravity(TabLayout.GRAVITY_FILL);


       /*(intent);
       BooKingDetails = root.findViewById(R.id.BooKingDetails);
         BooKingDetails.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
        Intent intent = new Intent(getActivity(), BookingDetailsActivity.class);
        startActivity

        }

        });*/


        final Vendor_MyBidsTabs_adapter adapter = new Vendor_MyBidsTabs_adapter(getChildFragmentManager(), getActivity(), tabLayout2.getTabCount(), get_user_id);
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


        imgarbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity_Service_provider.class);
                startActivity(intent);

            }
        });


        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity_Service_provider.class);
                startActivity(intent);

            }
        });

        return root;
    }


}

