package com.example.loger;

public class reult_First {
    private String sem;

    private boolean expanded;

    private reult_First(){

    }

    reult_First(String sem){

        this.sem = sem;
        this.expanded=false;
    }


    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
