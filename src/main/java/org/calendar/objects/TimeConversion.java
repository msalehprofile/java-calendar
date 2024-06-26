package org.calendar.objects;

public class TimeConversion {

    public static int convertTimeToMinutes(String time) {

        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return hours * 60 + minutes;
    }

    public static void main(String[] args) {
        convertMinutesToTime(380);
    }

    public static String convertMinutesToTime(long minutes) {
        long totalHours = minutes/60;
        long totalMinutes = minutes - totalHours * 60;

        if (totalHours < 10) {
            return "0" +totalHours + ":" + totalMinutes;
        }
            return totalHours + ":" + totalMinutes;
    }

    public static int convertToNumber(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return (hours * 60 + minutes)/30;
    }
}
