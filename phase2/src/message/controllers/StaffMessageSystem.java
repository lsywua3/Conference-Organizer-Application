package message.controllers;

import account.useCases.AccountManager;
import gateways.IGateway;
import message.presenters.StaffMessageSystemPresenter;
import message.useCases.StaffMessageManager;

import java.util.InputMismatchException;

public class StaffMessageSystem extends MessageSystem {

    private final StaffMessageManager mm;
    private final StaffMessageSystemPresenter presenter;

    /**
     * Creates a messaging system for staff
     *
     * @param user           The id of the user
     * @param accountManager The account manager that handles user account related operations
     * @param messageGate    The message gateway that handles the data operations related to messages
     * @param userGate       The user gateway that handles the data operations related to users
     */
    public StaffMessageSystem(int user, AccountManager accountManager, IGateway messageGate, IGateway userGate) {
        super(accountManager, messageGate, userGate);
        presenter = new StaffMessageSystemPresenter();
        mm = new StaffMessageManager(accountManager.getUserById(user), gate);
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
        // Commented this functionality for now.
        //presenter.presentAllEventInfo();
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
