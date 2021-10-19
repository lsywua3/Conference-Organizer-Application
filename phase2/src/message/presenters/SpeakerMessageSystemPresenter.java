package message.presenters;

public class SpeakerMessageSystemPresenter extends MessageSystemPresenter {

    /**
     * A constructor that constructs SpeakerMessageSystemPresenter
     */
    public SpeakerMessageSystemPresenter() {
        super();
    }

    /**
     * presents the instruction for asking for the event id of the event that a speaker wants to make an announcement
     * to when the speaker is trying to make an announcement to the attendees in an event
     */
    public void presentAnnounceMessageToEventEventInstruction() {
        System.out.println("Please enter the <event id> of the event that you want to make announcement to. " +
                "Enter [0] to go back");

    }

    /**
     * Presents the event not found notification message when an event that a speaker tries to access can not be found
     */
    public void presentEventNotFound() {
        System.out.println("Can not find this event in your event list, please try again!");
    }

    /**
     * Presents the instruction for asking for the content of the message that the speaker wants to announce when a
     * speaker wants to make an announcement to all attendees in an event
     */
    public void presentAnnounceMessageToEventContentInstruction() {
        System.out.println("Please enter the <content> of the message that you want to send. " +
                "Enter [0] to go back");

    }

    /**
     * The success or failure notification message when a speaker tries to send a message (announcement) to all the
     * attendees in an event
     *
     * @param success the boolean value representing whether the message has been sent successfully
     */
    public void presentAnnounceMessageToEventResult(boolean success) {
        String successMessage = "Successfully sent announcement message to all the attendees in this " +
                "event!";
        String failureMessage = "Failed to announce this message to attendees";
        presentSendingResult(success, successMessage, failureMessage);
    }


    /**
     * Presents the instruction for asking for the content of the message that a speaker wants to send when a speaker
     * wants to send a message to all the attendees in all of that speaker's events
     */
    public void presentAnnounceMessageToAllEventsContentInstruction() {
        System.out.println("Please enter the <content> of the message that you want to send. Enter [0] to go back");
    }

    /**
     * Presents the success or failure notification message when a speaker tries to send a message (announcement) to
     * all of the attendees in all of that speaker's events
     *
     * @param success the boolean value representing whether the message that the speaker tries to send is successfully
     *                sent
     */
    public void presentAnnounceMessageToAllEventsResult(boolean success) {
        String successMessage = "Successfully announced this message to all your event attendees!";
        String failureMessage = "Failed to announce this message, please try again";
        presentSendingResult(success, successMessage, failureMessage);
    }

    /**
     * Present the menu of the commands that a user can make when using an speaker messaging system
     */
    public void presentMenu() {
        System.out.println("----------------\nMessaging System Menu:");
        System.out.println("" +
                "[1] View all messages\n" +
                "[2] View one single message\n" +
                "[3] Send message to one attendee or speaker\n" +
                "[4] Reply to message\n" +
                "[5] Mark message\n" +
                "[6] Make announcement to all attendees in one of your event\n" +
                "[7] Make announcement to all attendees in all of your events\n" +
                "[0] Exit");
    }
}
