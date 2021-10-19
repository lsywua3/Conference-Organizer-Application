package request.presenters;

/**
 * Presenter for UserRequestSystem.
 */
public class UserRequestSystemPresenter extends RequestSystemPresenter {

    /**
     * Display a menu for UserRequestSystem.
     */
    public void presentMenu() {
        System.out.println("----------------\nRequest System Menu:");
        System.out.println("" +
                "[1] View my requests\n" +
                "[0] Exit");
    }


    /**
     * "[1] send a new request [2] cancel a request [0] exit"
     */
    public void presentViewMyRequestsInstruction() {
        System.out.println("[1] send a new request [2] cancel a request [0] exit");
    }

    /**
     * "Send Request: "
     */
    public void presentSendRequestInstruction() {
        System.out.println("Send Request: ");
    }

    /**
     * Display result of sending request.
     *
     * @param success true iff the request is sent.
     */
    public void presentSendRequestResult(boolean success) {
        if (success) {
            System.out.println("Request sent!");
        } else {
            System.out.println("Failed to send request!");
        }
    }

    /**
     * "Please enter request id to cancel request; enter [0] to exit;"
     */
    public void presentCancelRequestInstruction() {
        System.out.println("Please enter <request id> to cancel request; enter [0] to exit;");
    }

    /**
     * Display result of cancelling request.
     *
     * @param success true iff the request is cancelled.
     */
    public void presentCancelRequestResult(boolean success) {
        if (success) {
            System.out.println("Request cancelled!");
        } else {
            System.out.println("Failed to cancel request!");
        }
    }
}
