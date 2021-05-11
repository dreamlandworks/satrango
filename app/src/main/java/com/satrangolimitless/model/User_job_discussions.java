package com.satrangolimitless.model;

public class User_job_discussions {
    String id,vendor_id,vendor_msg,v_name,v_image;

    public User_job_discussions(String id, String vendor_id, String vendor_msg, String v_name, String v_image) {
        this.id = id;
        this.vendor_id = vendor_id;
        this.vendor_msg = vendor_msg;
        this.v_name = v_name;
        this.v_image = v_image;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendor_msg() {
        return vendor_msg;
    }

    public void setVendor_msg(String vendor_msg) {
        this.vendor_msg = vendor_msg;
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
