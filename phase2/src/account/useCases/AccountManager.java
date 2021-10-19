package account.useCases;

import account.entities.User;
import gateways.IGateway;
import interfaces.InfoAccessible;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all the users and their related methods.
 */
public class AccountManager implements InfoAccessible {
    private final List<User> allUsers;
    private final UserFactory factory;

    /**
     * Constructs a new AccountManager with IGateway gate.
     *
     * @param gate the gateway used to get all the users.
     */
    public AccountManager(IGateway gate) {
        allUsers = (ArrayList<User>) gate.read();
        this.factory = new UserFactory();
    }

    /**
     * Verify that the user can login.
     *
     * @param email    user's email
     * @param password user's account password.
     * @return true if the user can login, false otherwise.
     */
    public boolean verifyLogin(String email, String password) {
        for (User user : allUsers) {
            //Comparing the email and password of the current user to the input email and password.
            if (user.getEmail().equals(email) && user.checkPassword(password)) {
                //return true if the current user's email and password match the input.
                return true;
            }
        }
        return false;
    }

    /**
     * Create a new user in the corresponding type if the input email is not an
     * existing email that is already used.
     * <p>
     * Call the UserFactory to create the new user.
     *
     * @param name     user's name
     * @param type     type of the user
     * @param email    user's email
     * @param password user's account password.
     * @param gate     gateway for reading and writing.
     * @return true if account is created, false otherwise.
     */
    public boolean createAccount(String name, String type, String email, String password, IGateway gate) {
        if (checkEmailConflict(email)) {
            return false;
        }
        /*
        Use the factory to create a new user based on the parameters. Id is determined by adding 1 to the last user.
         */
        int userId = allUsers.get(allUsers.size() - 1).getId() + 1;
        User newUser = this.factory.getUser(type, userId, name, email, password);
        if (newUser == null) {
            return false; //passed in user type is invalid, check controller to fix the problem.
        }
        allUsers.add(newUser);
        //update gateway if we created a new user.
        gate.write(allUsers);
        return true;
    }

    /**
     * Check if the input email is an existing email that is already used, if yes,
     * we cannot create an account using this email.
     *
     * @param email email that is being checked
     * @return true if there is a conflict, and false otherwise.
     */
    private boolean checkEmailConflict(String email) {
        for (User user : allUsers) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a user based on its id.
     *
     * @param id user's id.
     * @return the user with id <id>, if unable to find a user, return null.
     */
    public User getUserById(int id) {
        for (User user : allUsers) {
            // Return the current user if it has the input id as its id.
            if (user.getId() == id) {
                return user;
            }
        }
        // Return null otherwise.
        return null;
    }

    /**
     * Get a user based on its email.
     *
     * @param email user's email
     * @return the user with email <email>, if unable to find a user, return null.
     */
    public User getUserByEmail(String email) {
        for (User user : allUsers) {
            // Return the current user if it has the input email as its email.
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        // Return null otherwise.
        return null;
    }

    /**
     * Get all users.
     *
     * @return <allUsers>.
     */
    public List<User> getAllUsers() {
        return allUsers;
    }

    /**
     * get user's id given user.
     *
     * @param user An user
     * @return user's id given user.
     */
    public int getUserId(User user) {
        return user.getId();
    }

    /**
     * get all information of a user.
     *
     * @param id user's id.
     * @return all information of the user with id <id>.
     */
    public List<String> getInfoList(int id) {
        User user = getUserById(id);
        List<String> info = new ArrayList<>();
        info.add(Integer.toString(user.getId()));
        info.add(user.getName());
        info.add(user.getEmail());
        info.add(user.getType());
        return info;
    }

    /**
     * Get user's specific info.
     *
     * @param id     user's id.
     * @param option a string that could be "id", "name", "type", or "email".
     * @return the user's <option> of the user with id <id>.
     */
    public String getInfo(int id, String option) {
//        System.out.println("Getting info for " +id);
        User user = getUserById(id);
        if (user == null) {
            return "null";
        }
        switch (option) {
            case "id":
                return Integer.toString(user.getId());
            case "name":
                return user.getName();
            case "type":
                return user.getType();
            case "email":
                return user.getEmail();
        }
        return "";
    }

    /**
     * Get a list of user based on their ids.
     *
     * @param userIdList a list of user ids.
     * @return a list of users with ids <ids>, with the assumption that all ids are valid.
     */
    public List<User> getUsersByIds(List<Integer> userIdList) {
        User user1;
        List<User> users = new ArrayList<>();
        for (Integer id : userIdList) {
            user1 = getUserById(id);
            users.add(user1);
        }
        return users;
    }

    /**
     * Get a all speaker ids.
     *
     * @return a list of ids of all users who are "Speaker".
     */
    public List<Integer> getSpeakersIds() {
        List<Integer> ids = new ArrayList<>();
        for (User user1 : allUsers) {
            if (user1.getType().equals("Speaker")) {
                ids.add(user1.getId());
            }
        }
        return ids;
    }

    /**
     * Save changes.
     *
     * @param gateway gateway for reading and writing.
     */
    public void save(IGateway gateway) {
        gateway.write(allUsers);
    }

    /**
     * Check if an id represents a speaker.
     *
     * @param id int id.
     * @return True iff the id represents a speaker.
     */
    public boolean isSpeaker(int id) {
        User user = getUserById(id);
        if (user == null) return false;
        return user.getType().equals("Speaker");
    }
}