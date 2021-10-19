package event.entities;

import java.util.ArrayList;
import java.util.List;

public class MultiSpeakerEvent extends Event {
    /**
     * Constructs a new UserEventManager with multi speakers.
     *
     * @param id        unique representation of the Event
     * @param title     title of the event
     * @param speakers  ArrayList of Speaker(s) (id) who are speaking at this event
     * @param room      id of the room where this event takes place
     * @param startTime integer representation of the starting time of this event
     * @param duration  time duration of this event in hours
     * @param capacity  the maximum amount of Attendable objects that can attend this event
     */
    public MultiSpeakerEvent(int id, String title, List<Integer> speakers, int room, int startTime, int duration,
                             int day, int capacity) {
        super(id, title, speakers, room, startTime, duration, day, capacity);
        this.type = "multiple speaker";
    }

    /**
     * @param speakers ArrayList of Speaker(s) (id) who are speaking at this event
     * @return whether the speakers of this event has been successfully changed.
     */
    public boolean setSpeakers(List<Integer> speakers) {
        if (speakers.size() >= 2) {
            this.speakers = speakers;
            return true;
        }
        return false;
    }
}