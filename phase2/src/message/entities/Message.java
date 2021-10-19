package message.entities;

/**
 * A message between two users.
 */
public class Message implements java.io.Serializable {

    private final int id;
    private final String content;
    private final int senderId;
    private final int receiverId;
    private String status;

    /**
     * Constructs a new Message object with the following parameters
     *
     * @param id         the unique id representation of this message
     * @param content    the String content of this message
     * @param senderId   the sender's id of this message
     * @param receiverId the receiver's id of this message
     */
    public Message(int id, String content, int senderId, int receiverId) {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = "unread";
    }

    /**
     * @return the id of this message
     */
    public int getId() {
        return id;
    }

    /**
     * @return the content of this message
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the sender's id of this message
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * @return the receiver's id of this message
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * @return the status of this message
     */
    public String getStatus() {
        return status;
    }

    /**
     * set the status of this message
     *
     * @param newStatus a string represent the status to set
     * @return true if set successful
     */
    public boolean setStatus(String newStatus) {
        if (!availableStatus(newStatus)) {
            return false;
        }
        this.status = newStatus;
        return true;
    }

    /**
     * check if given string is an available status
     *
     * @param s the string to check
     * @return true if s is an available status
     */
    private boolean availableStatus(String s) {
        if (s.equals(status)) {
            return false;
        }

        return s.equals("read") || s.equals("unread") || s.equals("achieved");
    }
}