package com.example.loger;

public class Note {
    private String title;
    private String finished;
    private String pending;
    private int priority;

    public Note(){

    }

    public  Note(String title,String finished,String pending,int priority){
        this.title=title;
        this.finished=finished;
        this.pending=pending;
        this.priority=priority;

    }

    public String getTitle() {
        return title;
    }

    public String getFinished() {

        return finished;
    }

    public String getPending() {

        return pending;
    }

    public int getPriority() {
        return priority;
    }
}
