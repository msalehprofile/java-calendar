package org.calendar;
import org.calendar.objects.Meetings;

import java.util.ArrayList;
import java.util.Scanner;


public class MeetingPlanner {
    private static String myName;
    private static String colleaguesName;
    static ArrayList<Meetings> personOneMeetingTimes = new ArrayList<>();
    static ArrayList<String> personOneTimeBounds = new ArrayList<>();
    static ArrayList<Meetings> personTwoMeetingTimes = new ArrayList<>();
    static ArrayList<String> personTwoTimeBounds = new ArrayList<>();

    public static void inputTimeBounds() {
        System.out.println("What is your name?");
        Scanner myNameinput = new Scanner(System.in);
        myName = myNameinput.nextLine();
        UserInteraction.firstPersonTimeBoundQuestion();
        UserInteraction.getStartAndFinish();
        personOneTimeBounds.add(UserInteraction.getStartTime());
        personOneTimeBounds.add(UserInteraction.getFinishTime());

        System.out.println(myName+ " what is your colleagues name?");
        Scanner colleaguesinput = new Scanner(System.in);
        colleaguesName = colleaguesinput.nextLine();
        UserInteraction.secondPersonTimeBoundQuestion();
        UserInteraction.getStartAndFinish();
        personTwoTimeBounds.add(UserInteraction.getStartTime());
        personTwoTimeBounds.add(UserInteraction.getFinishTime());

        UserInteraction.meetingsQuestion(myName,colleaguesName, personOneMeetingTimes, personTwoMeetingTimes);

        personOneMeetingTimes.sort((a, b) -> {
            return Integer.parseInt(a.getStartTime().substring(0, 1)) * 60 + Integer.parseInt(a.getStartTime().substring(3, 4)) - Integer.parseInt(b.getStartTime().substring(0, 1)) * 60 + Integer.parseInt(b.getStartTime().substring(3, 4));
        });

        personTwoMeetingTimes.sort((a, b) -> {
            return Integer.parseInt(a.getStartTime().substring(0, 1)) * 60 + Integer.parseInt(a.getStartTime().substring(3, 4)) - Integer.parseInt(b.getStartTime().substring(0, 1)) * 60 + Integer.parseInt(b.getStartTime().substring(3, 4));
        });

        System.out.println(personOneMeetingTimes);
        System.out.println(personTwoMeetingTimes);

    }

    public static void getMeetingTimes() {

    }
}

