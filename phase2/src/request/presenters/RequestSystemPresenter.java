package request.presenters;

import java.util.List;

/**
 * Presenter for Request Systems.
 */
public abstract class RequestSystemPresenter {
    public abstract void presentMenu();

    /**
     * Display a UserRequest in full.
     *
     * @param info List of String in format of [id, senderId, message, status].
     */
    public void presentRequest(List<String> info) {
        if (info == null) {
            System.out.println("Request Not found!");
            return;
        }
        String id = info.get(0);
        String senderId = info.get(1);
        String message = info.get(2);
        String status = info.get(3);
        System.out.println("Request [" + id + "] " + status.toUpperCase() + "\n" +
                "\tFrom #" + senderId + ":\n" +
                "\t" + message);
    }

    /**
     * Display the preview of a UserRequest.
     *
     * @param info List of String in format of [id, senderId, message, status].
     */
    public void presentRequestPreview(List<String> info) {
        if (info == null) {
            System.out.println("Request Not found!");
            return;
        }
        String id = info.get(0);
        String senderId = info.get(1);
        String message = info.get(2);
        message = message.length() > 20 ? message.substring(0, 20) : message;
        String status = info.get(3);
        System.out.println("[" + id + "] " + message + "...  <" + status.toUpperCase() + ">");
    }


    /**
     * Display a List of UserRequests
     *
     * @param requestsInfo a List of UserRequest info.
     */
    public void presentRequests(List<List<String>> requestsInfo) {
        for (List<String> info : requestsInfo) {
            presentRequestPreview(info);
        }
        presentLine();
    }

    /**
     * Display an illegal input message.
     * "Illegal input!"
     */
    public void presentInputError() {
        System.out.println("Illegal input!");
    }


    /**
     * Display an illegal command message.
     * "Illegal command!"
     */
    public void presentCommandError() {
        System.out.println("Illegal command!");
    }

    public void presentRequestNotFound() {
        System.out.println("Request Not Found!");
    }

    /**
     * Dsiplay a confirm message.
     */
    public void presentConfirm() {
        System.out.println("[1] Confirm [0] Cancel");
    }

    /**
     * Display a separation line;
     */
    public void presentLine() {
        System.out.println("--------");
    }
}
