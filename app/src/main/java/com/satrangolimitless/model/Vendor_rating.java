package com.satrangolimitless.model;

public class Vendor_rating {

    String id,booking_id,Professionalism_rating,Behaviour_rating,Skills_rating,overall_rating,username,user_image,time,message;

    public Vendor_rating(String id, String booking_id, String professionalism_rating, String behaviour_rating, String skills_rating, String overall_rating, String username, String user_image, String message, String time) {
        this.id = id;
        this.booking_id = booking_id;
        this.Professionalism_rating = professionalism_rating;
        this.Behaviour_rating = behaviour_rating;
        this.Skills_rating = skills_rating;
        this.overall_rating = overall_rating;
        this.username = username;
        this.user_image = user_image;
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getProfessionalism_rating() {
        return Professionalism_rating;
    }

    public void setProfessionalism_rating(String professionalism_rating) {
        Professionalism_rating = professionalism_rating;
    }

    public String getBehaviour_rating() {
        return Behaviour_rating;
    }

    public void setBehaviour_rating(String behaviour_rating) {
        Behaviour_rating = behaviour_rating;
    }

    public String getSkills_rating() {
        return Skills_rating;
    }

    public void setSkills_rating(String skills_rating) {
        Skills_rating = skills_rating;
    }

    public String getOverall_rating() {
        return overall_rating;
    }

    public void setOverall_rating(String overall_rating) {
        this.overall_rating = overall_rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
