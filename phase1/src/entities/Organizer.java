package entities;

import java.util.ArrayList;

/**
 * An organizer of the conference.
 */
public class Organizer extends User implements Messageable {
    private ArrayList<Integer> sentMessages;
    private ArrayList<Integer> receivedMessages;
    private boolean hasNewMessage = false;

    /**
     * Constructs a new Organizer with the following parameters.
     * Calls the User constructor and passes in the type "Organizer".
     *
     * @param email    the organizer's email.
     * @param password the account password
     * @param name     the name of the Organizer
     * @param id       the id of the Organizer
     */
    public Organizer(String email, String password, String name, int id) {
        super(email, password, name, "Organizer", id);
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();
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
