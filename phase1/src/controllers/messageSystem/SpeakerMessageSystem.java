package controllers.messageSystem;

import entities.Messageable;
import gateways.IGateway;
import useCase.accountManager.AccountManager;
import useCase.eventManager.EventBoard;
import useCase.eventManager.SpeakerEventManager;
import useCase.messageManager.SpeakerMessageManager;

import java.util.InputMismatchException;

/**
 * Message system for speaker that take user inputs and perform message operations.
 */
public class SpeakerMessageSystem extends MessageSystem {

    private final SpeakerMessageManager mm;
    private final SpeakerEventManager sem;
    private final EventBoard eb;

    /**
     * Constructs a message system for a speaker user that allows that speaker to run messaging operations
     * including sending messages, viewing messages, viewing one message, replying to a message, sending a message
     * to all the attendees of one event that this speaker is holding, sending a message to all the attendees of
     * all the events that this speaker is holding.
     * Initializes SpeakerMessageManager mm, SpeakerEventManager sem, EventBoard eb, AccountManager accountManager,
     * IGateway gate and IGateway userGate
     *
     * @param user           the id of the user that is using this system
     * @param accountManager the account manager that helps message managers to handle changes related to user accounts
     * @param gate           the messageGateway that helps reading and writing message data from/to database
     * @param userGate       the userGateway that helps accountManager to save message data associated
     *                       with specific users in database
     * @param eventGate      the eventGateway that helps creating the event board in order to get information about events
     */
    public SpeakerMessageSystem(int user, AccountManager accountManager, IGateway gate, IGateway userGate,
                                IGateway eventGate) {
        super(accountManager, gate, userGate);
        mm = new SpeakerMessageManager((Messageable) accountManager.getUserById(user), gate);
        sem = new SpeakerEventManager(accountManager.getUserById(user));
        eb = new EventBoard(eventGate);
    }


    /**
     * Takes a speaker's command and calls the corresponding messaging operation
     *
     * @param command the command that the user wants to make (represented as a number)
     */
    @Override
    public void navigateCommand(int command) {
        switch (command) {
            case 0:
                break;
            case 1:
                viewMessages();
                break;
            case 2:
                viewMessage();
                break;
            case 3:
                sendMessage();
                break;
            case 4:
                replyMessage();
                break;
            case 5:
                announceMessageToEvent();
                break;
            case 6:
                announceMessageToAllEvents();
                break;
        }
    }

    /**
     * Runs the speaker messaging system.
     *
     * @return 0 if the program execute successfully.
     */
    @Override
    public int run() {
        int choice = -1;
        do {
            try {
                System.out.println("----------------\nMessaging System Menu:");
                System.out.println("" +
                        "[1] View all messages\n" +
                        "[2] View one single message\n" +
                        "[3] Send message to one attendee or speaker\n" +
                        "[4] Reply to message\n" +
                        "[5] Make announcement to all attendees in one of your event\n" +
                        "[6] Make announcement to all attendees in all of your events\n" +
                        "[0] Exit");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 0 && choice <= 6) {
                    navigateCommand(choice);
                } else {
                    System.out.println("Illegal command!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Illegal input!");
                sc.next();
            }
        } while (choice != 0);
        return 0;
    }

    /**
     * Displays the user's all sent messages or all received messages of the user depending on the user's choice
     */
    @Override
    public void viewMessages() {
        viewMessagesTool(mm);
    }

    /**
     * Displays the preview of all the user's sent and received messages and asks the user to pick
     * one specific message from them to display that message information fully
     */
    @Override
    public void viewMessage() {
        viewMessageTool(mm);
    }

    /**
     * Sends a message to a valid user that this user specifies
     */
    @Override
    public void sendMessage() {
        sendMessageTool(mm);
    }

    /**
     * Displays the preview of all the user's received messages, asks the user to pick
     * one specific message from them to reply, and eventually sends a reply to that message
     */
    @Override
    public void replyMessage() {
        replyMessageTool(mm);
    }


    /**
     * Sends a message that the speaker wants to send to all the attendees of one specified event
     * that the speaker holds
     */
    public void announceMessageToEvent() {

        int choice;
        String content;
        boolean success;
        do {
            try {
                System.out.println("Please enter the <event id> of the event that you want to make announcement to. " +
                        "Enter [0] to go back");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (!sem.getMyEventList().contains(choice)) {
                    System.out.println("Failed to send to this event, please try again");
                } else {
                    System.out.println("Please enter the <content> of the message that you want to send. " +
                            "Enter [0] to go back");
                    content = sc.nextLine();
                    if (!content.equals("0")) {

                        success = mm.sendAll(content, gate,
                                accountManager.getUsersByIds(sem.getAttendees(eb.getEventById(choice))));

                        if (!success) {
                            System.out.println("Failed to announce this message to attendees");
                        } else {
                            System.out.println("Successfully sent announcement message to all the attendees in this " +
                                    "event!");
                            accountManager.save(userGate);
                        }
                    } else {
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("This is an invalid input, please input an integer instead.");
                sc.next();
            }
        } while (true);


    }


    /**
     * Sends a message that the speaker wants to send to all the attendees of all the events
     * that the speaker holds
     */
    public void announceMessageToAllEvents() {
        System.out.println("Please enter the <content> of the message that you want to send. Enter [0] to go back");
        String content = sc.nextLine();
        boolean success;
        if (!content.equals("0")) {

            success = mm.sendAll(content, gate, accountManager.getUsersByIds(sem.getAttendees(
                    eb.getAllEventsById(sem.getMyEventList()))));
            if (!success) {
                System.out.println("Failed to announce this message, please try again");
            } else {
                System.out.println("Successfully announced this message to all your event attendees!");
                accountManager.save(userGate);
            }
        }
    }

}
