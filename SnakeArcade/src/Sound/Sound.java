package Sound;

import javax.sound.sampled.*;
import java.io.*;

public class Sound {

    private Clip clip;
    private boolean isPlaying;

    /**
     * Loads a sound file from the given path
     * @param soundFilePath Path to the .wav sound file
     */
//    public Sound(String soundFilePath) {
//        try {
//            File soundFile = new File(soundFilePath);
//            if (!soundFile.exists()) {
//                throw new IOException("Sound file not found: " + soundFilePath);
//            }
//            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
//            clip = AudioSystem.getClip();
//            clip.open(audioStream);
//            isPlaying = false;
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            System.err.println("Error loading sound: " + e.getMessage());
//        }
//    }

    //VERSAO 3

    public Sound(String soundFilePath) {
        try {
            // Load the sound file as a resource stream
            InputStream inputStream = getClass().getResourceAsStream(soundFilePath);
            if (inputStream == null) {
                throw new IOException("Sound file not found in resources: " + soundFilePath);
            }

            // Wrap the InputStream in a BufferedInputStream to support mark/reset
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            // Get an AudioInputStream from the BufferedInputStream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedInputStream);

            // Get a Clip object and open the audio stream
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            isPlaying = false;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound: " + e.getMessage());
        }
    }

    /**
     * Toggles sound on or off
     */
    public void togglePlay() {
        if (isPlaying) {
            stop();
        } else {
            loop();
        }
    }

    /**
     * Plays the loaded sound
     */
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Rewinds to the beginning
            clip.start();
            isPlaying = true;
        }
    }

    /**
     * Loops the loaded sound continuously
     */
    public void loop() {
        if (clip != null) {
            clip.setFramePosition(0); // Rewinds to the beginning
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isPlaying = true;
        }
    }

    /**
     * Stops the sound if it is playing
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
        }
    }

    /**
     * Returns whether the sound is currently playing
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Closes the audio resources
     */
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}
