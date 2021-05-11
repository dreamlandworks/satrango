package com.satrangolimitless.model;

    public class User_UpcomingBooking {

   String id ,type, reason,
           cancel_by, job_status, date_time,
           weight, job_estimate, upload_doc,
           work_description, end_location3,
           to_location3, start_location3,
           end_location2, to_loaction2,start_location2,
           end_location1, to_location1, start_location1,
           to_time, from_time, booking_date,
           service_id,vendor_id, user_id, bookingId,image, address, user_contact, user_name,min_charge,user_image,user_rating;



        public User_UpcomingBooking(String id, String bookingId, String user_id, String vendor_id, String booking_date, String from_time, String to_time, String start_location1, String work_description, String type, String job_status, String to_location3, String user_name, String user_contact, String address, String image, String min_charge, String user_image, String user_rating) {
        this.id = id;
        this.type = type;
        this.start_location1 = start_location1;
        this.to_time = to_time;
        this.from_time = from_time;
        this.booking_date = booking_date;
        this.job_status = job_status;
        this.work_description = work_description;
        this.to_location3 = to_location3;
        this.vendor_id = vendor_id;
        this.user_id = user_id;
        this.bookingId = bookingId;
        this.user_name = user_name;
        this.user_contact = user_contact;
        this.address = address;
        this.image = image;
        this.reason = reason;
        this.cancel_by = cancel_by;
        this.date_time = date_time;
        this.weight = weight;
        this.job_estimate = job_estimate;
        this.upload_doc = upload_doc;
        this.end_location3 = end_location3;
        this.start_location3 = start_location3;
        this.end_location2 = end_location2;
        this.to_loaction2 = to_loaction2;
        this.start_location2 = start_location2;
        this.end_location1 = end_location1;
        this.to_location1 = to_location1;
        this.service_id = service_id;
        this.min_charge = min_charge;
        this.user_image = user_image;
        this.user_rating = user_rating;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCancel_by() {
        return cancel_by;
    }

    public void setCancel_by(String cancel_by) {
        this.cancel_by = cancel_by;
    }

    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getJob_estimate() {
        return job_estimate;
    }

    public void setJob_estimate(String job_estimate) {
        this.job_estimate = job_estimate;
    }

    public String getUpload_doc() {
        return upload_doc;
    }

    public void setUpload_doc(String upload_doc) {
        this.upload_doc = upload_doc;
    }

    public String getWork_description() {
        return work_description;
    }

    public void setWork_description(String work_description) {
        this.work_description = work_description;
    }

    public String getEnd_location3() {
        return end_location3;
    }

    public void setEnd_location3(String end_location3) {
        this.end_location3 = end_location3;
    }

    public String getTo_location3() {
        return to_location3;
    }

    public void setTo_location3(String to_location3) {
        this.to_location3 = to_location3;
    }

    public String getStart_location3() {
        return start_location3;
    }

    public void setStart_location3(String start_location3) {
        this.start_location3 = start_location3;
    }

    public String getEnd_location2() {
        return end_location2;
    }

    public void setEnd_location2(String end_location2) {
        this.end_location2 = end_location2;
    }

    public String getTo_loaction2() {
        return to_loaction2;
    }

    public void setTo_loaction2(String to_loaction2) {
        this.to_loaction2 = to_loaction2;
    }

    public String getStart_location2() {
        return start_location2;
    }

    public void setStart_location2(String start_location2) {
        this.start_location2 = start_location2;
    }

    public String getEnd_location1() {
        return end_location1;
    }

    public void setEnd_location1(String end_location1) {
        this.end_location1 = end_location1;
    }

    public String getTo_location1() {
        return to_location1;
    }

    public void setTo_location1(String to_location1) {
        this.to_location1 = to_location1;
    }

    public String getStart_location1() {
        return start_location1;
    }

    public void setStart_location1(String start_location1) {
        this.start_location1 = start_location1;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUser_contact() {
            return user_contact;
        }

        public void setUser_contact(String user_contact) {
            this.user_contact = user_contact;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getMin_charge() {
            return min_charge;
        }

        public void setMin_charge(String min_charge) {
            this.min_charge = min_charge;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getUser_rating() {
            return user_rating;
        }

        public void setUser_rating(String user_rating) {
            this.user_rating = user_rating;
        }
    }
