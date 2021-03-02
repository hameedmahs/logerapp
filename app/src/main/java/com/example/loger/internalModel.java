package com.example.loger;

public class internalModel {
    private String sub;
    private String internal;

    private  internalModel(){

    }

    public internalModel(String sub, String internal) {
        this.sub = sub;
        this.internal = internal;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getInternal() {
        return internal;
    }

    public void setInternal(String internal) {
        this.internal = internal;
    }
}
