package com.satrangolimitless.User_UI.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.Setting.ComplaintFragment;
import com.satrangolimitless.Setting.Privacyfragment;
import com.satrangolimitless.Setting.SuggestionAndfeedbackfragment;
import com.satrangolimitless.Setting.TermsAnsConditinfragment;
import com.satrangolimitless.User_UI.FAQ.FaqFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout linearlayout,l_privacy,l_faq,suggestion,ll_complaints;
    Fragment fragment;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View root=   inflater.inflate(R.layout.fragment_settings, container, false);

        linearlayout=root.findViewById(R.id.linearlayout);
        l_privacy=root.findViewById(R.id.l_privacy);
        l_faq=root.findViewById(R.id.l_faq);
        suggestion=root.findViewById(R.id.suggestion);
        ll_complaints=root.findViewById(R.id.ll_complaints);


        linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new TermsAnsConditinfragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();

            }
        });

        l_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new Privacyfragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });


        l_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new FaqFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });
        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new SuggestionAndfeedbackfragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });

        ll_complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragment = new ComplaintFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .addToBackStack("csds").commit();
            }
        });
        return root;
    }
}