package account.entities;


/**
 * An attendee of the conference who can attend events.
 */
public class Attendee extends User{

    /**
     * Constructs a new Attendee with the following parameters.
     * Calls the User constructor and passes in the type "Attendee".
     *
     * @param email    the Attendee's email.
     * @param password the account password
     * @param name     the name of the attendee
     * @param id       the id of the Organizer
     */
    public Attendee(String email, String password, String name, int id) {
        super(email, password, name, "Attendee", id);

    }

}
