package account.entities;


/**
 * An organizer of the conference.
 */
public class Organizer extends User{

    /**
     * Constructs a new Organizer with the following parameters.
     * Calls the User constructor and passes in the type "Organizer".
     *
     * @param email    the organizer's email.
     * @param password the account password
     * @param name     the name of the Organizer
     * @param id       the id of the Organizer
     */
    public Organizer(String email, String password, String name, int id) {
        super(email, password, name, "Organizer", id);
    }

}
