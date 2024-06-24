package org.calendar;

import org.calendar.objects.Calendar;
import org.calendar.objects.Meetings;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UserInteraction {
    private static String startTime;
    private static String finishTime;
    private static String userInput;

    static ArrayList<Meetings> personOneMeetings = new ArrayList<>();
    static ArrayList<Meetings> personTwoMeetings = new ArrayList<>();

    public static void firstPersonTimeBoundQuestion() {
        System.out.println("What time do you start and finish today?" + "\nStart time:");
    }

    public static void secondPersonTimeBoundQuestion() {
        System.out.println("What time do they start and finish today?" + "\nStart time:");
    }

    public static void meetingsQuestion(String name, String collegueName,ArrayList<Meetings> meetingList, ArrayList<Meetings> secondMeetingList) {
        System.out.println(name + " do you have meetings today? (Y/N)");
        Scanner input = new Scanner(System.in);
        userInput = input.nextLine().toLowerCase();
        if(userInput.equals("y")) {
            do {
                enteringMeetingTimes(meetingList);
                anotherMeetingCheck();

            } while (Objects.equals(userInput, "y"));
            collegueMeetingsQuestion(name, collegueName, meetingList, secondMeetingList);
        } else if (userInput.equals( "n")) {
            collegueMeetingsQuestion(name, collegueName, meetingList, secondMeetingList);
        } else {
            System.out.println("Please chose a valid option.");
            meetingsQuestion(name, collegueName, meetingList, secondMeetingList);
        }
    }

    public static void collegueMeetingsQuestion(String name, String collegueName, ArrayList<Meetings> meetingList, ArrayList<Meetings> secondMeetingList) {
        System.out.println(name + " does "+ collegueName + " you have meetings today? (Y/N)");
        Scanner input = new Scanner(System.in);
        userInput = input.nextLine().toLowerCase();
        if(userInput.equals("y")) {
            do {
                enteringMeetingTimes(secondMeetingList);
                anotherMeetingCheck();

            } while (Objects.equals(userInput, "y"));
        } else if (!userInput.equals( "n")) {
            System.out.println("Please chose a valid option.");
            meetingsQuestion(name, collegueName, meetingList, secondMeetingList);
        }
    }

    public static void enteringMeetingTimes(ArrayList<Meetings> meetingList){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the start and end times:"+ "\nStart time:");
        startTime = input.nextLine();

        System.out.println("Finish time:");
        finishTime = input.nextLine();
        System.out.println("start: "+ startTime);
        System.out.println("finish: "+ finishTime);
        meetingList.add(new Meetings(startTime,finishTime));

    }

    public static void getStartAndFinish() {
        Scanner myStartTime = new Scanner(System.in);
        startTime = myStartTime.nextLine();
        System.out.println("Finish time:");
        Scanner myFinishTime = new Scanner(System.in);
        finishTime = myFinishTime.nextLine();
    }

    public static void anotherMeetingCheck() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you have another meeting? (Y/N)");
        userInput = scanner.nextLine().toLowerCase();
    }


    public static String getStartTime() {
        return startTime;
    }

    public static String getFinishTime() {
        return finishTime;
    }

}
