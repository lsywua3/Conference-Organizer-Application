package rate;

import account.useCases.AccountManager;
import gateways.IGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The rating board that manage all rates. Rates are stored in HashMap of (SpeakerId : Rate)
 */
public class RateBoard {

    private final AccountManager acm;
    private HashMap<Integer, Rate> rates;

    /**
     * Constructor of RateBoard
     *
     * @param g   RateGateway
     * @param acm AccountManager
     */
    public RateBoard(IGateway g, AccountManager acm) {
        List<HashMap<Integer, Rate>> data = (List<HashMap<Integer, Rate>>) g.read();
        this.rates = new HashMap<>();
        if (data.size() != 0) {
            this.rates = data.get(0);
        }
        this.acm = acm;
    }

    /**
     * Check if the id is a speaker's id.
     *
     * @param id int id of a user.
     * @return true iff the id belongs to a speaker.
     */
    private boolean notAvailableSpeaker(int id) {
        return !acm.isSpeaker(id);
    }

    /**
     * Save data.
     *
     * @param g RateGateway.
     */
    private void save(IGateway g) {
        List wrappedData = new ArrayList();
        wrappedData.add(rates);
        g.write(wrappedData);
    }

    /**
     * Return the average rating for a speaker
     *
     * @param id the id of the speaker
     * @return a int represent the average rate
     */
    public double getRatingById(int id) {
        if (notAvailableSpeaker(id)) {
            return -1.0;
        }
        if (rates.get(id) == null) {
            return -1.0;
        }
        List<Integer> speakerRating = rates.get(id).getRatings();
        if (speakerRating == null) {
            return -1.0;
        }
        double sum = 0.0;
        for (int i : speakerRating) {
            sum += i;
        }
        return Math.round(sum / speakerRating.size() * 10.0) / 10.0;
    }

    /**
     * add a rating for the speaker
     *
     * @param g      the rate gateway
     * @param id     the id of speaker
     * @param rating the new rating for the speaker
     * @return true if the rating is add successfully
     */
    public boolean addRating(IGateway g, int id, int rating) {
        if (notAvailableSpeaker(id) || rating > 10 || rating <= 0) {
            return false;
        }
        rates.computeIfAbsent(id, k -> new Rate());
        rates.get(id).addRating(rating);
        save(g);
        return true;
    }

    /**
     * Get all comments for a speaker
     *
     * @param id the id of the speaker
     * @return a list of string of comment
     */
    public List<String> getCommentsById(int id) {
        List<String> speakerComments;
        if (notAvailableSpeaker(id)) {
            return new ArrayList<>();
        }
        if (rates.get(id) == null) {
            return new ArrayList<>();
        }
        speakerComments = rates.get(id).getComments();
        if (speakerComments == null) {
            return new ArrayList<>();
        }
        return speakerComments;
    }

    /**
     * Add a comment for a speaker
     *
     * @param g       the rate gateway
     * @param id      the id of speaker
     * @param comment the new comment
     * @return true if add successfully
     */
    public boolean addComment(IGateway g, int id, String comment) {
        if (notAvailableSpeaker(id) || comment.isEmpty()) {
            return false;
        }
        rates.computeIfAbsent(id, k -> new Rate());
        rates.get(id).addComment(comment);
        save(g);
        return true;
    }


}
