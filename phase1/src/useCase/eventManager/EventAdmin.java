package useCase.eventManager;

import entities.Attendee;
import entities.Event;
import entities.Speaker;
import gateways.IGateway;
import useCase.accountManager.AccountManager;

import java.util.ArrayList;


/**
 * A use case class that is administrative to modify events and add and remove new events.
 */
public class EventAdmin extends EventBoard {

    /**
     * Constructs a new EventAdmin object by setting the start time to 9 and end time to 17 (24-hour clock)
     * Take in the EventGateway as parameter and calls the super constructor from EventBoard.
     *
     * @param g EventGateway object
     */
    public EventAdmin(IGateway g) {
        super(g);
        int start = 9;
        int end = 17;
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
     * @param speakerId      the speaker's id of this event
     * @param room           the room's id of this event
     * @param startTime      the startTime of this event
     * @param duration       the time duration of this event (in hours)
     * @param capacity       the capacity of this event
     * @return true if the addition is successful, and false otherwise
     */
    public boolean addEvent(IGateway eventGateway, IGateway userGateway, AccountManager accountManager, String title,
                            int speakerId, int room, int startTime, int duration, int capacity) {
        int eventId = generateEventId();

        ArrayList<Integer> speakers = new ArrayList<>();
        if (accountManager.isSpeaker(speakerId)) {
            speakers.add(speakerId);
        } else {
            return false;
        }

        Event event = new Event(eventId, title, speakers, room, startTime, duration, capacity);
        if (scheduleGenerator.checkConflict(events, event)) {
            events.add(event);
            eventGateway.write(events);

            for (int id : speakers) {
                ((Speaker) accountManager.getUserById(id)).addTalk(eventId);
            }

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
                ArrayList<Integer> speakers = event.getSpeakers();
                for (int speakerId : speakers) {
                    ((Speaker) accountManager.getUserById(speakerId)).removeTalk(id);
                }
                ArrayList<Integer> attendees = event.getAttendees();
                for (int attendeeId : attendees) {
                    ((Attendee) accountManager.getUserById(attendeeId)).removeEvent(id);
                }
                accountManager.save(userGateway);
                return true;
            }
        }
        return false;
    }

    /**
     * This method helps with editing the information of an event.
     * Check if the event information conflicts with the schedule.
     * If not, change the attributes to the information given.
     * Save the changes through gateways or calling AccountManager.
     *
     * @param eventGateway EventGateway object
     * @param id           representation of the event to be edited
     * @param title        new title for this event
     * @param speakerId    new speaker's id for this event
     * @param room         new room id for this event
     * @param startTime    new start time for this event
     * @param duration     new duration for this event
     * @param capacity     new capacity for this event
     * @return true if the event has been successfully edited, and false if it conflicts with schedule.
     */
    public boolean setEvent(IGateway eventGateway, IGateway userGateway, AccountManager accountManager, int id,
                            String title, int speakerId, int room, int startTime, int duration, int capacity) {
        Event event = getEventById(id);

        if (scheduleGenerator.checkConflict(events, event)) {
            event.setTitle(title);
            ArrayList<Integer> preSpeakerIds = event.getSpeakers();
            for (int preSpeakerId : preSpeakerIds) {
                ((Speaker) accountManager.getUserById(preSpeakerId)).removeTalk(id);
            }
            ArrayList<Integer> speakers = new ArrayList<>();
            speakers.add(speakerId);
            event.setSpeakers(speakers);
            for (int newSpeakerId : speakers) {
                ((Speaker) accountManager.getUserById(newSpeakerId)).addTalk(id);
            }
            event.setRoom(room);
            event.setStartTime(startTime);
            event.setDuration(duration);
            event.setCapacity(capacity);
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
    public boolean rescheduleEvent(IGateway eventGateway, int id, int room, int startTime, int duration) {
        Event event = getEventById(id);
        Event newEvent = new Event(id, event.getTitle(), event.getSpeakers(), room, startTime, duration,
                event.getCapacity());

        if (scheduleGenerator.checkConflict(events, newEvent)) {
            event.setRoom(room);
            event.setStartTime(startTime);
            event.setDuration(duration);
            eventGateway.write(events);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method gets all the attendee(s) (id) who signed up for a given event.
     *
     * @param id representation of the event
     * @return ArrayList of attendee(s)' id, who are in this event.
     */
    public ArrayList<Integer> getEventAttendees(int id) {
        for (Event event : events) {
            if (event.getId() == id) {
                return event.getAttendees();
            }
        }
        return null;
    }
}

