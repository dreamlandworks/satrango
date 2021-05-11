package com.satrangolimitless.model;

public class UserAccountsmodel {
  String id,user_id,holder_name,account_number,ifsc_code,created_date ;

    public UserAccountsmodel(String id, String user_id, String holder_name, String account_number, String ifsc_code, String created_date) {
        this.id = id;
        this.user_id = user_id;
        this.holder_name = holder_name;
        this.account_number = account_number;
        this.ifsc_code = ifsc_code;
        this.created_date = created_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }


}
