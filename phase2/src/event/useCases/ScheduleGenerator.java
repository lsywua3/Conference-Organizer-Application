package event.useCases;

import event.entities.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * ScheduleGenerator is a helper facade class that generate a 2D list to represent Schedule.
 */
public class ScheduleGenerator implements Observer {
    private final int day;
    List<Integer> rooms;
    private int startTime;
    private int endTime;

    /**
     * Initialize a ScheduleGenerator
     *
     * @param rooms     all room we have
     * @param startTime start time of the day
     * @param endTime   end time of teh day
     */
    public ScheduleGenerator(List<Integer> rooms, int startTime, int endTime, int day) {
        this.rooms = rooms;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    /**
     * add a new room
     *
     * @param room the room added
     */
    public void addRoom(int room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
        }
    }

    /**
     * change start time
     *
     * @param time new start time
     */
    public void setStartTime(int time) {
        if (time < startTime) {
            startTime = time;
        }
    }

    /**
     * change end time
     *
     * @param time new end time
     */
    public void setEndTime(int time) {
        if (time > endTime) {
            endTime = time;
        }
    }

    /**
     * helper method that generate an empty grid with title
     *
     * @return a empty schedule with title
     */
    private List<List<String>> generateEmpty() {
        List<List<String>> schedule = new ArrayList<>();
        schedule.add(new ArrayList<>());
        schedule.get(0).add("Day 1");
        for (int room : rooms) {
            schedule.get(0).add("R" + room);
        }
        for (int d = 0; d < day; d++) {
            for (int i = 0; i < endTime - startTime; i++) {
                schedule.add(new ArrayList<>());
                schedule.get(i + d * (endTime - startTime + 1) + 1).add((startTime + i) + ":00");
                for (int j = 0; j < rooms.size(); j++) {
                    schedule.get(i + d * (endTime - startTime + 1) + 1).add("");
                }
            }
            if (d != day - 1) {
                schedule.add(new ArrayList<>());
                schedule.get((d + 1) * (endTime - startTime + 1)).add("Day " + (d + 2));
                for (int room : rooms) {
                    schedule.get((d + 1) * (endTime - startTime + 1)).add("R" + room);
                }
            }
        }
        return schedule;
    }


    /**
     * generate a grid for the schedule by the given events
     *
     * @param events list of event schedule has
     * @return the schedule based on events given
     */
    public List<List<String>> scheduleInfo(List<Event> events) {
        List<List<String>> schedule = generateEmpty();
        for (Event e : events) {
            int col = rooms.indexOf(e.getRoom()) + 1;
            int firstRow = e.getStartTime() - startTime + 1 + (e.getDay() - 1) * (endTime - startTime + 1);
            for (int i = 0; i < e.getDuration(); i++) {
                schedule.get(firstRow + i).set(col, "[" + e.getId() + "]" + e.getTitle());
            }
        }
        return schedule;
    }

    /**
     * Returns the occupation info for the given room.
     * Currently, the returned value is an ArrayList of Arraylists of Integers, where the inner ArrayList has
     * the start time at index 0 and duration at index 1, for every event that takes place in this room.
     *
     * @param events all the events taken from EventBoard
     * @param roomId room to be checked
     * @return ArrayList of ArrayList of integers, where the inner list includes the start time and duration for each
     * event in this room.
     */
    public List<List<String>> occupationInfo(List<Event> events, int roomId) {
        List<List<String>> occupation = new ArrayList<>();
        for (Event event : events) {
            if (event.getRoom() == roomId) {
                List<String> currEvent = new ArrayList<>();
                currEvent.add(Integer.toString(event.getStartTime()));
                currEvent.add(Integer.toString(event.getDuration()));
                currEvent.add(Integer.toString(event.getDay()));
                occupation.add(currEvent);
            }
        }
        return occupation;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.rooms = (ArrayList<Integer>) arg;
    }
}
