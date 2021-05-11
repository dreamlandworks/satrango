package com.satrangolimitless.model;

public class User_Notification_model  {
    String receiver_id,receiver_type,message,created_date;

    public User_Notification_model(String receiver_id, String receiver_type, String message, String created_date) {
        this.receiver_id = receiver_id;
        this.receiver_type = receiver_type;
        this.message = message;
        this.created_date = created_date;
    }


    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getReceiver_type() {
        return receiver_type;
    }

    public void setReceiver_type(String receiver_type) {
        this.receiver_type = receiver_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
