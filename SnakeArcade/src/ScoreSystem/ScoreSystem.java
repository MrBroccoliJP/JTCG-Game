package ScoreSystem;

import Game.Game;
import Game.Types.GameType;
import Game.Types.HardDifficulty;
import Game.Types.MediumDifficulty;
import Game.Types.NormalDifficulty;

import java.sql.SQLOutput;
import java.util.Arrays;

public class ScoreSystem {

    private int score;
    private int goodOrbsEaten;
    private int badOrbsEaten;
    private int[] highScore = new int[5];
    private String[] stats = new String[5];

    private int[] highScoresN = new int[5];
    private String[] highScoreStatsN = new String[5];
    private int[] highScoresM = new int[5];
    private String[] highScoreStatsM = new String[5];
    private int[] highScoresH = new int[5];
    private String[] highScoreStatsH = new String[5];


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

    public void saveHighScore(GameType gameType) {
        //System.out.println("#############IM HERE********");
        String newStat = "Score: " + score + "| Good Orbs Eaten: " + goodOrbsEaten + " | Bad Orbs Eaten: " + badOrbsEaten;
        int[] targetScores = null;
        String[] targetStats = null;

        if (gameType instanceof NormalDifficulty) {
            //newStat = "N " + newStat;
            targetScores = highScoresN;
            targetStats = highScoreStatsN;
        }
        else if (gameType instanceof MediumDifficulty) {
           // newStat = "M " + newStat;
            targetScores = highScoresM;
            targetStats = highScoreStatsM;
        }
        else if (gameType instanceof HardDifficulty) {
           // newStat = "H " + newStat;
            targetScores = highScoresH;
            targetStats = highScoreStatsH;
        }

        if (targetScores != null) {
            for (int i = 0; i < targetScores.length; i++) {
                if (score > targetScores[i]) {
                    for (int j = targetScores.length - 1; j > i; j--) {
                        targetScores[j] = targetScores[j - 1];
                        targetStats[j] = targetStats[j - 1];
                    }

                    targetScores[i] = score;
                    targetStats[i] = newStat;
                    break;
                }
            }
        }

        if (gameType instanceof NormalDifficulty) {
            highScoresN = targetScores;
            highScoreStatsN = targetStats;
        }
        else if (gameType instanceof MediumDifficulty) {
            highScoresM = targetScores;
            highScoreStatsM = targetStats;
        }
        else if (gameType instanceof HardDifficulty) {
            highScoresH = targetScores;
            highScoreStatsH = targetStats;
        }

        fileHandler.saveScoreToFile(highScoreStatsN, highScoreStatsM, highScoreStatsH);
    }

    public String printHighScoreList(GameType gameType, int i) {
        StringBuilder output = new StringBuilder();

        if (gameType instanceof NormalDifficulty) {
            highScore = highScoresN;
            stats = highScoreStatsN;
        }
        else if (gameType instanceof MediumDifficulty) {
            highScore = highScoresM;
            stats = highScoreStatsM;
        }
        else if (gameType instanceof HardDifficulty) {
            highScore = highScoresH;
            stats = highScoreStatsH;
        }


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

    public void setHighScores(int[] highScoresN, String[] statsN, int[] highScoresM, String[] statsM, int[] highScoresH, String[] statsH) {
        this.highScoresN = highScoresN;
        this.highScoreStatsN = statsN;
        this.highScoresM = highScoresM;
        this.highScoreStatsM = statsM;
        this.highScoresH = highScoresH;
        this.highScoreStatsH = statsH;
    }

}
