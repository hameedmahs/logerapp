package com.example.loger;

import java.util.Date;

public class Fees {
    private Date date;
    private String remitted;
    private String pending;
    private int priority;

    public Fees(){

    }

    public Fees(Date date, String remitted, String pending, int priority){
        this.date=date;
        this.remitted=remitted;
        this.pending=pending;
        this.priority=priority;

    }

    public Date getDate() {
        return date;
    }

    public String getRemitted() {

        return remitted;
    }

    public String getPending() {

        return pending;
    }

    public int getPriority() {
        return priority;
    }
}

