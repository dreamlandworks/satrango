package com.satrangolimitless.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.satrangolimitless.R;
import com.satrangolimitless.Vendor_UI.Activity_Single_movebooking_details;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_Booking_ConfirmDetails;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_CancelBooking_activity;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_MyBooking;
import com.satrangolimitless.Vendor_UI.Vendor_MyBooking.Vendor_ResheduleBooking_activity;
import com.satrangolimitless.model.Confirm_Bid;
import com.satrangolimitless.model.Vendor_UpcomingBooking;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.OTP_MATCH_by_vendor_usergiven;
import static com.satrangolimitless.Utils.Base_Url.Start_Booking_by_vendor;

public class Adapter_Vendor_HomeUpcomingBooking extends RecyclerView.Adapter<Adapter_Vendor_HomeUpcomingBooking.ViewHolder> {
    private final List<Vendor_UpcomingBooking> category_home_models;
    private final List<Confirm_Bid> confirm_bids;
    private final Context context;
    Session_vendor session1;
    String e1 = "", e2 = "", e3 = "", e4 = "", otp = "";
    String vid, bookingid;
    private EditText[] editTexts;

    public Adapter_Vendor_HomeUpcomingBooking(List<Vendor_UpcomingBooking> category_home_models, List<Confirm_Bid> confirm_bids, Context context) {
        this.category_home_models = category_home_models;
        this.confirm_bids = confirm_bids;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vendor_booking_upcoming, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Vendor_UpcomingBooking allCommunityModel = category_home_models.get(position);

        session1 = new Session_vendor(context);

        holder.txtname.setText(category_home_models.get(position).getUser_name());
        holder.txtrating.setText(category_home_models.get(position).getUser_rating());
        holder.txtamount.setText("₹ " + category_home_models.get(position).getMin_charge());
        holder.txtfromtime.setText(category_home_models.get(position).getFrom_time());
        holder.txttotime.setText("to " + category_home_models.get(position).getTo_time());
        holder.txtlocation.setText(category_home_models.get(position).getStart_location1());
        holder.txt_desc.setText(category_home_models.get(position).getWork_description());
        vid = session1.getUserId();


     /* try{

          final Confirm_Bid allCommunityModel2 = confirm_bids.get(position);
      }catch (IndexOutOfBoundsException e){
          e.printStackTrace();
      }
*/





/*
        try {
            String booking_date = category_home_models.get(position).getBooking_date();
            String start_dt = booking_date;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
            Date date = formatter.parse(start_dt);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String finalString = newFormat.format(date);
            System.out.println("final string------     " + finalString);
            holder.txtsheduleon.setText(finalString);


        } catch (Exception e) {
            e.printStackTrace();
        }
*/




/*
        Glide.with(context)
                .load(mediimage_url+allCommunityModel.getUser_image())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .dontTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .skipMemoryCache(true)
                .apply(new RequestOptions().override(600, 200)). into(holder.imageprofile);
*/

        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, Activity_Single_movebooking_details.class);
                intent.putExtra("id", category_home_models.get(position).getId());



               System.out.println("adapter home vendorupcom----      "+category_home_models.get(position).getId());
                context.startActivity(intent);

            }
        });


        holder.txtstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Vendor_Booking_ConfirmDetails.class);

                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("name", category_home_models.get(position).getUser_name());
                intent.putExtra("date", category_home_models.get(position).getDate_time());
                intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
                intent.putExtra("reting", category_home_models.get(position).getUser_rating());
                intent.putExtra("price", category_home_models.get(position).getMin_charge());
                intent.putExtra("status", category_home_models.get(position).getJob_status());
                intent.putExtra("image", category_home_models.get(position).getUser_image());

                context.startActivity(intent);

