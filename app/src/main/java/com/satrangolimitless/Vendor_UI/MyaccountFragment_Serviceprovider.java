package com.satrangolimitless.Vendor_UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.satrangolimitless.R;
import com.satrangolimitless.session.Session_vendor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyaccountFragment_Serviceprovider#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyaccountFragment_Serviceprovider extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Session_vendor session_vendor;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyaccountFragment_Serviceprovider() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyaccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyaccountFragment_Serviceprovider newInstance(String param1, String param2) {
        MyaccountFragment_Serviceprovider fragment = new MyaccountFragment_Serviceprovider();
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
        return inflater.inflate(R.layout.fragment_myaccount_service_provider, container, false);
    }
}