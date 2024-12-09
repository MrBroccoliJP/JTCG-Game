package Game;

import java.io.*;
import java.util.Arrays;

public class ScoreSystem {

    private int score;
    private int goodOrbsEaten;
    private int badOrbsEaten;
    private int[] highScore = new int[5];
    private String[] stats = new String[5];
    private FileHandler fileHandler;

    public ScoreSystem() {
        score = 0;
        Arrays.fill(highScore, -1);
        fileHandler = new FileHandler(this);
        fileHandler.readScoreFile();
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

    public void resetScore() {
        score = 0;
        goodOrbsEaten = 0;
        badOrbsEaten = 0;
    }

    @Override
    public String toString() {
        return score + " points";
    }

    public void saveHighScore() {
        String newStat = "Score: "+ score + "| Good Orbs Eaten: " + goodOrbsEaten + " | Bad Orbs Eaten: " + badOrbsEaten;

        for (int i = 0; i < highScore.length; i++) {
            if (score > highScore[i]) {

                for (int j = highScore.length - 1; j > i; j--) {
                    highScore[j] = highScore[j - 1];
                    stats[j] = stats[j - 1];
                }

                highScore[i] = score;
                stats[i] = newStat;
                break;
            }
        }
        fileHandler.saveScoreToFile(stats);
    }


    public String printHighScoreList(int i) {
        StringBuilder output = new StringBuilder();
            if(highScore[i] != -1) {
                output.append(stats[i]).append("\n");
            }
            else{
                output.append(" ");
            }
        return output.toString();
    }

    public void setStats(String[] stats) {
        this.stats = stats;
    }
    public void setHighScore(int[] highScore) {
        this.highScore = highScore;
    }


}
