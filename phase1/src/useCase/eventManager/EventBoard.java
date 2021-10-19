package useCase.eventManager;

import entities.Event;
import gateways.IGateway;
import useCase.InfoAccessible;
import useCase.accountManager.AccountManager;

import java.util.ArrayList;

/**
 * A use case class which stores information of all events in the conference and their related methods.
 */
public class EventBoard implements InfoAccessible {
    protected ArrayList<Event> events;
    protected ArrayList<Integer> rooms;
    protected ScheduleGenerator scheduleGenerator;

    /**
     * Constructs the EventBoard with IGateway gate.
     *
     * @param g the gateway used to get all events on event board.
     */
    public EventBoard(IGateway g) {
        events = (ArrayList<Event>) g.read();

        rooms = generateRooms();
        int start = 9;
        int end = 17;
        scheduleGenerator = new ScheduleGenerator(rooms, start, end);
    }

    /**
     * Return an event with a specific id.
     *
     * @param id event's id.
     * @return an event with corresponding id.
     */
    public Event getEventById(int id) {
        for (Event e : events) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    /**
     * Return all events on the Event Board.
     *
     * @return a list with all events on the Event Board.
     */
    public ArrayList<Event> getAllEvents() {
        return events;
    }


    /**
     * Generate an ArrayList of room numbers.
     *
     * @return An ArrayList of integer room numbers.
     */
    private ArrayList<Integer> generateRooms() {
        ArrayList<Integer> rooms = new ArrayList<>();
        rooms.add(101);
        rooms.add(102);
        rooms.add(103);
        rooms.add(201);
        rooms.add(202);
        rooms.add(203);
        return rooms;
    }

    /**
     * Return ids of all events on the Event Board.
     *
     * @return a list of ids for all events on the Event Board.
     */
    public ArrayList<Integer> getAllEventsId() {
        ArrayList<Integer> eventIds = new ArrayList<>();
        for (Event event : events) {
            eventIds.add(event.getId());
        }
        return eventIds;
    }

    /**
     * Return information about an event.
     *
     * @param id An event's id.
     * @return information in format of [id, title, speakers, startTime, duration, capacity, occupation, room]
     */
    public ArrayList<String> getInfoList(int id) {
        Event event = getEventById(id);
        ArrayList<String> info = new ArrayList<>();
        if (event == null) {
            return null;
        }
        info.add(Integer.toString(event.getId()));
        info.add(event.getTitle());
        info.add(event.getSpeakers().toString());
        info.add(Integer.toString(event.getStartTime()));
        info.add(Integer.toString(event.getDuration()));
        info.add(Integer.toString(event.getCapacity()));
        info.add(Integer.toString(event.getAttendees().size()));
        info.add(Integer.toString(event.getRoom()));
        return info;
    }

    /**
     * Return information about an event.
     *
     * @param id An event's id.
     * @return information in format of [id, title, speakers, startTime, duration,
     * capacity, occupation, room, speakerName]
     */
    public ArrayList<String> getInfoList(int id, AccountManager acm) {
        ArrayList<String> info = getInfoList(id);
        info.add(acm.getInfo(getEventById(id).getSpeakers().get(0), "name"));
        return info;
    }

    /**
     * Return one specific information about one specific event.
     *
     * @param id     An event's id.
     * @param option One information about this event.
     * @return a specific single information about this event.
     */
    public String getInfo(int id, String option) {
        Event event = getEventById(id);
        switch (option) {
            case "id":
                return Integer.toString(event.getId());
            case "title":
                return event.getTitle();
            case "speakers":
                return event.getSpeakers().toString();
            case "startTime":
                return Integer.toString(event.getStartTime());
            case "duration":
                return Integer.toString(event.getDuration());
            case "capacity":
                return Integer.toString(event.getCapacity());
            case "occupation":
                return Integer.toString(event.getAttendees().size());
            case "room":
                return Integer.toString(event.getRoom());
        }
        return "";
    }

    /**
     * Return a list of events by their ids.
     *
     * @param eventIds a list of event's id.
     * @return a list of Event with corresponding ids.
     */
    public ArrayList<Event> getAllEventsById(ArrayList<Integer> eventIds) {
        ArrayList<Event> events = new ArrayList<>();
        for (int id : eventIds) {
            events.add(getEventById(id));
        }
        return events;
    }

    /**
     * Return schedule for all events on this Event Board.
     *
     * @return a schedule.
     */
    public ArrayList<ArrayList<String>> getAllSchedule() {
        return scheduleGenerator.scheduleInfo(events);
    }

    /**
     * Save changes.
     *
     * @param gateway for reading and writing.
     */
    public void save(IGateway gateway) {
        gateway.write(events);
    }

    public void reload(IGateway gateway) {
        events = (ArrayList<Event>) gateway.read();
    }

    /**
     * Check if this thing is an event.
     *
     * @param id a int id
     * @return if this id belongs to an event
     */
    public boolean isValidId(int id) {
        Event event = getEventById(id);
        return event != null;
    }
}