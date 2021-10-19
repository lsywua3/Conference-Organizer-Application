package event.entities;

import java.util.*;

/**
 * An event in the conference.
 */
public abstract class Event implements java.io.Serializable {

    private final int id;
    private String title;
    private String description;
    private int room;
    private int startTime;
    private int duration;
    private int day;
    private int capacity;
    public List<Integer> speakers;
    private final List<Integer> attendees;
    public String type;

    /**
     * Constructs a new Event object with the following parameters.
     *
     * @param id        unique representation of the Event
     * @param title     title of the event
     * @param speakers  ArrayList of Speaker(s) (id) who are speaking at this event
     * @param room      id of the room where this event takes place
     * @param startTime integer representation of the starting time of this event
     * @param duration  time duration of this event in hours
     * @param capacity  the maximum amount of attendee that can attend this event
     */
    public Event(int id, String title, List<Integer> speakers, int room, int startTime, int duration, int day,
                 int capacity) {
        this.id = id;
        this.speakers = speakers;
        this.attendees = new ArrayList<>();

        this.title = title;
        this.room = room;
        this.startTime = startTime;
        this.duration = duration;
        this.day = day;
        this.capacity = capacity;
    }

    /**
     * @return the id of this event.
     */
    public int getId() {
        return id;
    }

    /**
     * @return the title of this event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the title of the event. Used to change the title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description of this event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of the event. Used to change the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the room where this event takes place.
     */
    public int getRoom() {
        return room;
    }

    /**
     * Setter for the room of the event. Used to change the room.
     */
    public void setRoom(int room) {
        this.room = room;
    }

    /**
     * @return the start time of this event.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Setter for the start time of the event. Used to change the start time.
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the time duration of this event (in hours).
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter for the duration of the event. Used to change the duration.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the date of this event (in hours).
     */
    public int getDay() {
        return day;
    }

    /**
     * Setter for the date of the event. Used to change the date.
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the capacity of this event.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Setter for the capacity of the event. Used to change the capacity.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the Speaker(s) (id) who are speaking at this event.
     */
    public List<Integer> getSpeakers() {
        return speakers;
    }

    /**
     * Setter for the speakers of the event. Used to change the speakers by passing in
     * a completely new ArrayList of Speaker(s) (id).
     */
    public abstract boolean setSpeakers(List<Integer> speakers);


    /**
     * @return the Attendable object(s) (id) who are attending this event.
     */
    public List<Integer> getAttendees() {
        return attendees;
    }


    /**
     * Take in the id representation of an Attendable object.
     * Check if this person has already signed up for this event, and if the event has room
     * for this person.
     * If both the conditions above are false, add this person's id to the attendees ArrayList.
     *
     * @return true if the addition is successful, and false otherwise.
     */
    public boolean addAttendee(int id) {
        if (hasAttendee(id) || !canSignUp()) {
            return false;
        }
        attendees.add(id);
        return true;
    }

    /**
     * Take in the id representation of an Attendable object
     * <p>
     * Check if this person is in the attendees ArrayList, if so, remove it.
     *
     * @return true if the deletion is successful, and false otherwise
     */
    public boolean removeAttendee(int id) {
        if (hasAttendee(id)) {
            attendees.remove((Integer) id);
            return true;
        }
        return false;
    }

    /**
     * Check if this event can allow one more attendee to sign up, i.e,
     * number of attendees < capacity.
     *
     * @return true if this event can include another attendee, and false otherwise.
     */
    public boolean canSignUp() {
        return capacity > attendees.size();
    }

    /**
     * Take in the id representation of an Attendable object.
     * Check if this person is in the attendees ArrayList of the event.
     *
     * @param id representation of this Attendable object.
     * @return true if the attendees ArrayList contains this Attendable object, and false otherwise
     */
    public boolean hasAttendee(int id) {
        return attendees.contains(id);
    }

    /**
     * @return the type of this event, whether it is talk, panel discussion, or a party.
     */
    public String getEventType() {
        return this.type;
    }
}