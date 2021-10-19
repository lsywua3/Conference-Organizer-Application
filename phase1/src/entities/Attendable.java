package entities;

import java.util.ArrayList;

/**
 * User can sign up and cancel an event.
 */
public interface Attendable {

    /**
     * Every class that implements the Attendable interface has the eventList attribute,
     * the ArrayList of all the Event(s) (id) that this Attendable object has signed up to.
     *
     * @return the ArrayList of all the Events' id that the user has signed up to.
     */
    ArrayList<Integer> getEventList();

    /**
     * Take in the id of a Event, checks if the user has already signed up for the input event.
     * If not, add the event to the eventList.
     *
     * @param id the input event id.
     * @return if the addition is successful.
     */
    boolean addEvent(int id);

    /**
     * Take in the id of a Event, checks if the user has already signed up for this event.
     * If so, delete the event from the eventList.
     *
     * @param id the input event id.
     * @return if the deletion is successful.
     */
    boolean removeEvent(int id);

    /**
     * Returns the id of the Attendable object for some use cases.
     *
     * @return id of the Attendable object.
     */
    int getId();
}
