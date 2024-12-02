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
        return score += amount;
    }
    public int resetScore() {
        return score = 0;
    }

    @Override
    public String toString() {
        return score + " points";
    }
}
