package com.satrangolimitless.model;

public class Services_Popularactivity_Model {
    public String id;
    public String name;
    public String icon;
    public String bgcolor;
    public String status;
    public String cat_name;



    public Services_Popularactivity_Model(String id, String name, String icon, String bgcolor, String cat_name) {

        this.id=id;
        this.name=name;
        this.icon=icon;
        this.bgcolor=bgcolor;
        this.cat_name=cat_name;





    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
}
