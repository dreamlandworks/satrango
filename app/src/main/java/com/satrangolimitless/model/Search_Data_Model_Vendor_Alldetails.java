package com.satrangolimitless.model;

public class Search_Data_Model_Vendor_Alldetails {
    String id, name,language,about,min_amount,cat_id,
            service_name,distance,
            subservice,jobs,off,rating,max_amount,job_done,
    Skills_rating,
            Satisfaction_rating,
    Behaviour_rating,
            Professionalism_rating,
    pr_days,
            pr_hours
            ;



    public Search_Data_Model_Vendor_Alldetails(String id, String name, String language, String about, String min_amount, String max_amount , String pr_hours, String pr_days, String cat_id, String service_name, String rating, String distance, String Professionalism_rating, String Behaviour_rating, String Satisfaction_rating, String Skills_rating, String job_done) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.about = about;
        this.min_amount = min_amount;
        this.max_amount = max_amount;
        this.pr_hours = pr_hours;
        this.pr_days = pr_days;
        this.cat_id = cat_id;
        this.service_name = service_name;
        this.rating = rating;
        this.distance = distance;
        this.subservice = subservice;
        this.Professionalism_rating = Professionalism_rating;
        this.Behaviour_rating = Behaviour_rating;
        this.Satisfaction_rating = Satisfaction_rating;
        this.Skills_rating = Skills_rating;
        this.job_done = job_done;


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

    public String getJob_done() {
        return job_done;
    }

    public void setJob_done(String job_done) {
        this.job_done = job_done;
    }

    public String getSkills_rating() {
        return Skills_rating;
    }

    public void setSkills_rating(String skills_rating) {
        Skills_rating = skills_rating;
    }

    public String getSatisfaction_rating() {
        return Satisfaction_rating;
    }

    public void setSatisfaction_rating(String satisfaction_rating) {
        Satisfaction_rating = satisfaction_rating;
    }

    public String getBehaviour_rating() {
        return Behaviour_rating;
    }

    public void setBehaviour_rating(String behaviour_rating) {
        Behaviour_rating = behaviour_rating;
    }

    public String getProfessionalism_rating() {
        return Professionalism_rating;
    }

    public void setProfessionalism_rating(String professionalism_rating) {
        Professionalism_rating = professionalism_rating;
    }

    public String getPr_days() {
        return pr_days;
    }

    public void setPr_days(String pr_days) {
        this.pr_days = pr_days;
    }

    public String getPr_hours() {
        return pr_hours;
    }

    public void setPr_hours(String pr_hours) {
        this.pr_hours = pr_hours;
    }
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}