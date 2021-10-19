package request.controllers;

import account.useCases.AccountManager;
import gateways.IGateway;
import request.presenters.UserRequestSystemPresenter;
import request.useCases.UserRequestManager;

import java.util.InputMismatchException;

/**
 * Request System for users who can send request.
 */
public class UserRequestSystem extends RequestSystem {
    private final UserRequestTool urTool = new UserRequestTool();
    private final UserRequestManager urm;
    private final UserRequestSystemPresenter presenter = new UserRequestSystemPresenter();

    public UserRequestSystem(int currUser, AccountManager acm, IGateway g) {
        super(currUser, acm, g);
        urm = new UserRequestManager(acm.getUserById(currUser), g);
    }

    /**
     * View requests of user.
     */
    public void viewMyRequests() {
        urTool.viewUserRequest(g, urm);
    }

    @Override
    public void navigateCommand(int command) {
        switch (command) {
            case 0:
                break;
            case 1:
                viewMyRequests();
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
