package com.example.loger;

public class AttendModel {

    private long total;
    private long present;
    private  String title;

    private AttendModel(){

    }

    private AttendModel(long total, long present, String title){
        this.total=total;
        this.present=present;
        this.title = title;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPresent() {
        return present;
    }

    public void setPresent(long present) {
        this.present = present;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
