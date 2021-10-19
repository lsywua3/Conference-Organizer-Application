package controllers.eventSystems;

import gateways.IGateway;
import useCase.accountManager.AccountManager;
import useCase.eventManager.SpeakerEventManager;

import java.util.InputMismatchException;


/**
 * Event System for a speaker. User can perform operations related to events.
 */
public class SpeakerEventSystem extends EventSystem {
    private final SpeakerEventManager uem;

    /**
     * Constructs event system for a speaker.
     *
     * @param currUser int id of the current user.
     * @param acm      AccountManager.
     * @param gateway  gateway for reading and writing.
     */
    public SpeakerEventSystem(int currUser, AccountManager acm, IGateway gateway) {
        super(currUser, acm, gateway);
        uem = new SpeakerEventManager(acm.getUserById(currUser));
    }

    /**
     * Displays the information of the talks given by current user.
     */
    public void getMyTalks() {
        System.out.println("You are giving the following talks:");
        presenter.presentEvents(uem.getMyEventsInfoList(eb, acm));
    }

    /**
     * Displays a schedule of the talks given by the current user.
     */
    public void getMySchedule() {
        System.out.println("This is your talk schedule: ");
        presenter.presentSchedule(uem.getMySchedule(eb));
    }

    /**
     * Takes an command and make speaker event system operations.
     *
     * @param command an integer represents the operation.
     */
    @Override
    public void navigateCommand(int command) {
        switch (command) {
            case 0:
                break;
            case 1:
                getAllEvents();
                break;
            case 2:
                getAllSchedule();
                break;
            case 3:
                getEvent();
                break;
            case 4:
                getMyTalks();
                break;
            case 5:
                getMySchedule();
                break;
        }
    }

    /**
     * Runs the event system of a speaker.
     *
     * @return 0 if the program execute successfully.
     */
    @Override
    public int run() {
        int choice = -1;
        do {
            try {
                System.out.println("----------------\nEvent System Menu: ");
                System.out.println("" +
                        "[1] Get all events\n" +
                        "[2] Get all schedule\n" +
                        "[3] Get information for specific event\n" +
                        "[4] View my talks\n" +
                        "[5] View my schedule\n" +
                        "[0] Exit");
                choice = s.nextInt();
                s.nextLine();
                if (choice >= 0 && choice <= 5) {
                    navigateCommand(choice);
                } else {
                    System.out.println("Illegal command!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Illegal input!");
                s.next();
            }
        } while (choice != 0);
        return 0;
    }
}
