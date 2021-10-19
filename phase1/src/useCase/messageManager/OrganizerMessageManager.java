package useCase.messageManager;

import entities.Message;
import entities.Messageable;
import entities.User;
import gateways.IGateway;

import java.util.ArrayList;

public class OrganizerMessageManager extends UserMessageManager {

    public OrganizerMessageManager(Messageable user, IGateway g) {
        super(user, g);
    }

    /**
     * Send messages with same content to all attendees/speakers in given users
     *
     * @param content the content of the message
     * @param g       the message gateway
     * @param users   the user list search for
     * @param type    string attendee/speaker
     * @return true if message send successful to all attendees/speakers
     */
    public boolean sendAll(String content, IGateway g, ArrayList<User> users, String type) {
        if (!(type.toLowerCase().equals("speaker") || type.toLowerCase().equals("attendee"))) {
            return false;
        }
        ArrayList<Message> messages = new ArrayList<>();
        ArrayList<Messageable> receivers = new ArrayList<>();
        for (User u : users) {
            if (u.getType().equals(type)) {
                receivers.add((Messageable) u);
            }
        }
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
}
