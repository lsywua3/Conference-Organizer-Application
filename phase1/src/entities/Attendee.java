package entities;

import java.util.ArrayList;

/**
 * An attendee of the conferene who can attend events.
 */
public class Attendee extends User implements Attendable, Messageable {
    private ArrayList<Integer> eventList;
    private ArrayList<Integer> sentMessages;
    private ArrayList<Integer> receivedMessages;
    private boolean hasNewMessage = false;

    /**
     * Constructs a new Attendee with the following parameters.
     * Calls the User constructor and passes in the type "Attendee".
     *
     * @param email    the Attendee's email.
     * @param password the account password
     * @param name     the name of the attendee
     * @param id       the id of the Organizer
     */
    public Attendee(String email, String password, String name, int id) {
        super(email, password, name, "Attendee", id);
        eventList = new ArrayList<>();
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();
    }

    @Override
    public ArrayList<Integer> getEventList() {
        return eventList;
    }

    @Override
    public boolean addEvent(int id) {
        if (!eventList.contains(id)) {
            Integer IntId = id;
            eventList.add(IntId);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEvent(int id) {
        if (eventList.contains(id)) {
            eventList.remove((Integer) id);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Integer> getSentMessages() {
        return sentMessages;
    }

    @Override
    public ArrayList<Integer> getReceivedMessages() {
        return receivedMessages;
    }

    @Override
    public void addSentMessage(int id) {
        if (!sentMessages.contains(id)) {
            sentMessages.add(id);
        }
    }

    @Override
    public void addReceivedMessage(int id) {
        if (!receivedMessages.contains(id)) {
            receivedMessages.add(id);
        }
    }

    @Override
    public boolean checkSentMessage(int id) {
        return sentMessages.contains(id);
    }

    @Override
    public boolean checkReceivedMessage(int id) {
        return receivedMessages.contains(id);
    }

    @Override
    public void setHasNewMessage(boolean status) {
        this.hasNewMessage = status;
    }

}
