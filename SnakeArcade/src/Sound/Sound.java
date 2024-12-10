package Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private Clip clip;
    private boolean isPlaying;

    /**
     * Loads a sound file from the given path
     * @param soundFilePath Path to the .wav sound file
     */
    public Sound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            if (!soundFile.exists()) {
                throw new IOException("Sound file not found: " + soundFilePath);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
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
