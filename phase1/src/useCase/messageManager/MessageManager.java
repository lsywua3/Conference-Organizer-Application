package useCase.messageManager;

import entities.Message;
import gateways.IGateway;

import java.util.ArrayList;

public class MessageManager {
    private ArrayList<Message> allMessage;

    /**
     * Construct a New Message Manager with IGateway g.
     *
     * @param g the gateway to get all messages.
     */
    public MessageManager(IGateway g) {
        allMessage = g.read();
    }

    /**
     * Return a message with a specific id.
     *
     * @param id a message's id.
     * @return a messages.
     */
    public Message getMessageById(int id) {
        for (Message m : allMessage) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Check if the message has been updated.
     *
     * @param newMessage a message
     * @param g          the gateway used to update the message.
     * @return true if the message is updated.
     */
    public boolean updateMessage(Message newMessage, IGateway g) {
        for (Message m : allMessage) {
            if (m.getId() == newMessage.getId()) {
                return false;
            }
        }
        allMessage.add(newMessage);
        g.write(allMessage);
        return true;
    }

    /**
     * add new message to allMessage and update gateway
     *
     * @param newMessages the new message needs to be added
     * @param g           the message gateway
     * @return true if update successful
     */
    public boolean updateMessage(ArrayList<Message> newMessages, IGateway g) {
        for (Message newM : newMessages) {
            for (Message m : allMessage) {
                if (m.getId() == newM.getId()) {
                    return false;
                }
            }
        }
        allMessage.addAll(newMessages);
        g.write(allMessage);
        return true;
    }

    /**
     * Generate a new if for a message.
     *
     * @return a id.
     */
    public int generateId() {
        return allMessage.get(allMessage.size() - 1).getId() + 1;
    }
}
