package event.presenters;

public class AttendeeEventSystemPresenter extends EventSystemPresenter {
    @Override
    public void presentMenu() {
        System.out.println("----------------\nEvent System Menu: ");
        System.out.println("" +
                "[1] Get all events\n" +
                "[2] Get all schedule\n" +
                "[3] Get information for specific event\n" +
                "[4] Sign up for an event\n" +
                "[5] Cancel event\n" +
                "[6] View my schedule\n" +
                "[0] Exit");
    }

    public void presentSignUpEventInstruction() {
        System.out.println("Please enter <event id> to sign up; Enter [0] to exit; ");
    }

    public void presentSignUpEventResult(boolean success, int eventId) {
        if (success) {
            System.out.println("You have signed up for event #" + eventId + "!");
        } else {
            System.out.println("Sign Up failed!");
        }
    }

    public void presentCancelEventInstruction() {
        System.out.println("Please enter <event id> to cancel; Enter [0] to exit; ");
    }

    public void presentCancelEventResult(boolean success, int eventId) {
        if (success) {
            System.out.println("You have cancelled event #" + eventId + "!");
        } else {
            System.out.println("Cancelling failed!");
        }
    }

    public void presentViewMyScheduleInstruction(){
        System.out.println("My Schedule: ");
    }
}
