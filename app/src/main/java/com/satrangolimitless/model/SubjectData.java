package com.satrangolimitless.model;

public class SubjectData {
    String SubjectName;
    String Link;
    String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public SubjectData(String image) {

        this.Image = image;
    }

}
