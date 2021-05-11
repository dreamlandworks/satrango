package com.satrangolimitless.User_UI.Search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.satrangolimitless.Adapter.Adapter_Search_data_vendor_alldetails;
import com.satrangolimitless.R;
import com.satrangolimitless.ServicesFilter_Fragment;
import com.satrangolimitless.ServicessortingFragment;

import com.satrangolimitless.model.Search_Data_Model_Vendor_Alldetails;
import com.satrangolimitless.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Searchdata_Api;
import static com.satrangolimitless.Utils.Base_Url.Searchfilter_Api;
import static com.satrangolimitless.Utils.Base_Url.Searchsorting_Api;

public class SearchFragment_vendor_alldetails extends Fragment {
    TextView txtfilterby,txtsortby;
    TextView txt_searchdataname,txt_count_result;
    View root;
    Fragment fragment;
    RecyclerView rec_search_data;
    Session session;
    String longi,latt,user_id,keyword;

    ArrayList<Search_Data_Model_Vendor_Alldetails> search_data_models = new ArrayList<>();
    Adapter_Search_data_vendor_alldetails adapter_search_data_vendor_all_detail;
    int count;
    String type="";
    String distance, to_amount, from_amount;
    String fresher,Profession,lowTohigh, highTolow, nearby,rating;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_search, container, false);
        session=new Session(getActivity());

        txtsortby=root.findViewById(R.id.txtsortby);
        txtfilterby=root.findViewById(R.id.txtfilterby);
        txt_searchdataname=root.findViewById(R.id.txt_searchdataname);
        txt_count_result=root.findViewById(R.id.txt_count_result);
        rec_search_data=root.findViewById(R.id.rec_search_data);

        type=  getArguments().getString("data");
System.out.println(" SearchFragment_vendor_alldetails type ----    "+type);
        user_id=session.getUserId();
        latt= session.getLattitude();
        longi=session.getLongitude();
        keyword = session.getKeyword();
        txt_searchdataname.setText("I Need "+keyword);
        System.out.println("searchy           "+keyword+" "+ longi+" "+ latt+" "+ user_id);


        if (type.contains("search")){
            SearchdataApi();
        }
        else if (type.contains("filter")){

            from_amount=  getArguments().getString("from_amount");
            to_amount=  getArguments().getString("to_amount");
            distance=  getArguments().getString("distance");
            SearchdataFilterApi();
        }
        else if (type.contains("sorting")){
            fresher=  getArguments().getString("fresher");
            Profession=  getArguments().getString("Profession");
            lowTohigh=  getArguments().getString("lowTohigh");
            highTolow=  getArguments().getString("highTolow");
            rating=  getArguments().getString("rating");
            nearby=  getArguments().getString("nearby");
            SearchdataSortingApi();
        }


        txtsortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fragment = new ServicessortingFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        txtfilterby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new ServicesFilter_Fragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

