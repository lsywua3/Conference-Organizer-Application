package message.presenters;

public class AttendeeMessageSystemPresenter extends MessageSystemPresenter {

    /**
     * The constructor that constructs a AttendeeMessageSystemPresenter
     */
    public AttendeeMessageSystemPresenter() {
        super();
    }

    /**
     * Present the menu of the commands that a user can make when using an attendee messaging system
     */
    public void presentMenu() {
        System.out.println("----------------\nMessaging System Menu:");
        System.out.println("" +
                "[1] View all messages\n" +
                "[2] View one single message\n" +
                "[3] Send message\n" +
                "[4] reply to message\n" +
                "[5] Mark message\n" +
                "[0] Exit");
    }


}
