package com.satrangolimitless.Utils;

public interface Base_Url {
//https://satrango.com/Api/
//    http://logicaltest.in/satarango/Api/
//        String BaseUrl = "http://logicaltest.in/satarango/Api/";
        String BaseUrl = "https://satrango.com/Api/";
//        String Image_url = "http://logicaltest.in/satarango/assets/uploaded/users/";
        String Image_url = "https://satrango.com/assets/uploaded/users/";




    String Profileimage_url = "";
    String mediimage_url = "https://satrango.com/assets/media/users/";

    String Signup_Api = "signup";
    String login_Api = "login";
    String logout = "logout";
    String ResendOTP_Api = "resend_otp";
    String verify_otp_signup_Api = "verify_otp";
    String verify_otp_forget_password_Api = "forget_otp_verify";
    String Setpassword_Api = "set_password";
    String Forgotpassword_Api = "forgot_password";
    String Resetpassword_Api = "reset_password";
    String Profileview = "get_user_profile";
    String Updateprofile = "user_update_profile";
    String User_jobpostpending = "user_posted_job";
    String User_jobdiscussion = "user_get_chat";
    String vendor_update_tariff = "vendor_update_tariff";

    //Userside


    String term_condition = "term_condition";
    String privacy_policy = "privacy_policy";
    String faq = "faq";
    String app_feedback = "app_feedback";
    String complaints = "complaints";
    String GetBookings_pending_user = "get_user_booking";
    String AwardVendorBid = "bid_awarded_by_user";
    String RejectVendorBid = "bid_reject";


    //Venodr side api----------------------------------------------------------------------------------------------

    String Get_Professions = "get_professions_list";
    String Upload_video = "upload_video";
    String Addtarriff_Api = "add_tariff";
    String Searchdata_Api = "search_data";
    String Searchfilter_Api = "filter_services";
    String Searchsorting_Api = "sorting_services";
    String View_vendor_profile = "get_vendor_profile";
    String View_vendor_tarrif = "user_teriff";
    String vendor_update_profile_personal = "vendor_update_profile";
    String vendor_update_skills = "vendor_update_skills";
    String Add_vendor = "add_vendor_profile";
    String Category_Api = "category";
    String Service_Api = "services";
    String Service_list_Api = "services_list";

    String Getbooking_status_during_timer = "get_work_status";
    String Get_upcoming_booking_Vendor = "get_upcoming_booking";
    String Get_InProgress_booking_Vendor = "get_inprogress_booking";
    String Get_InProgress_booking_USER = "user_inprogress_booking";

    String User_mark_appropriate_doc = "booking_doc_appropriate";
    String Booking_accept_by_vendor = "booking_accept_by_vendor";
    String Get_vendor_availability_time = "get_vendor_availability_time";
    String Booking_cancel_by_vendor = "booking_cancel";
    String View_Bookingdetail_Notificationclick = "booking_detail";


    String View_Vendor_reject_bookings = "vendor_canel_booking_list";
    String generateOTPOnBooking = "generateOTPOnBooking";
    String Start_Booking_by_vendor = "booking_start_by_vendor";
    String OTP_MATCH_by_vendor_usergiven = "booking_match_otp";

    //    match_otp
    String Booking_cancel_by_vendor_after_accept = "booking_cancel_after_accept";
    String Booking_reshedule_by_vendor_after_accept = "reshedule_booking";
    String Booking_paused_by_vendor = "booking_paushed";
    String Booking_resumed_by_vendor = "booking_resumed";
    String Booking_markcomplete_by_vendor = "booking_complete";
    String Raiseextraamount_by_vendor = "vendor_exrta_charge";
    String BillDetails_Beforecomplete_by_vendor = "bill_detail";
    String Bookingcomplete_by_vendor_getOTP = "booking_complete_confirm";
    String Bookingmarkcomplete_by_user_matchOTPvendor = "booking_completed_by_user";
    String ShowBookingcomplete_reject_bookingbothside = "completed_booking";


    String Vendor_Notifications = "get_notification";
    String UserNotifications = "get_user_notification";
    String get_latest_offer = "get_latest_offer";
    String get_expiry_soon = "get_expiry_soon";
    String get_tailormadeofer_offers = "get_all_offers";
    String get_Vendor_New_Jobs = "get_vendor_jobs";
    String get_Vendor_awarded_Jobs = "get_vendor_bid_list";
    String get_vendor_allbid_list = "get_vendor_allbid_list";
    String get_User_awarded_Jobs = "get_user_bid_list";
    String user_getbids_placed_vendorlist = "get_bid_list";
    String user_side_bids_discussions = "discussion_list";
    String vendor_discussion_list = "vendor_discussion_list";

    String user_side_chat = "user_discuss_with_vendor";
    String vendor_side_chat = "vendor_discuss_with_user";
    String bookingListByDate = "bookingListByDate";
    String get_week_days = "get_week_days";
    String mark_complete = "mark_complete";
    String rate_booking_userside = "post_rating";
    String Top_Picks = "Top_Picks";
    String update_exta_booking_status = "update_exta_booking_status";
    String vendor_online_offline_status = "onlineOffline";
    String leaderboard = "leaderboard";
    String hotJobs = "hotJobs";
    String MyAccountdetails_user_side = "user_account_details";
    String vendor_account_detail = "vendor_account_detail";

    String Add_Bank_account = "addbankaccount";
    String UserBank_account = "getAccount";
    String UserAddfunds = "addFunds";
    String Userwithdrawfunds = "addWithdraw";
    String getTransactionhistory = "getTransactionhistory";
    String getvenderrating = "getvenderrating";



    String user_discuss_booking_with_vendor = "user_discuss_booking_with_vendor";
    String vendor_discuss_booking_with_user = "vendor_discuss_booking_with_user";
    String user_get_booking_chat = "user_get_booking_chat";
    String vendor_get_booking_chat = "vendor_get_booking_chat";
    String getcomplaints = "getcomplaints";
    String getbookingtime = "getTime";

}
