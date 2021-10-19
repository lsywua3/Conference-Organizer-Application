package controllers.eventSystems;

import controllers.SystemRunnable;
import gateways.IGateway;
import presenters.EventSystemPresenter;
import useCase.accountManager.AccountManager;
import useCase.eventManager.EventBoard;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * User can perform operations related to events.
 */
public abstract class EventSystem implements SystemRunnable {
    protected int currUser;

    protected EventBoard eb;
    protected Scanner s = new Scanner(System.in);
    protected EventSystemPresenter presenter;
    protected IGateway eventGateway;
    protected AccountManager acm;

    /**
     * Constructs a event system which runs event operations.
     *
     * @param currUser int id of the current user.
     * @param gateway  gateway for reading and writing.
     */
    public EventSystem(int currUser, AccountManager acm, IGateway gateway) {
        this.currUser = currUser;
        eventGateway = gateway;
        eb = new EventBoard(eventGateway);
        this.acm = acm;
        presenter = new EventSystemPresenter();
    }

    /**
     * Takes user input of an event id and display the information of this event.
     */
    public void getEvent() {
        int input = -1;
        do {
            try {
                System.out.println("Enter <event id> to get the event info; enter [0] to exit.");
                input = s.nextInt();
                s.nextLine();
                if (input != 0) {
                    presenter.presentEvent(eb.getInfoList(input, acm));
                }
            } catch (InputMismatchException e) {
                System.out.println("Illegal Input!");
                s.next();
            }
        } while (input != 0);
    }


    /**
     * Displays the information for all the events.
     */
    public void getAllEvents() {
        System.out.println("These are all the events: ");
        ArrayList<ArrayList<String>> eventInfo = new ArrayList<>();
        for (int i : eb.getAllEventsId()) {
            eventInfo.add(eb.getInfoList(i, acm));
        }
        presenter.presentEvents(eventInfo);
    }


    /**
     * Displays a table of the schedule of the conference.
     */
    public void getAllSchedule() {
        System.out.println("This is the schedule of the conference: ");
        presenter.presentSchedule(eb.getAllSchedule());
    }

    /**
     * Takes a command and calls the corresponding operation.
     *
     * @param command an integer represents the operation.
     */
    public abstract void navigateCommand(int command);

    /**
     * Runs the event system.
     *
     * @return 0 if the program execute successfully.
     */
    public abstract int run();
}
