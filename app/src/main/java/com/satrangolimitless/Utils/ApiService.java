package com.satrangolimitless.Utils;
import com.satrangolimitless.model.Add_Booking_Blue_collar;
import com.satrangolimitless.model.Add_Booking_Multi_move;
import com.satrangolimitless.model.Add_Booking_Single_move;
import com.satrangolimitless.model.Add_Job_Post;
import com.satrangolimitless.model.Add_Job_Post_Multimove;
import com.satrangolimitless.model.BlueCollar_Job_Post;
import com.satrangolimitless.model.Place_Bid;
import com.satrangolimitless.model.UpdatePlace_Bid;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {




    @Multipart
    @POST("single_move_booking")
    Call<Add_Booking_Single_move> Add_Employee(

            @Part("user_id") RequestBody user_id,
            @Part("vendor_id") RequestBody vendor_id,
            @Part("service_id") RequestBody service_id,
            @Part("booking_date") RequestBody booking_date,
            @Part("from_time") RequestBody from_time,
            @Part("to_time") RequestBody to_time,
            @Part("start_location1") RequestBody start_location1,
            @Part("work_description") RequestBody work_description,
            @Part("booking_type") RequestBody booking_type,
            @Part MultipartBody.Part[] surveyImage


    );



    @Multipart
    @POST("blue_collar_booking")
    Call<Add_Booking_Blue_collar> Add_Employee_blue(

            @Part("user_id") RequestBody user_id,
            @Part("vendor_id") RequestBody vendor_id,
            @Part("service_id") RequestBody service_id,
            @Part("booking_date") RequestBody booking_date,
            @Part("from_time") RequestBody from_time,
            @Part("to_time") RequestBody to_time,
            @Part("start_location1") RequestBody start_location1,
            @Part("work_description") RequestBody work_description,
            @Part("job_estimate") RequestBody job_estimate,
            @Part("booking_type") RequestBody booking_type,
            @Part MultipartBody.Part[] surveyImage


    );

    @Multipart
    @POST("multi_move_booking")
    Call<Add_Booking_Multi_move> Add_Employee_Multi(

            @Part("user_id") RequestBody user_id,
            @Part("vendor_id") RequestBody vendor_id,
            @Part("service_id") RequestBody service_id,
            @Part("booking_date") RequestBody booking_date,
            @Part("from_time") RequestBody from_time,
            @Part("to_time") RequestBody to_time,
            @Part("start_location1") RequestBody start_location1,
            @Part("to_location1") RequestBody to_location1,
            @Part("end_location1") RequestBody end_location1,
            @Part("weight") RequestBody weight,
            @Part("work_description") RequestBody work_description,
            @Part("booking_type") RequestBody booking_type,
            @Part MultipartBody.Part[] surveyImage
    );



    @Multipart
    @POST("job_post")
    Call<Add_Job_Post> ADD_JOB_POST(

            @Part("user_id") RequestBody user_id,
            @Part("date") RequestBody date,
            @Part("time") RequestBody time,
            @Part("location") RequestBody location,
            @Part("estimate_time") RequestBody estimate_time,
            @Part("description") RequestBody description,
            @Part("accept_bid_for") RequestBody accept_bid_for,
            @Part("bid_per") RequestBody bid_per,
            @Part("bid_min_range") RequestBody bid_min_range,
            @Part("bid_max_range") RequestBody bid_max_range,
            @Part("language") RequestBody language,
            @Part("key_skills") RequestBody key_skills,
            @Part("job_type") RequestBody job_type,
            @Part("apply_type") RequestBody apply_type,
            @Part MultipartBody.Part[] surveyImage

   );


    @Multipart
    @POST("vendor_bid_at_job")
        Call<Place_Bid> PLACE_BID(

            @Part("vendor_id") RequestBody user_id,
            @Part("job_id") RequestBody job_id,
            @Part("bid_amount") RequestBody bid_amount,
            @Part("proposal") RequestBody proposal,
             @Part("estimated_time") RequestBody estimate_time,
                @Part("submission_type") RequestBody submission_type,
            @Part MultipartBody.Part[] surveyImage


    );

    @Multipart
    @POST("update_bid_job")
        Call<UpdatePlace_Bid> UPDATE_PLACE_BID(

            @Part("vendor_id") RequestBody user_id,
            @Part("job_id") RequestBody job_id,
            @Part("bid_amount") RequestBody bid_amount,
            @Part("proposal") RequestBody proposal,
             @Part("estimated_time") RequestBody estimate_time,
                @Part("submission_type") RequestBody submission_type,
            @Part MultipartBody.Part[] surveyImage


    );




    @Multipart
    @POST("multi_move_job_post")
    Call<Add_Job_Post_Multimove> ADD_JOB_POST_Multimove(

            @Part("user_id") RequestBody user_id,
            @Part("date") RequestBody date,
            @Part("time") RequestBody time,
            @Part("location") RequestBody location,
            @Part("to_location") RequestBody to_location,
            @Part("end_location") RequestBody end_location,
            @Part("weight") RequestBody weight,
            @Part("description") RequestBody description,
            @Part("bid_min_range") RequestBody bid_min_range,
            @Part("bid_max_range") RequestBody bid_max_range,
            @Part("bid_per") RequestBody bid_per,
            @Part("estimate_time") RequestBody estimate_time,
            @Part("accept_bid_for") RequestBody accept_bid_for,
            @Part("key_skills") RequestBody key_skills,
            @Part("vehical_type") RequestBody vehical_type,
            @Part("language") RequestBody language,
            @Part MultipartBody.Part[] surveyImage


    );



    @Multipart
    @POST("postJobEveryWhere")
    Call<BlueCollar_Job_Post> Post_job_Blue_collar(

            @Part("user_id") RequestBody user_id,
            @Part("date") RequestBody date,
            @Part("time") RequestBody time,
            @Part("location") RequestBody location,
            @Part("estimate_time") RequestBody estimate_time,
            @Part("description") RequestBody description,
            @Part("bid_per") RequestBody bid_per,
            @Part("bid_min_range") RequestBody bid_min_range,
            @Part("bid_max_range") RequestBody bid_max_range,
            @Part("accept_bid_for") RequestBody accept_bid_for,
            @Part("language") RequestBody language,
            @Part("key_skills") RequestBody key_skills,
            @Part("apply_type") RequestBody apply_type,
            @Part MultipartBody.Part[] surveyImage


    );




//
}
