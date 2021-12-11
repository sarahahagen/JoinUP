package com.example.joinup;

public class EventDetail {
    private String eventTitle;
    private String eventInfo;
    private String imageDescription;
    private int numberOfStudents;

    public EventDetail() {
        eventTitle = "N/A";
        eventInfo = "N/A";
        imageDescription = "N/A";
        numberOfStudents = 0;
    }

    public EventDetail(String eventTitle, String eventInfo) {
        this.eventTitle = eventTitle;
        this.eventInfo = eventInfo;
        imageDescription = "N/A";
        numberOfStudents = 0;
    }

    public EventDetail(String eventTitle, String eventInfo, int numberOfStudents) {
        this.eventTitle = eventTitle;
        this.eventInfo = eventInfo;
        imageDescription = "N/A";
        this.numberOfStudents = numberOfStudents;
    }

    public EventDetail(String eventTitle, String imageDescription, String eventInfo, int numberOfStudents) {
        this.eventTitle = eventTitle;
        this.eventInfo = eventInfo;
        this.imageDescription = imageDescription;
        this.numberOfStudents = numberOfStudents;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
            this.numberOfStudents += numberOfStudents;
        if (this.numberOfStudents < 0) {
            this.numberOfStudents = 0;
        }
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
