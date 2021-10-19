package rate;

import account.useCases.AccountManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for RateSystem.
 */
public class RatePresenter {
    /**
     * present the menu for rate system
     */
    public void presentMenu() {
        System.out.println("----------------\nRating System Menu:");
        System.out.println("" +
                "[1] Rate for a Speaker\n" +
                "[2] Comment a Speaker\n" +
                "[3] View rating for a speaker\n" +
                "[4] View comments of a speaker\n" +
                "[0] Exit");
    }

    private List<String> idToName(List<Integer> ids, AccountManager acm) {
        List<String> names = new ArrayList<>();
        for (int id : ids) {
            names.add(acm.getInfo(id, "name"));
        }
        return names;
    }

    private String idToName(int id, AccountManager acm) {
        return acm.getInfo(id, "name");
    }

    /**
     * Present all speakers in format of "[id] name"
     *
     * @param speakers list of speaker ids.
     * @param acm      AccountManager
     */
    public void presentAvailableSpeakers(List<Integer> speakers, AccountManager acm) {
        List<String> speakerNames = idToName(speakers, acm);
        System.out.println("Here are all speakers in the system:");
        for (int i = 0; i < speakers.size(); i++) {
            System.out.println("[" + speakers.get(i) + "] " + speakerNames.get(i));
        }
    }

    /**
     * present the instruction to view ratings
     */
    public void presentViewRatingInstruction() {
        System.out.println("Please enter the <Speaker id> you want to view rating about. Enter [0] to exit");
    }

    /**
     * present the average rate for a speaker
     *
     * @param rating the average rate
     * @param id     the speaker id
     * @param acm    an AccountManager
     */
    public void presentRating(double rating, int id, AccountManager acm) {
        if (rating < 1.0) {
            System.out.println("There is no rating for this speaker yet");
            return;
        }
        String speakerName = idToName(id, acm);
        System.out.println("The current rating for " + speakerName + " is " + rating);
    }


    /**
     * present the instruction to view comments
     */
    public void presentViewCommentsInstruction() {
        System.out.println("Please enter the <Speaker id> you want to view comments about; enter [0] to exit");
    }

    /**
     * present all comments for a speaker
     *
     * @param comments the list of comments for the speaker
     * @param id       the id of the speaker
     * @param acm      an AccountManager
     */
    public void presentComments(List<String> comments, int id, AccountManager acm) {
        if (comments.size() == 0) {
            System.out.println("There is no comments for this Speaker yet");
            return;
        }
        String speakerName = idToName(id, acm);
        System.out.println("Here are all comments of " + speakerName + ":");
        System.out.println("-----------------------------------------");
        for (int i = 1; i <= comments.size(); i++) {
            System.out.println("> " + comments.get(i - 1));
        }
        System.out.println("-----------------------------------------");
    }

    /**
     * present the instruction to choose which speaker to add rating
     */
    public void presentAddRatingSpeakerInstruction() {
        System.out.println("Please enter the <Speaker id> you want to rate; enter [0] to exit");
    }

    /**
     * present the instruction to add a rating
     */
    public void presentAddRatingRateInstruction() {
        System.out.println("Please enter <rating> from 1-10 you want to rate for this speaker; "
                + "enter [0] to exit");
    }

    /**
     * present the result for adding rate
     *
     * @param success a boolean that represent if add rating successful
     */
    public void presentAddRatingResult(boolean success) {
        if (success) {
            System.out.println("Rate successful!");
        } else {
            System.out.println("Failed to rate, please double check the rating you enter and try again");
        }
    }

    /**
     * present the instruction to choose which speaker to add comment
     */
    public void presentAddCommentSpeakerInstruction() {
        System.out.println("Please enter the <Speaker id> you want to comment. Enter [0] to exit");
    }

    /**
     * present the instruction to add a comment
     */
    public void presentAddCommentCommentInstruction() {
        System.out.println("Please enter your <comment> for this speaker, empty comment is not allowed "
                + "Enter [0] to exit");
    }

    /**
     * present the result for adding comment
     *
     * @param success a boolean that represent if add comment successful
     */
    public void presentAddCommentResult(boolean success) {
        if (success) {
            System.out.println("Comment successful!");
        } else {
            System.out.println("Failed to comment, please double check the rating you enter and try again");
        }
    }


    /**
     * present the error message for invalid input in menu
     */
    public void presentCommandError() {
        System.out.println("This is not a valid option, please try again");
    }

    /**
     * present the error message for general invalid input
     */
    public void presentInputError() {
        System.out.println("This is not a valid input, please try again");
    }

    /**
     * present the error message for invalid speaker id
     */
    public void presentSpeakerNotFound() {
        System.out.println("The entered id is either not exist or not a speaker. Please double check and try again");
    }
}