//                Intent intent = new Intent(getActivity(), BooknowActivity.class);
//                startActivity(intent);


        return root;
    }




    /*
    Search data api----------------------------------------------------------------------------------
     */
    private void SearchdataApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        search_data_models.clear();
        String url = BaseUrl +Searchdata_Api ;
        System.out.println("params-------     "+user_id+latt+longi);
        AndroidNetworking.post(url)
                .addBodyParameter("keyword",keyword )
                .addBodyParameter("user_id",user_id )
                .addBodyParameter("lat",latt )
                .addBodyParameter("lang",longi )

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
                                System.out.println(
                                        "SearchdataApi-------   "+ jsonArray
                                );
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Search_Data_Model_Vendor_Alldetails service_models = new Search_Data_Model_Vendor_Alldetails(
                                            dataObject.getString("id"),
                                            dataObject.getString("fname"),
                                            dataObject.getString("language"),
                                            dataObject.getString("about"),
                                            dataObject.getString("min_amount"),
                                            dataObject.getString("max_amount"),
                                            dataObject.getString("pr_hours"),
                                            dataObject.getString("pr_days"),
                                            dataObject.getString("cat_id"),
                                            dataObject.getString("service_name"),
                                            dataObject.getString("rating"),
                                            dataObject.getString("distance"),

                                            dataObject.getString("Professionalism_rating"),
                                            dataObject.getString("Behaviour_rating"),
                                            dataObject.getString("Satisfaction_rating"),
                                            dataObject.getString("Skills_rating"),
                                            dataObject.getString("job_done"));
                                    search_data_models.add(service_models);
                                    count=search_data_models.size();
                                }


                            } else {

                            }
                            txt_count_result.setText(String.valueOf(count));
                            adapter_search_data_vendor_all_detail = new Adapter_Search_data_vendor_alldetails(search_data_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_search_data.setLayoutManager(mLayoutManger);
                            rec_search_data.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            rec_search_data.setItemAnimator(new DefaultItemAnimator());
                            rec_search_data.setAdapter(adapter_search_data_vendor_all_detail);
                            adapter_search_data_vendor_all_detail.notifyDataSetChanged();
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



//search filter api


    private void SearchdataFilterApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        search_data_models.clear();
        String url = BaseUrl +Searchfilter_Api ;
        System.out.println("params-------     "+user_id+latt+longi);
        AndroidNetworking.post(url)
                .addBodyParameter("keyword",keyword )
                .addBodyParameter("user_id",user_id )
                .addBodyParameter("lat",latt )
                .addBodyParameter("lang",longi )
                .addBodyParameter("from_amount",from_amount )
                .addBodyParameter("to_amount",to_amount )
                .addBodyParameter("distance",distance )

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
                                System.out.println(
                                        "SearchdataFilterApi-------   "+ jsonArray
                                );
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Search_Data_Model_Vendor_Alldetails service_models = new Search_Data_Model_Vendor_Alldetails(
                                            dataObject.getString("id"),
                                            dataObject.getString("fname"),
                                            dataObject.getString("language"),
                                            dataObject.getString("about"),
                                            dataObject.getString("min_amount"),
                                            dataObject.getString("max_amount"),
                                            dataObject.getString("pr_hours"),
                                            dataObject.getString("pr_days"),
                                            dataObject.getString("cat_id"),
                                            dataObject.getString("service_name"),
                                            dataObject.getString("rating"),
                                            dataObject.getString("distance"),
                                            dataObject.getString("Professionalism_rating"),
                                            dataObject.getString("Behaviour_rating"),
                                            dataObject.getString("Satisfaction_rating"),
                                            dataObject.getString("Skills_rating"),
                                            dataObject.getString("job_done"));
                                    search_data_models.add(service_models);
                                    count=search_data_models.size();
                                }


                            } else {

                            }
                            txt_count_result.setText(String.valueOf(count));
                            adapter_search_data_vendor_all_detail = new Adapter_Search_data_vendor_alldetails(search_data_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_search_data.setLayoutManager(mLayoutManger);
                            rec_search_data.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            rec_search_data.setItemAnimator(new DefaultItemAnimator());
                            rec_search_data.setAdapter(adapter_search_data_vendor_all_detail);
                            adapter_search_data_vendor_all_detail.notifyDataSetChanged();
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

//    Search sorting services


    private void SearchdataSortingApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        search_data_models.clear();
        String url = BaseUrl +Searchsorting_Api ;
        System.out.println("params---keyword----     "+keyword);
        System.out.println("params----user_id---     "+user_id);
        System.out.println("params----latt---     "+latt);
        System.out.println("params---longi----     "+longi);
        System.out.println("params---rating----     "+rating);
        System.out.println("params---nearby----     "+nearby);
        System.out.println("params---highTolow----     "+highTolow);
        System.out.println("params---lowTohigh----     "+lowTohigh);
        System.out.println("params-----Profession--     "+Profession);
        System.out.println("params----fresher---     "+fresher);

        AndroidNetworking.post(url)
                .addBodyParameter("keyword",keyword )
                .addBodyParameter("user_id",user_id )
                    .addBodyParameter("lat",latt )
                   .addBodyParameter("lang",longi )
                 .addBodyParameter("rating",rating )
                 .addBodyParameter("nearby",nearby )
                .addBodyParameter("highTolow",highTolow )
                .addBodyParameter("lowTohigh",lowTohigh )
                .addBodyParameter("Profession",Profession )
                .addBodyParameter("fresher",fresher )

                /*keyword:Ac conditioner Repair
user_id:55
lat:22.7520048
lang:75.9045232
rating:rating
nearby:
highTolow:
lowTohigh:
profession:Profession
fresher:fresher*/



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
                                System.out.println(
                                        "SearchdataFilterApi-------   "+ jsonArray
                                );
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.e("jsonarray", jsonArray.toString());
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    Search_Data_Model_Vendor_Alldetails service_models = new Search_Data_Model_Vendor_Alldetails(
                                            dataObject.getString("id"),
                                            dataObject.getString("fname"),
                                            dataObject.getString("language"),
                                            dataObject.getString("about"),
                                            dataObject.getString("min_amount"),
                                            dataObject.getString("max_amount"),
                                            dataObject.getString("pr_hours"),
                                            dataObject.getString("pr_days"),
                                            dataObject.getString("cat_id"),
                                            dataObject.getString("service_name"),
                                            dataObject.getString("rating"),
                                            dataObject.getString("distance"),
                                            dataObject.getString("Professionalism_rating"),
                                            dataObject.getString("Behaviour_rating"),
                                            dataObject.getString("Satisfaction_rating"),
                                            dataObject.getString("Skills_rating"),
                                            dataObject.getString("job_done"));
                                    search_data_models.add(service_models);
                                    count=search_data_models.size();
                                }


                            } else {

                            }
                            txt_count_result.setText(String.valueOf(count));
                            adapter_search_data_vendor_all_detail = new Adapter_Search_data_vendor_alldetails(search_data_models, getActivity());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            rec_search_data.setLayoutManager(mLayoutManger);
                            rec_search_data.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            rec_search_data.setItemAnimator(new DefaultItemAnimator());
                            rec_search_data.setAdapter(adapter_search_data_vendor_all_detail);
                            adapter_search_data_vendor_all_detail.notifyDataSetChanged();
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