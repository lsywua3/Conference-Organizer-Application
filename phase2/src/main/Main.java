package main;

import menu.MenuSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean exit = false;
        MainPresenter presenter = new MainPresenter();

        while (!exit) {
            MenuSystem menu = new MenuSystem();
            menu.run();

            try {
                presenter.presentEndProgram();
                exit = s.nextBoolean();
            } catch (InputMismatchException e) {
                presenter.presentWillContinue();
                s.next();
            }
        }
    }
}
