package org.calendar.objects;

public class TimeConversion {

    public static int convertTimeToMinutes(String time) {

        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return hours * 60 + minutes;
    }

    public static String convertNumberToTime(int number) {
        long totalMinutes = number* 30L;
        long totalHours = totalMinutes/60;
        long hourMinutes = totalMinutes - totalHours * 60;

        if (totalHours < 10 && hourMinutes == 0) {
            return "0" + totalHours + ":00";
        } else if(totalHours < 10 ) {
            return "0" +totalHours + ":" + hourMinutes;
        }

        if (hourMinutes == 0) {
            return totalHours + ":00";
        }
            return totalHours + ":" + hourMinutes;
    }

    public static int convertMeetingDurationToNumber(String meetingDuration) {

        return Integer.parseInt(meetingDuration)/30;
    }

    public static int convertToNumber(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return (hours * 60 + minutes)/30;
    }


}
