package com.satrangolimitless.Vendor_UI.Vendor_Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
public class Vendor_SettingsFragment extends Fragment {


    private String mParam1;
    private String mParam2;
    LinearLayout linearlayout,l_privacy,l_faq,suggestion,ll_complaint,requstcomplaints;
    Fragment fragment;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LandingActivity_Service_provider.Layout_hader.setVisibility(View.GONE);
        View root=   inflater.inflate(R.layout.fragment_vendorsettings, container, false);

        linearlayout=root.findViewById(R.id.linearlayout);
        l_privacy=root.findViewById(R.id.l_privacy);
        l_faq=root.findViewById(R.id.l_faq);
        suggestion=root.findViewById(R.id.suggestion);
        ll_complaint=root.findViewById(R.id.ll_complaint);
        requstcomplaints=root.findViewById(R.id.requstcomplaints);


        linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Vendor_TermsAndConditionfragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();

            }
        });

        l_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Vendor_Privacyfragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });


        l_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Vendor_FAQfragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });

        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Vendor_SuggestionAndfeedbackfragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });

        ll_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Vendor_Complaintfragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });


        requstcomplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Vendor_mycomplaintsandrequest();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();

            }
        });
        return root;
    }
}