package controllers.menuSystem;

import controllers.SystemRunnable;
import account.controllers.AccountSystem;
import controllers.eventSystems.AttendeeEventSystem;
import controllers.eventSystems.EventSystem;
import controllers.eventSystems.OrganizerEventSystem;
import controllers.eventSystems.SpeakerEventSystem;
import controllers.messageSystem.AttendeeMessageSystem;
import controllers.messageSystem.MessageSystem;
import controllers.messageSystem.OrganizerMessageSystem;
import controllers.messageSystem.SpeakerMessageSystem;
import gateways.EventGateway;
import gateways.MessageGateway;
import gateways.UserGateway;
import presenters.MainMenuPresenter;
import useCase.accountManager.AccountManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuSystem implements SystemRunnable {
    private final UserGateway userGateway = new UserGateway();
    private final EventGateway eventGateway = new EventGateway();
    private final MessageGateway messageGateway = new MessageGateway();

    private final AccountManager acm = new AccountManager(userGateway);

    private final AccountSystem accountSystem = new AccountSystem(userGateway, acm);
    private final MainMenuPresenter presenter = new MainMenuPresenter();
    private EventSystem eventSystem;
    private MessageSystem messageSystem;
    private int currUser = 0;


    /**
     * Build systems according to the type of the user.
     *
     * @param type a string ("Organizer", "Speaker", "Attendee") representing the type of the user.
     */
    public void buildSystems(String type) {
        switch (type.toLowerCase()) {
            case "organizer":
                eventSystem = new OrganizerEventSystem(currUser, acm, eventGateway, userGateway);
                messageSystem = new OrganizerMessageSystem(currUser, acm, messageGateway, userGateway);
                break;
            case "speaker":
                eventSystem = new SpeakerEventSystem(currUser, acm, eventGateway);
                messageSystem = new SpeakerMessageSystem(currUser, acm, messageGateway, userGateway, eventGateway);
                break;
            case "attendee":
                eventSystem = new AttendeeEventSystem(currUser, acm, eventGateway, userGateway);
                messageSystem = new AttendeeMessageSystem(currUser, acm, messageGateway, userGateway);
                break;
        }
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
        buildSystems(acm.getInfo(currUser, "type"));
        Scanner s = new Scanner(System.in);
        int choice = -1;
        do {
            presenter.presentMenu(acm.getInfo(currUser, "name"), acm.getInfo(currUser, "id"));
            try {
                choice = s.nextInt();
                s.nextLine();
                if (choice >= 0 && choice <= 2) {
                    navigateCommand(choice);
                } else {
                    System.out.println("Illegal command!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Illegal input!");
                s.next();
            }
        } while (choice != 0);
        currUser = 0;
        return 0;
    }
}
