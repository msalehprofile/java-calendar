package org.calendar.objects;

import java.sql.Time;
import java.util.Arrays;

public class Meetings {
    private String[] meetingTimes;

    public Meetings(String startTime, String endTime) {
        this.meetingTimes = new String[]{startTime, endTime};
    }

    public String getStartTime() {
        return meetingTimes[0];
    }

    public String getEndTime() {
        return meetingTimes[1];
    }

    public String[] getTimes() {
        return meetingTimes;
    }

    public void setTimes(String startTime, String endTime) {
        this.meetingTimes = new String[]{startTime, endTime};
    }

    @Override
    public String toString() {
        return  "[" + meetingTimes[0] + ", "+ meetingTimes[1] + "]";
    }
}
