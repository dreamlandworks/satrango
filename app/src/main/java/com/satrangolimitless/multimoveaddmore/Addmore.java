package com.satrangolimitless.multimoveaddmore;

import java.io.Serializable;

public class Addmore implements Serializable {
    public String ToLocation;
    public String ItemPrice;

    public Addmore() {

    }


    public Addmore(String toLocation) {
        ToLocation = toLocation;

    }

    public String getToLocation() {
        return ToLocation;
    }

    public void setToLocation(String toLocation) {
        ToLocation = toLocation;
    }

    public int getItemPrice() {
        return Integer.parseInt(ItemPrice);
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

}
