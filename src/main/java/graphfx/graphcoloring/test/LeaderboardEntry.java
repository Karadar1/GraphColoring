package graphfx.graphcoloring.test;

public class LeaderboardEntry {
    private String username;
    private int score;
    private int timeElapsed;
    private String date;

    public LeaderboardEntry(String username, int score, int timeElapsed, String date) {
        this.username = username;
        this.score = score;
        this.timeElapsed = timeElapsed;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}