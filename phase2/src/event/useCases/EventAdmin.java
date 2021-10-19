package event.useCases;

import account.useCases.AccountManager;
import event.entities.Event;
import event.entities.MultiSpeakerEvent;
import event.entities.NoSpeakerEvent;
import event.entities.OneSpeakerEvent;
import gateways.IGateway;
import room.RoomManager;

import java.util.List;
import java.util.Observer;


/**
 * A use case class that is administrative to modify events and add and remove new events.
 */
public class EventAdmin extends EventBoard implements Observer {

    /**
     * Constructs a new EventAdmin object by setting the start time to 9 and end time to 17 (24-hour clock)
     * Take in the EventGateway as parameter and calls the super constructor from EventBoard.
     *
     * @param g EventGateway object
     */
    public EventAdmin(IGateway g) {
        super(g);
    }


    /**
     * This method helps to add an event.
     * <p>
     * Take in the EventGateway, UserGateway, AccountManager to help with writing files and creating events.
     * <p>
     * First, the id of an event is determined by adding 1 to the id of the last event added to
     * the events ArrayList. If events is empty, then the initial id begins from 1000.
     * <p>
     * Then check if the input speakerId is a valid speaker, if not, return false
     * Check is this event conflicts with existing events.
     * <p>
     * If all the checks are passed, create a new event and add its id to the events list.
     * Add the events to the speaker's talks attribute through AccountManager.
     * Save the changes through gateways or calling AccountManager.
     *
     * @param eventGateway   EventGateway object
     * @param userGateway    UserGateway object
     * @param accountManager AccountManager object
     * @param title          the title of this event
     * @param speakers       the list of speakers' id of this event
     * @param room           the room's id of this event
     * @param startTime      the startTime of this event
     * @param duration       the time duration of this event (in hours)
     * @param capacity       the capacity of this event
     * @return true if the addition is successful, and false otherwise
     */
    public boolean addEvent(IGateway eventGateway, IGateway userGateway, AccountManager accountManager, RoomManager rm,
                            String title, List<Integer> speakers, int room, int startTime, int duration, int day,
                            int capacity) {
        int eventId = generateEventId();
        Event event;
        if (Integer.parseInt(rm.getInfo(room, "capacity")) < capacity) {
            return false;
        }

        for (Integer speaker : speakers) {
            if (!accountManager.isSpeaker(speaker)) {
                return false;
            }
        }

        if (speakers.size() == 0) {
            event = new NoSpeakerEvent(eventId, title, speakers, room, startTime, duration, day, capacity);
        } else if (speakers.size() == 1) {
            event = new OneSpeakerEvent(eventId, title, speakers, room, startTime, duration, day, capacity);
        } else {
            event = new MultiSpeakerEvent(eventId, title, speakers, room, startTime, duration, day, capacity);
        }

        ConflictChecker conflictChecker = new ConflictChecker(START_TIME, END_TIME, rooms, DAY);
        if (conflictChecker.checkConflict(events, event)) {
            events.add(event);
            eventGateway.write(events);
            accountManager.save(userGateway);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generate an id for the latest event.
     *
     * @return int id.
     */
    private int generateEventId() {
        if (events.size() == 0) {
            return 1000;
        } else {
            return events.get(events.size() - 1).getId() + 1;
        }
    }

    /**
     * This method helps to remove an event.
     * <p>
     * Take in the EventGateway, UserGateway, AccountManager to help with writing and making changes.
     * Take in the id of the event to be removed.
     * <p>
     * Check if this event is in the events list, if so, remove it.
     * Also remove the events from the speakers' talks attribute.
     * Save the changes through gateways or calling AccountManager.
     *
     * @param eventGateway   EventGateway object
     * @param userGateway    UserGateway object
     * @param accountManager AccountManager object
     * @param id             representation of the event to be removed
     * @return true if the deletion is successful, and false otherwise.
     */
    public boolean removeEvent(IGateway eventGateway, IGateway userGateway, AccountManager accountManager, int id) {
        for (Event event : events) {
            if (event.getId() == id) {
                events.remove(event);
                eventGateway.write(events);
                accountManager.save(userGateway);
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to edit information in a event.
     * This method is a helper method for setEvent and rescheduleEvent.
     * <p>
     * This method could not change speakers of an event.
     *
     * @param id        representation of the event to be edited
     * @param title     new title for this event
     * @param room      new room id for this event
     * @param startTime new start time for this event
     * @param duration  new duration for this event
     * @param capacity  new capacity for this event
     */
    public void setEventInformation(int id, String title, int room, int startTime, int duration, int day,
                                    int capacity) {
        Event event = getEventById(id);

        event.setTitle(title);
        event.setRoom(room);
        event.setStartTime(startTime);
        event.setDuration(duration);
        event.setDay(day);
        event.setCapacity(capacity);
    }

    /**
     * This method helps with editing the information of an event.
     * Check if the event information conflicts with the schedule.
     * If not, change the attributes to the information given.
     * Save the changes through gateways or calling AccountManager.
     *
     * @param eventGateway EventGateway object
     * @param eventId      representation of the event to be edited
     * @param title        new title for this event
     * @param speakers     the list of speakers' id of this event
     * @param roomId       new room id for this event
     * @param startTime    new start time for this event
     * @param duration     new duration for this event
     * @param capacity     new capacity for this event
     * @return true if the event has been successfully edited, and false if it conflicts with schedule.
     */
    public boolean setEvent(IGateway eventGateway, IGateway userGateway, AccountManager accountManager, RoomManager rm,
                            int eventId, String title, List<Integer> speakers, int roomId, int startTime, int duration,
                            int day, int capacity) {
        Event event = getEventById(eventId);
        if (Integer.parseInt(rm.getInfo(roomId, "capacity")) < capacity) {
            return false;
        }

        ConflictChecker conflictChecker = new ConflictChecker(START_TIME, END_TIME, rooms, DAY);
        if (conflictChecker.checkConflict(events, event)) {
            setEventInformation(eventId, title, roomId, startTime, duration, day, capacity);
            event.setSpeakers(speakers);
            eventGateway.write(events);
            accountManager.save(userGateway);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method helps to reschedule an event.
     * Take in the information about the new room, startTime and duration.
     * Then, create a temporary newEvent and test if it is valid in the schedule.
     * If so, change the attributes in the old event object.
     *
     * @param eventGateway EventGateway object
     * @param id           representation of the event that will be rescheduled
     * @param room         the new room of this event
     * @param startTime    the new startTime of this event
     * @param duration     the new duration of this event
     * @return true if the event has been successfully rescheduled, and false otherwise.
     */
    public boolean rescheduleEvent(IGateway eventGateway, int id, int room, int startTime, int duration, int day) {
        Event event = getEventById(id);
        Event newEvent;

        if (event.getEventType().equals("no speaker")) {
            newEvent = new NoSpeakerEvent(id, event.getTitle(), event.getSpeakers(), room,
                    startTime, duration, day, event.getCapacity());
        } else if (event.getEventType().equals("one speaker")) {
            newEvent = new OneSpeakerEvent(id, event.getTitle(), event.getSpeakers(), room,
                    startTime, duration, day, event.getCapacity());
        } else {
            newEvent = new MultiSpeakerEvent(id, event.getTitle(), event.getSpeakers(), room,
                    startTime, duration, day, event.getCapacity());
        }

        ConflictChecker conflictChecker = new ConflictChecker(START_TIME, END_TIME, rooms, DAY);
        if (conflictChecker.checkConflict(events, newEvent)) {
            setEventInformation(id, event.getTitle(), room, startTime, duration, day, event.getCapacity());
            eventGateway.write(events);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method gets all the attendee(s) (id) who signed up for a given event.
     *
     * @param eventId representation of the event
     * @return ArrayList of attendee(s)' id, who are in this event.
     */
    public List<Integer> getEventAttendees(int eventId) {
        for (Event event : events) {
            if (event.getId() == eventId) {
                return event.getAttendees();
            }
        }
        return null;
    }

    public boolean resetCapacity(RoomManager rm, int eventId, int newCapacity) {
        if (Integer.parseInt(rm.getInfo(Integer.parseInt(rm.getInfo(eventId, "capacity")), "capacity")) >=
                newCapacity) {
            getEventById(eventId).setCapacity(newCapacity);
            return true;
        }
        return false;
    }
}

