package useCase.messageManager;

import entities.Message;
import entities.Messageable;
import entities.User;
import gateways.IGateway;
import useCase.InfoAccessible;

import java.util.ArrayList;

public abstract class UserMessageManager implements InfoAccessible {
    protected final Messageable user;
    protected final MessageManager messageManager;

    /**
     * Constructs a new UserMessageManager
     * messageable user and messageManager messagemanager
     *
     * @param user the user that use this manager.
     * @param g    the gateway for creating the messageManager
     */
    public UserMessageManager(Messageable user, IGateway g) {
        this.messageManager = new MessageManager(g);
        this.user = user;
    }

    /**
     * @return the list of message id represent all sent messages this user have
     */
    public ArrayList<Integer> getSentMessage() {
        return user.getSentMessages();
    }

    /**
     * @return the list of message id represent all received messages this user have
     */
    public ArrayList<Integer> getReceivedMessage() {
        return user.getReceivedMessages();
    }

    /**
     * Get a message by given id if and only if the message belongs to the user
     *
     * @param id the id of message you want to get
     * @return a message object by the given message id
     */
    public Message getMessageById(int id) {
        if (user.getSentMessages().contains(id) || user.getReceivedMessages().contains(id)) {
            return messageManager.getMessageById(id);
        }
        return null;
    }

    /**
     * Get one information of a message belongs to current user
     *
     * @param id     id of message
     * @param option the type of info you want
     * @return string of info get from message, empty if message not belongs to current user or type is wrong
     */
    public String getInfo(int id, String option) {
        if (!(user.getSentMessages().contains(id) || user.getReceivedMessages().contains(id))) {
            return "";
        }
        Message message = messageManager.getMessageById(id);
        switch (option) {
            case "senderId":
                return Integer.toString(message.getSenderId());
            case "receiverId":
                return Integer.toString(message.getReceiverId());
            case "content":
                return message.getContent();
        }
        return "";
    }

    /**
     * get all info of a given message
     *
     * @param id id of the message
     * @return a list of string that contains all info of the given message id,
     * list with empty string if message not belongs to current user
     */
    public ArrayList<String> getInfoList(int id) {
        ArrayList<String> info = new ArrayList<>();
        if (!(user.getSentMessages().contains(id) || user.getReceivedMessages().contains(id))) {
            info.add("");
            info.add("");
            info.add("");
            return info;
        }
        Message message = messageManager.getMessageById(id);
        info.add(Integer.toString(message.getSenderId()));
        info.add(Integer.toString(message.getReceiverId()));
        info.add(message.getContent());
        return info;
    }

    /**
     * Send message from current user to receiver
     *
     * @param receiver the receiver of the message
     * @param content  the content of the message
     * @param g        the message gateway
     * @return true if message is sent successfully
     */
    public boolean sendMessage(User receiver, String content, IGateway g) {
        Messageable checkedReceiver;

        if (checkAvailability(receiver)) {
            checkedReceiver = (Messageable) receiver;
        } else {
            return false;
        }

        Message newMessage = new Message(messageManager.generateId(), content, user.getId(), checkedReceiver.getId());
        int newMessageId = newMessage.getId();
        if (user.checkSentMessage(newMessageId) || checkedReceiver.checkReceivedMessage(newMessageId)) {
            return false;
        } else {
            user.addSentMessage(newMessageId);
            checkedReceiver.addReceivedMessage(newMessageId);
        }
        messageManager.updateMessage(newMessage, g);
        return true;
    }

    /**
     * Used by send method to check if receiver is allowed to be sent message
     *
     * @param u a user object
     * @return true if user can be sent message
     */
    private boolean checkAvailability(User u) {
        if (u.getType().equals("Organizer")) {
            return false;
        }
        return u instanceof Messageable;
    }
}
