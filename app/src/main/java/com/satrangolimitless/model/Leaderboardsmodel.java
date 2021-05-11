package com.satrangolimitless.model;

public class Leaderboardsmodel {

    String  id,fname,image,distance,rank;

    public Leaderboardsmodel(String id, String fname, String image, String distance, String rank) {
        this.id = id;
        this.fname = fname;
        this.image = image;
        this.distance = distance;
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
