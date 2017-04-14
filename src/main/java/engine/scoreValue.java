package engine;

/**
 * Created by jeff on 4/1/17.
 */
public class scoreValue {

    private String userName;
    private String name;
    private String score;
    private String rating;
    private String time;

    public scoreValue(String un, String n, String s, String r, String t) {
        userName = un;
        name = n;
        score = s;
        rating = r;
        time = t;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getRating() {
        return rating;
    }

    public String getTime() {
        return time;
    }
}
