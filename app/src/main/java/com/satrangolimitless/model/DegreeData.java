package com.satrangolimitless.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DegreeData {
    @SerializedName("bookingId")
    @Expose
    private String id;



    public DegreeData(String id) {
        this.id=id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
