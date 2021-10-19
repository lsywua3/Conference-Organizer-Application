package entities;

import java.util.ArrayList;

/**
 * A speaker of the conference who has a list of talks to give.
 */
public class Speaker extends User implements Messageable {
    private ArrayList<Integer> talks;
    private ArrayList<Integer> sentMessages;
    private ArrayList<Integer> receivedMessages;
    private boolean hasNewMessage = false;

    /**
     * Constructs a new Speaker with the following parameters.
     * Calls the User constructor and passes in the type "Speaker".
     *
     * @param email    the Speaker's email.
     * @param password the account password
     * @param name     the name of the Speaker
     * @param id       the id of the Organizer
     */
    public Speaker(String email, String password, String name, int id) {
        super(email, password, name, "Speaker", id);
        talks = new ArrayList<>();
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();
    }

    /**
     * Speaker objects have the talks attribute that is an ArrayList of Event(s) (id), representing
     * the Event(s) that this speaker will be speaking at.
     *
     * @return the talk attribute.
     */
    public ArrayList<Integer> getTalks() {
        return talks;
    }

    /**
     * Take in the id of a Event, checks if it has already been added to the talks attribute. If not,
     * add it to the talks attribute, indicating that this speaker will be speaking at this Event.
     *
     * @param id representation of a Event object
     * @return true if the addition was successful, and false otherwise
     */
    public boolean addTalk(int id) {
        if (!talks.contains(id)) {
            talks.add(id);
            return true;
        }
        return false;
    }

    /**
     * Take in the id of a Event, checks if it has already been added to the talks attribute. If so,
     * remove from the talks attribute, indicating that this speaker will no longer be speaking at this Event.
     * <p>
     * This method assumes that there is no duplicate event in talks, by the implementation of addTalk,
     * so immediately returns true after finding one matching event.
     *
     * @param id representation of a Event object
     * @return true if the deletion was successful, and false otherwise
     */
    public boolean removeTalk(int id) {
        if (talks.contains(id)) {
            talks.remove((Integer) id);
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