//                bookingid = category_home_models.get(position).getId();
//                Startbooking();
            }
        });


        holder.txtreshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Vendor_ResheduleBooking_activity.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("name", category_home_models.get(position).getUser_name());
                intent.putExtra("date", category_home_models.get(position).getDate_time());
                intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
                intent.putExtra("reting", category_home_models.get(position).getUser_rating());
                intent.putExtra("price", category_home_models.get(position).getMin_charge());
                intent.putExtra("image", category_home_models.get(position).getUser_image());

                context.startActivity(intent);
            }
        });

        holder.txtcancelbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Vendor_CancelBooking_activity.class);
                intent.putExtra("id", category_home_models.get(position).getId());
                intent.putExtra("name", category_home_models.get(position).getUser_name());
                intent.putExtra("date", category_home_models.get(position).getDate_time());
                intent.putExtra("bookingid", category_home_models.get(position).getBookingId());
                intent.putExtra("reting", category_home_models.get(position).getUser_rating());
                intent.putExtra("price", category_home_models.get(position).getMin_charge());
                intent.putExtra("image", category_home_models.get(position).getUser_image());
                context.startActivity(intent);
            }
        });


//job post

 /*       if (confirm_bids.isEmpty())
        {
            System.out.println("if<><><><   "+confirm_bids);
            holder.ll_bidview.setVisibility(View.GONE);
        }else {
            System.out.println("else<><><><   "+confirm_bids);
            holder.ll_bidview.setVisibility(View.VISIBLE);

            holder.txtname2.setText(confirm_bids.get(position).getUser_name());
            holder.txtrating2.setVisibility(View.GONE);
            holder.txtamount2.setText("₹ " + confirm_bids.get(position).getBid_amount());
            holder.txtfromtime2.setText(confirm_bids.get(position).getModify_date());
            holder.txttotime2.setVisibility(View.GONE);
            holder.txtlocation2.setVisibility(View.GONE);
            holder.txt_desc2.setText(confirm_bids.get(position).getProposal());

        }


        holder.txtcancelbooking2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Vendor_CancelBooking_activity.class);
                intent.putExtra("id", confirm_bids.get(position).getId());
                intent.putExtra("name", confirm_bids.get(position).getUser_name());
                intent.putExtra("date", confirm_bids.get(position).getModify_date());
                intent.putExtra("bookingid", "");
                intent.putExtra("reting", "");
                intent.putExtra("price", confirm_bids.get(position).getBid_amount());
                intent.putExtra("image", confirm_bids.get(position).getAttachment());

                context.startActivity(intent);
            }
        });

        holder.txtreshedule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Vendor_ResheduleBooking_activity.class);
                intent.putExtra("id", confirm_bids.get(position).getId());
                intent.putExtra("name", confirm_bids.get(position).getUser_name());
                intent.putExtra("date", confirm_bids.get(position).getModify_date());
                intent.putExtra("bookingid", confirm_bids.get(position).getJob_id());
                intent.putExtra("reting", "");
                intent.putExtra("price", confirm_bids.get(position).getBid_amount());
                intent.putExtra("image", confirm_bids.get(position).getAttachment());

                context.startActivity(intent);
            }
        });


        holder.txtstart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Vendor_Booking_ConfirmDetails.class);

                intent.putExtra("id", confirm_bids.get(position).getId());
                intent.putExtra("name", confirm_bids.get(position).getUser_name());
                intent.putExtra("date", confirm_bids.get(position).getModify_date());
                intent.putExtra("bookingid", confirm_bids.get(position).getJob_id());
                intent.putExtra("reting","");
                intent.putExtra("price", confirm_bids.get(position).getBid_amount());
                intent.putExtra("status", "");
                intent.putExtra("image", "");
                context.startActivity(intent);

//                bookingid = category_home_models.get(position).getId();
//                Startbooking();
            }
        });


        holder.ll_details2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, Activity_Single_movebooking_details.class);
                intent.putExtra("id", confirm_bids.get(position).getId());
                context.startActivity(intent);

            }
        });*/

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

    private void Startbooking() {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + Start_Booking_by_vendor;
        System.out.println("param vid " + vid + "  bookid " + bookingid);
        AndroidNetworking.post(url)
                .addBodyParameter("vendor_id", vid)
                .addBodyParameter("bookingId", bookingid)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("Startbookingresponce-  ", jsonObject.toString());
                                OTPmatchdialog();

                            } else {


                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }


