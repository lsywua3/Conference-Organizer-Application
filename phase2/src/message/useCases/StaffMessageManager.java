package message.useCases;

import account.entities.User;
import gateways.IGateway;

public class StaffMessageManager extends UserMessageManager {

    public StaffMessageManager(User u, IGateway g) {
        super(u, g);
    }

    @Override
    protected boolean checkAvailability(User u) {
        return u.getType().equals("Organizer") || u.getType().equals("Speaker") || u.getType().equals("Staff");
    }
}
