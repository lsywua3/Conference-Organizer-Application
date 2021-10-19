package message.useCases;

import account.entities.User;
import gateways.IGateway;
import message.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class SpeakerMessageManager extends UserMessageManager {

    public SpeakerMessageManager(User user, IGateway g) {
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
    public boolean sendAll(String content, IGateway g, List<User> attendees) {
        if (!checkAvailability(attendees)) {
            return false;
        }

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < attendees.size(); i++) {
            Message newMessage
                    = new Message(messageManager.generateId() + i, content, user.getId(), attendees.get(i).getId());
            messages.add(newMessage);
        }
        messageManager.updateMessage(messages, g);
        return true;
    }


    @Override
    protected boolean checkAvailability(User u) {
        return u.getType().equals("Speaker") || u.getType().equals("Attendee");
    }


    /**
     * Check if given list of user are all attendees
     *
     * @param users a list of user needs to be checked
     * @return true if all user in given list are attendees
     */
    private boolean checkAvailability(List<User> users) {
        for (User u : users) {
            if (!u.getType().equals("Attendee")) {
                return false;
            }
        }
        return true;
    }
}
