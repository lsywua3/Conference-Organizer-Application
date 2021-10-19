package account.controllers;

import controllers.SystemRunnable;
import entities.User;
import gateways.IGateway;
import useCase.accountManager.AccountManager;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * System for account login and register.
 */
public class AccountSystem implements SystemRunnable {
    private AccountManager acm;
    private IGateway gate;
    private Scanner sc = new Scanner(System.in);
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
            System.out.println("Please enter your email: ");
            email = sc.next();
            System.out.println("Please enter your password: ");
            password = sc.next();
            if (!acm.verifyLogin(email, password)) {
                System.out.println("Email or password is incorrect, please try again.");
            } else {
                user1 = acm.getUserByEmail(email);
                System.out.println("Logged in successfully!");
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
            System.out.println("Please enter your name: ");
            name = sc.nextLine();
            System.out.println("Please enter a valid email: ");
            email = sc.nextLine();
            System.out.println("Please enter a valid password: ");
            password = sc.nextLine();
            created = acm.createAccount(name, type, email, password, gate);
            if (created) {
                int id = acm.getUserId(acm.getUserByEmail(email));
                System.out.println("Successfully created account #" + id);
            } else {
                System.out.println("Email is already used, please try again.");
            }
        } while (!created);
    }

    /**
     * Get the account manager.
     *
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
                System.out.println("You entered Account System:");
                System.out.println("" +
                        "[1] Login into your account\n" +
                        "[2] Register a new account\n" +
                        "[0] Exit");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 0 && choice <= 2) {
                    navigateCommand(choice);
                } else {
                    System.out.println("Illegal command!");
                }
//                System.out.println("CUI: " + currentUserId);
//                System.out.println(choice != 0 || currentUserId == 0);
            } catch (InputMismatchException e) {
                System.out.println("Illegal input!");
                sc.next();
            }
        } while (choice != 0 && currentUserId == 0);
        return currentUserId;
    }


}