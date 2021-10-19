package main;

import controllers.menuSystem.MenuSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            MenuSystem menu = new MenuSystem();
            menu.run();
            try {
                System.out.println("End the program? [true/false]");
                exit = s.nextBoolean();
            } catch (InputMismatchException e) {
                System.out.println("The program will continue.");
                s.next();
            }
        }
    }
}
