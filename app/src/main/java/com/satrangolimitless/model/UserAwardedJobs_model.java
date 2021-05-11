package com.satrangolimitless.model;

public class UserAwardedJobs_model {
    String id,job_id,bid_amount,attachment,created_date,estimated_time,description,location,bid_min_range,bid_max_range,bid_per,accept_bid_for,v_name,v_image,status,language,total_bid,avg_range;

    public UserAwardedJobs_model(String id, String job_id, String bid_amount, String attachment, String created_date, String estimated_time, String description, String location, String bid_min_range, String bid_max_range, String bid_per, String accept_bid_for, String v_name, String v_image, String status, String language, String total_bid, String avg_range) {
        this.id = id;
        this.job_id = job_id;
        this.bid_amount = bid_amount;
        this.attachment = attachment;
        this.created_date = created_date;
        this.estimated_time = estimated_time;
        this.description = description;
        this.location = location;
        this.bid_min_range = bid_min_range;
        this.bid_max_range = bid_max_range;
        this.bid_per = bid_per;
        this.accept_bid_for = accept_bid_for;
        this.v_name = v_name;
        this.v_image = v_image;
        this.status = status;
        this.language = language;
        this.total_bid = total_bid;
        this.avg_range = avg_range;
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

    public String getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(String bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(String estimated_time) {
        this.estimated_time = estimated_time;
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

    public String getBid_per() {
        return bid_per;
    }

    public void setBid_per(String bid_per) {
        this.bid_per = bid_per;
    }

    public String getAccept_bid_for() {
        return accept_bid_for;
    }

    public void setAccept_bid_for(String accept_bid_for) {
        this.accept_bid_for = accept_bid_for;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getV_image() {
        return v_image;
    }

    public void setV_image(String v_image) {
        this.v_image = v_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTotal_bid() {
        return total_bid;
    }

    public void setTotal_bid(String total_bid) {
        this.total_bid = total_bid;
    }

    public String getAvg_range() {
        return avg_range;
    }

    public void setAvg_range(String avg_range) {
        this.avg_range = avg_range;
    }
}
