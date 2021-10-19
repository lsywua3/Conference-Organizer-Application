package request.useCases;

import gateways.IGateway;
import interfaces.InfoAccessible;
import request.entities.UserRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * UserRequestAdmin allows viewing, status-changing, and adding/removing of requests.
 */
public class UserRequestAdmin implements InfoAccessible {
    private List<UserRequest> userRequests;

    public UserRequestAdmin(IGateway g) {
        load(g);
    }

    /**
     * Return the UserRequest object specified by id.
     *
     * @param id int id of the UserRequest.
     * @return UserRequest with the given id.
     */
    public UserRequest getUserRequestById(int id) {
        for (UserRequest r : userRequests) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    /**
     * Get a List of userRequests send by the given user.
     *
     * @param userId id of the user.
     * @return a List of userRequests.
     */
    public List<UserRequest> getRequestOfUser(int userId) {
        List<UserRequest> list = new ArrayList<>();
        for (UserRequest r : userRequests) {
            if (r.getSenderId() == userId) {
                list.add(r);
            }
        }
        return list;
    }

    public List<UserRequest> getAllRequests() {
        return userRequests;
    }

    /**
     * Add a userRequest to the data.
     *
     * @param g UserRequestGateway
     * @param r UserRequest
     * @return true if the request is added.
     */
    protected boolean addUserRequest(IGateway g, UserRequest r) {
        if (getUserRequestById(r.getId()) != null) {
            return false;
        }
        userRequests.add(r);
        save(g);
        return true;
    }

    /**
     * Remove a userRequest from the data.
     *
     * @param g  UserRequestGateway
     * @param id id of the request
     * @return true if the request is removed.
     */
    protected boolean removeUserRequest(IGateway g, int id) {
        UserRequest r = getUserRequestById(id);
        if (r == null) {
            return false;
        }
        userRequests.remove(r);
        save(g);
        return true;
    }

    /**
     * Set the status of a userRequest to "addressed".
     *
     * @param g  UserRequestGateway
     * @param id int id of the request
     * @return true if the request can be addressed.
     */
    public boolean addressUserRequest(IGateway g, int id) {
        UserRequest r = getUserRequestById(id);
        if (r == null) {
            return false;
        }
        r.setAddressed();
        save(g);
        return true;
    }

    /**
     * Set the status of a userRequest to "pending".
     *
     * @param g  UserRequestGateway
     * @param id int id of the request
     * @return true if the request can be pending.
     */
    public boolean pendingUserRequest(IGateway g, int id) {
        UserRequest r = getUserRequestById(id);
        if (r == null) {
            return false;
        }
        r.setPending();
        save(g);
        return true;
    }

    /**
     * Generate a new id for a userRequest.
     *
     * @return a valid new id.
     */
    public int generateId() {
        if (userRequests.size() == 0) {
            return 100;
        }
        return userRequests.get(userRequests.size() - 1).getId() + 1;
    }


    /**
     * Get a info list of the given id.
     *
     * @param id A UserRequest's id.
     * @return a list in format of [id, senderId, message, status]
     */
    @Override
    public ArrayList<String> getInfoList(int id) {
        ArrayList<String> info = new ArrayList<>();
        UserRequest r = getUserRequestById(id);
        if (r == null) {
            return null;
        }
        info.add(Integer.toString(r.getId()));
        info.add(Integer.toString(r.getSenderId()));
        info.add(r.getMessage());
        info.add(r.getStatus());
        return info;
    }

    @Override
    public String getInfo(int id, String option) {
        ArrayList<String> info = new ArrayList<>();
        UserRequest r = getUserRequestById(id);
        if (r == null) {
            return "";
        }
        switch (option) {
            case "id":
                return Integer.toString(r.getId());
            case "senderId":
                return Integer.toString(r.getSenderId());
            case "message":
                return r.getMessage();
            case "status":
                return r.getStatus();
        }
        return "";
    }

    /**
     * Get a list containing the infoList of all userRequests.
     *
     * @return a list containing all infoList of all userRequests.
     */
    public List<List<String>> getAllRequestsInfo() {
        return getAllRequestsInfo(getAllRequests());
    }

    /**
     * Get a list containing the infoList of all given requests.
     *
     * @param requests A List of userRequests
     * @return a list containing the infoList of the given requests.
     */
    public List<List<String>> getAllRequestsInfo(List<UserRequest> requests) {
        List<List<String>> infoList = new ArrayList<>();
        for (UserRequest r : requests) {
            infoList.add(getInfoList(r.getId()));
        }
        return infoList;
    }

    /**
     * Load data from gateway.
     *
     * @param g UserRequestGateway
     */
    public void load(IGateway g) {
        userRequests = (ArrayList<UserRequest>) g.read();
    }

    /**
     * Save changes.
     *
     * @param g UserRequestGateway
     */
    public void save(IGateway g) {
        g.write(userRequests);
    }

}
