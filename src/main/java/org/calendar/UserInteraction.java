package org.calendar;

import org.calendar.objects.Meetings;
import org.calendar.objects.TimeConversion;

import java.sql.SQLOutput;
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

    public static void meetingsQuestion(String name, ArrayList<Meetings> personOneMeetingTimes, ArrayList<Meetings> secondMeetingList) {
        System.out.println(name + " do you have meetings today? (Y/N)");
        Scanner input = new Scanner(System.in);
        userInput = input.nextLine().toLowerCase();
        if(userInput.equals("y")) {
            do {
                enteringMeetingTimes(personOneMeetingTimes);
                anotherMeetingCheck();

            } while (Objects.equals(userInput, "y"));
            collegueMeetingsQuestion();
        } else if (userInput.equals( "n")) {
            System.out.println("Thank you for confirming you have no meetings.");
            collegueMeetingsQuestion();
        } else {
            System.out.println("Please chose a valid option.");
            meetingsQuestion(name, personOneMeetingTimes, secondMeetingList);
        }
    }

    public static void enteringMeetingTimes(ArrayList<Meetings> personOneMeetingTimes){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the start and end times:"+ "\nStart time:");
        startTime = input.nextLine();

        System.out.println("Finish time:");
        finishTime = input.nextLine();

        personOneMeetingTimes.add(new Meetings(startTime,finishTime));
//        meetingList.add(new Meetings(startTime,finishTime));

    }

    public static void anotherMeetingCheck() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you have another meeting? (Y/N)");
        userInput = scanner.nextLine().toLowerCase();
    }

    public static void collegueMeetingsQuestion() {
//////        personTwoMeetings.add(new Meetings("09:00","10:30"));
        personTwoMeetings.add(new Meetings("10:30","11:30"));
        personTwoMeetings.add(new Meetings("14:00","15:00"));
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

    public static void checkAvailabilityBlocksAreLongEnough( ArrayList<String> mutualAvailableTimeBlocks, String meetingLength, ArrayList<Meetings> personOneMeetingTimes, String colleaguesName, String myName, ArrayList<Meetings> personTwoMeetingTimes) {
        Scanner scanner = new Scanner(System.in);
        int numberSystem = 0;
        ArrayList<Integer> valueOfSetGroup = new ArrayList<>();
        System.out.println("\nPlease select the time frame when you would like to book your meeting:");
        for(int i=0; i < mutualAvailableTimeBlocks.size(); i += 2) {
            if((i+2) == mutualAvailableTimeBlocks.size()) {
                System.out.println(String.valueOf(numberSystem+=1) +".Start: "+ TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i))) + ", End: " + TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i +1))));
                valueOfSetGroup.add(numberSystem);
            } else if((Integer.parseInt(mutualAvailableTimeBlocks.get(i+1)) - Integer.parseInt(mutualAvailableTimeBlocks.get(i))  == 0)) {
                System.out.println(String.valueOf(numberSystem+=1) +".Start: "+ TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i))) + ", End: " + TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i +1))+1));
                valueOfSetGroup.add(numberSystem);
            } else if(Integer.parseInt(mutualAvailableTimeBlocks.get(i+1)) - Integer.parseInt(mutualAvailableTimeBlocks.get(i)) >= TimeConversion.convertMeetingDurationToNumber(meetingLength)) {
                System.out.println(String.valueOf(numberSystem+=1) +".Start: "+ TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i))) + ", End: " + TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i +1))+1));
                valueOfSetGroup.add(numberSystem);
            }
        }
        userInput = scanner.nextLine();

        if( !userInput.matches("[0-9]+")) {
            System.out.println("Please enter a valid option.");
            checkAvailabilityBlocksAreLongEnough(mutualAvailableTimeBlocks, meetingLength, personOneMeetingTimes, colleaguesName, myName, personTwoMeetingTimes);
        }

        if(!valueOfSetGroup.contains(Integer.parseInt(userInput)) && userInput.matches("[0-9]+")) {
            System.out.println("Please enter a valid option.");
            checkAvailabilityBlocksAreLongEnough(mutualAvailableTimeBlocks, meetingLength, personOneMeetingTimes, colleaguesName, myName, personTwoMeetingTimes);
        }
        String chosenStartTime = TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get((Integer.parseInt(userInput)-1)*2)));
        String chosenEndTime = TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get((Integer.parseInt(userInput)-1)*2))+TimeConversion.convertMeetingDurationToNumber(meetingLength));
        System.out.println("Thank you "+myName+", you have successfully added a meeting to yours and "+ colleaguesName +" calendar for the below times:" +
                "\nStart Time: "+ chosenStartTime +
                "\nEnd Time: "+chosenEndTime);

        personOneMeetingTimes.add(new Meetings(chosenStartTime,chosenEndTime));
        personTwoMeetingTimes.add(new Meetings(chosenStartTime,chosenEndTime));
        valueOfSetGroup.clear();
    }

    public static void userChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWould you like to book another meeting?" +
                "\n1. Yes" +
                "\n2. No");

        userInput = scanner.nextLine();

        if(!Objects.equals(userInput, "1") && !Objects.equals(userInput, "2")) {
            System.out.println("Please enter a valid option.");
            userChoice();
        } else if (userInput.equals("2")) {
            System.out.println("What would you like to do next?");
        }
    }
}
