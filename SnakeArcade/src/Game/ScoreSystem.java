package Game;

import java.util.Arrays;

public class ScoreSystem {

    private int score;
    private int goodOrbsEaten;
    private int badOrbsEaten;
    private int[] highScore = new int[4];
    private String[] stats = new String[4];

    public ScoreSystem() {
        score = 0;
        Arrays.fill(highScore, -1);
    }
    public int getScore() {
        return score;
    }
    public int addScore(int amount) {
        return score += amount;
    }

    public void addGoodOrbsEaten(int amount) {
        this.goodOrbsEaten += amount;
    }
    public void addBadOrbsEaten(int amount) {
        this.badOrbsEaten += amount;
    }

    public int resetScore() {
        return score = 0;
    }

    @Override
    public String toString() {
        return score + " points";
    }

    public void saveHighScore() {
        String newStat = "Good Orbs Eaten: " + goodOrbsEaten + " | Bad Orbs Eaten: " + badOrbsEaten;

        for (int i = 0; i < highScore.length; i++) {
            if (score > highScore[i]) {

                for (int j = highScore.length - 1; j > i; j--) {
                    highScore[j] = highScore[j - 1];
                    stats[j] = stats[j - 1];
                }

                // Insert the new score and stat
                highScore[i] = score;
                stats[i] = newStat;
                break; // Exit loop since the score is now inserted
            }
        }
    }

    public String printHighScoreList() {
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < highScore.length ; i++) {
            if(highScore[i] != -1) {
                output.append(i + 1).append(": Score:").append(highScore[i]).append(" | ").append(stats[i]).append("\n");
            }
        }
        return output.toString();
    }
}
