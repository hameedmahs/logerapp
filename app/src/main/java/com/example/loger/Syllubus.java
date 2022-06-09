package com.example.loger;

public class Syllubus {
    private String unit;
    private String syllabus;
    private String sub;
    private boolean expanded;


    private Syllubus() {

    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Syllubus(String unit, String syllabus, String sub) {
        this.unit = unit;
        this.syllabus = syllabus;
        this.sub = sub;
        this.expanded = false;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
