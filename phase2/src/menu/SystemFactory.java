package menu;

import account.useCases.AccountManager;
import event.controllers.*;
import gateways.*;
import message.controllers.*;
import rate.RateSystem;
import request.controllers.OrganizerRequestSystem;
import request.controllers.RequestSystem;
import request.controllers.UserRequestSystem;

/**
 * A factory class that build systems for the MenuSystem according to userType.
 */
public class SystemFactory {
    private String userType;

    private IGateway userGateway;
    private IGateway eventGateway;
    private IGateway roomGateway;
    private IGateway messageGateway;
    private IGateway requestGateway;
    private RateGateway rateGateway;
    private AccountManager acm;

    /**
     * Constructor.
     * @param userType String type of user.
     */
    public SystemFactory (String userType) {
        this.userType = userType;
    }

    /**
     * Constructor.
     * @param userType userType
     * @param userGateway userGateway
     * @param eventGateway eventGateway
     * @param roomGateway roomGateway
     * @param messageGateway messageGateway
     * @param requestGateway requestGateway
     * @param rateGateway rateGateway
     * @param acm AccountManager
     */
    public SystemFactory(String userType, IGateway userGateway, IGateway eventGateway, IGateway roomGateway,
                         IGateway messageGateway, IGateway requestGateway, RateGateway rateGateway,
                         AccountManager acm) {
        this.userType = userType;
        this.userGateway = userGateway;
        this.eventGateway = eventGateway;
        this.roomGateway = roomGateway;
        this.messageGateway = messageGateway;
        this.requestGateway = requestGateway;
        this.rateGateway = rateGateway;
        this.acm = acm;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setUserGateway(IGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void setEventGateway(IGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    public void setRoomGateway(IGateway roomGateway) {
        this.roomGateway = roomGateway;
    }

    public void setMessageGateway(IGateway messageGateway) {
        this.messageGateway = messageGateway;
    }

    public void setRequestGateway(IGateway requestGateway) {
        this.requestGateway = requestGateway;
    }

    public void setRateGateway(RateGateway rateGateway) {
        this.rateGateway = rateGateway;
    }

    public void setAcm(AccountManager acm) {
        this.acm = acm;
    }

    public EventSystem buildEventSystem(int currUser){
        switch (userType.toLowerCase()) {
            case "organizer":
                return new OrganizerEventSystem(currUser, acm, eventGateway, userGateway, roomGateway);
            case "speaker":
                return new SpeakerEventSystem(currUser, acm, eventGateway, roomGateway);
            case "attendee":
                return new AttendeeEventSystem(currUser, acm, eventGateway, userGateway, roomGateway);
            case "staff":
                return new StaffRoomSystem(currUser, acm, eventGateway, roomGateway);
        }
        return null;
    }


    public MessageSystem buildMessageSystem(int currUser){
        switch (userType.toLowerCase()) {
            case "organizer":
                return new OrganizerMessageSystem(currUser, acm, messageGateway, userGateway);
            case "speaker":
                return new SpeakerMessageSystem(currUser, acm, messageGateway, userGateway, eventGateway,
                        roomGateway);
            case "attendee":
                return new AttendeeMessageSystem(currUser, acm, messageGateway, userGateway);
            case "staff":
                return new StaffMessageSystem(currUser, acm, messageGateway, userGateway);
        }
        return null;
    }

    public RequestSystem buildRequestSystem(int currUser){
        switch (userType.toLowerCase()) {
            case "organizer":
                return new OrganizerRequestSystem(currUser, acm, requestGateway);
            case "speaker":
            case "attendee":
            case "staff":
                return new UserRequestSystem(currUser, acm, requestGateway);
        }
        return null;
    }

    public RateSystem buildRateSystem() {
        return new RateSystem(acm, rateGateway);
    }
}


