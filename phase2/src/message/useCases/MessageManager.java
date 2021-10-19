package message.useCases;

import gateways.IGateway;
import message.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private final List<Message> allMessage;

    /**
     * Construct a New Message Manager with IGateway g.
     *
     * @param g the gateway to get all messages.
     */
    public MessageManager(IGateway g) {
        allMessage = (List<Message>) g.read();
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
     */
    public void updateMessage(Message newMessage, IGateway g) {
        for (Message m : allMessage) {
            if (m.getId() == newMessage.getId()) {
                return;
            }
        }
        allMessage.add(newMessage);
        g.write(allMessage);
    }

    /**
     * add new message to allMessage and update gateway
     *
     * @param newMessages the new message needs to be added
     * @param g           the message gateway
     */
    public void updateMessage(List<Message> newMessages, IGateway g) {
        for (Message newM : newMessages) {
            for (Message m : allMessage) {
                if (m.getId() == newM.getId()) {
                    return;
                }
            }
        }
        allMessage.addAll(newMessages);
        g.write(allMessage);
    }

    public void updateMessage(IGateway g) {
        g.write(allMessage);
    }

    /**
     * Return all received messages for a user
     *
     * @param id user id
     * @return a list of message id
     */
    protected List<Integer> getReceivedMessages(int id) {
        List<Integer> receivedMessages = new ArrayList<>();
        for (Message m : allMessage) {
            if (m.getReceiverId() == id) {
                receivedMessages.add(m.getId());
            }
        }
        return receivedMessages;
    }

    /**
     * Return all sent messages for a user
     *
     * @param id user id
     * @return a list of message id
     */
    protected List<Integer> getSentMessages(int id) {
        List<Integer> sentMessages = new ArrayList<>();
        for (Message m : allMessage) {
            if (m.getSenderId() == id) {
                sentMessages.add(m.getId());
            }
        }
        return sentMessages;
    }

    /**
     * return all received message with status equal to
     * given status for a user
     *
     * @param id     id of the user
     * @param status a string represent the status wanted
     * @return a list of message id
     */
    protected List<Integer> getMessagesByStatus(int id, String status) {
        List<Integer> resultMessages = new ArrayList<>();
        for (Message m : allMessage) {
            if (m.getReceiverId() == id && m.getStatus().equals(status)) {
                resultMessages.add(m.getId());
            }
        }
        return resultMessages;
    }

    /**
     * Generate a new if for a message.
     *
     * @return a id.
     */
    public int generateId() {
        if (allMessage.size() ==0) {
            return 1000;
        }
        return allMessage.get(allMessage.size() - 1).getId() + 1;
    }
}
