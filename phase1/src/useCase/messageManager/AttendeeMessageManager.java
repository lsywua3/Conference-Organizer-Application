package useCase.messageManager;

import entities.Messageable;
import gateways.IGateway;

public class AttendeeMessageManager extends UserMessageManager {

    public AttendeeMessageManager(Messageable user, IGateway g) {
        super(user, g);
    }
}
