package com.satrangolimitless.model;

public class Transactionhistrymodel {

    String id,transfer_type,money,created_date;

    public Transactionhistrymodel(String id, String transfer_type, String money, String created_date) {
            this.id = id;
            this.transfer_type = transfer_type;
            this.money = money;
            this.created_date = created_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }


}
