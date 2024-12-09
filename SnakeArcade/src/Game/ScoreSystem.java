package Game;

import java.io.*;
import java.util.Arrays;

public class ScoreSystem {

    private int score;
    private int goodOrbsEaten;
    private int badOrbsEaten;
    private int[] highScore = new int[5];
    private String[] stats = new String[5];

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

    public void saveScoreToFile(){
        createScoreFile();
        try {
            FileWriter scoreFileWriter = new FileWriter("scores.txt");
            for (int i = 0; i < highScore.length; i++) {
                scoreFileWriter.write("N " + printHighScoreList(i));
            }

            scoreFileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createScoreFile(){
        try {
            File scoreFile = new File("scores.txt");
            if(!scoreFile.exists()){
                scoreFile.createNewFile();
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public String printHighScoreList(int i) {
        StringBuilder output = new StringBuilder();
       // for(int i = 0; i < highScore.length ; i++) {
            if(highScore[i] != -1) {
                output.append("Score: ").append(highScore[i]).append(" | ").append(stats[i]).append("\n");
            }
            else{
                output.append(" ");
            }
        //}
        return output.toString();
    }

    public void readScoreFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("scores.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        }
            catch (FileNotFoundException e){
                System.err.println("Score file not found");
            }
            catch (IOException e){
                System.err.println("Error reading score file");
            }

        }

}
