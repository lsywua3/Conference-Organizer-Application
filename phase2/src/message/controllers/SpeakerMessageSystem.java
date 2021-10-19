package message.controllers;

import account.useCases.AccountManager;
import event.useCases.EventBoard;
import event.useCases.SpeakerEventManager;
import gateways.IGateway;
import message.presenters.SpeakerMessageSystemPresenter;
import message.useCases.SpeakerMessageManager;
import room.RoomManager;

import java.util.InputMismatchException;


public class SpeakerMessageSystem extends MessageSystem {

    private final SpeakerMessageManager mm;
    private final SpeakerEventManager uem;
    private final EventBoard eb;
    private final SpeakerMessageSystemPresenter presenter;
    private final RoomManager rm;

    /**
     * Constructs a message system for a speaker user that allows that speaker to run messaging operations
     * including sending messages, viewing messages, viewing one message, replying to a message, sending a message
     * to all the attendees of one event that this speaker is holding, sending a message to all the attendees of
     * all the events that this speaker is holding.
     * Initializes SpeakerMessageManager mm, SpeakerEventManager sem, EventBoard eb, AccountManager accountManager,
     * IGateway gate, IGateway userGate and SpeakerSystemPresenter presenter.
     *
     * @param user           the id of the user that is using this system
     * @param accountManager the account manager that helps message managers to handle changes related to user accounts
     * @param gate           the messageGateway that helps reading and writing message data from/to database
     * @param userGate       the userGateway that helps accountManager to save message data associated
     *                       with specific users in database
     * @param eventGate      the eventGateway that helps creating the event board in order to get information about events
     */

    public SpeakerMessageSystem(int user, AccountManager accountManager, IGateway gate, IGateway userGate,
                                IGateway eventGate, IGateway roomGateway) {
        super(accountManager, gate, userGate);
        presenter = new SpeakerMessageSystemPresenter();
        mm = new SpeakerMessageManager(accountManager.getUserById(user), gate);
        eb = new EventBoard(eventGate);
        rm = new RoomManager(roomGateway, eb.getScheduleGenerator());
        uem = new SpeakerEventManager(accountManager.getUserById(user), rm);
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
     * Sends a message that the speaker wants to send to all the attendees of one specified event
     * that the speaker holds
     */
    public void announceMessageToEvent() {

        int choice;
        String content;
        boolean success;
        do {
            try {
                presenter.presentAnnounceMessageToEventEventInstruction();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (!uem.getMyEventList(eb).contains(choice)) {
                    presenter.presentEventNotFound();
                } else {
                    presenter.presentAnnounceMessageToEventContentInstruction();
                    content = sc.nextLine();
                    if (!content.equals("0")) {

                        success = mm.sendAll(content, gate,
                                accountManager.getUsersByIds(uem.getAttendees(eb.getEventById(choice))));
                        presenter.presentAnnounceMessageToEventResult(success);
                        if (success) {
                            accountManager.save(userGate);
                        }
                    } else {
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                sc.next();
            }
        } while (true);


    }


    /**
     * Sends a message that the speaker wants to send to all the attendees of all the events
     * that the speaker holds
     */
    public void announceMessageToAllEvents() {
        presenter.presentAnnounceMessageToAllEventsContentInstruction();
        String content = sc.nextLine();
        boolean success;
        if (!content.equals("0")) {

            success = mm.sendAll(content, gate, accountManager.getUsersByIds(uem.getAttendees(
                    eb.getAllEventsById(uem.getMyEventList(eb)))));
            presenter.presentAnnounceMessageToAllEventsResult(success);
            if (success) {
                accountManager.save(userGate);
            }
        }
    }


    /**
     * Allow user to mark status of message
     */
    @Override
    public void markMessage() {
        markMessageTool(mm, presenter);
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
                markMessage();
                break;
            case 6:
                announceMessageToEvent();
                break;
            case 7:
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
