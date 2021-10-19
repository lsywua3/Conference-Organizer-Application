package message.useCases;

import account.entities.User;
import gateways.IGateway;
import interfaces.InfoAccessible;
import message.entities.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class UserMessageManager implements InfoAccessible {
    protected final User user;
    protected final MessageManager messageManager;

    /**
     * Constructs a new UserMessageManager
     * and messageManager messagemanager
     *
     * @param user the user that use this manager.
     * @param g    the gateway for creating the messageManager
     */
    public UserMessageManager(User user, IGateway g) {
        this.messageManager = new MessageManager(g);
        this.user = user;
    }

    /**
     * @return the list of message id represent all sent messages this user have
     */
    public List<Integer> getSentMessage() {
        return messageManager.getSentMessages(user.getId());
    }

    /**
     * @return the list of message id represent all received messages this user have
     */
    public List<Integer> getReceivedMessage() {
        return messageManager.getReceivedMessages(user.getId());
    }

    public List<Integer> getMessageByStatus(String status) {
        return messageManager.getMessagesByStatus(user.getId(), status);
    }

    /**
     * Get a message by given id if and only if the message belongs to the user
     *
     * @param id the id of message you want to get
     * @return a message object by the given message id
     */
    public Message getMessageById(int id) {
        Message result = messageManager.getMessageById(id);
        if (result == null) {
            return null;
        }
        if (result.getSenderId() != user.getId() && result.getReceiverId() != user.getId()) {
            return null;
        }
        return result;
    }

    /**
     * Get one information of a message belongs to current user
     *
     * @param id     id of message
     * @param option the type of info you want
     * @return string of info get from message, empty if message not belongs to current user or type is wrong
     */
    public String getInfo(int id, String option) {
        Message message = messageManager.getMessageById(id);
        if (message.getSenderId() != user.getId() && message.getReceiverId() != user.getId()) {
            return "";
        }
        switch (option) {
            case "senderId":
                return Integer.toString(message.getSenderId());
            case "receiverId":
                return Integer.toString(message.getReceiverId());
            case "content":
                return message.getContent();
            case "status":
                return message.getStatus();
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
    public List<String> getInfoList(int id) {
        List<String> info = new ArrayList<>();
        Message message = messageManager.getMessageById(id);
        if (message.getSenderId() != user.getId() && message.getReceiverId() != user.getId()) {
            info.add("");
            info.add("");
            info.add("");
            info.add("");
            return info;
        }
        info.add(Integer.toString(message.getSenderId()));
        info.add(Integer.toString(message.getReceiverId()));
        info.add(message.getContent());
        if (message.getSenderId() == user.getId()) {
            info.add("send");
        } else {
            info.add(message.getStatus());
        }
        return info;
    }


    /**
     * Used by send method to check if receiver is allowed to be sent message
     *
     * @param u a user object
     * @return true if user can be sent message
     */
    protected abstract boolean checkAvailability(User u);

    /**
     * Send message from current user to receiver
     *
     * @param receiver the receiver of the message
     * @param content  the content of the message
     * @param g        the message gateway
     * @return true if message is sent successfully
     */
    public boolean sendMessage(User receiver, String content, IGateway g) {
        if (!checkAvailability(receiver)) {
            return false;
        }
        Message newMessage = new Message(messageManager.generateId(), content, user.getId(), receiver.getId());
        messageManager.updateMessage(newMessage, g);
        return true;
    }

    public boolean setStatus(int messageId, String newStatus, IGateway g) {
        Message m = messageManager.getMessageById(messageId);
        if (m.getReceiverId() != user.getId()) {
            return false;
        }
        if (m.setStatus(newStatus)) {
            messageManager.updateMessage(g);
            return true;
        }
        return false;
    }
}
