package com.satrangolimitless.model;

public class Vendor_newjob_discussions {
    String id,user_id,vendor_msg,v_name,v_image;

    public Vendor_newjob_discussions(String id,String user_id, String vendor_msg, String v_name, String v_image) {
        this.id = id;
        this.vendor_msg = vendor_msg;
        this.v_name = v_name;
        this.v_image = v_image;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
