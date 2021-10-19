package controllers.eventSystems;

import account.controllers.AccountSystem;
import gateways.IGateway;
import gateways.UserGateway;
import useCase.accountManager.AccountManager;
import useCase.eventManager.EventAdmin;

import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Event System for an organizer. User can perform operations related to events.
 */
public class OrganizerEventSystem extends EventSystem {
    private final EventAdmin eventAdmin;
    private IGateway userGateway;

    /**
     * Constructs event system for an organizer.
     *
     * @param currUser     int id of the current user.
     * @param acm          AccountManager.
     * @param eventGateway EventGateway for read and write.
     * @param userGateway  UserGateway for read and write.
     */
    public OrganizerEventSystem(int currUser, AccountManager acm, IGateway eventGateway, IGateway userGateway) {
        super(currUser, acm, eventGateway);
        this.userGateway = userGateway;
        eventAdmin = new EventAdmin(eventGateway);
    }

    /**
     * Takes user inputs to add an event to the conference.
     *
     * @return True if the event is added; False if the event is not added.
     */
    public boolean addEvent() {
        boolean legalInput = false;
        getAllSchedule();
        do {
            try {
                System.out.println("All Speakers:");
                presenter.presentSpeakers(acm.getSpeakersIds());
                System.out.println("Please enter information for the event: ");
                System.out.print("<title>: ");
                System.out.print("<speaker id>: ");
                System.out.print("<room id>: ");
                System.out.print("<time>: ");
                System.out.println("[1] Confirm [0] Cancel");
//                s.nextLine();
                String title = s.nextLine();

                int speaker = s.nextInt();
                s.nextLine();

                int room = s.nextInt();
                s.nextLine();

                int time = s.nextInt();
                s.nextLine();
                legalInput = true;

                if (s.nextInt() == 0) {
                    s.nextLine();
                    return false;
                }
                boolean success = eventAdmin.addEvent(eventGateway, userGateway, acm, title, speaker,
                        room, time, 1, 2);

                if (success) {
                    System.out.println("Event has been successfully added!");
                    eb.reload(eventGateway);
                } else {
                    System.out.println("Event adding failed!");
                }
                return success;
            } catch (InputMismatchException e) {
                System.out.println("Illegal input!");
                s.next();
            }
        } while (!legalInput);
        return false;
    }

    /**
     * Takes the user input of an id of event and remove this event from the conference.
     *
     * @return True if the event is removed; False if the event is not removed.
     */
    public boolean removeEvent() {
        boolean legalInput = false;
        getAllEvents();
        do {
            try {
                System.out.println("Please enter <event id> of the event that you want to remove; Enter [0] to exit.");
                int eventId = s.nextInt();
                s.nextLine();
                legalInput = true;
                boolean success = eventAdmin.removeEvent(eventGateway, userGateway, acm, eventId);
                if (success) {
                    System.out.println("Successfully cancelled Event [" + eventId + "]!");
                    eb.reload(eventGateway);
                } else {
                    System.out.println("Event cancelling failed!");
                }
                return success;
            } catch (InputMismatchException e) {
                System.out.println("Illegal input!");
                s.next();
            }
        } while (!legalInput);
        return false;
    }

    /**
     * Takes user input to edit the information of an event.
     *
     * @return True if the edit is successful; False if not.
     */
    public boolean editEvent() {
        boolean legalInput = false;
        getAllEvents();
        do {
            try {
                System.out.println("Please enter <event id> for event that you want to edit: ");
                int eventId = s.nextInt();
                s.nextLine();
                if (eventAdmin.getEventById(eventId) == null) {
                    System.out.println("Event does not exist!");
                    return false;
                }
                System.out.println("Currently: ");
                presenter.presentEvent(eb.getInfoList(eventId, acm));
                System.out.println("--------");
                System.out.print("<title>: ");
                String title = s.nextLine();
                System.out.print("<speaker id>: ");
                int speaker = s.nextInt();
                s.nextLine();
                System.out.print("<room id>: ");
                int room = s.nextInt();
                s.nextLine();
                System.out.print("<time>: ");
                int time = s.nextInt();
                s.nextLine();
                legalInput = true;
                boolean success = eventAdmin.setEvent(eventGateway, userGateway, acm, eventId, title, speaker, room,
                        time, 1, 2);
                if (success) {
                    System.out.println("Event has been successfully edited!");
                    eb.reload(eventGateway);
                } else {
                    System.out.println("Event edit failed!");
                }
                return success;
            } catch (InputMismatchException e) {
                System.out.println("Illegal input!");
                s.next();
            }
        } while (!legalInput);
        return false;
    }

    /**
     * Takes user input to reschedule the time and room of an event.
     *
     * @return True if the reschedule is successful; False if not.
     */
    public boolean rescheduleEvent() {
        boolean legalInput = false;
        getAllSchedule();
        do {
            try {
//                getAllSchedule();
                System.out.println("Please enter <event id> for event that you want to reschedule: ");
                int eventId = s.nextInt();
                s.nextLine();
                if (eventAdmin.getEventById(eventId) == null) {
                    System.out.println("Event does not exist!");
                    return false;
                }
                System.out.println("Currently: ");
                presenter.presentEvent(eb.getInfoList(eventId, acm));
                System.out.println("--------");
                System.out.print("<room id>: ");
                int room = s.nextInt();
                s.nextLine();
                System.out.print("<time>: ");
                int time = s.nextInt();
                s.nextLine();
                legalInput = true;
                boolean success = eventAdmin.rescheduleEvent(eventGateway, eventId, room, time, 1);
                if (success) {
                    System.out.println("Event has been successfully rescheduled!");
                    eb.reload(eventGateway);
                } else {
                    System.out.println("Reschedule failed!");
                }
                return success;
            } catch (InputMismatchException e) {
                System.out.println("Illegal input!");
            }
        } while (!legalInput);
        return false;
    }

    public void createAccount(){
        Scanner sc = new Scanner(System.in);
        AccountSystem accountSystem = new AccountSystem(new UserGateway(), acm);
        String type;
        boolean registered = false;
        do {
            System.out.println("Please enter your type: ");
            type = sc.nextLine();
            if (type.equals("Speaker") || type.equals("Organizer") || type.equals("Attendee")){
                accountSystem.register(type);
                registered = true;
            } else {
                System.out.println("Invalid user type, please try again.");
            }
        } while (!registered);
    }

    /**
     * Takes an command and make organizer event system operations.
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
                addEvent();
                break;
            case 5:
                removeEvent();
                break;
            case 6:
                editEvent();
                break;
            case 7:
                rescheduleEvent();
                break;
            case 8:
                AccountSystem accountSystem = new AccountSystem(new UserGateway(), acm);
                accountSystem.register("Speaker");

        }
    }

    /**
     * Runs the event system of an organizer.
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
                        "[4] Create a new event\n" +
                        "[5] Cancel an event\n" +
                        "[6] Edit an event\n" +
                        "[7] Reschedule an event\n" +
                        "[8] Create an account\n" +
                        "[0] Exit");
                choice = s.nextInt();
                s.nextLine();
                if (choice >= 0 && choice <= 8) {
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
