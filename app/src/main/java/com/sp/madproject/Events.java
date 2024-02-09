package com.sp.madproject;

public class Events {
    private String id = "";
    private String eventTitle = "";
    private String eventDesc = "";
    private String eventLocation = "";
    private String imageAsString = "";


    public String getId(){return id;}

    public void setId(String id) {this.id = id;}

    public String getTitle() {return eventTitle;}
    public void setTitle(String eventTitle) {this.eventTitle= eventTitle;}

    public String getDesc() {return eventDesc;}
    public void setDesc(String eventDesc) {this.eventDesc = eventDesc;}

    public String getLocation() {return eventLocation;}
    public void setLocation(String eventLocation) {this.eventLocation = eventLocation;}

    public String getImageAsString() {return imageAsString;}
    public void setImageAsString(String imageAsString) {this.imageAsString = imageAsString;}
}
