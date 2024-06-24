package org.calendar.objects;

import java.util.ArrayList;

public class Calendar {
    private ArrayList<Meetings> meetings;

    public void setMeetings(ArrayList<Meetings> meetings) {
        this.meetings = meetings;
    }

    public void getMeetings() {
        System.out.println(meetings);
    }



}
