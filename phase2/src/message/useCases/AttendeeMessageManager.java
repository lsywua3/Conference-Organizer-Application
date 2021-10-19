package message.useCases;

import account.entities.User;
import gateways.IGateway;

public class AttendeeMessageManager extends UserMessageManager {

    public AttendeeMessageManager(User user, IGateway g) {
        super(user, g);
    }

    @Override
    protected boolean checkAvailability(User u) {
        return u.getType().equals("Speaker") || u.getType().equals("Attendee");
    }


}
