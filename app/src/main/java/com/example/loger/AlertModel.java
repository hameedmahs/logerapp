package com.example.loger;

public class AlertModel {

    private  String title_key;
    private String discription;
    private  boolean expandable;
    private  String notifier;
    private String category;





    private AlertModel(){

    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    AlertModel(String title_key, String discription, String notifier, String category, int priority) {
        this.title_key = title_key;
        this.discription = discription;
         this.notifier = notifier;
        this.category = category;

        this.expandable=false;

    }
    public String getNotifier() {
        return notifier;
    }

    public void setNotifier(String notifier) {
        this.notifier = notifier;
    }


    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }





    public String getTitle_key() {
        return title_key;
    }

    public void setTitle_key(String title_key) {
        this.title_key = title_key;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
