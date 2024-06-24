package org.calendar;
import org.calendar.objects.Meetings;

import java.util.ArrayList;
import java.util.Scanner;


public class MeetingPlanner {
    private static String myName;
    private static String colleaguesName = "Gary";
    static ArrayList<Meetings> personOneMeetingTimes = new ArrayList<>();
    static ArrayList<String> personOneTimeBounds = new ArrayList<>();
    static ArrayList<Meetings> personTwoMeetingTimes = new ArrayList<>();
    static String[] personTwoTimeBounds = {"9:00", "17:30"};
    private static String meetingLength;


    public static void inputTimeBounds() {
        System.out.println("What is your name?");
        Scanner scanner = new Scanner(System.in);
        myName = scanner.nextLine();
        UserInteraction.firstPersonTimeBoundQuestion();
        UserInteraction.getStartAndFinish();
        personOneTimeBounds.add(UserInteraction.getStartTime());
        personOneTimeBounds.add(UserInteraction.getFinishTime());

        getMeetingTimes();
    }

    public static void getMeetingTimes() {
        UserInteraction.meetingsQuestion(myName, personOneMeetingTimes, personTwoMeetingTimes);
        personTwoMeetingTimes = UserInteraction.getPersonTwoMeetings();

        personOneMeetingTimes.sort((a, b) -> {
            return Integer.parseInt(a.getStartTime().substring(0, 1)) * 60 + Integer.parseInt(a.getStartTime().substring(3, 4)) - Integer.parseInt(b.getStartTime().substring(0, 1)) * 60 + Integer.parseInt(b.getStartTime().substring(3, 4));
        });

        personTwoMeetingTimes.sort((a, b) -> {
            return Integer.parseInt(a.getStartTime().substring(0, 1)) * 60 + Integer.parseInt(a.getStartTime().substring(3, 4)) - Integer.parseInt(b.getStartTime().substring(0, 1)) * 60 + Integer.parseInt(b.getStartTime().substring(3, 4));
        });

        System.out.println(personOneMeetingTimes);
        System.out.println(personTwoMeetingTimes);

        UserInteraction.meetingDuration(colleaguesName);
        meetingLength = UserInteraction.getMeetingLength();
    }
}

