package com.satrangolimitless.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.satrangolimitless.R;
import com.satrangolimitless.User_UI.My_Job_Posts.Activity_user_jobpost_biddetails;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_Booking_Confirm_Details_Inprogress;
import com.satrangolimitless.model.User_job_bids_list;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.satrangolimitless.Utils.Base_Url.AwardVendorBid;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Image_url;
import static com.satrangolimitless.Utils.Base_Url.RejectVendorBid;

public class Adapter_User_Pending_jobs_Bids extends RecyclerView.Adapter<Adapter_User_Pending_jobs_Bids.ViewHolder> {
    private final List<User_job_bids_list> category_home_models;
    private final Context context;
    Session_vendor session1;
    String e1 = "", e2 = "", e3 = "", e4 = "", otp = "";
    String vid, bookingid;
    private EditText[] editTexts;
String job_id,id,user_id;
    private RequestQueue rQueue;
    public Adapter_User_Pending_jobs_Bids(List<User_job_bids_list> category_home_models, Context context) {
        this.category_home_models = category_home_models;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_bid_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User_job_bids_list allCommunityModel = category_home_models.get(position);

        session1 = new Session_vendor(context);
        holder.txtbidamount.setText("₹ " + category_home_models.get(position).getBid_amount());
        holder.txtestimatetime.setText( category_home_models.get(position).getEstimated_time());
        holder.txtproposal.setText( category_home_models.get(position).getProposal());
        holder.txtvname.setText(  category_home_models.get(position).getV_name());
        holder.txtrating.setText(  category_home_models.get(position).getRating());
        holder.txtrank.setText( "# "+ category_home_models.get(position).getJob_points());
        holder.txtjobpoints.setText(  category_home_models.get(position).getTotal());
        holder.txtuserconn.setText(  category_home_models.get(position).getUsersconn());

        Glide.with(context).load(Image_url + allCommunityModel.getV_image()).into(holder.v_imagebids);



        holder.txtaward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.lldetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Activity_user_jobpost_biddetails.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("jobid", category_home_models.get(position).getJob_id());
                intent.putExtra("esttime", category_home_models.get(position).getEstimated_time());
                intent.putExtra("proposal", category_home_models.get(position).getProposal());
                intent.putExtra("name", category_home_models.get(position).getV_name());
                intent.putExtra("vendrid", category_home_models.get(position).getVendor_id());
                intent.putExtra("attachment", category_home_models.get(position).getAttachment());
                intent.putExtra("v_image", category_home_models.get(position).getAttachment());
                intent.putExtra("job_points", category_home_models.get(position).getJob_points());
                intent.putExtra("usersconn", category_home_models.get(position).getUsersconn());
                intent.putExtra("total", category_home_models.get(position).getTotal());
                intent.putExtra("rating", category_home_models.get(position).getRating());
                intent.putExtra("bid_amount", category_home_models.get(position).getBid_amount());


                context.startActivity(intent);


            }
        });

        holder.txtaward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

               /* id=category_home_models.get(position).getId();
                job_id=category_home_models.get(position).getJob_id();

                AwardBidapi();*/
            }
        });
        holder.txtreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

               /* id=category_home_models.get(position).getId();
                job_id=category_home_models.get(position).getJob_id();
                RejectBidapi();*/
            }
        });






    }


    @Override
    public int getItemCount() {
        return category_home_models.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lldetails,ll_reject,ll_award;
        ImageView img_call, heart, v_imagebids;
        TextView
        txtreject, txtestimatetime,txtbidamount,txtproposal,txtvname,txtaward,txtrating,txtrank,txtjobpoints,txtuserconn;
        CircleImageView imageprofile;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtestimatetime = itemView.findViewById(R.id.txtestimatetime);
            txtbidamount = itemView.findViewById(R.id.txtbidamount);
            txtproposal = itemView.findViewById(R.id.txtproposal);
            txtvname = itemView.findViewById(R.id.txtvname);
            txtreject = itemView.findViewById(R.id.txtreject);
            txtaward = itemView.findViewById(R.id.txtaward);
            txtrating = itemView.findViewById(R.id.txtrating);
            txtrank = itemView.findViewById(R.id.txtrank);
            txtjobpoints = itemView.findViewById(R.id.txtjobpoints);
            v_imagebids = itemView.findViewById(R.id.v_imagebids);
            lldetails = itemView.findViewById(R.id.lldetails);
            txtuserconn = itemView.findViewById(R.id.txtuserconn);
            ll_reject = itemView.findViewById(R.id.ll_reject);
            ll_award = itemView.findViewById(R.id.ll_award);
        }
    }

    
    
    
    
    
//    Award Bids  api


    private void AwardBidapi() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + AwardVendorBid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);

                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");


//                                System.out.println("profile======    " + jsonObject1.toString());


                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", session1.getUserId());
                params.put("bid_id", id);
                params.put("job_id", job_id);

                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);


    }



//Reject bids api



    private void RejectBidapi() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl + RejectVendorBid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);

                            String msg = jsonObject.getString("msg");
                            if (jsonObject.optString("result").equals("true")) {
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");


//                                System.out.println("profile======    " + jsonObject1.toString());


                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                            } else {
                                progressDialog.dismiss();

                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", session1.getUserId());
                params.put("bid_id", id);
                params.put("job_id", job_id);

                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);


    }







}
