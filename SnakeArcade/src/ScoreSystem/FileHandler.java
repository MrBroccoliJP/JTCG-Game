package ScoreSystem;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class FileHandler {
    private final String fileName = "scores.txt";
    private final ScoreSystem scoreSystem;
    public int[] fileScoresTypeN = new int[5];
    public String[] fileStatsTypeN = new String[5];
    public int[] fileScoresTypeM = new int[5];
    public String[] fileStatsTypeM = new String[5];
    public int[] fileScoresTypeH = new int[5];
    public String[] fileStatsTypeH = new String[5];

    public FileHandler(ScoreSystem scoreSystem) {
        this.scoreSystem = scoreSystem;
    }

    private void initializeArrays() {
        Arrays.fill(fileScoresTypeN, -1);
        Arrays.fill(fileScoresTypeM, -1);
        Arrays.fill(fileScoresTypeH, -1);
        Arrays.fill(fileStatsTypeN, "");
        Arrays.fill(fileStatsTypeM, "");
        Arrays.fill(fileStatsTypeH, "");
    }

    public void saveScoreToFile(String[] fileStatsTypeN, String[] fileStatsTypeM, String[] fileStatsTypeH) {
        String[] buffer = new String[15];

        // always have exactly 5 entries per difficulty, even if empty
        for (int i = 0; i < 5; i++) {
            buffer[i] = fileStatsTypeN[i] != null ? "N " + fileStatsTypeN[i] : "";
            buffer[i + 5] = fileStatsTypeM[i] != null ? "M " +  fileStatsTypeM[i] : "";
            buffer[i + 10] = fileStatsTypeH[i] != null ? "H " + fileStatsTypeH[i] : "";
        }

        writeToFile(buffer);
    }

    private void writeToFile(String[] buffer) {
        createScoreFile();
        try (FileWriter scoreFileWriter = new FileWriter(fileName)) {
            for (String entry : buffer) {
                // Always write a line, even if empty
                scoreFileWriter.write((entry != null ? entry : "") + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing scores to file: " + e.getMessage());
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
        initializeArrays();

        File scoreFile = new File(fileName);

        // If file doesn't exist, create it and set default values
        if (!scoreFile.exists()) {
            createScoreFile();
            // Set default scores to initial state
            scoreSystem.setHighScores(fileScoresTypeN, fileStatsTypeN,
                    fileScoresTypeM, fileStatsTypeM,
                    fileScoresTypeH, fileStatsTypeH);
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int nCount = 0, mCount = 0, hCount = 0;
            String line;

            for (int i = 0; i < 15; i++) {
                line = reader.readLine();

                if (line == null) break;
                line = line.trim();

                if (line.isEmpty()) continue;
                char gameType = line.charAt(0);

                switch (gameType) {
                    case 'N':
                        if (nCount < 5) {
                            parseLineForDifficulty(line, fileScoresTypeN, fileStatsTypeN, nCount);
                            nCount++;
                        }
                        break;
                    case 'M':
                        if (mCount < 5) {
                            parseLineForDifficulty(line, fileScoresTypeM, fileStatsTypeM, mCount);
                            mCount++;
                        }
                        break;
                    case 'H':
                        if (hCount < 5) {
                            parseLineForDifficulty(line, fileScoresTypeH, fileStatsTypeH, hCount);
                            hCount++;
                        }
                        break;
                }
            }

            // Set the parsed scores in the score system
            scoreSystem.setHighScores(fileScoresTypeN, fileStatsTypeN,
                    fileScoresTypeM, fileStatsTypeM,
                    fileScoresTypeH, fileStatsTypeH);
        }
        catch (FileNotFoundException e){
            createScoreFile();
            System.err.println("Score file not found" + e.getMessage());
        }
        catch (IOException e){
            System.err.println("Error reading score file" + e.getMessage());
        }
    }

    private void parseLineForDifficulty(String line, int[] scoreArray, String[] statsArray, int index) {
        try {
            // Remove the difficulty type prefix
            String restOfString = line.substring(2).trim();

            // Store the full stats string
            statsArray[index] = restOfString;

            // Extract score
            String[] parts = restOfString.split("\\|");
            for (String part : parts) {
                if (part.trim().startsWith("Score:")) {
                    // Get the number after "Score:"
                    int score = Integer.parseInt(part.trim().substring(7).trim());
                    scoreArray[index] = score;
                    break;
                }
            }
        } catch (Exception e) {
            // If parsing fails, set default values
            scoreArray[index] = -1;
            statsArray[index] = "";
            System.out.println("Parsing score line: [LINE EMPTY]" + line);
        }
    }

}
