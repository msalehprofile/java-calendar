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
    private static boolean calendarStatus = true;

    public static boolean isCalendarStatus() {
        return calendarStatus;
    }

    public static String getMeetingLength() {
        return meetingLength;
    }

    static ArrayList<Meetings> personTwoMeetings = new ArrayList<>();

    public static ArrayList<Meetings> getPersonTwoMeetings() {
        return personTwoMeetings;
    }

    public static void firstPersonTimeBoundQuestion() {
        System.out.println("\nWhat time do you start and finish today?" + "\nStart time:");
    }

    public static void getStartAndFinish() {
        Scanner myStartTime = new Scanner(System.in);
        startTime = myStartTime.nextLine();
        System.out.println("\nFinish time:");
        Scanner myFinishTime = new Scanner(System.in);
        finishTime = myFinishTime.nextLine();
    }

    public static void meetingsQuestion(String name, ArrayList<Meetings> personOneMeetingTimes, ArrayList<Meetings> secondMeetingList) {
        boolean validAnswer= false;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("\n"+name + " do you have meetings today? (Y/N)");
            userInput = input.nextLine().toLowerCase();

            if(userInput.equals("y")) {
                validAnswer=true;
                do {
                    enteringMeetingTimes(personOneMeetingTimes);
                    anotherMeetingCheck();
                } while (Objects.equals(userInput, "y"));
            } else if (userInput.equals( "n")) {
                validAnswer=true;
                System.out.println("\nThank you for confirming you have no meetings.");
            } else {
                System.out.println("\nPlease chose a valid option.");
            }

        } while (!validAnswer);
        collegueMeetingsQuestion();
    }

    public static void enteringMeetingTimes(ArrayList<Meetings> personOneMeetingTimes){
        Scanner input = new Scanner(System.in);
        System.out.println("\nPlease enter the start and end times in 24 hour format 00:00:"+ "\nStart time:");
        startTime = input.nextLine();

        System.out.println("Finish time:");
        finishTime = input.nextLine();

        personOneMeetingTimes.add(new Meetings(startTime,finishTime));
    }

    public static void anotherMeetingCheck() {
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\nDo you have another meeting? (Y/N)");
            userInput = scanner.nextLine().toLowerCase();

            if(!userInput.equals("n") && !userInput.equals("y")) {
                System.out.println("\nPlease enter a valid option.");
            } else {
                validInput=true;
            }

        } while (!validInput);

    }

    public static void collegueMeetingsQuestion() {
        personTwoMeetings.add(new Meetings("10:30","11:30"));
        personTwoMeetings.add(new Meetings("14:00","15:00"));
    }

    public static void meetingDuration(String colleaguesName) {
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("\nHow long would you like the meeting with " + colleaguesName + " to be? (Please input in minutes, in half an hour chunks.)");
            meetingLength = scanner.nextLine();

            if (!meetingLength.matches("^\\d+$") || Integer.parseInt(meetingLength) % 30 != 0) {
                System.out.println("\nPlease enter a valid option.");
            } else {
                validInput=true;
            }

        } while(!validInput);
    }

    public static String getStartTime() {
        return startTime;
    }

    public static String getFinishTime() {
        return finishTime;
    }

    public static void checkAvailabilityBlocksAreLongEnough( ArrayList<String> mutualAvailableTimeBlocks, String meetingLength, ArrayList<Meetings> personOneMeetingTimes, String colleaguesName, String myName, ArrayList<Meetings> personTwoMeetingTimes) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> valueOfSetGroup = new ArrayList<>();
        boolean validInput = false;

        do {
            int numberSystem = 0;
            System.out.println("\nPlease select the time frame when you would like to book your meeting:");
            for(int i=0; i < mutualAvailableTimeBlocks.size(); i += 2) {
                int differenceBetweenMeetings = Integer.parseInt(mutualAvailableTimeBlocks.get(i+1)) - Integer.parseInt(mutualAvailableTimeBlocks.get(i)) +1;
                int intMeetingLength  = TimeConversion.convertMeetingDurationToNumber(meetingLength);
                if((i+2) == mutualAvailableTimeBlocks.size()) {
                    System.out.println(String.valueOf(numberSystem+=1) +".Start: "+ TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i))) + ", End: " + TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i +1))));
                    valueOfSetGroup.add(numberSystem);
                } else if( differenceBetweenMeetings == 0 && (differenceBetweenMeetings >= intMeetingLength)) {
                    System.out.println("difference: " +differenceBetweenMeetings);
                    System.out.println("meeting length: "+intMeetingLength);
                    System.out.println(String.valueOf(numberSystem+=1) +".Start: "+ TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i))) + ", End: " + TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i +1))+1));
                    valueOfSetGroup.add(numberSystem);
                } else if(differenceBetweenMeetings >= TimeConversion.convertMeetingDurationToNumber(meetingLength)) {
                    System.out.println(String.valueOf(numberSystem+=1) +".Start: "+ TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i))) + ", End: " + TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get(i +1))+1));
                    valueOfSetGroup.add(numberSystem);
                }
            }

            userInput = scanner.nextLine();

            if( !userInput.matches("[0-9]+") || !valueOfSetGroup.contains(Integer.parseInt(userInput))) {
                System.out.println("\nPlease enter a valid option.");
            } else {
                String chosenStartTime = TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get((Integer.parseInt(userInput) - 1) * 2)));
                String chosenEndTime = TimeConversion.convertNumberToTime(Integer.parseInt(mutualAvailableTimeBlocks.get((Integer.parseInt(userInput) - 1) * 2)) + TimeConversion.convertMeetingDurationToNumber(meetingLength));
                System.out.println("\nThank you " + myName + ", you have successfully added a meeting to yours and " + colleaguesName + " calendar for the below times:" +
                        "\nStart Time: " + chosenStartTime +
                        "\nEnd Time: " + chosenEndTime);

                personOneMeetingTimes.add(new Meetings(chosenStartTime, chosenEndTime));
                personTwoMeetingTimes.add(new Meetings(chosenStartTime, chosenEndTime));
                validInput = true;
            }
        } while(!validInput);
        valueOfSetGroup.clear();
    }

    public static void userChoice(ArrayList<Meetings> personOneMeetingTimes) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        do {
            System.out.println("\nWould you like to book another meeting?" +
                    "\n1. Yes" +
                    "\n2. No");

            userInput = scanner.nextLine();

            if(!Objects.equals(userInput, "1") && !Objects.equals(userInput, "2")) {
                System.out.println("\nPlease enter a valid option.");
            } else if (userInput.equals("2")) {
                validInput=true;
                viewingOptions(personOneMeetingTimes);
            } else {
                validInput=true;
            }
        } while(!validInput);
    }

    public static void viewingOptions(ArrayList<Meetings> personOneMeetingTimes) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        do {
            System.out.println("\nWhat would you like to do next?" +
                    "\n1. View your scheduled Meetings." +
                    "\n2. Leave calendar.");

            userInput = scanner.nextLine();

            if(!Objects.equals(userInput, "1") && !Objects.equals(userInput, "2")) {
                System.out.println("\nPlease enter a valid option.");
            } else if (userInput.equals("1")) {
                validInput=true;
                System.out.println("\nYour scheduled meetings are as followed:");
                for (int i=0;i < personOneMeetingTimes.size(); i++) {
                    System.out.println((i+1) +". "+ personOneMeetingTimes.get(i));
                }
                userChoice(personOneMeetingTimes);
            } else if (userInput.equals("2")) {
                validInput=true;
                System.out.println("\nThank you for using the calendar, see you again soon!");
                calendarStatus=false;
            }
        } while(!validInput);
    }
}
