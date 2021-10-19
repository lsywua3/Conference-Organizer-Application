package useCase.eventManager;

import entities.User;

import java.util.ArrayList;

/**
 * A use case class that contains user's events
 */
public abstract class UserEventManager {
    public final User user;

    /**
     * Constructs a new UserEventManager with a User.
     *
     * @param user An instance of User who needs a user event manager.
     */
    public UserEventManager(User user) {
        this.user = user;
    }

    //public abstract boolean checkAvailability(User user);

    /**
     * Return an Arraylist of the events the user participates in.
     *
     * @return a ArrayList of user events;
     */
    public abstract ArrayList<Integer> getMyEventList();

    /**
     * Return an Arraylist of the schedule that contains the events the user participates in.
     *
     * @param eb event board
     * @return an ArrayList generated by ScheduleGenerator
     * @see ScheduleGenerator
     */
    public abstract ArrayList<ArrayList<String>> getMySchedule(EventBoard eb);


    /**
     * Generate a schedule generator according too rooms.
     *
     * @return An instance of ScheduleGenerator.
     */
    protected ScheduleGenerator generateScheduleGenerator() {
        ArrayList<Integer> rooms;
        rooms = new ArrayList<>();
        rooms.add(101);
        rooms.add(102);
        rooms.add(103);
        rooms.add(201);
        rooms.add(202);
        rooms.add(203);
        int start = 9;
        int end = 17;
        return new ScheduleGenerator(rooms, start, end);
    }
}