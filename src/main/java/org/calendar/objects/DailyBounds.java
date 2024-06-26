package org.calendar.objects;

public class DailyBounds {
    private String[] dailyAvailability;

    public DailyBounds(String time, String working, String available) {
        this.dailyAvailability =  new String[]{time, working, available};;
    }

    public String getStart() {
        return dailyAvailability[0];
    }

    public void setworking(String working) {
        this.dailyAvailability[1] = working;
    }

    public String getworking() {
        return dailyAvailability[1];
    }

    public String getAvailable() {
        return dailyAvailability[2];
    }

    public void setAvailable(String available) {
        this.dailyAvailability[2] = available;
    }

    @Override
    public String toString() {
        return  "[" + dailyAvailability[0] + ", "+ dailyAvailability[1] + ", "+ dailyAvailability[2] + "]";
    }

}
