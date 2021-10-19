package menu;

import event.controllers.*;
import gateways.*;
import interfaces.SystemRunnable;
import account.controllers.AccountSystem;
import message.controllers.*;
import account.useCases.AccountManager;
import rate.RateSystem;
import request.controllers.RequestSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuSystem implements SystemRunnable {
    private final UserGateway userGateway = new UserGateway();
    private final EventGateway eventGateway = new EventGateway();
    private final RoomGateway roomGateway = new RoomGateway();
    private final MessageGateway messageGateway = new MessageGateway();
    private final UserRequestGateway requestGateway = new UserRequestGateway();
    private final RateGateway rateGateway = new RateGateway();

    private final AccountManager acm = new AccountManager(userGateway);

    private final AccountSystem accountSystem = new AccountSystem(userGateway, acm);
    private EventSystem eventSystem;
    private MessageSystem messageSystem;
    private RequestSystem requestSystem;
    private RateSystem rateSystem;

    private final MainMenuPresenter presenter = new MainMenuPresenter();

    private int currUser = 0;


    /**
     * Build systems according to the type of the user.
     *
     * @param type a string ("Organizer", "Speaker", "Attendee", "Staff") representing the type of the user.
     */
    public void buildSystems(String type) {
        SystemFactory sf = new SystemFactory(type, userGateway, eventGateway, roomGateway, messageGateway,
                                                requestGateway, rateGateway, acm);

        eventSystem = sf.buildEventSystem(currUser);
        messageSystem = sf.buildMessageSystem(currUser);
        requestSystem = sf.buildRequestSystem(currUser);
        rateSystem = sf.buildRateSystem();
    }


    /**
     * Takes an int command and runs the corresponding system.
     *
     * @param command an integer represents the operation.
     */
    @Override
    public void navigateCommand(int command) {
        switch (command) {
            case 0:
                break;
            case 1:
                eventSystem.run();
                break;
            case 2:
                messageSystem.run();
                break;
            case 3:
                requestSystem.run();
                break;
            case 4:
                rateSystem.run();
                break;
        }
    }

    /**
     * Runs the system by displaying the menu of operations and taking user input.
     * <p>
     * Runs the account system to login a user, and then displays menu of systems that the user can run.
     *
     * @return 0 if the program execute successfully.
     */
    @Override
    public int run() {
        presenter.presentWelcome();

        currUser = accountSystem.run();

        if (currUser == 0) {
            return 0;
        }

        buildSystems(acm.getInfo(currUser, "type"));
        Scanner s = new Scanner(System.in);
        int choice = -1;
        do {
            presenter.presentMenu(acm.getInfo(currUser, "name"), acm.getInfo(currUser, "id"));
            try {
                choice = s.nextInt();
                s.nextLine();
                if (choice >= 0 && choice <= 4) {
                    navigateCommand(choice);
                } else {
                    presenter.presentInputError();
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (choice != 0);
        currUser = 0;
        return 0;
    }
}
