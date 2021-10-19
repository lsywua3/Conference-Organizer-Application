package account.entities;


/**
 * A speaker of the conference who has a list of talks to give.
 */
public class Speaker extends User{

    /**
     * Constructs a new Speaker with the following parameters.
     * Calls the User constructor and passes in the type "Speaker".
     *
     * @param email    the Speaker's email.
     * @param password the account password
     * @param name     the name of the Speaker
     * @param id       the id of the Organizer
     */
    public Speaker(String email, String password, String name, int id) {
        super(email, password, name, "Speaker", id);
    }

}
