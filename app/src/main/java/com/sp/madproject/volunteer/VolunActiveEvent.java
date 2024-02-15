package com.sp.madproject.volunteer;

public class VolunActiveEvent {
    String event_desc, event_location, event_title;
    String event_img;

    public VolunActiveEvent(){

    }

    public VolunActiveEvent(String event_desc, String event_location, String event_title, String event_img) {
        this.event_desc = event_desc;
        this.event_location = event_location;
        this.event_title = event_title;
        this.event_img = event_img;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_img() {
        return event_img;
    }

    public void setEvent_img(String event_img) {
        this.event_img = event_img;
    }
}
