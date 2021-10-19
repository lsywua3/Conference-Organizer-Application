package rate;

import account.useCases.AccountManager;
import gateways.RateGateway;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * RateSystem allows user to view and add ratings (1-10) and comments to speakers of the conference.
 */
public class RateSystem implements interfaces.SystemRunnable {
    private final AccountManager acm;
    private final RateBoard rb;
    private final RateGateway rateGateway;
    private final RatePresenter ratePresenter;
    private final Scanner sc = new Scanner(System.in);

    /**
     * @param acm         AccountManager
     * @param rateGateway RateGateway
     */
    public RateSystem(AccountManager acm, RateGateway rateGateway) {
        this.acm = acm;
        this.rateGateway = rateGateway;
        this.rb = new RateBoard(rateGateway, acm);
        this.ratePresenter = new RatePresenter();
    }

    /**
     * Display average rating of speakers according to user input.
     */
    private void viewRating() {
        int choice;
        List<Integer> allSpeakers = acm.getSpeakersIds();
        ratePresenter.presentAvailableSpeakers(allSpeakers, acm);
        do {
            try {
                ratePresenter.presentViewRatingInstruction();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (acm.isSpeaker(choice)) {
                    ratePresenter.presentRating(rb.getRatingById(choice), choice, acm);
                } else {
                    ratePresenter.presentSpeakerNotFound();
                }
            } catch (InputMismatchException e) {
                ratePresenter.presentInputError();
                sc.next();
            }
        } while (true);
    }

    /**
     * Display comments of speakers according to user input.
     */
    private void viewComments() {
        int choice;
        List<Integer> allSpeakers = acm.getSpeakersIds();
        ratePresenter.presentAvailableSpeakers(allSpeakers, acm);
        do {
            try {
                ratePresenter.presentViewCommentsInstruction();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (acm.isSpeaker(choice)) {
                    ratePresenter.presentComments(rb.getCommentsById(choice), choice, acm);
                } else {
                    ratePresenter.presentSpeakerNotFound();
                }
            } catch (InputMismatchException e) {
                ratePresenter.presentInputError();
                sc.next();
            }
        } while (true);
    }

    /**
     * Allows user to add a ratings.
     */
    private void addRating() {
        int speaker;
        int rate;
        boolean success;
        List<Integer> allSpeakers = acm.getSpeakersIds();
        ratePresenter.presentAvailableSpeakers(allSpeakers, acm);
        do {
            try {
                ratePresenter.presentAddRatingSpeakerInstruction();
                speaker = sc.nextInt();
                sc.nextLine();
                if (speaker == 0) {
                    return;
                } else if (acm.isSpeaker(speaker)) {
                    ratePresenter.presentAddRatingRateInstruction();
                    rate = sc.nextInt();
                    if (rate != 0) {
                        success = rb.addRating(rateGateway, speaker, rate);
                        ratePresenter.presentAddRatingResult(success);
                    }
                } else {
                    ratePresenter.presentSpeakerNotFound();
                }
            } catch (InputMismatchException e) {
                ratePresenter.presentInputError();
                sc.next();
            }
        } while (true);
    }

    /**
     * Allows user to add a comment.
     */
    private void addComment() {
        int speaker;
        String comment;
        boolean success;
        List<Integer> allSpeakers = acm.getSpeakersIds();
        ratePresenter.presentAvailableSpeakers(allSpeakers, acm);
        do {
            try {
                ratePresenter.presentAddCommentSpeakerInstruction();
                speaker = sc.nextInt();
                sc.nextLine();
                if (speaker == 0) {
                    return;
                } else if (acm.isSpeaker(speaker)) {
                    ratePresenter.presentAddCommentCommentInstruction();
                    comment = sc.nextLine();
                    if (!comment.equals("0")) {
                        success = rb.addComment(rateGateway, speaker, comment);
                        ratePresenter.presentAddCommentResult(success);
                    }
                } else {
                    ratePresenter.presentSpeakerNotFound();
                }
            } catch (InputMismatchException e) {
                ratePresenter.presentInputError();
                sc.next();
            }
        } while (true);
    }

    /**
     * direct user input to functions
     *
     * @param command an integer represents the operation.
     */
    @Override
    public void navigateCommand(int command) {
        switch (command) {
            case 0:
                break;
            case 1:
                addRating();
                break;
            case 2:
                addComment();
                break;
            case 3:
                viewRating();
                break;
            case 4:
                viewComments();
                break;
        }
    }

    /**
     * main block of the system
     *
     * @return 0 if user choose to exit
     */
    @Override
    public int run() {
        int choice = -1;
        do {
            try {
                ratePresenter.presentMenu();
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 0 && choice <= 4) {
                    navigateCommand(choice);
                } else {
                    ratePresenter.presentCommandError();
                }
            } catch (InputMismatchException e) {
                ratePresenter.presentInputError();
                sc.next();
            }
        } while (choice != 0);
        return 0;
    }
}
