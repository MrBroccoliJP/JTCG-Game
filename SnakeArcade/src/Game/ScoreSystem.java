package Game;

public class ScoreSystem {
    private int score;
    public ScoreSystem() {
        score = 0;
    }
    public int getScore() {
        return score;
    }
    public int addScore(int amount) {
        score = score + amount;
        return score;
    }
}
