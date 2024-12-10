package Sound;

public class SoundManager {

    private Sound menuSound;
    private Sound normalGameStartSound;
    private Sound mediumGameStartSound;
    private Sound hardGameStartSound;
    private Sound eatOrbSound;
    private Sound eatBadOrbSound;
    private Sound endGameSound;
    private Sound rainbowSound;

    public SoundManager() {
        // Load sound files
        menuSound = new Sound("resources/menuSound.wav");
        normalGameStartSound = new Sound("resources/normalGameStartSound.wav");
        mediumGameStartSound = new Sound("resources/mediumGameStartSound.wav");
        hardGameStartSound = new Sound("resources/hardGameStartSound.wav");
        eatOrbSound = new Sound("resources/eatOrbSound.wav");
        eatBadOrbSound = new Sound("resources/eatBadOrbSound.wav");
        endGameSound = new Sound("resources/endGameSound.wav");
        rainbowSound = new Sound("resources/rainbowModeSound.wav");
    }

    /**
     * Toggles the normal game start sound
     */
    public void toggleNormalGameStartSound() {
        normalGameStartSound.togglePlay();
    }

    /**
     * Toggles the medium game start sound
     */
    public void toggleMediumGameStartSound() {
        mediumGameStartSound.togglePlay();
    }

    /**
     * Toggles the hard game start sound
     */
    public void toggleHardGameStartSound() {
        hardGameStartSound.togglePlay();
    }

    /**
     * Toggles the menu sound
     */
    public void toggleMenuSound() {
        menuSound.togglePlay();
    }
    /**
     * Toggles the rainbowMode sound
     */
    public void toggleRainbowSound() {
        rainbowSound.togglePlay();
    }

    /**
     * plays eat good orb sound
     */
    public void playEatOrbSound() {
        eatOrbSound.play();
    }

    /**
     * plays the eat bad orb sound
     */
    public void playEatBadOrbSound() {
        eatBadOrbSound.play();
    }

    /**
     * Toggles the end game sound
     */
    public void playEndGameSound() {
        endGameSound.play();
    }

    /**
     * Stops all currently playing sounds
     */
    public void stopAllSounds() {
        menuSound.stop();
        normalGameStartSound.stop();
        mediumGameStartSound.stop();
        hardGameStartSound.stop();
        eatOrbSound.stop();
        eatBadOrbSound.stop();
        endGameSound.stop();
    }
}
