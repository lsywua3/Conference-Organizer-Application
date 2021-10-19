package message.controllers;

import account.useCases.AccountManager;
import gateways.IGateway;
import message.presenters.OrganizerMessageSystemPresenter;
import message.useCases.OrganizerMessageManager;

import java.util.InputMismatchException;

public class OrganizerMessageSystem extends MessageSystem {

    private final OrganizerMessageManager mm;
    private final OrganizerMessageSystemPresenter presenter = new OrganizerMessageSystemPresenter();

    /**
     * Constructs a message system for an organizer user that allows that organizer to run messaging operations
     * including sending messages, viewing messages, viewing one message, replying to a message, making announcement
     * to all attendees users, and making announcement to all speakers users
     * Initializes OrganizerMessageManager mm, AccountManager accountManager, IGateway gate, IGateway userGate and
     * OrganizerMessageSystemPresenter presenter.
     *
     * @param user           the id of the user that is using this system
     * @param accountManager the account manager that helps message managers to handle changes related to user accounts
     * @param gate           the messageGateway that helps reading and writing message data from/to database
     * @param userGate       the userGateway that helps accountManager to save message data associated
     *                       with specific users in database
     */

    public OrganizerMessageSystem(int user, AccountManager accountManager, IGateway gate, IGateway userGate) {
        super(accountManager, gate, userGate);
        mm = new OrganizerMessageManager(accountManager.getUserById(user), gate);
    }

    /**
     * Displays the preview of all the user's sent and received messages and asks the user to pick
     * one specific message from them to display that message information fully
     */

    @Override
    public void viewMessage() {
        viewMessageTool(mm, presenter);
    }

    /**
     * Displays the user's all sent messages or all received messages of the user depending on the user's choice
     */
    @Override
    public void viewMessages() {
        viewMessagesTool(mm, presenter);
    }

    /**
     * Sends a message to a valid user that this user specifies
     */

    @Override
    public void sendMessage() {
        sendMessageTool(mm, presenter);
    }

    /**
     * Displays the preview of all the user's received messages, asks the user to pick
     * one specific message from them to reply, and eventually sends a reply to that message
     */
    @Override
    public void replyMessage() {
        replyMessageTool(mm, presenter);
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
        presenter.presentSendMessageToAllContentInstruction();
        String content = sc.nextLine();
        boolean success;
        if (!content.equals("0")) {
            success = mm.sendAll(content, gate, accountManager.getAllUsers(), receiverType);
            presenter.presentSendMessageToAllResult(success);
            if (success) {
                accountManager.save(userGate);
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

    public void senMessageToAllStaff() {
        sendMessageToAllTool("Speaker");
    }
    // Put these 3 send to all into one method ... Good design?


    /**
     * Allow user to mark status of message
     */
    @Override
    public void markMessage() {
        markMessageTool(mm, presenter);
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
                markMessage();
                break;
            case 6:
                sendMessageToAllAttendees();
                break;
            case 7:
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
                presenter.presentMenu();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 0 && choice <= 7) {
                    navigateCommand(choice);
                } else {
                    presenter.presentCommandError();
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                sc.next();
            }
        } while (choice != 0);
        return 0;
    }

}
