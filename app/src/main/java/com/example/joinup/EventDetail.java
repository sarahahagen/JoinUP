package com.example.joinup;

public class EventDetail {
    private String eventTitle;
    private String eventInfo;
    private int numberOfStudents;
    private String imageURL;

    public EventDetail() {
        eventTitle = "N/A";
        eventInfo = "N/A";
        numberOfStudents = 0;
        imageURL = "N/A";
    }

    public EventDetail(String eventTitle, String eventInfo) {
        this.eventTitle = eventTitle;
        imageURL = "N/A";
        this.eventInfo = eventInfo;
        numberOfStudents = 0;
    }

    public EventDetail(String eventTitle, String eventInfo, int numberOfStudents) {
        this.eventTitle = eventTitle;
        imageURL = "N/A";
        this.eventInfo = eventInfo;
        this.numberOfStudents = numberOfStudents;
    }

    public EventDetail(String eventTitle, String imageURL, String eventInfo, int numberOfStudents) {
        this.eventTitle = eventTitle;
        this.imageURL = imageURL;
        this.eventInfo = eventInfo;
        this.numberOfStudents = numberOfStudents;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents += numberOfStudents;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    @Override
    public String toString() {
        return "Title: " + eventTitle + "\n" + "Event Information: " + eventInfo;
    }
}
