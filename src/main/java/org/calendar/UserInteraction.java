package org.calendar;

import org.calendar.objects.Meetings;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UserInteraction {
    private static String startTime;
    private static String finishTime;
    private static String userInput;
    private static String meetingLength;

    public static String getMeetingLength() {
        return meetingLength;
    }

    static ArrayList<Meetings> personTwoMeetings = new ArrayList<>();

    public static ArrayList<Meetings> getPersonTwoMeetings() {
        return personTwoMeetings;
    }

    public static void firstPersonTimeBoundQuestion() {
        System.out.println("What time do you start and finish today?" + "\nStart time:");
    }

    public static void getStartAndFinish() {
        Scanner myStartTime = new Scanner(System.in);
        startTime = myStartTime.nextLine();
        System.out.println("Finish time:");
        Scanner myFinishTime = new Scanner(System.in);
        finishTime = myFinishTime.nextLine();
    }

    public static void meetingsQuestion(String name, ArrayList<Meetings> meetingList, ArrayList<Meetings> secondMeetingList) {
        System.out.println(name + " do you have meetings today? (Y/N)");
        Scanner input = new Scanner(System.in);
        userInput = input.nextLine().toLowerCase();
        if(userInput.equals("y")) {
            do {
                enteringMeetingTimes(meetingList);
                anotherMeetingCheck();

            } while (Objects.equals(userInput, "y"));
            collegueMeetingsQuestion();
        } else if (userInput.equals( "n")) {
            System.out.println("Thank you for confirming you have no meetings.");
            collegueMeetingsQuestion();
        } else {
            System.out.println("Please chose a valid option.");
            meetingsQuestion(name, meetingList, secondMeetingList);
        }
    }

    public static void enteringMeetingTimes(ArrayList<Meetings> meetingList){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the start and end times:"+ "\nStart time:");
        startTime = input.nextLine();

        System.out.println("Finish time:");
        finishTime = input.nextLine();

        meetingList.add(new Meetings(startTime,finishTime));

    }

    public static void anotherMeetingCheck() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you have another meeting? (Y/N)");
        userInput = scanner.nextLine().toLowerCase();
    }

    public static void collegueMeetingsQuestion() {
        personTwoMeetings.add(new Meetings("09:00","10:30"));
        personTwoMeetings.add(new Meetings("10:40","11:30"));
        personTwoMeetings.add(new Meetings("14:00","15:30"));
    }

    public static void meetingDuration(String colleaguesName) {
        System.out.println("How long would you like the meeting with " + colleaguesName + " to be? (Please input in minutes");
        Scanner scanner = new Scanner(System.in);
        meetingLength = scanner.nextLine();
    }

    public static String getStartTime() {
        return startTime;
    }

    public static String getFinishTime() {
        return finishTime;
    }

}
