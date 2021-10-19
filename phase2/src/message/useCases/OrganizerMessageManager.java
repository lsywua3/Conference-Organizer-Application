package message.useCases;

import account.entities.User;
import gateways.IGateway;
import message.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class OrganizerMessageManager extends UserMessageManager {

    public OrganizerMessageManager(User user, IGateway g) {
        super(user, g);
    }

    @Override
    protected boolean checkAvailability(User u) {
        // Double check this part ...
        return !u.getType().equals("Organizer");
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
    public boolean sendAll(String content, IGateway g, List<User> users, String type) {
        if (!(type.toLowerCase().equals("speaker") || type.toLowerCase().equals("attendee") ||
                type.toLowerCase().equals("staff"))) {
            return false;
        }
        List<Message> messages = new ArrayList<>();
        List<User> receivers = new ArrayList<>();
        for (User u : users) {
            if (u.getType().equals(type)) {
                receivers.add(u);
            }
        }
        for (int i = 0; i < receivers.size(); i++) {
            Message newMessage
                    = new Message(messageManager.generateId() + i, content, user.getId(), receivers.get(i).getId());
            messages.add(newMessage);
        }
        messageManager.updateMessage(messages, g);
        return true;
    }
}
