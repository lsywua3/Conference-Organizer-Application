package message.presenters;

public class OrganizerMessageSystemPresenter extends MessageSystemPresenter {
    /**
     * A constructor that constructs a OrganizerMessageSystemPresenter
     */
    public OrganizerMessageSystemPresenter() {
        super();
    }

    /**
     * Presents the instruction for asking for the content of the message that an organizer wants to send when an
     * organizer wants to send a message to all specified users
     */
    public void presentSendMessageToAllContentInstruction() {
        System.out.println("Please enter the <content> of the message that you want to send. Enter [0] to go back");
    }

    /**
     * Present the success or failure notification message for sending the message after an organizer has sent a message
     * to all specified users
     *
     * @param success the boolean value representing whether the send message has been successfully sent
     */
    public void presentSendMessageToAllResult(boolean success) {
        String successMessage = "Your message has been successfully sent!";
        String failureMessage = "Failed to send this message, please try again with valid input";
        presentSendingResult(success, successMessage, failureMessage);
    }

    /**
     * Present the menu of the commands that a user can make when using an organizer messaging system
     */
    public void presentMenu() {
        System.out.println("----------------\nMessaging System Menu:");
        System.out.println("" +
                "[1] View all messages\n" +
                "[2] View one single message\n" +
                "[3] Send message to one attendee or speaker\n" +
                "[4] Reply to message\n" +
                "[5] Mark message\n" +
                "[6] Send message to all attendees\n" +
                "[7] Send message to all speakers\n" +
                "[0] Exit");
    }


}
