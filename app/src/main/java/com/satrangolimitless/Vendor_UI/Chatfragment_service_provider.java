package com.satrangolimitless.Vendor_UI;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.satrangolimitless.LandingActivity_Service_provider;
import com.satrangolimitless.R;
import com.satrangolimitless.session.Session_vendor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Chatfragment_service_provider#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Chatfragment_service_provider extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView cancel;
    CardView carlayout;
    Session_vendor session_vendor;
    public Chatfragment_service_provider() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chatfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Chatfragment_service_provider newInstance(String param1, String param2) {
        Chatfragment_service_provider fragment = new Chatfragment_service_provider();
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
        ((LandingActivity_Service_provider)getActivity()).Layout_hader.setVisibility(View.GONE);

        View  root= inflater.inflate(R.layout.fragment_chatfragment, container, false);
        cancel=root.findViewById(R.id.cancel);
        carlayout=root.findViewById(R.id.carlayout);

       /* LayoutInflater inflater1 = LayoutInflater.from(getActivity());
        final BottomSheetDialog dialog1 = new BottomSheetDialog(getActivity());
        Window window = dialog1.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        View view1 = inflater1.inflate(R.layout.fragment_chatfragment, null);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setContentView(view1);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.getWindow().setGravity(Gravity.BOTTOM);
        dialog1.setCancelable(true);

        dialog1.show();*/


       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               carlayout.setVisibility(View.GONE);
           }
       });



        return root;
    }
}