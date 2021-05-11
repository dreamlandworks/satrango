package com.satrangolimitless.Dynamicview;

public class Days_Model {

String id,size,size_name;


public Days_Model(String id, String size, String size_name)
{
    this.id=id;
    this.size=size;
    this.size_name=size_name;
}

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
