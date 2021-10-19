package controllers.messageSystem;

import entities.Messageable;
import gateways.IGateway;
import useCase.accountManager.AccountManager;
import useCase.messageManager.OrganizerMessageManager;

import java.util.InputMismatchException;

/**
 * Message system for organizer that take user inputs and perform message operations.
 */
public class OrganizerMessageSystem extends MessageSystem {

    private final OrganizerMessageManager mm;


    /**
     * Constructs a message system for an organizer user that allows that organizer to run messaging operations
     * including sending messages, viewing messages, viewing one message, replying to a message, making announcement
     * to all attendees users, and making announcement to all speakers users
     * Initializes OrganizerMessageManager mm, AccountManager accountManager, IGateway gate and IGateway userGate
     *
     * @param user           the id of the user that is using this system
     * @param accountManager the account manager that helps message managers to handle changes related to user accounts
     * @param gate           the messageGateway that helps reading and writing message data from/to database
     * @param userGate       the userGateway that helps accountManager to save message data associated
     *                       with specific users in database
     */

    public OrganizerMessageSystem(int user, AccountManager accountManager, IGateway gate, IGateway userGate) {
        super(accountManager, gate, userGate);
        mm = new OrganizerMessageManager((Messageable) accountManager.getUserById(user), gate);
    }

    /**
     * Takes an organizer's command and calls the corresponding messaging operation
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
                sendMessageToAllAttendees();
                break;
            case 6:
                sendMessageToAllSpeakers();
                break;
        }
    }

    /**
     * Runs the organizer messaging system.
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
                        "[5] Send message to all attendees\n" +
                        "[6] Send message to all speakers\n" +
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
     * Helper method that helps sendMessageToAllSpeaker() method and sendMessageToAllAttendees() method
     * Creates the functionality of sending a message that the organizer wants to send to all the users
     * of a given type
     *
     * @param receiverType the type of the users that receive this message, and this parameter should only
     *                     be either "Speaker" or "Attendee" (precondition)
     */
    private void sendMessageToAllTool(String receiverType) {
        System.out.println("Please enter the <content> of the message that you want to send. Enter [0] to go back");
        String content = sc.nextLine();
        boolean success;
        if (!content.equals("0")) {
            success = mm.sendAll(content, gate, accountManager.getAllUsers(), receiverType);
            if (success) {
                System.out.println("Your message has been successfully sent!");
                accountManager.save(userGate);
            } else {
                System.out.println("Failed to send this message, please try again with valid input");
            }

        }

    }

    /**
     * Sends a message that the organizer wants to send to all the attendees
     */
    public void sendMessageToAllAttendees() {
        sendMessageToAllTool("Attendee");
    }

    /**
     * Sends a message that the organizer wants to send to all the speakers
     */
    public void sendMessageToAllSpeakers() {
        sendMessageToAllTool("Speaker");
    }


}
