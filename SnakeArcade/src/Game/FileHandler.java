package Game;

import java.io.*;
import java.util.Objects;

public class FileHandler {
    private final String fileName = "scores.txt";
    private final ScoreSystem scoreSystem;

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
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine(); //WHO GETS THIS LINE? IS IT PARSED HERE OR IN THE SCORESYSTEM ?
            }
        }
        catch (FileNotFoundException e){
            createScoreFile();
            System.err.println("Score file not found" + e.getMessage());
        }
        catch (IOException e){
            System.err.println("Error reading score file" + e.getMessage());
        }
    }
}
