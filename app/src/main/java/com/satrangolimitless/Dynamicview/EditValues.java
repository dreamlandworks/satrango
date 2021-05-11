package com.satrangolimitless.Dynamicview;



public class EditValues {
    public String FromTime;
    public String ToTime;
    public String Day;
    public String Choosedays;



    public EditValues(String itemName, String itemPrice, String day, String choosedays) {
        FromTime = itemName;
        ToTime = itemPrice;
        Day = day;
        Choosedays = choosedays;
    }

    public EditValues() {

    }


    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }


    public String getChoosedays() {
        return Choosedays;
    }

    public void setChoosedays(String chsd) {
        Choosedays = chsd;
    }


}
