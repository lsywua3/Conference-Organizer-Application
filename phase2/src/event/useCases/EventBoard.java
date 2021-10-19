package event.useCases;

import account.useCases.AccountManager;
import event.entities.Event;
import gateways.IGateway;
import interfaces.InfoAccessible;

import java.util.*;

/**
 * A use case class which stores information of all events in the conference and their related methods.
 */
public class EventBoard implements InfoAccessible, Observer{
    protected final int START_TIME = 9;
    protected final int END_TIME = 17;
    protected final int DAY = 3;

    protected List<Event> events;
    protected List<Integer> rooms;
    protected ScheduleGenerator scheduleGenerator;

    /**
     * Constructs the EventBoard with IGateway gate.
     * @param g the gateway used to get all events on event board.
     */
    public EventBoard(IGateway g) {
        events = (ArrayList<Event>) g.read();
        rooms = new ArrayList<>();
        scheduleGenerator = new ScheduleGenerator(rooms, START_TIME, END_TIME, DAY);
    }

    /**
     * Return an event with a specific id.
     * @param id event's id.
     * @return an event with corresponding id.
     */
    public Event getEventById(int id) {
        for (Event e: events){
            if (e.getId() == id){
                return e;
            }
        }
        return null;
    }

    /**
     * Return all events on the Event Board.
     * @return a list with all events on the Event Board.
     */
    public List<Event> getAllEvents() {
        return events;
    }

    /**
     * Return ids of all events on the Event Board.
     * @return a list of ids for all events on the Event Board.
     */
    public List<Integer> getAllEventsId() {
        List<Integer> eventIds = new ArrayList<>();
        for (Event event: events){
            eventIds.add(event.getId());
        }
        return eventIds;
    }

    /**
     * Return a list of events by their ids.
     * @param eventIds a list of event's id.
     * @return a list of Event with corresponding ids.
     */
    public List<Event> getAllEventsById(List<Integer> eventIds) {
        List<Event> events = new ArrayList<>();
        for (int id: eventIds){
            events.add(getEventById(id));
        }
        return events;
    }

    /**
     * Return all events for a speaker.
     * @return a list of events that this speaker is going to talk.
     */
    public List<Integer> getSpeakerEvents(int id) {
        List<Integer> speakerEvents = new ArrayList<>();
        for (Event event: events) {
            for (int speakerId: event.getSpeakers()) {
                if (speakerId == id) {
                    speakerEvents.add(event.getId());
                }
            }
        }
        return speakerEvents;
    }

    /**
     * Return all events for an attendee.
     * @return a list of events that this attendee is going to talk.
     */
    public List<Integer> getAttendeeEvents(int id) {
        List<Integer> attendeeEvents = new ArrayList<>();
        for (Event event: events) {
            for (int attendeeId: event.getAttendees()) {
                if (attendeeId == id) {
                    attendeeEvents.add(event.getId());
                }
            }
        }
        return attendeeEvents;
    }


    /**
     * Return one specific information about one specific event.
     * @param id An event's id.
     * @param option One information about this event.
     * @return a specific single information about this event.
     */
    public String getInfo(int id, String option){
        Event event = getEventById(id);
        switch (option){
            case "id": return Integer.toString(event.getId());
            case "title": return event.getTitle();
            case "speakers": return event.getSpeakers().toString();
            case "day": return Integer.toString(event.getDay());
            case "startTime": return Integer.toString(event.getStartTime());
            case "duration": return Integer.toString(event.getDuration());
            case "capacity": return Integer.toString(event.getCapacity());
            case "occupation": return Integer.toString(event.getAttendees().size());
            case "room": return Integer.toString(event.getRoom());
        }
        return "";
    }

    /**
     * Return information about an event.
     * @param id An event's id.
     * @return information in format of [id, title, speakers, startTime, duration, capacity, occupation, room]
     */
    public List<String> getInfoList(int id){
        Event event = getEventById(id);
        List<String> info = new ArrayList<>();
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
        info.add(event.getEventType());
        info.add(Integer.toString(event.getDay()));
        return info;
    }

    /**
     * Return information about an event.
     * @param id An event's id.
     * @return information in format of [id, title, speakers, startTime, duration,
     * capacity, occupation, room, speakerName]
     */
    public List<String> getInfoList(int id, AccountManager acm){
        List<String> info = getInfoList(id);

        if (info == null) {
            return null;
        }

        List<Integer> speakers = getEventById(id).getSpeakers();
        StringBuilder names = new StringBuilder();

        for (int speaker: speakers) {
           names.append("[").append(speaker).append("]").append(acm.getInfo(speaker, "name")).append(", ");
        }

        info.add(names.toString());
        return info;
    }

    /**
     * Return schedule for all events on this Event Board.
     * @return a schedule.
     */
    public List<List<String>> getAllSchedule() {
        return scheduleGenerator.scheduleInfo(events);
    }

    /**
     * Save changes.
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
     * @param id a int id
     * @return if this id belongs to an event
     */
    public boolean isValidId(int id) {
        Event event = getEventById(id);
        return event != null;
    }

    public ScheduleGenerator getScheduleGenerator() {
        return scheduleGenerator;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.rooms = (ArrayList<Integer>) arg;
    }
}