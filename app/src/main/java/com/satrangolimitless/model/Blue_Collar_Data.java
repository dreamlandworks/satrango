package com.satrangolimitless.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Blue_Collar_Data {
    @SerializedName("bookingId")
    @Expose
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
