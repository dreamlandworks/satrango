package com.satrangolimitless.Addmore_Installments_Bidamount;

import java.io.Serializable;

public class Addmoreamount implements Serializable {

    public String ItemPrice;

    public Addmoreamount() {

    }


    public Addmoreamount(String itemPrice) {
        ItemPrice = itemPrice;

    }



    public int getItemPrice() {
        return Integer.parseInt(ItemPrice);
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

}
