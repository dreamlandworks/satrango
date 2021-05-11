package com.satrangolimitless.model;

public class toppicksHome_Model {

    public String id;
    public String cat_id;
    public String name;

    public String icon;
    String total_job,
            skills;
    public toppicksHome_Model(String id, String name, String icon, String skills, String total_job) {

        this.id=id;
         this.name=name;
        this.icon=icon;
        this.skills=skills;
        this.total_job=total_job;



    }

    public String getTotal_job() {
        return total_job;
    }

    public void setTotal_job(String total_job) {
        this.total_job = total_job;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }







}
