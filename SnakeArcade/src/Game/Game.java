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
    private int gameTypeSelection = 0;
    private SoundManager soundManager = new SoundManager();

    private GameType gameType;

    private MyGameKeyboard mygameKeyboard;

    //game constant
    public static int CELLSIZE = 20;

    //menu interaction
    private boolean menuButtonPressed = false;
    private boolean menuUpPressed = false;
    private boolean menuDownPressed = false;
    private boolean menuLeftPressed = false;
    private boolean menuRightPressed = false;
    private boolean AKeyPressed = false;
    private boolean BKeyPressed = false;
    private boolean konamiMode = false;



    private Queue<Integer> keyQueue = new LinkedList<>();

    /**
     * Constructor initializes game map and score system
     * Starts the initial game screen
     */
    public Game()  {
        //initializes the map, 50 cols, 30 rows and cell the size of CELLSIZE
        map = new Map(50, 40, CELLSIZE);
        screen = new Screen(scoreSystem, map);

        //displays the initial starting screen
        try {
            startMenu();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Initializes the main snake game
     * Sets up game map, snake, keyboard, and initial game elements
     */
    private void startSnakeGame(int gameTypeSelection) {
        //starts the game map
        map.init();
        scoreSystem = new ScoreSystem();
        screen = new Screen(scoreSystem, map);

        Text title = new Text(Map.PADDING , map.rowToY(map.getRows())-10,"SnakeArcade");
        title.grow(20,15);
        title.translate(title.getX()+(Map.PADDING-title.getX()),5);
        title.setColor(Color.BLACK);
        title.draw();

        screen.drawScore();


        try {
            switch (gameTypeSelection) {
                case 0:
                    startNormalDifficulty();
                    break;
                case 1:
                    startMediumDifficulty();
                    break;
                case 2:
                    startHardDifficulty();
                    break;
            }

        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }



    /**
     * Displays initial starting screen
     */
    private void startMenu() throws InterruptedException {
        soundManager.toggleMenuSound();
        mygameKeyboard = new MyGameKeyboard();
        mygameKeyboard.setGameStage(0);
        mygameKeyboard.init(this, null);

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

    private void handleMenuButtonSelection(){
        if(menuUpPressed) {
            switch (gameTypeSelection) {
                case 0:
                    gameTypeSelection = 2;
                    break;
                case 1:
                    gameTypeSelection = 0;
                    break;
                case 2:
                    gameTypeSelection = 1;
                    break;
            }
            menuUpPressed = false;
        }
        if(menuDownPressed) {
            switch (gameTypeSelection) {
                case 0:
                    gameTypeSelection = 1;
                    break;
                case 1:
                    gameTypeSelection = 2;
                    break;
                case 2:
                    gameTypeSelection = 0;
                    break;
            }
            menuDownPressed = false;
        }
    }

    /**
     * Main game loop for normal difficulty
     * Handles snake movement, orb interactions, and game progression
     */
    private void startNormalDifficulty() throws InterruptedException {
        soundManager.toggleNormalGameStartSound();
        gameType = new NormalDifficulty(map, screen , scoreSystem,soundManager, konamiMode );
        mygameKeyboard.setGameType(gameType);
        gameType.start();
        scoreSystem.saveHighScore(gameType);
        soundManager.stopAllSounds();
        endScreen();

    }
    private void startMediumDifficulty() throws InterruptedException {
        gameType = new MediumDifficulty(map, screen , scoreSystem,soundManager, konamiMode);
        soundManager.toggleMediumGameStartSound();
        mygameKeyboard.setGameType(gameType);
        gameType.start();
        scoreSystem.saveHighScore(gameType);
        soundManager.stopAllSounds();
        endScreen();

    }
    private void startHardDifficulty() throws InterruptedException {
        gameType = new HardDifficulty(map, screen , scoreSystem, soundManager, konamiMode);
        soundManager.toggleHardGameStartSound();
        mygameKeyboard.setGameType(gameType);
        gameType.start();
        scoreSystem.saveHighScore(gameType);
        soundManager.stopAllSounds();
        endScreen();
    }

    /**
     * Receives keyboard input for menu interaction
     */
    public void menuKeyboardInput(int keyCode) {
        if(keyCode == KeyboardEvent.KEY_SPACE) {
            menuButtonPressed = true;  //spacebar pressed
        }
        if(keyCode == KeyboardEvent.KEY_UP){
            menuUpPressed = true;
        }
        if(keyCode == KeyboardEvent.KEY_DOWN){
            menuDownPressed = true;
        }
        if(keyCode == KeyboardEvent.KEY_LEFT){
            this.menuLeftPressed = true;
        }
        if(keyCode == KeyboardEvent.KEY_RIGHT){
            menuRightPressed = true;
        }
        if(keyCode == KeyboardEvent.KEY_A){
            AKeyPressed = true;
        }
        if(keyCode == KeyboardEvent.KEY_B){
            BKeyPressed = true;
        }


            keyQueue.offer(keyCode);
            AKeyPressed = false;
            BKeyPressed = false;

            if(keyQueue.size() > 10){
                keyQueue.poll();
            }
        checkForKonamiCode(keyQueue);

    }

    private void checkForKonamiCode(Queue<Integer> keyQueue) {
        Integer[] target = {38,38,40,40,37,39,37,39,66,65};

        if(Arrays.equals(keyQueue.toArray(), target)){
            System.out.println("konami achieved");
            konamiMode = true;
        }
    }


    /**
     * Displays end screen with game stats
     */
    private void endScreen() throws InterruptedException {
        soundManager.stopAllSounds();
        soundManager.playEndGameSound();
        menuButtonPressed = false;
        konamiMode = false;
        map.resetColors();

        //System.out.println(scoreSystem.printHighScoreList());

        screen.endScreen(gameType);
        mygameKeyboard.setGameStage(0);
        try {
            while (!menuButtonPressed) {
                Thread.sleep(100);
            }
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        gameType.delete();
        screen.clear();
        menuButtonPressed = false;

        scoreSystem.resetScore();
        //startSnakeGame();
        startMenu();
        //startSnakeGame(1);

    }

    public void exit(){
        System.exit(0);
    }

}