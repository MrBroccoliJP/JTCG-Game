package Sound;

import Game.ResourcePath;

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
//        menuSound = new Sound(ResourcePath.RESOURCE_PATH + "menuSound.wav");
//        normalGameStartSound = new Sound(ResourcePath.RESOURCE_PATH + "normalGameStartSound.wav");
//        mediumGameStartSound = new Sound(ResourcePath.RESOURCE_PATH + "mediumGameStartSound.wav");
//        hardGameStartSound = new Sound(ResourcePath.RESOURCE_PATH + "hardGameStartSound.wav");
//        eatOrbSound = new Sound(ResourcePath.RESOURCE_PATH + "eatOrbSound.wav");
//        eatBadOrbSound = new Sound(ResourcePath.RESOURCE_PATH + "eatBadOrbSound.wav");
//        endGameSound = new Sound(ResourcePath.RESOURCE_PATH + "endGameSound.wav");
//        rainbowSound = new Sound(ResourcePath.RESOURCE_PATH + "rainbowModeSound.wav");

        menuSound = new Sound("/menuSound.wav");
        normalGameStartSound = new Sound("/normalGameStartSound.wav");
        mediumGameStartSound = new Sound("/mediumGameStartSound.wav");
        hardGameStartSound = new Sound("/hardGameStartSound.wav");
        eatOrbSound = new Sound("/eatOrbSound.wav");
        eatBadOrbSound = new Sound("/eatBadOrbSound.wav");
        endGameSound = new Sound("/endGameSound.wav");
        rainbowSound = new Sound("/rainbowModeSound.wav");
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

    public void toggleGameStartSound(String difficultyName) {
        switch (difficultyName.toLowerCase()) {
            case "normal":
                normalGameStartSound.togglePlay();
                break;
            case "medium":
                mediumGameStartSound.togglePlay();
                break;
            case "hard":
                hardGameStartSound.togglePlay();
                break;
            default:
                throw new IllegalArgumentException("Unknown difficulty: " + difficultyName);
        }
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
