package presenters;

import useCase.eventManager.ScheduleGenerator;

import java.util.ArrayList;

/**
 * Presenter for Event System.
 */
public class EventSystemPresenter {

    /**
     * Display the information of an event.
     *
     * @param eventInfo an ArrayList of String containing the information of the event, in format of
     *                  [id, title, speakers, startTime, duration, capacity, current occupations, speakerName]
     */
    public void presentEvent(ArrayList<String> eventInfo) {
        if (eventInfo == null) {
            System.out.println("Your designated event does not exist.");
        } else {
            String result = "";
            String id = eventInfo.get(0);
            String title = eventInfo.get(1);
            String speakers = eventInfo.get(2);
            String startTime = eventInfo.get(3);
            String duration = eventInfo.get(4);
            String capacity = eventInfo.get(5);
            String occupation = eventInfo.get(6);
            String room = eventInfo.get(7);
            String speakerName = eventInfo.get(8);
            result += "[" + id + "] " + title + " by " + speakers + " " + speakerName + "\n";
            result += "       TIME: " + startTime + ":00, DUR:" + duration + "hr. in R" + room +
                    " (" + occupation + "/" + capacity + ")";
            System.out.println(result);
        }
    }


    /**
     * Display the information of a list of events.
     *
     * @param eventList an ArrayList<ArrayList<String>> of a list of events, each element of the arraylist is an
     *                  arraylist of event information in format of [id, title, speakers, startTime, duration,
     *                  capacity, current occupations, speakerName]
     */
    public void presentEvents(ArrayList<ArrayList<String>> eventList) {
        for (ArrayList<String> eventInfo : eventList) {
            presentEvent(eventInfo);
        }
    }

    /**
     * Display a schedule.
     *
     * @param schedule a schedule generated by ScheduleGenerator.
     * @see ScheduleGenerator
     */
    public void presentSchedule(ArrayList<ArrayList<String>> schedule) {
        int[] maxLengths = new int[schedule.get(0).size()];
        StringBuilder formatBuilder = new StringBuilder();
        StringBuilder result = new StringBuilder();
        int num = 0;
        ArrayList<Integer> separator = new ArrayList<>();
        StringBuilder lineBuilder = new StringBuilder();
        for (ArrayList<String> row : schedule) {
            for (int i = 0; i < row.size(); i++) {
                maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
            }
        }
        for (int maxLength : maxLengths) {
            formatBuilder.append("%-").append(maxLength + 2).append("s");
            num += maxLength + 2;
            separator.add(num);
        }
        lineBuilder.append("+");
        for (int i = 1; i < num; i++) {
            if (separator.contains(i)) {
                lineBuilder.append("+");
            } else {
                lineBuilder.append("-");
            }
        }
        lineBuilder.append("+");
        result.append(lineBuilder).append("\n");
        String format = formatBuilder.toString();
        for (ArrayList<String> row : schedule) {
            for (int i = 0; i < row.size(); i++) {
                row.set(i, "|" + row.get(i));
            }
            result.append(String.format(format, row.toArray())).append("|").append("\n");
            result.append(lineBuilder).append("\n");
        }
        System.out.println(result.toString());
    }

    /**
     * Present a list a speaker ids.
     *
     * @param speakers ArrayList of Integer containing speaker ids.
     */
    public void presentSpeakers(ArrayList<Integer> speakers) {
        StringBuilder result = new StringBuilder();
        for (int id : speakers) {
            result.append("[").append(id).append("], ");
        }

        System.out.println(result);
    }
}