package com.satrangolimitless.model;

public class Vendor_job_bids_list {

   String v_image,v_name,submission_type,attachment,estimated_time,proposal,bid_amount,vendor_id,job_id,id,job_points,total,usersconn,rating,status;

    public Vendor_job_bids_list(String id, String job_id, String vendor_id , String bid_amount, String proposal, String  estimated_time, String attachment, String submission_type, String  v_name, String v_image, String job_points, String total, String usersconn, String rating, String status) {
        this.v_image = v_image;
        this.v_name = v_name;
        this.submission_type = submission_type;
        this.attachment = attachment;
        this.estimated_time = estimated_time;
        this.proposal = proposal;
        this.bid_amount = bid_amount;
        this.vendor_id = vendor_id;
        this.job_id = job_id;
        this.id = id;
        this.job_points = job_points;
        this.total = total;
        this.usersconn = usersconn;
        this.rating = rating;
        this.status = status;
    }

    public String getV_image() {
        return v_image;
    }

    public void setV_image(String v_image) {
        this.v_image = v_image;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getSubmission_type() {
        return submission_type;
    }

    public void setSubmission_type(String submission_type) {
        this.submission_type = submission_type;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(String estimated_time) {
        this.estimated_time = estimated_time;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public String getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(String bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getJob_points() {
        return job_points;
    }

    public void setJob_points(String job_points) {
        this.job_points = job_points;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsersconn() {
        return usersconn;
    }

    public void setUsersconn(String usersconn) {
        this.usersconn = usersconn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
