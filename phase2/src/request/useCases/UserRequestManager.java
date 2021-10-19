package request.useCases;

import account.entities.User;
import gateways.IGateway;
import request.entities.UserRequest;

import java.util.List;

/**
 * UserRequestManager allows user to send request, view and remove their own requests.
 */
public class UserRequestManager {
    private final User user;
    private final UserRequestAdmin ura;

    public UserRequestManager(User user, IGateway g) {
        this.user = user;
        this.ura = new UserRequestAdmin(g);
    }

    /**
     * Get requests of user.
     *
     * @return a List of Integer of request id
     */
    public List<UserRequest> getMyRequests() {
        return ura.getRequestOfUser(user.getId());
    }

    /**
     * Get info lists of requests of user.
     *
     * @return InfoList of Strings of the requests of the user.
     */
    public List<List<String>> getMyRequestsInfo() {
        return ura.getAllRequestsInfo(getMyRequests());
    }

    /**
     * Send a request.
     *
     * @param g       UserRequestGateway.
     * @param message content of the request
     * @return true iff the request is sent.
     */
    public boolean sendUserRequest(IGateway g, String message) {
        UserRequest newRequest = new UserRequest(ura.generateId(), user.getId(), message);
        return ura.addUserRequest(g, newRequest);
    }

    /**
     * Cancel a request.
     *
     * @param g  UserRequestGateway.
     * @param id int id of the request to be cancelled.
     * @return true iff the request is cancelled.
     */
    public boolean cancelUserRequest(IGateway g, int id) {
        if (ura.getUserRequestById(id) == null || ura.getUserRequestById(id).getSenderId() != user.getId()) {
            return false;
        }
        return ura.removeUserRequest(g, id);
    }

    /**
     * Load data from gateway.
     *
     * @param g UserRequestGateway
     */
    public void load(IGateway g) {
        ura.load(g);
    }

    /**
     * Save changes.
     *
     * @param g UserRequestGateway
     */
    public void save(IGateway g) {
        ura.save(g);
    }
}


