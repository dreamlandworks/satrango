package com.satrangolimitless.model;

public class Categories_Popularactivity_Model {

    public String id;
    public String cat_id;
    public String name;
    public String status;
    public String icon;
    public String created_date;
    public String cat_name;
    public Categories_Popularactivity_Model(String id, String name, String icon) {

        this.id=id;

        this.name=name;
        this.status=status;
        this.icon=icon;




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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }



}
