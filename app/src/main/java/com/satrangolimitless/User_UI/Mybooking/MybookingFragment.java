package com.satrangolimitless.User_UI.Mybooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.satrangolimitless.Utils.Base_Url.Image_url;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MybookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MybookingFragment extends Fragment {
LinearLayout BooKingDetails;
    View root;
    TabLayout tabLayout2;
    ViewPager viewPager;
    String userid,get_user_id;
    ImageView imgbackb;
    TextView txtbacks;
    ImageView imgprobooking;
    Session session;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MybookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MybookingFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((LandingActivity)getActivity()).Layout_hader.setVisibility(View.GONE);
        root = inflater.inflate(R.layout.fragment_mybooking, container, false);

        session=new Session(getActivity());

        imgbackb =  root.findViewById(R.id.imgbackb);
        txtbacks =  root.findViewById(R.id.txtbacks);
        imgprobooking =  root.findViewById(R.id.imgprobooking);
        tabLayout2 =  root.findViewById(R.id.tabLayout2);
        viewPager =  root.findViewById(R.id.viewPager2);
        tabLayout2.addTab(tabLayout2.newTab().setText("Pending"));
        tabLayout2.addTab(tabLayout2.newTab().setText("In Progress"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Completed"));
        tabLayout2.setTabGravity(TabLayout.GRAVITY_FILL);


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

        Glide.with(getActivity())
                .load(Image_url + session.getProfileimage())
                .into(imgprobooking);

        final User_BookingTabsAdapter adapter = new User_BookingTabsAdapter(getChildFragmentManager(), getActivity(), tabLayout2.getTabCount(),get_user_id);
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

