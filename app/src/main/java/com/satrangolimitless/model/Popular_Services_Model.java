package com.satrangolimitless.model;

public class Popular_Services_Model {
    public String id;
    public String name;
    public String icon;
    public String bgcolor;
    public String status;
    public Popular_Services_Model(String id, String name, String icon, String bgcolor, String status) {

        this.id=id;
        this.name=name;
        this.icon=icon;
        this.bgcolor=bgcolor;
        this.status=status;




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
}
