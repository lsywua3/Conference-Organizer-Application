package event.controllers;

import interfaces.SystemRunnable;
import gateways.IGateway;
import event.gateway.ScheduleHTMLDownloader;
import event.presenters.EventSystemPresenter;
import account.useCases.AccountManager;
import event.useCases.EventBoard;
import room.RoomManager;

import java.util.*;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * User can perform operations related to events.
 */
public abstract class EventSystem implements SystemRunnable {
    protected int currUser;

    protected Scanner s = new Scanner(System.in);

    protected EventBoard eb;
    protected AccountManager acm;

    private EventSystemPresenter presenter;
    protected IGateway eventGateway;
    protected ScheduleHTMLDownloader scheduleHTMLDownloader = new ScheduleHTMLDownloader();

    protected RoomManager rm;

    /**
     * Constructs a event system which runs event operations.
     *
     * @param currUser int id of the current user.
     * @param gateway  gateway for reading and writing.
     */
    public EventSystem(int currUser, AccountManager acm, IGateway gateway, EventSystemPresenter presenter,
                       IGateway roomGateway) {
        this.currUser = currUser;
        eventGateway = gateway;
        eb = new EventBoard(eventGateway);
        this.acm = acm;
        this.presenter = presenter;
        rm = new RoomManager(roomGateway, eb.getScheduleGenerator());
        rm.addObserver(eb);
        rm.addObserver(eb.getScheduleGenerator());
        rm.updateRooms();

    }

    /**
     * Takes user input for event id;
     * @return Integer id if the input is valid; 0 if input is 0; -1 if input is not valid or input id not found;
     */
    public int inputEventId(){
        try {
            int eventId = s.nextInt();
            s.nextLine();
            if (eventId == 0) return 0;
            if (eb.getEventById(eventId) == null) {
                presenter.presentEventNotFound();
                return -1;
            } else {
                return eventId;
            }
        } catch (InputMismatchException e) {
            presenter.presentInputError();
            s.next();
        }
        return -1;
    }

    /**
     * Takes user input of an event id and display the information of this event.
     */
    public void getEvent() {
        do {
            try {
                presenter.presentGetEventInstruction();
                int eventId = inputEventId();
                if (eventId == 0 || eventId == -1) {
                    return;
                }
                presenter.presentEvent(eb.getInfoList(eventId, acm));
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (true);
    }


    /**
     * Displays the information for all the events.
     */
    public void getAllEvents() {
        presenter.presentGetAllEventsInstruction();
        List<List<String>> eventInfo = new ArrayList<>();
        for (int i : eb.getAllEventsId()) {
            eventInfo.add(eb.getInfoList(i, acm));
        }
        presenter.presentEvents(eventInfo);
    }


    protected void displayAllSchedule(){
        presenter.presentGetAllScheduleInstruction();
        presenter.presentSchedule(eb.getAllSchedule());
    }


    /**
     * Displays a table of the schedule of the conference.
     * Takes user input to decide whether downloading the table of the schedule 
     * and outputting it as html, or exit.
     */
    public void getAllSchedule() {
        displayAllSchedule();

        try {
            presenter.presentDownloadScheduleInstruction();
            int ans = s.nextInt();
            s.nextLine();
            if (ans == 1) {
                scheduleHTMLDownloader.write(eb.getAllSchedule());
            }
        } catch (InputMismatchException e) {
            presenter.presentInputError();
        }
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
