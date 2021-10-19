package entities;

import java.util.ArrayList;

/**
 * User can send an receive messages.
 */
public interface Messageable {

    /**
     * Every class that implements the Messageable interface has the sentMessages attribute, the
     * ArrayList of all the Message(s) (id) sent by this Messageable object.
     *
     * @return the sentMessages attribute of this Messageable object.
     */
    ArrayList<Integer> getSentMessages();

    /**
     * Every class that implements the Messageable interface has the receivedMessages attribute, the
     * ArrayList of all the Message(s) (id) received by this Messageable object.
     *
     * @return the receivedMessages attribute of this Messageable object.
     */
    ArrayList<Integer> getReceivedMessages();

    /**
     * Take in the id of a Message, add it into the sentMessages ArrayList if it is not in it before.
     *
     * @param id representation of a Message object.
     */
    void addSentMessage(int id);

    /**
     * Take in the id of a Message, add it into the receivedMessages ArrayList if it is not in it before.
     *
     * @param id representation of a Message object.
     */
    void addReceivedMessage(int id);

    /**
     * Take in the id of a Message, check if it is in the sentMessages ArrayList for this Messageable object.
     *
     * @param id representation of a Message object
     * @return true if sentMessages contains this message, and false otherwise.
     */
    boolean checkSentMessage(int id);

    /**
     * Take in the id of a Message, check if it is in the receivedMessages ArrayList for this Messageable object.
     *
     * @param id representation of a Message object
     * @return true if receivedMessages contains this message, and false otherwise.
     */
    boolean checkReceivedMessage(int id);

    /**
     * Every class that implements the Messageable interface has a hasNewMessage attribute,
     * indicating whether or not there is a new message for the Messageable object. This is set
     * to false initially.
     * <p>
     * This is the setter for this attribute
     *
     * @param status what the system wants this attribute to refer to.
     */
    void setHasNewMessage(boolean status);

    /**
     * Returns the id of the Messageable object for some use cases.
     *
     * @return id of the Messageable object.
     */
    int getId();

    /**
     * Returns the type of the Messageable object for some use cases.
     *
     * @return String type of the Messageable object.
     */
    String getType();
}
