package org.calendar;
import org.calendar.objects.DailyBounds;
import org.calendar.objects.Meetings;
import org.calendar.objects.TimeConversion;

import java.util.*;
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
    static ArrayList<String> mutualAvailableTimes = new ArrayList<>();
    static ArrayList<String> mutualAvailableTimeBlocks = new ArrayList<>();

    public static void planMeetings() {
        inputTimeBounds();

        while(true) {
            getMeetingTimes();
            findAvailableMeetingTime();
            System.out.println("p1 meeting times: "+personOneMeetingTimes);
            UserInteraction.checkAvailabilityBlocksAreLongEnough(mutualAvailableTimeBlocks, meetingLength, personOneMeetingTimes, colleaguesName, myName, personTwoMeetingTimes);
            mutualAvailableTimeBlocks.clear();
            UserInteraction.userChoice();
        }
    }

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
                personOneAvailableTime.setWorking("Y");
            }
        }

        for (int i=0; i < personTwoAvailableTimes.size(); i++) {
            if (Integer.parseInt(String.valueOf(personTwoAvailableTimes.get(i).getStart())) >= TimeConversion.convertToNumber(personTwoTimeBounds.get(0)) && Integer.parseInt(String.valueOf(personOneAvailableTimes.get(i).getStart())) <= TimeConversion.convertToNumber(personOneTimeBounds.get(1))) {
                personTwoAvailableTimes.get(i).setWorking("Y");
            }
        }
        UserInteraction.meetingsQuestion(myName, personOneMeetingTimes, personTwoMeetingTimes);
    }



    public static void getMeetingTimes() {
        personOneMeetingTimesConvertedToNumbers.clear();
        personTwoMeetingTimesConvertedToNumbers.clear();

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
            String finishingNumber =String.valueOf(TimeConversion.convertToNumber(personTwoMeetingTime.getEndTime())-1);
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

        System.out.println("meeting times ACTUAL: " +personTwoMeetingTimes);
        System.out.println("meeting times: " + personOneMeetingTimesConvertedToNumbers);
        System.out.println("meeting times: " + personTwoMeetingTimesConvertedToNumbers);

    }

    public static void findAvailableMeetingTime() {
        mutualAvailableTimeBlocks.clear();
        mutualAvailableTimes.clear();

        for (int i=0; i < personOneAvailableTimes.size(); i++) {

            if(Objects.equals(personOneAvailableTimes.get(i).getAvailable(), "Y") && Objects.equals(personOneAvailableTimes.get(i).getWorking(), "Y") && Objects.equals(personTwoAvailableTimes.get(i).getAvailable(), "Y") && Objects.equals(personTwoAvailableTimes.get(i).getWorking(), "Y")) {
                mutualAvailableTimes.add(personOneAvailableTimes.get(i).getStart());
            }
        }

        System.out.println("avail ties: "+mutualAvailableTimes);

        for (int i=0; i < mutualAvailableTimes.size(); i++) {
            if(i==0 && personOneMeetingTimes.isEmpty() && (Integer.parseInt(mutualAvailableTimes.get(i))+1 != Integer.parseInt(mutualAvailableTimes.get(i+1)))) {
                mutualAvailableTimeBlocks.add(mutualAvailableTimes.get(i));
                mutualAvailableTimeBlocks.add(String.valueOf(Integer.parseInt(mutualAvailableTimes.get(i))));

            } else if(i==0 && (!personOneMeetingTimes.isEmpty() ) && (Integer.parseInt(mutualAvailableTimes.get(i))+1 != Integer.parseInt(mutualAvailableTimes.get(i+1)))) {
                mutualAvailableTimeBlocks.add(String.valueOf(Integer.parseInt(mutualAvailableTimes.get(i))));
                mutualAvailableTimeBlocks.add(String.valueOf(Integer.parseInt(mutualAvailableTimes.get(i))));
                System.out.println("hetting here");
            } else if (i==0 && personOneMeetingTimes.isEmpty() ) {
                mutualAvailableTimeBlocks.add(String.valueOf(Integer.parseInt(mutualAvailableTimes.get(i))));
                System.out.println("is empty (should be true): "+personOneMeetingTimes.isEmpty());
            }  else if (i == 0 && !personOneMeetingTimes.isEmpty()) {
                mutualAvailableTimeBlocks.add(String.valueOf(Integer.parseInt(mutualAvailableTimes.get(i))));
                System.out.println("is empty(should be false): "+personOneMeetingTimes.isEmpty());
            }  else if(i == mutualAvailableTimes.size()-1) {
                mutualAvailableTimeBlocks.add(mutualAvailableTimes.get(i));
            } else if (Integer.parseInt(mutualAvailableTimes.get(i))-1 != Integer.parseInt(mutualAvailableTimes.get(i-1))) {
                mutualAvailableTimeBlocks.add(String.valueOf(Integer.parseInt(mutualAvailableTimes.get(i))));
            } else if (Integer.parseInt(mutualAvailableTimes.get(i))+1 != Integer.parseInt(mutualAvailableTimes.get(i+1))) {
                mutualAvailableTimeBlocks.add(String.valueOf(Integer.parseInt(mutualAvailableTimes.get(i))));
            } ;
        }
        System.out.println("new: "+mutualAvailableTimeBlocks);
    }




}

