package entities;

public abstract class User implements java.io.Serializable {
    //id is used for backend purposes, storing users using id
    //finding user by id. id is unique.
    private final int id;
    //Email is used for login. User indicates message receiver using email
    private final String email;
    private final String type;
    private String password;
    //Name is used to present the real name of the User to the UI.
    //Helps with clarity and understandability.
    private String name;

    /**
     * Constructs a new User with the following parameters
     * Called by subclasses, which passes in the type of the User.
     * Currently, the three types are: "Attendee", "Organizer", "Speaker"
     *
     * @param email    the Attendee's email.
     * @param password the account password
     * @param name     the name of the User
     * @param type     the type of the User (Attendee, Organizer, Speaker)
     * @param id       the unique id of the User, starts from 1000.
     */
    public User(String email, String password, String name, String type, int id) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
    }

    /**
     * @return the int of the user's id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the String of the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the String of the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the String of the user's type
     */
    public String getType() {
        return type;
    }

    /**
     * @param input the input password to be checked
     * @return the boolean result if the input matches user password
     */
    public boolean checkPassword(String input) {
        return input.equals(password);
    }

    private String encodePassword(String pwd) {
        return pwd;
    }

    private String decodePassword(String pwd) {
        return pwd;
    }
}
