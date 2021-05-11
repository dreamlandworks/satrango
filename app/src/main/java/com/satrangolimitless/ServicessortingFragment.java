package com.satrangolimitless;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.satrangolimitless.User_UI.Search.SearchFragment;
import com.satrangolimitless.session.Session;

public class ServicessortingFragment extends Fragment {
    View root;
    Fragment fragment;
    Button btn_apply,
    btn_close;
    Session session;
    ImageView imgback;
String fresher="",Profession="", lowTohigh="", highTolow="", nearby="", rating="";
    TextView txtrating, txtreview, txtranking, txtnearme, txtprhightolow, txtprlowtohigh, txtprofessional,
    txtfresher;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.activity_servicessorting, container, false);

        session=new Session(getActivity());
        btn_apply = root.findViewById(R.id.btn_apply);
        btn_close = root.findViewById(R.id.btn_close);
        imgback = root.findViewById(R.id.imgback);

        txtrating = root.findViewById(R.id.txtrating);
        txtreview = root.findViewById(R.id.txtreview);
        txtranking = root.findViewById(R.id.txtranking);
        txtnearme = root.findViewById(R.id.txtnearme);
        txtprhightolow = root.findViewById(R.id.txtprhightolow);
        txtprlowtohigh = root.findViewById(R.id.txtprlowtohigh);
        txtprofessional = root.findViewById(R.id.txtprofessional);
        txtfresher = root.findViewById(R.id.txtfresher);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        txtrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating="rating";
                txtrating.setTextColor(getResources().getColor(R.color.black));
                txtrating.setBackgroundColor(Color.parseColor("#fdc400"));

                txtreview.setTextColor(getResources().getColor(R.color.white));
                txtreview.setBackgroundColor(Color.parseColor("#00296a"));

                txtranking.setTextColor(getResources().getColor(R.color.white));
                txtranking.setBackgroundColor(Color.parseColor("#00296a"));

                txtnearme.setTextColor(getResources().getColor(R.color.white));
                txtnearme.setBackgroundColor(Color.parseColor("#00296a"));
            }
        });
        txtreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating="review";
                txtreview.setTextColor(getResources().getColor(R.color.black));
                txtreview.setBackgroundColor(Color.parseColor("#fdc400"));

                txtrating.setTextColor(getResources().getColor(R.color.white));
                txtrating.setBackgroundColor(Color.parseColor("#00296a"));

                txtranking.setTextColor(getResources().getColor(R.color.white));
                txtranking.setBackgroundColor(Color.parseColor("#00296a"));

                txtnearme.setTextColor(getResources().getColor(R.color.white));
                txtnearme.setBackgroundColor(Color.parseColor("#00296a"));



            }
        });

        txtranking .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rating="ranking";
                txtranking.setTextColor(getResources().getColor(R.color.black));
                txtranking.setBackgroundColor(Color.parseColor("#fdc400"));

                txtrating.setTextColor(getResources().getColor(R.color.white));
                txtrating.setBackgroundColor(Color.parseColor("#00296a"));

                txtreview.setTextColor(getResources().getColor(R.color.white));
                txtreview.setBackgroundColor(Color.parseColor("#00296a"));

                txtnearme.setTextColor(getResources().getColor(R.color.white));
                txtnearme.setBackgroundColor(Color.parseColor("#00296a"));



            }
        });

        txtnearme .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearby="nearby";
                txtnearme.setTextColor(getResources().getColor(R.color.black));
                txtnearme.setBackgroundColor(Color.parseColor("#fdc400"));

                txtrating.setTextColor(getResources().getColor(R.color.white));
                txtrating.setBackgroundColor(Color.parseColor("#00296a"));

                txtreview.setTextColor(getResources().getColor(R.color.white));
                txtreview.setBackgroundColor(Color.parseColor("#00296a"));

                txtranking.setTextColor(getResources().getColor(R.color.white));
                txtranking.setBackgroundColor(Color.parseColor("#00296a"));





            }
        });

        txtprhightolow .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highTolow="highTolow";
                txtprhightolow.setTextColor(getResources().getColor(R.color.black));
                txtprhightolow.setBackgroundColor(Color.parseColor("#fdc400"));

                txtprlowtohigh.setTextColor(getResources().getColor(R.color.white));
                txtprlowtohigh.setBackgroundColor(Color.parseColor("#00296a"));

            }
        });

        txtprlowtohigh .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowTohigh="lowTohigh";
                txtprlowtohigh.setTextColor(getResources().getColor(R.color.black));
                txtprlowtohigh.setBackgroundColor(Color.parseColor("#fdc400"));

                txtprhightolow.setTextColor(getResources().getColor(R.color.white));
                txtprhightolow.setBackgroundColor(Color.parseColor("#00296a"));
            }
        });

        txtprofessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profession="Profession";
                txtprofessional.setTextColor(getResources().getColor(R.color.black));
                txtprofessional.setBackgroundColor(Color.parseColor("#fdc400"));

                txtfresher.setTextColor(getResources().getColor(R.color.white));
                txtfresher.setBackgroundColor(Color.parseColor("#00296a"));
            }
        });

        txtfresher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fresher="fresher";
                txtfresher.setTextColor(getResources().getColor(R.color.black));
                txtfresher.setBackgroundColor(Color.parseColor("#fdc400"));

                txtprofessional.setTextColor(getResources().getColor(R.color.white));
                txtprofessional.setBackgroundColor(Color.parseColor("#00296a"));
            }
        });






        btn_apply.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();
        bundle.putString("data", "sorting");
        bundle.putString("fresher", fresher);
        bundle.putString("Profession",Profession);
        bundle.putString("lowTohigh", lowTohigh);
        bundle.putString("highTolow", highTolow);
        bundle.putString("rating", rating);
        bundle.putString("nearby", nearby);

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

    }
});
        return root;
    }


}