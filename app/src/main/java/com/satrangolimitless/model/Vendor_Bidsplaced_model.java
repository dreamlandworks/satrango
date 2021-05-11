package com.satrangolimitless.model;

public class Vendor_Bidsplaced_model {
String id,job_id, description,location,bid_min_range,bid_max_range,fname,image,avg_range,status, total_bid,attachment,estimated_time,created_date,proposal,bid_amount,submission_type;

    public Vendor_Bidsplaced_model(String id, String job_id, String description, String location, String bid_min_range, String bid_max_range, String fname, String image, String avg_range, String status, String total_bid, String attachment, String estimated_time, String created_date, String proposal, String bid_amount, String submission_type) {
        this.id = id;
        this.job_id = job_id;
        this.description = description;
        this.location = location;
        this.bid_min_range = bid_min_range;
        this.bid_max_range = bid_max_range;
        this.fname = fname;
        this.image = image;
        this.avg_range = avg_range;
        this.status = status;
        this.total_bid = total_bid;
        this.attachment = attachment;
        this.estimated_time = estimated_time;
        this.created_date = created_date;
        this.proposal = proposal;
        this.bid_amount = bid_amount;
        this.submission_type = submission_type;
    }

    public String getSubmission_type() {
        return submission_type;
    }

    public void setSubmission_type(String submission_type) {
        this.submission_type = submission_type;
    }

    public String getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(String bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBid_min_range() {
        return bid_min_range;
    }

    public void setBid_min_range(String bid_min_range) {
        this.bid_min_range = bid_min_range;
    }

    public String getBid_max_range() {
        return bid_max_range;
    }

    public void setBid_max_range(String bid_max_range) {
        this.bid_max_range = bid_max_range;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAvg_range() {
        return avg_range;
    }

    public void setAvg_range(String avg_range) {
        this.avg_range = avg_range;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_bid() {
        return total_bid;
    }

    public void setTotal_bid(String total_bid) {
        this.total_bid = total_bid;
    }
}


