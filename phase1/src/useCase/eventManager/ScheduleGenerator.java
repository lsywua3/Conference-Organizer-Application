package useCase.eventManager;

import entities.Event;

import java.util.ArrayList;

public class ScheduleGenerator {
    ArrayList<Integer> rooms;
    int startTime;
    int endTime;

    /**
     * Initialize a ScheduleGenerator
     *
     * @param rooms     all room we have
     * @param startTime start time of the day
     * @param endTime   end time of teh day
     */
    public ScheduleGenerator(ArrayList<Integer> rooms, int startTime, int endTime) {
        this.rooms = rooms;
        this.startTime = startTime;
        this.endTime = endTime;
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
    private ArrayList<ArrayList<String>> generateEmpty() {
        ArrayList<ArrayList<String>> schedule = new ArrayList<>();
        schedule.add(new ArrayList<>());
        schedule.get(0).add("");
        for (int room : rooms) {
            schedule.get(0).add("R" + room);
        }
        for (int i = 0; i < endTime - startTime; i++) {
            schedule.add(new ArrayList<>());
            schedule.get(i + 1).add((startTime + i) + ":00");
            for (int j = 0; j < rooms.size(); j++) {
                schedule.get(i + 1).add("");
            }
        }
        return schedule;
    }

    /**
     * check if the new event will cause conflict
     *
     * @param events   a list of event
     * @param newEvent a event object
     * @return true if no conflict caused
     */
    public boolean checkConflict(ArrayList<Event> events, Event newEvent) {
        int newRoom = newEvent.getRoom();
        int newStart = newEvent.getStartTime();
        int newEnd = newStart + newEvent.getDuration();
        ArrayList<Integer> newSpeakers = newEvent.getSpeakers();
        if (!rooms.contains(newRoom) || newStart < startTime || newEnd > endTime) {
            return false;
        }
        for (Event e : events) {
            if (e.getId() == newEvent.getId()) {
                continue;
            }
            int room = e.getRoom();
            int start = e.getStartTime();
            int end = start + e.getDuration();
            ArrayList<Integer> speakers = e.getSpeakers();
            if (!(start >= newEnd || end <= newStart)) {
                if (room == newRoom) {
                    return false;
                }
                for (int id : newSpeakers) {
                    if (speakers.contains(id)) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    /**
     * check if new event will cause conflict for an attendee
     *
     * @param events   all events attendee has
     * @param newEvent event attendee wants to add
     * @return true if no conflict caused
     */
    public boolean checkConflictAttendee(ArrayList<Event> events, Event newEvent) {
        int newStart = newEvent.getStartTime();
        int newEnd = newStart + newEvent.getDuration();
        for (Event e : events) {
            int start = e.getStartTime();
            int end = start + e.getDuration();
            if (!(start >= newEnd || end <= newStart)) {
                return false;
            }
        }
        return true;
    }

    /**
     * generate a grid for the schedule by the given events
     *
     * @param events list of event schedule has
     * @return the schedule based on events given
     */
    public ArrayList<ArrayList<String>> scheduleInfo(ArrayList<Event> events) {
        ArrayList<ArrayList<String>> schedule = generateEmpty();
        for (Event e : events) {
            int col = rooms.indexOf(e.getRoom()) + 1;
            int firstRow = e.getStartTime() - startTime + 1;
            for (int i = 0; i < e.getDuration(); i++) {
                schedule.get(firstRow + i).set(col, "[" + e.getId() + "]" + e.getTitle());
            }
        }
        return schedule;
    }
}
