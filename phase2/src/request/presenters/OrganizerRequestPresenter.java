package request.presenters;

/**
 * Presenter for OrganizerRequestSystem.
 */
public class OrganizerRequestPresenter extends RequestSystemPresenter {
    /**
     * Display a menu for OrganizerRequestSystem.
     */
    public void presentMenu() {
        System.out.println("----------------\nRequest System Menu:");
        System.out.println("" +
                "[1] View all requests\n" +
                "[0] Exit");
    }

    /**
     * "Requests: "
     */
    public void presentViewRequestsInstruction() {
        System.out.println("Requests: ");
    }

    /**
     * "Please enter request id to view request; Enter [0] to exit;"
     */
    public void presentViewRequestInstruction() {
        System.out.println("Please enter <request id> to view request; Enter [0] to exit;");
    }

    /**
     * "[1] address request [2] pend request [0] back"
     */
    public void presentOperateRequestInstruction() {
        System.out.println("[1] address request [2] pend request [0] back");
    }

    /**
     * Display result of operation.
     *
     * @param success true iff the operation is successful.
     */
    public void presentOperateRequestResult(boolean success) {
        if (success) {
            System.out.println("Operation succeeded!");
        } else {
            System.out.println("Operation failed!");
        }
    }
}
