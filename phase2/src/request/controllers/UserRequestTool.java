package request.controllers;

import gateways.IGateway;
import request.presenters.UserRequestSystemPresenter;
import request.useCases.UserRequestManager;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A helper class that view, send, cancel user requests.
 */
public class UserRequestTool {
    private final UserRequestSystemPresenter presenter = new UserRequestSystemPresenter();
    private final Scanner s = new Scanner(System.in);

    /**
     * Display user requests
     *
     * @param requestGate UserRequestGateway
     * @param urm         UserRequestManager
     */
    protected void viewUserRequest(IGateway requestGate, UserRequestManager urm) {
        do {
            try {
                presenter.presentRequests(urm.getMyRequestsInfo());
                presenter.presentViewMyRequestsInstruction();
                int choice = s.nextInt();
                s.nextLine();
                if (choice == 0) {
                    return;
                } else if (choice == 1) {
                    sendUserRequest(requestGate, urm);
                } else if (choice == 2) {
                    cancelUserRequest(requestGate, urm);
                } else {
                    presenter.presentCommandError();
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (true);
    }


    /**
     * Send userRequest according to user input.
     *
     * @param requestGate UserRequestGateway
     * @param urm         UserRequestManager
     */
    protected void sendUserRequest(IGateway requestGate, UserRequestManager urm) {
        boolean legalInput = false;
        do {
            try {
                presenter.presentSendRequestInstruction();
                String message = s.nextLine();
                presenter.presentConfirm();
                int confirm = s.nextInt();
                s.nextLine();
                boolean success = false;
                if (confirm == 1) {
                    success = urm.sendUserRequest(requestGate, message);
                } else {
                    return;
                }
                legalInput = true;
                presenter.presentSendRequestResult(success);
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (!legalInput);
    }

    /**
     * Cancel userRequest according to user input.
     *
     * @param requestGate UserRequestGateway
     * @param urm         UserRequestManager
     */
    public void cancelUserRequest(IGateway requestGate, UserRequestManager urm) {
        boolean legalInput = false;
        do {
            try {
                presenter.presentCancelRequestInstruction();
                int requestId = s.nextInt();
                s.nextLine();
                if (requestId == 0) {
                    return;
                }
                presenter.presentConfirm();
                int confirm = s.nextInt();
                s.nextLine();
                boolean success = false;
                if (confirm == 1) {
                    success = urm.cancelUserRequest(requestGate, requestId);
                }
                legalInput = true;
                presenter.presentCancelRequestResult(success);
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (!legalInput);
    }
}
