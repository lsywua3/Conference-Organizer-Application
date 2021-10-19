package request.entities;

import java.io.Serializable;

/**
 * A UserRequest is a request that the user of the conference can send to the Organizer. The organizer can address/pend
 * the request.
 */
public class UserRequest implements Serializable {
    private final int id;
    private final int senderId;
    private final String message;
    private String status;

    /**
     * Constructor for userRequest.
     *
     * @param id       id of the request.
     * @param senderId id of the sender.
     * @param message  String content of the request.
     */
    public UserRequest(int id, int senderId, String message) {
        this.id = id;
        this.senderId = senderId;
        this.message = message;
        status = "pending";
    }


    /**
     * Get request id.
     *
     * @return request id
     */
    public int getId() {
        return id;
    }

    /**
     * Get sender id
     *
     * @return sender id
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * Get message.
     *
     * @return content of the request.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get status of the request.
     *
     * @return "pending" or "addressed"
     */
    public String getStatus() {
        return status;
    }

    /**
     * Change the status of a request to "pending".
     */
    public void setPending() {
        status = "pending";
    }

    /**
     * Change the status of a request to "addressed".
     */
    public void setAddressed() {
        status = "addressed";
    }
}
