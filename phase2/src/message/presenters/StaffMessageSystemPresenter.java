package message.presenters;

public class StaffMessageSystemPresenter extends MessageSystemPresenter {


    @Override
    public void presentMenu() {
        System.out.println("----------------\nMessaging System Menu:");
        System.out.println("" +
                "[1] View all messages\n" +
                "[2] View one single message\n" +
                "[3] Send message to one organizer or staff or speaker\n" +
                "[4] Reply to message\n" +
                "[5] Mark message\n" +
                "[0] Exit");
    }
}
