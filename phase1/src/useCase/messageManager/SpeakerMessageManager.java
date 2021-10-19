package useCase.messageManager;

import entities.Message;
import entities.Messageable;
import entities.User;
import gateways.IGateway;

import java.util.ArrayList;

public class SpeakerMessageManager extends UserMessageManager {

    public SpeakerMessageManager(Messageable user, IGateway g) {
        super(user, g);
    }

    /**
     * Send message to all given attendees
     *
     * @param content   the content of message
     * @param g         message gateway
     * @param attendees a list of attendees as receiver
     * @return true if all user in given list are attendee and message send successfully
     */
    public boolean sendAll(String content, IGateway g, ArrayList<User> attendees) {
        ArrayList<Messageable> receivers = new ArrayList<>();
        if (checkAvailability(attendees)) {
            for (User u : attendees) {
                receivers.add((Messageable) u);
            }
        } else {
            return false;
        }

        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < receivers.size(); i++) {
            Message newMessage
                    = new Message(messageManager.generateId() + i, content, user.getId(), receivers.get(i).getId());
            messages.add(newMessage);
            int newMessageId = newMessage.getId();
            if (user.checkSentMessage(newMessageId) || receivers.get(i).checkReceivedMessage(newMessageId)) {
                return false;
            }
        }
        for (int i = 0; i < receivers.size(); i++) {
            user.addSentMessage(messages.get(i).getId());
            receivers.get(i).addReceivedMessage(messages.get(i).getId());
        }
        messageManager.updateMessage(messages, g);
        return true;
    }

    /**
     * Check if given list of user are all attendees
     *
     * @param users a list of user needs to be checked
     * @return true if all user in given list are attendees
     */
    private boolean checkAvailability(ArrayList<User> users) {
        for (User u : users) {
            if (!u.getType().equals("Attendee")) {
                return false;
            }
        }
        return true;
    }
}
