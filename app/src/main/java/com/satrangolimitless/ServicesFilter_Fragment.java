package com.satrangolimitless;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.satrangolimitless.User_UI.Search.SearchFragment;
import com.satrangolimitless.session.Session;


public class ServicesFilter_Fragment extends Fragment {
    Button btn_apply, btn_close;
    String from_amount, to_amount, distance;
    Fragment fragment;
TextView txtmaxprice,txtminprice,txtminkm;
    View root;

    Session session;

    int count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_servicesfilter, container, false);
        session = new Session(getActivity());
        btn_apply = root.findViewById(R.id.btn_apply);
        btn_close = root.findViewById(R.id.btn_close);
        txtminkm = root.findViewById(R.id.txtminkm);
        txtmaxprice = root.findViewById(R.id.txtmaxprice);
        txtminprice = root.findViewById(R.id.txtminprice);


//                Intent intent = new Intent(getActivity(), BooknowActivity.class);
//                startActivity(intent);


        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) root.findViewById(R.id.rangeSeekbardistance);
        final CrystalRangeSeekbar rangeSeekbarprice = (CrystalRangeSeekbar) root.findViewById(R.id.rangeSeekbarprice);

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                System.out.println("mmmmmmm     " + (String.valueOf(minValue)));

                System.out.println("amount     " + (String.valueOf(minValue)));
                System.out.println("max amount       " + (String.valueOf(maxValue)));
                distance = (String.valueOf(minValue));
                txtminkm.setText(distance);

            }
        });

        rangeSeekbarprice.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {


                from_amount = (String.valueOf(minValue));
                to_amount = (String.valueOf(maxValue));
                System.out.println("mxcxcxcxcxcxcxcxcxcxcxcxcxc       " + (String.valueOf(maxValue)));
                txtminprice.setText(from_amount);
                        txtmaxprice.setText(to_amount);
            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("data", "filter");
                bundle.putString("from_amount", from_amount);
                bundle.putString("to_amount", to_amount);
                bundle.putString("distance", distance);

                fragment = new SearchFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                fragment.setArguments(bundle);

            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("data", "close");
                bundle.putString("from_amount", "");
                bundle.putString("to_amount", "");
                bundle.putString("distance", "");

                fragment = new SearchFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                fragment.setArguments(bundle);

            }
        });

        return root;
    }


}