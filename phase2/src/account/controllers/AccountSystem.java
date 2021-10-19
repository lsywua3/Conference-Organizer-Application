package account.controllers;

import account.presenters.AccountSystemPresenter;
import interfaces.SystemRunnable;
import account.entities.User;
import gateways.IGateway;
import account.useCases.AccountManager;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * System for account login and register.
 */
public class AccountSystem implements SystemRunnable {
    private AccountManager acm;
    private IGateway gate;
    private Scanner sc = new Scanner(System.in);
    private AccountSystemPresenter presenter = new AccountSystemPresenter();
    private int currentUserId;

    /**
     * Constructs a new AccountSystem with UserGateway g.
     *
     * @param g the UserGateway used to get all users.
     */
    public AccountSystem(IGateway g, AccountManager acm) {
        gate = g;
        this.acm = acm;
        currentUserId = 0;
    }

    /**
     * Takes user input of an email and password to login.
     */
    public void login() {
        boolean validInput = false;
        String email;
        String password;
        User user1 = null;
        do {
            presenter.presentLoginEmailInstruction();
            email = sc.next();
            presenter.presentLoginPasswordInstruction();
            password = sc.next();
            if (!acm.verifyLogin(email, password)) {
                presenter.presentInvalidLoginError();
            } else {
                user1 = acm.getUserByEmail(email);
                presenter.presentSuccessfulLogin();
                validInput = true;
            }
        } while (!validInput);
        currentUserId = acm.getUserId(user1);
    }

    /**
     * Takes user input of a name, email, and password to register a new user account.
     *
     * @param type user's type.
     */
    public void register(String type) {
        String name;
        String password;
        String email;
        boolean created;
        do {
            presenter.presentSignupName();
            name = sc.nextLine();
            presenter.presentSignupEmail();
            email = sc.nextLine();
            presenter.presentSignupPassword();
            password = sc.nextLine();
            created = acm.createAccount(name, type, email, password, gate);
            if (created) {
                int id = acm.getUserId(acm.getUserByEmail(email));
                presenter.presentRegistered(type, id);

            } else {
                presenter.presentInvalidEmail();
            }
        } while (!created);
    }

    /**
     * Get the account manager.
     * @return An AccountManager.
     */
    public AccountManager getAccountManager() {
        return this.acm;
    }

    /**
     * Takes an command and make account system operations.
     *
     * @param command an integer represents the operation.
     */
    @Override
    public void navigateCommand(int command) {
        switch (command) {
            case 0:
                break;
            case 1:
                login();
                break;
            case 2:
                register("Attendee");
                break;
        }
    }

    /**
     * Runs the account system.
     *
     * @return current user id if the program execute successfully.
     */
    @Override
    public int run() {
        int choice = -1;
        do {
            try {
                presenter.presentMenu();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 0 && choice <= 2) {
                    navigateCommand(choice);
                } else {
                    presenter.presentCommandError();
                }
//                System.out.println("CUI: " + currentUserId);
//                System.out.println(choice != 0 || currentUserId == 0);
            } catch (InputMismatchException e) {
                presenter.presentInputError();
                sc.next();
            }
        } while (choice != 0 && currentUserId == 0);
        return currentUserId;
    }


}