package com.satrangolimitless.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Add_Booking_Blue_collar {

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    private Data data;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
