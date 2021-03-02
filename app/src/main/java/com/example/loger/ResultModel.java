package com.example.loger;

public class ResultModel {
    private String sub;
    private int mark;
    private String status;


    private ResultModel(){


    }
    public ResultModel( String sub, int mark,String status) {
        this.sub = sub;
        this.mark = mark;
        this.status=status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
