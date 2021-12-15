package com.example.joinup;

import java.util.ArrayList;

public class EventDetail {
    private String eventTitle;
    private String eventInfo;
    private ArrayList<String> attendees;
    private int numberOfStudents;
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;
    private String coordinatorEmail;

    public EventDetail() {
        eventTitle = "N/A";
        eventInfo = "N/A";
        numberOfStudents = 0;
        attendees = new ArrayList<>();
        month = -1;
        day = -1;
        year = -1;
        hour = -1;
        minute = -1;
        coordinatorEmail = "";
    }

    public EventDetail(String eventTitle, String eventInfo) {
        this.eventTitle = eventTitle;
        this.eventInfo = eventInfo;
        numberOfStudents = 0;
        attendees = new ArrayList<>();
        month = -1;
        day = -1;
        year = -1;
        hour = -1;
        minute = -1;
        coordinatorEmail = "";
    }

    public EventDetail(String eventTitle, String eventInfo, int numberOfStudents) {
        this.eventTitle = eventTitle;
        this.eventInfo = eventInfo;
        this.numberOfStudents = numberOfStudents;
        attendees = new ArrayList<>();
        month = -1;
        day = -1;
        year = -1;
        hour = -1;
        minute = -1;
        coordinatorEmail = "";
    }


    public void setTimeDay(int month, int day, int year, int hour, int minute) {
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.year = year;
        this.minute = minute;
        coordinatorEmail = "";
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

    public boolean addAttendee(String email) {

        if (attendees.contains(email)) {
            return false;
        }
        else {
            attendees.add(email);
            numberOfStudents++;
            return true;
        }
    }

    public boolean removeAttendee(String email) {

        if (attendees.contains(email)) {
            numberOfStudents--;
            attendees.remove(email);
            return true;
        }
        return false;
    }

    public ArrayList<String> getAttendees() {
        return attendees;
    }

    public String readableDateTime() {
        if (month == -1) {
            return "No date/time entered";
        }
        return month +  "/" + day + "/" + year + " at " + hour + ":" + minute;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getKey() {
        return coordinatorEmail + "$" + eventTitle;
    }

    public void setKey(String key) {
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

    public void setAttendees(ArrayList<String> attendees) {
        this.attendees = attendees;
    }

    public void setCompressedAttendees(String attendees) {

        ArrayList<String> lst = new ArrayList<>();
        for (String s : attendees.split("#")) {
            lst.add(s);
        }
        this.attendees = lst;
    }

    public String getCompressedAttendees() {
        String str = "";
        for (String s : attendees) {
            str += "#" + s;
        }
        return str;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getCoordinatorEmail() {
        return coordinatorEmail;
    }

    public void setCoordinatorEmail(String coordinatorEmail) {
        this.coordinatorEmail = coordinatorEmail;
    }

    @Override
    public String toString() {
        return "Title: " + eventTitle + "\n" + "Event Information: " + eventInfo;
    }
}
