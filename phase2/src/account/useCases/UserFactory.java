package account.useCases;

import account.entities.*;

public class UserFactory {

    public User getUser(String userType, int userId, String name, String email, String password) {

        if (userType.equalsIgnoreCase("ATTENDEE")) {
            return new Attendee(email, password, name, userId);
        } else if (userType.equalsIgnoreCase("ORGANIZER")) {
            return new Organizer(email, password, name, userId);
        } else if (userType.equalsIgnoreCase("SPEAKER")) {
            return new Speaker(email, password, name, userId);
        } else if (userType.equalsIgnoreCase("STAFF")) {
            return new Staff(email, password, name, userId);
        }
        // controller should not pass in unexpected type, but if so, return null.
        return null;
    }
}
