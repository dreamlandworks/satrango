package com.satrangolimitless.model;

public class Confirm_Bid {
    String id,job_id,vendor_id,bid_amount,proposal,estimated_time,attachment,submission_type,accepted_by,reject_by,status,modify_date,user_name,phone;

    public Confirm_Bid(String id, String job_id, String vendor_id, String bid_amount, String proposal, String estimated_time, String attachment, String submission_type, String accepted_by, String reject_by, String status, String modify_date, String user_name, String phone) {
        this.id = id;
        this.job_id = job_id;
        this.vendor_id = vendor_id;
        this.bid_amount = bid_amount;
        this.proposal = proposal;
        this.estimated_time = estimated_time;
        this.attachment = attachment;
        this.submission_type = submission_type;
        this.accepted_by = accepted_by;
        this.reject_by = reject_by;
        this.status = status;
        this.modify_date = modify_date;
        this.user_name = user_name;
        this.phone = phone;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(String bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public String getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(String estimated_time) {
        this.estimated_time = estimated_time;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSubmission_type() {
        return submission_type;
    }

    public void setSubmission_type(String submission_type) {
        this.submission_type = submission_type;
    }

    public String getAccepted_by() {
        return accepted_by;
    }

    public void setAccepted_by(String accepted_by) {
        this.accepted_by = accepted_by;
    }

    public String getReject_by() {
        return reject_by;
    }

    public void setReject_by(String reject_by) {
        this.reject_by = reject_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModify_date() {
        return modify_date;
    }

    public void setModify_date(String modify_date) {
        this.modify_date = modify_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
