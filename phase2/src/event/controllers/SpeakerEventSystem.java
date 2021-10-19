package event.controllers;

import event.presenters.SpeakerEventSystemPresenter;
import gateways.IGateway;
import event.gateway.ScheduleHTMLDownloader;
import account.useCases.AccountManager;
import event.useCases.SpeakerEventManager;

import java.util.InputMismatchException;


/**
 * Event System for a speaker. User can perform operations related to events.
 */
public class SpeakerEventSystem extends EventSystem {
    private final SpeakerEventManager uem;
    private SpeakerEventSystemPresenter presenter = new SpeakerEventSystemPresenter();
    private ScheduleHTMLDownloader scheduleHTMLDownloader = new ScheduleHTMLDownloader();

    /**
     * Constructs event system for a speaker.
     *
     * @param currUser int id of the current user.
     * @param acm      AccountManager.
     * @param gateway  gateway for reading and writing.
     */
    public SpeakerEventSystem(int currUser, AccountManager acm, IGateway gateway, IGateway roomGateway) {
        super(currUser, acm, gateway, new SpeakerEventSystemPresenter(), roomGateway);
        uem = new SpeakerEventManager(acm.getUserById(currUser), rm);
    }

    /**
     * Displays the information of the talks given by current user.
     */
    public void getMyTalks() {
        presenter.presentViewMyTalksInstruction();
        presenter.presentEvents(uem.getMyEventsInfoList(eb, acm));
    }

    /**
     * Displays a schedule of the talks given by the current user.
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
                presenter.presentMenu();
                choice = s.nextInt();
                s.nextLine();
                if (choice >= 0 && choice <= 5) {
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
