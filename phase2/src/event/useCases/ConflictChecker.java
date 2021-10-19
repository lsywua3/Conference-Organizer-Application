package event.useCases;

import event.entities.Event;

import java.util.List;

/**
 * A helper class that checks schedule conflict of events.
 */
public class ConflictChecker {
    private int startTime;
    private int endTime;
    private int day;
    private List<Integer> rooms;

    public ConflictChecker(int startTime, int endTime, List<Integer> rooms, int day) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rooms = rooms;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setRooms(List<Integer> rooms) {
        this.rooms = rooms;
    }

    /**
     * Check if the new event will cause conflict.
     * @param events a list of event
     * @param newEvent a event object
     * @return true if no conflict caused by the new event.
     */
    public boolean checkConflict(List<Event> events, Event newEvent){
        int newRoom = newEvent.getRoom();
        int newStart = newEvent.getStartTime();
        int newEnd = newStart + newEvent.getDuration();
        int newDay = newEvent.getDay();
        List<Integer> newSpeakers = newEvent.getSpeakers();
        if (!rooms.contains(newRoom) || newStart < startTime || newEnd > endTime || newDay < 1 || newDay > day){
            return false;
        }
        for (Event e : events){
            if (e.getId() == newEvent.getId() || e.getDay() != newEvent.getDay()){
                continue;
            }
            int room = e.getRoom();
            int start = e.getStartTime();
            int end = start + e.getDuration();
            List<Integer> speakers = e.getSpeakers();
            if (!(start >= newEnd || end <= newStart)){
                if (room == newRoom){
                    return false;
                }
                for (int id : newSpeakers){
                    if (speakers.contains(id)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
