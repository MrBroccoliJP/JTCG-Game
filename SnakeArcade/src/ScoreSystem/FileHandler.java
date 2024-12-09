package ScoreSystem;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class FileHandler {
    private final String fileName = "scores.txt";
    private final ScoreSystem scoreSystem;
    private int[] fileScoresTypeN = new int[5];
    private String[] fileStatsTypeN = new String[5];
    private int[] fileScoresTypeM = new int[5];
    private String[] fileStatsTypeM = new String[5];
    private int[] fileScoresTypeH = new int[5];
    private String[] fileStatsTypeH = new String[5];

    public FileHandler(ScoreSystem scoreSystem) {
        this.scoreSystem = scoreSystem;
    }

    public void saveScoreToFile(String[] stats){
        createScoreFile();
        try {
            FileWriter scoreFileWriter = new FileWriter(fileName);
            for (int i = 0; i < stats.length; i++) {
                if (!Objects.equals(scoreSystem.printHighScoreList(i), " ")) {
                    scoreFileWriter.write("N " + scoreSystem.printHighScoreList(i));
                }
            }

            scoreFileWriter.close();
        }
        catch (IOException e) {
            System.err.println("Error writing scores to file" + e.getMessage());
        }

    }

    private void createScoreFile(){
        try {
            File scoreFile = new File(fileName);
            if(!scoreFile.exists()){
                scoreFile.createNewFile();
            }

        }
        catch(IOException e){
            System.err.println("IO exception" + e.getMessage());
        }
    }

    public void readScoreFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            char lastTypeRead = 'X';
            int n = 0;
            char gameType = ' ';
           String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                gameType = line.charAt(0); //reads the type of game
                if(gameType != lastTypeRead){
                    lastTypeRead = gameType;
                    n = 0;
                }
                else{
                    n++;
                }
                parseLineReadScoreFile(line, n);

                line = reader.readLine();
            }

            scoreSystem.setHighScore(fileScoresTypeN);
            System.out.println(Arrays.toString(fileScoresTypeN));
            scoreSystem.setStats(fileStatsTypeN);
            System.out.println(Arrays.toString(fileStatsTypeN));
        }
        catch (FileNotFoundException e){
            createScoreFile();
            System.err.println("Score file not found" + e.getMessage());
        }
        catch (IOException e){
            System.err.println("Error reading score file" + e.getMessage());
        }
    }

    private void parseLineReadScoreFile(String line, int n){
        char gameType = line.charAt(0); // This gives us 'N' for the type of game

        String restOfString = line.substring(2).trim();
        String[] parts = restOfString.split("\\|");
        int score = 0;
        for (String part : parts) {
            if (part.trim().startsWith("Score:")) {
                // Get the number after "Score:"
                score = Integer.parseInt(part.trim().substring(7).trim());
            }
        }

        if(gameType == 'N'){
            fileScoresTypeN[n] = score;
            fileStatsTypeN[n] = restOfString;
        }
    }
}
