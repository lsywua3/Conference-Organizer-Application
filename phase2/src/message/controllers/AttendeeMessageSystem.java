package message.controllers;

import account.useCases.AccountManager;
import gateways.IGateway;
import message.presenters.AttendeeMessageSystemPresenter;
import message.useCases.AttendeeMessageManager;

import java.util.InputMismatchException;

public class AttendeeMessageSystem extends MessageSystem {

    private final AttendeeMessageManager mm;
    private final AttendeeMessageSystemPresenter presenter;


    /**
     * Constructs a message system for an attendee user that allows that attendee to run messaging operations
     * including sending messages, viewing messages, viewing one message and replying to a message
     * Initializes AttendeeMessageManager mm, AccountManager accountManager, IGateway gate, IGateway userGate
     * and AttendeeMessageSystemPresenter presenter.
     *
     * @param user           the id of the user that is using this system
     * @param accountManager the account manager that helps message managers to handle changes related to user accounts
     * @param gate           the messageGateway that helps reading and writing message data from/to database
     * @param userGate       the userGateway that helps accountManager to save message data associated
     *                       with specific users in database
     */

    public AttendeeMessageSystem(int user, AccountManager accountManager, IGateway gate, IGateway userGate) {
        super(accountManager, gate, userGate);
        presenter = new AttendeeMessageSystemPresenter();
        mm = new AttendeeMessageManager(accountManager.getUserById(user), gate);
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
     * Allow user to mark status of message
     */
    @Override
    public void markMessage() {
        markMessageTool(mm, presenter);
    }


    /**
     * Takes an attendee user's command and calls the corresponding messaging operation
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
        }
    }

    /**
     * Runs the attendee messaging system.
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
                if (choice >= 0 && choice <= 5) {
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
