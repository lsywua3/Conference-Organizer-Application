package event.presenters;

public class SpeakerEventSystemPresenter extends EventSystemPresenter {
    @Override
    public void presentMenu() {
        System.out.println("----------------\nEvent System Menu: ");
        System.out.println("" +
                "[1] Get all events\n" +
                "[2] Get all schedule\n" +
                "[3] Get information for specific event\n" +
                "[4] View my talks\n" +
                "[5] View my schedule\n" +
                "[0] Exit");
    }

    public void presentViewMyTalksInstruction(){
        System.out.println("My Talks:");
    }

    public void presentViewMyScheduleInstruction(){
        System.out.println("My Schedule: ");
    }
}
