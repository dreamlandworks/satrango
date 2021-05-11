package com.satrangolimitless.model;

public class Search_Data_Model {
    String id, name,language,min_amount,cat_id,
            service_name,distance,
            subservice,jobs,off,rating,max_amount
            ;




    public Search_Data_Model(String id, String name, String language, String min_amount, String cat_id , String service_name, String distance, String jobs, String off, String max_amount, String rating) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.min_amount = min_amount;
        this.cat_id = cat_id;
        this.service_name = service_name;
        this.distance = distance;
        this.subservice = subservice;
        this.jobs = jobs;
        this.off = off;
        this.max_amount = max_amount;
        this.rating = rating;

    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(String min_amount) {
        this.min_amount = min_amount;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getSubservice() {
        return subservice;
    }

    public void setSubservice(String subservice) {
        this.subservice = subservice;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMax_amount() {
        return max_amount;
    }

    public void setMax_amount(String max_amount) {
        this.max_amount = max_amount;
    }
}