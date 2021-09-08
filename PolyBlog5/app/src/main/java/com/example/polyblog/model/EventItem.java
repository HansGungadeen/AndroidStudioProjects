package com.example.polyblog.model;

public class EventItem {

    public String event_date,event_location,event_name;

    public EventItem() {
    }

    public EventItem(String event_date, String event_location, String event_name) {
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }
}
