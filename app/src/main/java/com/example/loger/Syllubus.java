package com.example.loger;

public class Syllubus {
    private  String unit;
    private  String syllabus;
    private  String subject;
    private  boolean expanded;



    private Syllubus(){

    }

    public Syllubus(String unit, String syllabus, String subject) {
        this.unit = unit;
        this.syllabus = syllabus;
        this.subject = subject;
        this.expanded=false;
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
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
