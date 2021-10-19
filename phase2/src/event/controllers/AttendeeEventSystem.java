package event.controllers;

import event.presenters.AttendeeEventSystemPresenter;
import gateways.IGateway;
import event.gateway.ScheduleHTMLDownloader;
import account.useCases.AccountManager;
import event.useCases.AttendeeEventManager;

import java.util.InputMismatchException;


/**
 * Event System for an attendee. User can perform operations related to events.
 */
public class AttendeeEventSystem extends EventSystem {
    private final AttendeeEventManager uem;
    private IGateway userGateway;
    private AttendeeEventSystemPresenter presenter = new AttendeeEventSystemPresenter();
    private ScheduleHTMLDownloader scheduleHTMLDownloader = new ScheduleHTMLDownloader();

    /**
     * Constructs event system for an attendee.
     *
     * @param currUser     int id of the current user.
     * @param acm          AccountManager.
     * @param eventGateway EventGateway for read and write.
     * @param userGateway  UserGateway for read and write.
     */
    public AttendeeEventSystem(int currUser, AccountManager acm, IGateway eventGateway, IGateway userGateway,
                               IGateway roomGateway) {
        super(currUser, acm, eventGateway, new AttendeeEventSystemPresenter(), roomGateway);
        uem = new AttendeeEventManager(acm.getUserById(currUser), rm);
        this.userGateway = userGateway;
    }

    /**
     * Takes user input of an event id to sign up the event.
     */
    public void signUpEvent() {
        boolean legalInput = false;
        getAllEvents();
        do {
            try {
                presenter.presentSignUpEventInstruction();
                int eventId = inputEventId();
                if (eventId == 0 || eventId == -1) {
                    return;
                }
                legalInput = true;
                boolean success = uem.signUpEvent(eventGateway, eb, eventId);
                presenter.presentSignUpEventResult(success, eventId);
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (!legalInput);
    }

    /**
     * Takes user input of an event id to cancel sign up.
     */
    public void cancelEvent() {
        boolean legalInput = false;
        presenter.presentViewMyScheduleInstruction();
        presenter.presentSchedule(uem.getMySchedule(eb));
        do {
            try {
                presenter.presentCancelEventInstruction();
                int eventId = inputEventId();
                if (eventId == 0 || eventId == -1) {
                    return;
                }
                legalInput = true;
                boolean success = uem.cancelEvent(eventGateway, eb, eventId);
                presenter.presentCancelEventResult(success, eventId);
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (!legalInput);
    }


    /**
     * Displays a schedule for the events the attendee has signed up for.
     * Takes user input to decide whether downloading the table of the schedule 
     * and outputting it as html, or exit.
     */
    public void getMySchedule() {
        presenter.presentViewMyScheduleInstruction();
        presenter.presentSchedule(uem.getMySchedule(eb));

        try {
            presenter.presentDownloadScheduleInstruction();
            int ans = s.nextInt();
            if (ans == 1) {
                scheduleHTMLDownloader.write(uem.getMySchedule(eb));
            }
        } catch (InputMismatchException e) {
            presenter.presentInputError();
        }
    }

    /**
     * Takes an command and make attendee event system operations.
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
                signUpEvent();
                break;
            case 5:
                cancelEvent();
                break;
            case 6:
                getMySchedule();
                break;
        }
    }

    /**
     * Runs the event system of an attendee.
     *
     * @return 0 if the program execute successfully.
     */
    @Override
    public int run() {
        int choice = -1;
        do {
            try {
                presenter.presentMenu();

                choice = s.nextInt();
                s.nextLine();
                if (choice >= 0 && choice <= 6) {
                    navigateCommand(choice);
                } else {
                    presenter.presentCommandError();
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (choice != 0);
        return 0;
    }
}
