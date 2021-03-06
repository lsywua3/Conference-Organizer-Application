package event.useCases;

import account.entities.User;
import event.entities.Event;
import gateways.IGateway;
import room.RoomManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A UserEventManager for an attendee.
 */
public class AttendeeEventManager extends UserEventManager {
    /**
     * Constructs a new UserEventManager with a Speaker.
     *
     * @param user An instance of Attendee.
     */
    public AttendeeEventManager(User user, RoomManager rm) {
        super(user, rm);
    }

    /**
     * Signs up an event for the user.
     *
     * @param eventGateway An EventGateway.
     * @param eb           An EventBoard.
     * @param eventId      Int id of the event.
     * @return True if the sign up is successful; False if not.
     */
    public boolean signUpEvent(IGateway eventGateway, EventBoard eb, int eventId) {
        if (!eb.isValidId(eventId)) {
            return false;
        }
        Event event = eb.getEventById(eventId);
        if (event.addAttendee(user.getId())) {
            eb.save(eventGateway);
            return true;
        }
        return false;
    }

    /**
     * Cancels user's position in an event.
     *
     * @param eventGateway An EventGateway.
     * @param eb           An EventBoard.
     * @param eventId      Int id of the event.
     * @return True if cancelling is successful; false if not.
     */
    public boolean cancelEvent(IGateway eventGateway, EventBoard eb, int eventId) {
        if (!getMyEventList(eb).contains(eventId)) {
            return false;
        }
        if (eb.getEventById(eventId).removeAttendee(user.getId())) {
            eb.save(eventGateway);
            return true;
        }
        return false;
    }

    /**
     * Return an ArrayList of Integer ids of the events the attendee attend.
     *
     * @param eb An instance of EventBoard.
     * @return An ArrayList of Integer event ids.
     */
    public List<Integer> getMyEventList(EventBoard eb) {
        return eb.getAttendeeEvents(user.getId());
    }

    /**
     * Get An ArrayList of information of events.
     *
     * @param eb An instance of EventBoard.
     * @return An ArrayList of information generated by EventBoard.getInfo().
     * @see EventBoard
     */
    public List<List<String>> getMyEventsInfoList(EventBoard eb) {
        List<List<String>> result = new ArrayList<>();
        List<Integer> myEventList = getMyEventList(eb);
        for (int event : myEventList) {
            result.add(eb.getInfoList(event));
        }
        return result;
    }

    public List<List<String>> getMySchedule(EventBoard eb) {
        List<Event> eventList = eb.getAllEventsById(getMyEventList(eb));
        return eb.scheduleGenerator.scheduleInfo(eventList);
    }


}
