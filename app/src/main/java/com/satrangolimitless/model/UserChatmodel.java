package com.satrangolimitless.model;

public class UserChatmodel {
    String id;
    String job_id;
    String user_id;
    String vendor_id;
    String user_msg;
    String vendor_msg;
    String files;
    String date_time;
    String v_name;
    String v_image;


    public UserChatmodel(String id, String job_id, String user_id, String vendor_id, String user_msg, String vendor_msg, String files, String date_time, String v_name, String v_image) {
        this.id = id;
        this.job_id = job_id;
        this.user_id = user_id;
        this.vendor_id = vendor_id;
        this.user_msg = user_msg;
        this.vendor_msg = vendor_msg;
        this.files = files;
        this.date_time = date_time;
        this.v_name = v_name;
        this.v_image = v_image;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getUser_msg() {
        return user_msg;
    }

    public void setUser_msg(String user_msg) {
        this.user_msg = user_msg;
    }

    public String getVendor_msg() {
        return vendor_msg;
    }

    public void setVendor_msg(String vendor_msg) {
        this.vendor_msg = vendor_msg;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
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
}
