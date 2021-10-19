package account.entities;

public class Staff extends User{

    /**
     * Constructs a new Staff with the following parameters.
     * Calls the User constructor and passes in the type "Staff".
     *
     * @param email    the staff's email.
     * @param password the account password
     * @param name     the name of the Staff
     * @param id       the id of the Staff
     */
    public Staff(String email, String password, String name, int id) {
        super(email, password, name, "Staff", id);
    }
}
