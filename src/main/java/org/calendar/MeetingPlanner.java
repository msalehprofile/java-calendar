package org.calendar;
import org.calendar.objects.DailyBounds;
import org.calendar.objects.Meetings;
import org.calendar.objects.TimeConversion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class MeetingPlanner {
    private static String myName;
    private static String colleaguesName = "Gary";
    static ArrayList<Meetings> personOneMeetingTimes = new ArrayList<>();
    static ArrayList<String> personOneTimeBounds = new ArrayList<>();
    static ArrayList<Meetings> personTwoMeetingTimes = new ArrayList<>();
    static List<String> personTwoTimeBounds = List.of(new String[]{"09:00", "17:30"});
    private static String meetingLength;
    static ArrayList<DailyBounds> personOneAvailableTimes = new ArrayList<>();
    static ArrayList<DailyBounds> personTwoAvailableTimes = new ArrayList<>();
    static ArrayList<String> personOneMeetingTimesConvertedToNumbers = new ArrayList<>();
    static ArrayList<String> personTwoMeetingTimesConvertedToNumbers = new ArrayList<>();

    public static void inputTimeBounds() {
        System.out.println("What is your name?");
        Scanner scanner = new Scanner(System.in);
        myName = scanner.nextLine();
        UserInteraction.firstPersonTimeBoundQuestion();
        UserInteraction.getStartAndFinish();
        personOneTimeBounds.add(UserInteraction.getStartTime());
        personOneTimeBounds.add(UserInteraction.getFinishTime());

        for (int i=0; i < 48; i++) {
            personOneAvailableTimes.add((new DailyBounds(String.valueOf(i),"N","Y")));
        }

        for (int i=0; i < 48; i++) {
            personTwoAvailableTimes.add((new DailyBounds(String.valueOf(i),"N","Y")));
        }

        for (DailyBounds personOneAvailableTime : personOneAvailableTimes) {
            if (Integer.parseInt(String.valueOf(personOneAvailableTime.getStart())) >= TimeConversion.convertToNumber(personOneTimeBounds.get(0)) && Integer.parseInt(String.valueOf(personOneAvailableTime.getStart())) <= TimeConversion.convertToNumber(personOneTimeBounds.get(1))) {
                personOneAvailableTime.setworking("Y");
            }
        }

        for (int i=0; i < personTwoAvailableTimes.size(); i++) {
            if (Integer.parseInt(String.valueOf(personTwoAvailableTimes.get(i).getStart())) >= TimeConversion.convertToNumber(personTwoTimeBounds.get(0)) && Integer.parseInt(String.valueOf(personOneAvailableTimes.get(i).getStart())) <= TimeConversion.convertToNumber(personOneTimeBounds.get(1))) {
                personTwoAvailableTimes.get(i).setworking("Y");
            }
        }
//        System.out.println("P1avail times: "+ personOneAvailableTimes);
//        System.out.println("P2avail times: "+ personTwoAvailableTimes);
        getMeetingTimes();
    }



    public static void getMeetingTimes() {
        UserInteraction.meetingsQuestion(myName, personOneMeetingTimes, personTwoMeetingTimes);
        personTwoMeetingTimes = UserInteraction.getPersonTwoMeetings();

        personOneMeetingTimes.sort((a, b) -> {
            return TimeConversion.convertTimeToMinutes(a.getStartTime()) - TimeConversion.convertTimeToMinutes(b.getStartTime());
        });

        personTwoMeetingTimes.sort((a, b) -> {
            return TimeConversion.convertTimeToMinutes(a.getStartTime()) - TimeConversion.convertTimeToMinutes(b.getStartTime());
        });

        for (Meetings personOneMeetingTime : personOneMeetingTimes) {
            String startingNumber = String.valueOf(TimeConversion.convertToNumber(personOneMeetingTime.getStartTime()));
            String finishingNumber = String.valueOf(TimeConversion.convertToNumber(personOneMeetingTime.getEndTime())-1);
            personOneMeetingTimesConvertedToNumbers.add(startingNumber);
            personOneMeetingTimesConvertedToNumbers.add(finishingNumber);
            int difference = Integer.parseInt(finishingNumber)- Integer.parseInt(startingNumber)-1;

            if(difference > 0) {
                for (int i = 1; i < difference +1; i++) {
                    personOneMeetingTimesConvertedToNumbers.add(String.valueOf((Integer.parseInt(startingNumber)+i)));
                }
            }
        }

        for (Meetings personTwoMeetingTime : personTwoMeetingTimes) {
            String startingNumber =String.valueOf(TimeConversion.convertToNumber(personTwoMeetingTime.getStartTime()));
            String finishingNumber =String.valueOf(TimeConversion.convertToNumber(personTwoMeetingTime.getEndTime()));
            personTwoMeetingTimesConvertedToNumbers.add(startingNumber);
            personTwoMeetingTimesConvertedToNumbers.add(finishingNumber);

            int difference = Integer.parseInt(finishingNumber)- Integer.parseInt(startingNumber)-1;
            if(difference > 0) {
                for (int i = 1; i < difference +1; i++) {
                    personTwoMeetingTimesConvertedToNumbers.add(String.valueOf((Integer.parseInt(startingNumber)+i)));
                }
            }
        }

        for (DailyBounds personOneAvailableTime : personOneAvailableTimes) {
            if (personOneMeetingTimesConvertedToNumbers.contains(personOneAvailableTime.getStart())) {
                personOneAvailableTime.setAvailable("N");
            };
        }

        for (DailyBounds personTwoAvailableTime : personTwoAvailableTimes) {
            if (personTwoMeetingTimesConvertedToNumbers.contains(personTwoAvailableTime.getStart())) {
                personTwoAvailableTime.setAvailable("N");
            };
        }

        System.out.println("P1avail times: "+ personOneAvailableTimes);
        System.out.println("P1avail times: "+ personTwoAvailableTimes);

        UserInteraction.meetingDuration(colleaguesName);
        meetingLength = UserInteraction.getMeetingLength();

        System.out.println("meeting times: " + personOneMeetingTimesConvertedToNumbers);
        System.out.println("meeting times: " + personTwoMeetingTimesConvertedToNumbers);


    }

//    public static void findAvailableMeetingTime() {
//        TimeConversion.convertTimeToMinutes(personOneTimeBounds.get(0));
//        long personOneAvailableTime = TimeConversion.convertTimeToMinutes(personOneMeetingTimes.get(0).getStartTime()) - TimeConversion.convertTimeToMinutes(personOneTimeBounds.get(0));
//        long personTwoAvailableTime = TimeConversion.convertTimeToMinutes(personTwoMeetingTimes.get(0).getStartTime()) - TimeConversion.convertTimeToMinutes(Arrays.stream(personTwoTimeBounds).distinct().collect(Collectors.toList()).get(0));
//
//        if (personOneAvailableTime >= Integer.parseInt(meetingLength) && personTwoAvailableTime>= Integer.parseInt(meetingLength)) {
//            availableTimes.add(new Meetings(personOneTimeBounds.get(0),TimeConversion.convertMinutesToTime(TimeConversion.convertTimeToMinutes(personOneTimeBounds.get(0)+meetingLength))));
//            System.out.println("Meeting starts at: "+ personOneTimeBounds.get(0) + " and finishes at: "+ TimeConversion.convertMinutesToTime(TimeConversion.convertTimeToMinutes(personOneTimeBounds.get(0)+meetingLength)));
//            System.out.println("avail times: " + availableTimes);
//        }
//
//        for (Meetings personOneMeetings: personOneMeetingTimes) {
//            TimeConversion.convertTimeToMinutes(String.valueOf(personOneMeetingTimes.get(0)));
//        }
//
//        System.out.println(TimeConversion.convertTimeToMinutes(personOneTimeBounds.get(0)));
//    }

}

