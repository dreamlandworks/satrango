package com.satrangolimitless.User_UI.Offer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.Adapter.Adapter_Expiringsoon_offers;
import com.satrangolimitless.Adapter.Adapter_Latest_offers;
import com.satrangolimitless.Adapter.Adapter_Tailormade_offers;
import com.satrangolimitless.ForgotPasswordActivity;
import com.satrangolimitless.ForgotpasswordotpActivity;
import com.satrangolimitless.LandingActivity;
import com.satrangolimitless.R;
import com.satrangolimitless.model.Latest_offers_model;
import com.satrangolimitless.model.Offers_expiringsoon_model;
import com.satrangolimitless.model.Tailormadeoffers_model;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.get_tailormadeofer_offers;
import static com.satrangolimitless.Utils.Base_Url.get_expiry_soon;
import static com.satrangolimitless.Utils.Base_Url.get_latest_offer;

public class OfferFragment extends Fragment {

    Session session;
    String name, user_id, image;
    CircleImageView imgprofile;
    RecyclerView recv_tailormade, recv_offerexpiring, recv_latestoffers;
    ArrayList<Latest_offers_model> latest_offers_models = new ArrayList<>();
    Adapter_Latest_offers adapter_latest_offers;

    ArrayList<Offers_expiringsoon_model> offers_expiringsoon_models = new ArrayList<>();
    Adapter_Expiringsoon_offers adapter_expiringsoon_offers;

    ArrayList<Tailormadeoffers_model> tailormadeoffers_models = new ArrayList<>();
    Adapter_Tailormade_offers adapter_tailormade_offers;

    ImageView ivbak;
    private TextView latestOfferTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((LandingActivity) getActivity()).Layout_hader.setVisibility(View.GONE);


            View root = inflater.inflate(R.layout.fragment_offer, container, false);

        session = new Session(getActivity());
        ivbak = root.findViewById(R.id.ivbak);
        imgprofile = root.findViewById(R.id.imgprofile);
        recv_latestoffers = root.findViewById(R.id.recv_latestoffers);
        latestOfferTV = root.findViewById(R.id.Offersexpiringsoon);
        recv_offerexpiring = root.findViewById(R.id.recv_offerexpiring);
        recv_tailormade = root.findViewById(R.id.recv_tailormade);
        image = session.getProfileimage();
        try {
            Glide.with(getActivity())
                    .load(Image_url + image)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .centerCrop()
                            .dontTransform())
                    .skipMemoryCache(true)
                    .into(imgprofile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ivbak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity.class);
                startActivity(intent);
            }
        });
        get_expiry_soonapi();
        LatestoffersApi();
        get_Tailormadeoffersapi();
        return root;
    }


//    Latest offers api

    private void LatestoffersApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + get_latest_offer;
        latest_offers_models.clear();
        AndroidNetworking.post(url)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                System.out.println("LatestoffersApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Latest_offers_model allCommunityModel = new Latest_offers_model(
                                            dataObject.getString("created_date"),
                                            dataObject.getString("banner"),
                                            dataObject.getString("end_date"),
                                            dataObject.getString("start_date"),
                                            dataObject.getString("status"),
                                            dataObject.getString("amount"),
                                            dataObject.getString("percentage"),
                                            dataObject.getString("type"),
                                            dataObject.getString("code"),
                                            dataObject.getString("title"),
                                            dataObject.getString("id"));
                                    latest_offers_models.add(allCommunityModel);
                                }
                            } else {
                                latestOfferTV.setVisibility(View.GONE);
                            }
                            adapter_latest_offers = new Adapter_Latest_offers(latest_offers_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recv_latestoffers.setLayoutManager(mLayoutManger);
                            recv_latestoffers.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

                            recv_latestoffers.setItemAnimator(new DefaultItemAnimator());
                            recv_latestoffers.setAdapter(adapter_latest_offers);
                            adapter_latest_offers.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_my_join", anError.toString());
                    }
                });
    }

    //Offers Expiring soon api
    private void get_expiry_soonapi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + get_expiry_soon;
        offers_expiringsoon_models.clear();
        AndroidNetworking.post(url)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                System.out.println("LatestoffersApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Offers_expiringsoon_model allCommunityModel = new Offers_expiringsoon_model(
                                            dataObject.getString("created_date"),
                                            dataObject.getString("banner"),
                                            dataObject.getString("end_date"),
                                            dataObject.getString("start_date"),
                                            dataObject.getString("status"),
                                            dataObject.getString("amount"),
                                            dataObject.getString("percentage"),
                                            dataObject.getString("type"),
                                            dataObject.getString("code"),
                                            dataObject.getString("title"),
                                            dataObject.getString("id"));
                                    offers_expiringsoon_models.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_expiringsoon_offers = new Adapter_Expiringsoon_offers(offers_expiringsoon_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recv_offerexpiring.setLayoutManager(mLayoutManger);
                            recv_offerexpiring.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

                            recv_offerexpiring.setItemAnimator(new DefaultItemAnimator());
                            recv_offerexpiring.setAdapter(adapter_expiringsoon_offers);
                            adapter_expiringsoon_offers.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_my_join", anError.toString());
                    }
                });
    }

//    Tailor made ofers api




    private void get_Tailormadeoffersapi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + get_tailormadeofer_offers;
        tailormadeoffers_models.clear();
        AndroidNetworking.post(url)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                System.out.println("LatestoffersApi-------   " + jsonArray);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Tailormadeoffers_model allCommunityModel = new Tailormadeoffers_model(
                                            dataObject.getString("created_date"),
                                            dataObject.getString("banner"),
                                            dataObject.getString("end_date"),
                                            dataObject.getString("start_date"),
                                            dataObject.getString("status"),
                                            dataObject.getString("amount"),
                                            dataObject.getString("percentage"),
                                            dataObject.getString("type"),
                                            dataObject.getString("code"),
                                            dataObject.getString("title"),
                                            dataObject.getString("id"));
                                    tailormadeoffers_models.add(allCommunityModel);
                                }


                            } else {

                            }
                            adapter_tailormade_offers = new Adapter_Tailormade_offers(tailormadeoffers_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recv_tailormade.setLayoutManager(mLayoutManger);
                            recv_tailormade.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

                            recv_tailormade.setItemAnimator(new DefaultItemAnimator());
                            recv_tailormade.setAdapter(adapter_tailormade_offers);
                            adapter_tailormade_offers.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Log.e("error_my_join", anError.toString());
                    }
                });
    }

}