package controllers.messageSystem;

import controllers.SystemRunnable;
import gateways.IGateway;
import presenters.MessageSystemPresenter;
import useCase.accountManager.AccountManager;
import useCase.messageManager.UserMessageManager;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Message system that take user inputs and perform message operations.
 */
public abstract class MessageSystem implements SystemRunnable {


    protected AccountManager accountManager;

    protected Scanner sc = new Scanner(System.in);

    protected MessageSystemPresenter presenter;


    protected IGateway gate;
    protected IGateway userGate;


    /**
     * Constructs a message system that allow users to run messaging operations
     * Initializes AccountManager accountManager, IGateway gate and IGateway userGate
     *
     * @param accountManager the account manager that helps message managers to handle changes related to user accounts
     * @param gate           the messageGateway that helps reading and writing message data from/to database
     * @param userGate       the userGateway that helps accountManager to save message data associated
     *                       with specific users in data file
     */
    public MessageSystem(AccountManager accountManager, IGateway gate, IGateway userGate) {
        this.gate = gate;
        this.userGate = userGate;
        this.accountManager = accountManager;
        presenter = new MessageSystemPresenter();
    }


    /**
     * Takes a user's command and calls the corresponding messaging operation
     *
     * @param command the command that the user wants to make (represented as a number)
     */
    public abstract void navigateCommand(int command);

    /**
     * Runs the messaging system.
     *
     * @return 0 if the program execute successfully.
     */
    public abstract int run();

    /**
     * Displays the user's all sent messages or all received messages of the user depending on the user's choice
     */
    public abstract void viewMessages();

    /**
     * Displays the preview of all the user's sent and received messages and asks the user to pick
     * one specific message from them to display that message information fully
     */
    public abstract void viewMessage();

    /**
     * Sends a message to a valid user that this user specifies
     */
    public abstract void sendMessage();

    /**
     * Displays the preview of all the user's received messages, asks the user to pick
     * one specific message from them to reply, and eventually sends a reply to that message
     */
    public abstract void replyMessage();

    /**
     * Helper method that helps viewMessages() method
     * Creates the functionality of displaying the user's all sent messages or all received messages
     * depending on the user's choice
     *
     * @param mm the user message manager of a specific user that provides information
     *           of the messages that needs to be viewed
     */
    protected void viewMessagesTool(UserMessageManager mm) {


        int choice;
        ArrayList<ArrayList<String>> messagesInfoList = new ArrayList<>();
        do {
            try {
                System.out.println("Which message option would you like to view?");
                System.out.println("[1] all received messages [2] all sent messages [0] exit");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (choice != 1 && choice != 2) {
                    System.out.println("This is an invalid input, please only enter [1] [2] or [0].");
                } else {

                    if (choice == 1) {
                        for (int messageId : mm.getReceivedMessage()) {
                            messagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
                        }
                        presenter.presentMessages(messagesInfoList);
                        messagesInfoList = new ArrayList<>();
                    } else {
                        for (int messageId : mm.getSentMessage()) {
                            messagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
                        }
                        presenter.presentMessages(messagesInfoList);
                        messagesInfoList = new ArrayList<>();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("This is an invalid input, please input an integer instead.");
                sc.next();
            }
        } while (true);

    }

    /**
     * Helper method that helps viewMessage() method
     * Creates the functionality of displaying the preview of all the user's sent and received messages
     * and asks the user to pick one specific message from them to display that message information fully
     *
     * @param mm the user message manager of a specific user that provides information
     *           of the message that needs to be viewed
     */
    protected void viewMessageTool(UserMessageManager mm) {

        int choice;

        ArrayList<ArrayList<String>> receivedMessagesInfoList = new ArrayList<>();
        ArrayList<ArrayList<String>> sentMessagesInfoList = new ArrayList<>();

        for (int messageId : mm.getReceivedMessage()) {
            receivedMessagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
        }

        for (int messageId : mm.getSentMessage()) {
            sentMessagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
        }


        presenter.presentMessagesPreview(receivedMessagesInfoList, sentMessagesInfoList);


        do {
            try {
                System.out.println("Please enter the <message id> of the message that you want to view. Enter [0] to " +
                        "exit");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (mm.getMessageById(choice) == null) {
                    System.out.println("This is an invalid message id, please try again");
                } else {
                    presenter.presentMessage(presenter.generatePresenterInfo(mm, accountManager, choice));

                }
            } catch (InputMismatchException e) {
                System.out.println("This is an invalid input, please input an integer instead.");
                sc.next();
            }
        } while (true);

    }

    /**
     * Helper method that helps sendMessage() method to send message
     * Creates the functionality of sending a message to a valid user that this user specifies
     *
     * @param mm the user message manager of a specific user that takes care of the backend of sending
     *           the specified message to the specified user if possible
     */
    protected void sendMessageTool(UserMessageManager mm) {
        String email;
        String content;
        boolean success;
        do {
            System.out.println("Please enter the <email> of the user that you want this message to be sent to. " +
                    "Enter [0] to go back");
            email = sc.nextLine();
            if (email.equals("0")) {
                return;
            } else if (accountManager.getUserByEmail(email) == null) {
                System.out.println("This is not a valid email address, please try again.");
            } else {
                System.out.println("Please enter the <content> of the message that you want to send. " +
                        "Enter [0] to go back");
                content = sc.nextLine();
                if (!content.equals("0")) {

                    success = mm.sendMessage(accountManager.getUserByEmail(email), content, gate);
                    if (!success) {
                        System.out.println("failed sending the message, please double check and try again.");
                    } else {
                        System.out.println("Your message has been successfully sent!");
                        accountManager.save(userGate);
                    }
                }
            }
        } while (true);


    }

    /**
     * Helper method that helps replyMessage() method
     * Creates the functionality of displaying the preview of all the user's received messages,
     * asking the user to pick one specific message from them to reply, and eventually sending
     * a reply to that message
     *
     * @param mm the user message manager of a specific user that takes care of the backend of sending
     *           the specified message to the specified user if possible and providing the information related
     *           to messages
     */
    protected void replyMessageTool(UserMessageManager mm) {
        int choice;
        String content;
        boolean success;
        System.out.println("These are all the messages that you have received");

        ArrayList<String> receivedInfoList;
        ArrayList<ArrayList<String>> receivedMessagesInfoList = new ArrayList<>();

        for (int messageId : mm.getReceivedMessage()) {
            receivedMessagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
        }


        presenter.presentMessagePreview(receivedMessagesInfoList, "received");

        do {
            try {
                System.out.println("Please enter the <message id> of the message that you want to reply. Enter" +
                        " [0] to exit");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (mm.getMessageById(choice) == null) {
                    System.out.println("This is an invalid input, please try again.");
                } else {
                    System.out.println("Please enter the <content> of the message that you want to reply. " +
                            "Enter [0] to go back");
                    content = sc.nextLine();
                    if (!content.equals("0")) {
                        success = mm.sendMessage(accountManager.getUserById(
                                Integer.parseInt(mm.getInfo(choice, "senderId"))), content, gate);
                        if (!success) {
                            System.out.println("failed replying the message, please double check and try again");
                        } else {
                            System.out.println("Your reply message has been successfully sent!");
                            accountManager.save(userGate);
                        }
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("This is an invalid input, please input an integer instead.");
                sc.next();
            }
        } while (true);

    }

}



