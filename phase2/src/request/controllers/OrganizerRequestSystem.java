package request.controllers;

import account.useCases.AccountManager;
import gateways.IGateway;
import request.presenters.OrganizerRequestPresenter;
import request.useCases.UserRequestAdmin;

import java.util.InputMismatchException;
import java.util.List;

/**
 * Request system for Organizer to view and mark requests.
 */
public class OrganizerRequestSystem extends RequestSystem {
    private final UserRequestAdmin ura;
    private final OrganizerRequestPresenter presenter = new OrganizerRequestPresenter();

    public OrganizerRequestSystem(int currUser, AccountManager acm, IGateway g) {
        super(currUser, acm, g);
        ura = new UserRequestAdmin(g);
    }

    /**
     * Display all Requests.
     */
    public void viewRequests() {
        presenter.presentViewRequestsInstruction();
        presenter.presentRequests(ura.getAllRequestsInfo());
        viewRequest();
    }

    /**
     * Display single requests in details according to user input.
     */
    private void viewRequest() {
        do {
            try {
                presenter.presentViewRequestInstruction();
                int requestId = s.nextInt();
                s.nextLine();
                if (requestId == 0) {
                    return;
                }
                List<String> info = ura.getInfoList(requestId);
                if (info == null) {
                    presenter.presentRequestNotFound();
                    continue;
                }
                presenter.presentRequest(info);
                operateRequest(requestId);

            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (true);
    }

    /**
     * Address or pend request according to user input.
     *
     * @param requestId int id of the request.
     */
    private void operateRequest(int requestId) {
        boolean legalInput = false;
        do {
            try {
                presenter.presentOperateRequestInstruction();
                int input = s.nextInt();
                boolean success;
                if (input == 0) {
                    return;
                } else if (input == 1) {
                    success = ura.addressUserRequest(g, requestId);
                    legalInput = true;
                } else if (input == 2) {
                    success = ura.pendingUserRequest(g, requestId);
                    legalInput = true;
                } else {
                    presenter.presentCommandError();
                    continue;
                }
                presenter.presentOperateRequestResult(success);
                presenter.presentRequestPreview(ura.getInfoList(requestId));
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (!legalInput);
    }


    @Override
    public void navigateCommand(int command) {
        switch (command) {
            case 0:
                break;
            case 1:
                viewRequests();
                break;
        }
    }

    @Override
    public int run() {
        int choice = -1;
        do {
            try {
                presenter.presentMenu();
                choice = s.nextInt();
                s.nextLine();
                if (choice >= 0 && choice <= 1) {
                    navigateCommand(choice);
                } else {
                    presenter.presentCommandError();
                }
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                s.next();
            }
        } while (choice != 0);
        return 0;
    }
}

