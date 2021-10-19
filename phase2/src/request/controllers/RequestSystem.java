package request.controllers;

import account.useCases.AccountManager;
import gateways.IGateway;
import interfaces.SystemRunnable;

import java.util.Scanner;

/**
 * Abstract class of a system that deal with user requests.
 */
public abstract class RequestSystem implements SystemRunnable {
    protected Scanner s = new Scanner(System.in);
    protected AccountManager acm;
    protected IGateway g;
    protected int currUser;


    /**
     * Constructor for request System
     *
     * @param currUser current user id
     * @param acm      AccountManager
     * @param g        UserRequestGateway
     */
    public RequestSystem(int currUser, AccountManager acm, IGateway g) {
        this.acm = acm;
        this.g = g;
        this.currUser = currUser;
    }


    @Override
    public abstract void navigateCommand(int command);

    @Override
    public abstract int run();
}
