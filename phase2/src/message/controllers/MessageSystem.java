package message.controllers;

import account.useCases.AccountManager;
import gateways.IGateway;
import interfaces.SystemRunnable;
import message.presenters.MessageSystemPresenter;
import message.useCases.UserMessageManager;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Message system that take user inputs and perform message operations.
 */
public abstract class MessageSystem implements SystemRunnable {


    protected AccountManager accountManager;
    protected Scanner sc = new Scanner(System.in);
    protected IGateway gate;
    protected IGateway userGate;


    /**
     * Constructs a message system that allow users to run messaging operations
     * Initializes AccountManager accountManager, IGateway gate and IGateway userGate
     *
     * @param accountManager the account manager that helps message managers to handle changes related to user accounts
     * @param gate           the messageGateway that helps reading and writing message data from/to database
     * @param userGate       the userGateway that helps accountManager to save message data associated
     *                       with specific users in database
     */
    public MessageSystem(AccountManager accountManager, IGateway gate, IGateway userGate) {
        this.gate = gate;
        this.userGate = userGate;
        this.accountManager = accountManager;
    }


    /**
     * Helper method that helps viewMessage() method
     * Creates the functionality of displaying the preview of all the user's sent and received messages
     * and asks the user to pick one specific message from them to display that message information fully
     *
     * @param mm        the user message manager of a specific user that provides information
     *                  of the message that needs to be viewed
     * @param presenter the presenter that presents the contents that need to be displayed in this method
     */
    protected void viewMessageTool(UserMessageManager mm, MessageSystemPresenter presenter) {

        int choice;

        List<List<String>> receivedMessagesInfoList = new ArrayList<>();
        List<List<String>> sentMessagesInfoList = new ArrayList<>();

        for (int messageId : mm.getReceivedMessage()) {
            receivedMessagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
        }

        for (int messageId : mm.getSentMessage()) {
            sentMessagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
        }


        presenter.presentMessagesPreview(receivedMessagesInfoList, sentMessagesInfoList);


        do {
            try {
                presenter.presentViewMessageMessageInstruction();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (mm.getMessageById(choice) == null) {
                    presenter.presentMessageNotFound();
                } else {
                    presenter.presentMessage(presenter.generatePresenterInfo(mm, accountManager, choice));
                    mm.setStatus(choice, "read", gate);
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                sc.next();
            }
        } while (true);

    }


    /**
     * Displays the preview of all the user's sent and received messages and asks the user to pick
     * one specific message from them to display that message information fully
     */
    public abstract void viewMessage();

    /**
     * Helper method that helps viewMessages() method
     * Creates the functionality of displaying the user's all sent messages or all received messages
     * depending on the user's choice
     *
     * @param mm        the user message manager of a specific user that provides information
     *                  of the messages that needs to be viewed
     * @param presenter the presenter that presents the contents that need to be displayed in this method
     */
    protected void viewMessagesTool(UserMessageManager mm, MessageSystemPresenter presenter) {


        int choice;
        List<List<String>> messagesInfoList = new ArrayList<>();
        do {
            try {
                presenter.presentViewMessagesMessageInstruction();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5) {
                    presenter.presentInputError();
                } else {

                    if (choice == 1) {
                        for (int messageId : mm.getMessageByStatus("unread")) {
                            messagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
                        }
                    } else if (choice == 2) {
                        for (int messageId : mm.getMessageByStatus("read")) {
                            messagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
                        }
                    } else if (choice == 3) {
                        for (int messageId : mm.getMessageByStatus("achieved")) {
                            messagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
                        }
                    } else if (choice == 4) {
                        for (int messageId : mm.getReceivedMessage()) {
                            messagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
                        }
                    } else {
                        for (int messageId : mm.getSentMessage()) {
                            messagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
                        }
                    }
                    presenter.presentMessages(messagesInfoList);
                    messagesInfoList = new ArrayList<>();
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                sc.next();
            }
        } while (true);

    }

    /**
     * Displays the user's all sent messages or all received messages of the user depending on the user's choice
     */
    public abstract void viewMessages();


    /**
     * Helper method that helps sendMessage() method
     * Creates the functionality of sending a message to a valid user that this user specifies
     *
     * @param mm        the user message manager of a specific user that takes care of the backend of sending
     *                  the specified message to the specified user if possible
     * @param presenter the presenter that presents the contents that need to be displayed in this method
     */
    protected void sendMessageTool(UserMessageManager mm, MessageSystemPresenter presenter) {
        String email;
        String content;
        boolean success;
        do {
            presenter.presentSendMessageEmailInstruction();
            email = sc.nextLine();
            if (email.equals("0")) {
                return;
            } else if (accountManager.getUserByEmail(email) == null) {
                presenter.presentUserNotFound();
            } else {
                presenter.presentSendMessageContentInstruction();
                content = sc.nextLine();
                if (!content.equals("0")) {

                    success = mm.sendMessage(accountManager.getUserByEmail(email), content, gate);
                    presenter.presentSendMessageResult(success);
                    if (success) {
                        accountManager.save(userGate);
                    }
                }
            }
        } while (true);


    }


    /**
     * Sends a message to a valid user that this user specifies
     */
    public abstract void sendMessage();

    /**
     * Helper method that helps replyMessage() method
     * Creates the functionality of displaying the preview of all the user's received messages,
     * asking the user to pick one specific message from them to reply, and eventually sending
     * a reply to that message
     *
     * @param mm        the user message manager of a specific user that takes care of the backend of sending
     *                  the specified message to the specified user if possible and providing the information related
     *                  to messages
     * @param presenter the presenter that presents the contents that need to be displayed in this method
     */
    protected void replyMessageTool(UserMessageManager mm, MessageSystemPresenter presenter) {
        int choice;
        String content;
        boolean success;
        presenter.presentReplyMessageAllMessagesIntroduction();

        List<List<String>> receivedMessagesInfoList = new ArrayList<>();

        for (int messageId : mm.getReceivedMessage()) {
            receivedMessagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
        }


        presenter.presentMessagePreview(receivedMessagesInfoList, "received");

        do {
            try {
                presenter.presentReplyMessageMessageInstruction();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (mm.getMessageById(choice) == null) {
                    presenter.presentMessageNotFound();
                } else {
                    presenter.presentReplyMessageContentInstruction();
                    content = sc.nextLine();
                    if (!content.equals("0")) {
                        success = mm.sendMessage(accountManager.getUserById(
                                Integer.parseInt(mm.getInfo(choice, "senderId"))), content, gate);
                        presenter.presentReplyMessageResult(success);
                        if (success) {
                            accountManager.save(userGate);
                        }
                    }
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                sc.next();
            }
        } while (true);

    }

    /**
     * Displays the preview of all the user's received messages, asks the user to pick
     * one specific message from them to reply, and eventually sends a reply to that message
     */
    public abstract void replyMessage();

    /**
     * Allow user to change status of message
     *
     * @param mm        the user message manager of a specific user that takes care of the backend of sending
     *                  the specified message to the specified user if possible and providing the information related
     *                  to messages
     * @param presenter the presenter that presents the contents that need to be displayed in this method
     */
    protected void markMessageTool(UserMessageManager mm, MessageSystemPresenter presenter) {

        int choice;
        int choice2;
        boolean success;

        List<List<String>> receivedMessagesInfoList = new ArrayList<>();

        for (int messageId : mm.getReceivedMessage()) {
            receivedMessagesInfoList.add(presenter.generatePresenterInfo(mm, accountManager, messageId));
        }


        presenter.presentMessagePreview(receivedMessagesInfoList, "received");


        do {
            try {
                presenter.presentMarkMessageMessageInstruction();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (mm.getMessageById(choice) == null) {
                    presenter.presentMessageNotFound();
                } else {
                    presenter.presentMarkMessageOptionsInstruction();
                    choice2 = sc.nextInt();
                    if (choice2 == 0) {
                        return;
                    } else if (choice2 != 1 && choice2 != 2 && choice2 != 3) {
                        presenter.presentInputError();
                    } else {
                        String newStatus;
                        if (choice2 == 1) {
                            newStatus = "unread";
                        } else if (choice2 == 2) {
                            newStatus = "read";
                        } else {
                            newStatus = "achieved";
                        }
                        success = mm.setStatus(choice, newStatus, gate);
                        presenter.presentMarkMessageResult(success);
                    }
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                sc.next();
            }
        } while (true);

    }

    public abstract void markMessage();

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

}



