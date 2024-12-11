package Game;

import Game.Types.GameType;
import Game.Types.HardDifficulty;
import Game.Types.MediumDifficulty;
import Game.Types.NormalDifficulty;
import Input.*;
import Snake.Snake;
import ScoreSystem.ScoreSystem;
import Sound.SoundManager;
import com.codeforall.online.simplegraphics.graphics.*;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEvent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


public class Game {

    //Game core components
    private Snake snake;
    private Map map;
    private Screen screen;
    private ScoreSystem scoreSystem;
    private SoundManager soundManager;

    private GameType gameType;
    private int gameTypeSelection = 0;

    private MyGameKeyboard mygameKeyboard;

    //game constant
    public static int CELLSIZE = 20;

    //menu interaction
    private boolean menuButtonPressed = false;
    private boolean menuUpPressed = false;
    private boolean menuDownPressed = false;
    private boolean konamiMode = false;  //cheat code activation flag

    private Queue<Integer> keyQueue = new LinkedList<>();

    /**
     * Constructor initializes game map, score system, keyboard and sound
     * Game map: 50 cols, 30 rows, 20x20 px
     */
    public Game() {
        //initializes the map, 50 cols, 30 rows and cell the size of CELLSIZE
        map = new Map(50, 40, CELLSIZE);
        screen = new Screen(scoreSystem, map);
        soundManager = new SoundManager();
        mygameKeyboard = new MyGameKeyboard();
        mygameKeyboard.init(this, null);

        try {
            //displays the initial starting screen
            startMenu();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Initializes the main Snake game based on the selected difficulty.
     *
     * @param gameTypeSelection Difficulty selection index
     */
    private void startSnakeGame(int gameTypeSelection) {
        //starts the game map
        map.init();
        scoreSystem = new ScoreSystem();
        drawTitle();
        screen = new Screen(scoreSystem, map);
        screen.drawScore();


        try {
            switch (gameTypeSelection) {
                case 0 -> startNormalDifficulty();
                case 1 -> startMediumDifficulty();
                case 2 -> startHardDifficulty();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Displays the initial starting menu and handles user interaction.
     */
    private void startMenu() throws InterruptedException {
        soundManager.toggleMenuSound();
        mygameKeyboard.setGameStage(0);
        screen.startMenu();
        while (!menuButtonPressed) {
            handleMenuButtonSelection();
            screen.drawStartMenuRect(gameTypeSelection);
            Thread.sleep(100);
        }
        mygameKeyboard.setGameStage(1);
        menuButtonPressed = false;
        screen.startMenuDelete();
        soundManager.stopAllSounds();
        startSnakeGame(gameTypeSelection);
    }

    /**
     * Handles user input for menu navigation.
     */
    private void handleMenuButtonSelection() {
        if (menuUpPressed) {
            switch (gameTypeSelection) {
                case 0 -> gameTypeSelection = 2;
                case 1 -> gameTypeSelection = 0;
                case 2 -> gameTypeSelection = 1;
            }
            menuUpPressed = false;
        }
        if (menuDownPressed) {
            switch (gameTypeSelection) {
                case 0 -> gameTypeSelection = 1;
                case 1 -> gameTypeSelection = 2;
                case 2 -> gameTypeSelection = 0;
            }
            menuDownPressed = false;
        }
    }

    /**
     * Draws the title on the game screen.
     */
    private void drawTitle() {
        Text title = new Text(Map.PADDING, map.rowToY(map.getRows()) - 10, "SnakeArcade");
        title.grow(20, 15);
        title.translate(title.getX() + (Map.PADDING - title.getX()), 5);
        title.setColor(Color.BLACK);
        title.draw();
    }

    /**
     * Starts the game with Normal difficulty settings.
     */
    private void startNormalDifficulty() throws InterruptedException {
        initializeGame(new NormalDifficulty(map, screen, scoreSystem, soundManager, konamiMode),
                "Normal");
    }

    /**
     * Starts the game with Medium difficulty settings.
     */
    private void startMediumDifficulty() throws InterruptedException {
        initializeGame(new MediumDifficulty(map, screen, scoreSystem, soundManager, konamiMode),
                "Medium");
    }

    /**
     * Starts the game with Hard difficulty settings.
     */
    private void startHardDifficulty() throws InterruptedException {
        initializeGame(new HardDifficulty(map, screen, scoreSystem, soundManager, konamiMode),
                "Hard");
    }

    /**
     * Initializes the game with the provided difficulty settings.
     *
     * @param gameType       The selected game difficulty
     * @param difficultyName The name of the difficulty (for logging/sound purposes)
     */
    private void initializeGame(GameType gameType, String difficultyName) throws InterruptedException {
        this.gameType = gameType;
        mygameKeyboard.setGameStage(1); // Set keyboard to gameplay stage
        soundManager.toggleGameStartSound(difficultyName); // Play start sound
        mygameKeyboard.setGameType(gameType); // Link game type to keyboard
        gameType.start(); // Start the game loop
        scoreSystem.saveHighScore(gameType); // Save high score
        soundManager.stopAllSounds();
        endScreen(); // Display end screen
    }

    /**
     * Handles keyboard input during menu navigation.
     */
    public void menuKeyboardInput(int keyCode) {
        keyQueue.offer(keyCode); // Add key to queue

        // Set menu interaction flags based on key press
        if (keyCode == KeyboardEvent.KEY_SPACE) {
            menuButtonPressed = true;
        } else if (keyCode == KeyboardEvent.KEY_UP) {
            menuUpPressed = true;
        } else if (keyCode == KeyboardEvent.KEY_DOWN) {
            menuDownPressed = true;
        }

        // Limit queue size and check for Konami code
        if (keyQueue.size() > 10) {
            keyQueue.poll(); // Remove oldest key
        }
        checkForKonamiCode(keyQueue);
    }


    /**
     * Checks if the Konami code has been entered.
     * Cheat code, enables cheat mode on the game
     */
    private void checkForKonamiCode(Queue<Integer> keyQueue) {
        Integer[] target = {38, 38, 40, 40, 37, 39, 37, 39, 66, 65}; // Up, Up, Down, Down, Left, Right, Left, Right, B, A

        if (Arrays.equals(keyQueue.toArray(), target)) {
            System.out.println("Konami code activated!");
            konamiMode = true;
        }
    }


    /**
     * Displays the end screen and resets game state.
     */
    private void endScreen() throws InterruptedException {
        soundManager.stopAllSounds();
        soundManager.playEndGameSound();
        resetKeys();
        konamiMode = false;
        map.resetColors();
        screen.endScreen(gameType);
        mygameKeyboard.setGameStage(0);
        keyQueue.clear();
        try {
            while (!menuButtonPressed) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        gameType.delete();
        screen.clear();
        menuButtonPressed = false;
        scoreSystem.resetScore();
        startMenu();
    }

    /**
     * Resets menu-related key states.
     */
    public void resetKeys() {
        menuButtonPressed = false;
        menuUpPressed = false;
        menuDownPressed = false;
    }

    /**
     * Exits the application.
     */
    public void exit() {
        System.exit(0);
    }

}