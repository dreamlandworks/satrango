package com.satrangolimitless.model;

public class Vendor_Notification_model {

      String bookingId,sender_id,receiver_id,receiver_type,message,
    created_date,fname,image,type ;

    public Vendor_Notification_model(String bookingId, String sender_id, String receiver_id, String receiver_type, String message, String created_date, String fname, String image, String type) {
        this.bookingId = bookingId;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.receiver_type = receiver_type;
        this.message = message;
        this.created_date = created_date;
        this.fname = fname;
        this.image = image;
        this.type = type;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