//Start booking by vendor api

    public void OTPmatchdialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_booking_otp_confirm, null);

        builder.setCancelable(false);


        Button btn_yes = view.findViewById(R.id.btn_yes);
        final EditText edt_one = view.findViewById(R.id.edt_one);
        final EditText edt_two = view.findViewById(R.id.edt_two);
        final EditText edt_three = view.findViewById(R.id.edt_three);
        final EditText edt_four = view.findViewById(R.id.edt_four);
        edt_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    edt_two.requestFocus();
                }
            }
        });

        edt_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    edt_three.requestFocus();
                }
            }
        });

        edt_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    edt_four.requestFocus();
                }
            }
        });


        edt_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {

                }
            }
        });


        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1 = edt_one.getText().toString();
                e2 = edt_two.getText().toString();
                e3 = edt_three.getText().toString();
                e4 = edt_four.getText().toString();
                otp = e1 + e2 + e3 + e4;

                if (otp.isEmpty()) {

                    Toast.makeText(context, "Please enter correct OTP.", Toast.LENGTH_SHORT).show();
                } else {

                    OTP_Matchapi();
                }

            }
        });
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


//Dialog for otp match api

    private void OTP_Matchapi() {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        String url = BaseUrl + OTP_MATCH_by_vendor_usergiven;
        AndroidNetworking.post(url)
                .addBodyParameter("otp", otp)
                .addBodyParameter("bookingId", bookingid)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {

                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                Log.e("otpmatchapiresponce-  ", jsonObject.toString());

                                Vendor_MyBooking fragment = new Vendor_MyBooking();
                                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame, fragment)
                                        .commit();
                            } else {


                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_my_join", anError.toString());


                    }
                });


    }

//Api for OTP MATCH BY VENDOR

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_details,ll_details2,ll_bidview;

        ImageView img_call, heart, fav2;

        TextView txtreshedule, txtname, txt_desc, txtsheduleon, txtfromtime, txttotime, txtlocation, txtamount, txtrating, txtstart, txtcancelbooking;
        TextView txtreshedule2, txtname2, txt_desc2, txtsheduleon2, txtfromtime2, txttotime2, txtlocation2, txtamount2, txtrating2, txtstart2, txtcancelbooking2;

        CircleImageView imageprofile;
        CircleImageView imageprofile2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageprofile = itemView.findViewById(R.id.imageprofile);
            txtreshedule = itemView.findViewById(R.id.txtreshedule);
            txtstart = itemView.findViewById(R.id.txtstart);
            txtcancelbooking = itemView.findViewById(R.id.txtcancelbooking);
            txtname = itemView.findViewById(R.id.txtname);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txtsheduleon = itemView.findViewById(R.id.txtsheduleon);
            txtfromtime = itemView.findViewById(R.id.txtfromtime);
            txttotime = itemView.findViewById(R.id.txttotime);
            txtlocation = itemView.findViewById(R.id.txtlocation);
            txtamount = itemView.findViewById(R.id.txtamount);
            txtrating = itemView.findViewById(R.id.txtrating);
            ll_details = itemView.findViewById(R.id.ll_details);
            ll_bidview = itemView.findViewById(R.id.ll_bidview);


            txtreshedule2 = itemView.findViewById(R.id.txtreshedule2);
            txtstart2 = itemView.findViewById(R.id.txtstart2);
            txtcancelbooking2 = itemView.findViewById(R.id.txtcancelbooking2);
            txtname2 = itemView.findViewById(R.id.txtname2);
            txt_desc2 = itemView.findViewById(R.id.txt_desc2);
            txtsheduleon2 = itemView.findViewById(R.id.txtsheduleon2);
            txtfromtime2 = itemView.findViewById(R.id.txtfromtime2);
            txttotime2 = itemView.findViewById(R.id.txttotime2);
            txtlocation2 = itemView.findViewById(R.id.txtlocation2);
            txtamount2 = itemView.findViewById(R.id.txtamount2);
            txtrating2 = itemView.findViewById(R.id.txtrating2);
            ll_details2 = itemView.findViewById(R.id.ll_details2);
        }
    }


}
