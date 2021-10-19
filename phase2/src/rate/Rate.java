package rate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A rate that contain rating and comments for a speaker
 */
public class Rate implements Serializable {
    private List<Integer> ratings;
    private List<String> comments;

    public Rate(){
        ratings = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addRating(int rating){
        ratings.add(rating);
    }

    public void addComment(String comment){
        comments.add(comment);
    }
}
