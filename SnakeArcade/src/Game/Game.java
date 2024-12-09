package Game;

import Game.Types.GameType;
import Game.Types.NormalDifficulty;
import Input.*;
import Orbs.*;
import Snake.Snake;
import ScoreSystem.ScoreSystem;
import com.codeforall.online.simplegraphics.graphics.*;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.online.simplegraphics.pictures.*;

import java.util.LinkedList;
import java.util.Queue;

public class Game {

    //Game core components
    private Snake snake;
    private Grid grid;
    private Screen screen;
    private ScoreSystem scoreSystem;
    private int gameTypeSelection = 0;

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
    private Queue<Integer> keyQueue = new LinkedList<>();

    /**
     * Constructor initializes game grid and score system
     * Starts the initial game screen
     */
    public Game()  {
        //initializes the grid, 50 cols, 30 rows and cell the size of CELLSIZE
        grid = new Grid(50, 40, CELLSIZE);
        screen = new Screen(scoreSystem, grid);
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
     * Sets up game grid, snake, keyboard, and initial game elements
     */
    private void startSnakeGame() {
        //starts the game grid
        grid.init();
        scoreSystem = new ScoreSystem();
        screen = new Screen(scoreSystem, grid);

        Text title = new Text(Grid.PADDING ,grid.rowToY(grid.getRows())-10,"SnakeArcade");
        title.grow(20,15);
        title.translate(title.getX()+(Grid.PADDING-title.getX()),5);
        title.setColor(Color.BLACK);
        title.draw();

        screen.drawScore();


        try {
            startNormalDifficulty();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Displays initial starting screen
     */
    private void startMenu() throws InterruptedException {

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
        startSnakeGame();
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
            }
            menuDownPressed = false;
        }
    }

    /**
     * Main game loop for normal difficulty
     * Handles snake movement, orb interactions, and game progression
     */
    private void startNormalDifficulty() throws InterruptedException {

        gameType = new NormalDifficulty(grid, screen , scoreSystem);
        mygameKeyboard.setGameType(gameType);
        gameType.start();
        endScreen();
    }

    /**
     * Receives keyboard input for menu interaction
     */
    public void menuKeyboardInput(int keyCode ) {
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
            menuLeftPressed = true;
        }
        if(keyCode == KeyboardEvent.KEY_RIGHT){
            menuRightPressed = true;
        }
        keyQueue.add(keyCode);
    }



    /**
     * Displays end screen with game stats
     */
    private void endScreen() {
        menuButtonPressed = false;
        scoreSystem.saveHighScore();
        //System.out.println(scoreSystem.printHighScoreList());

        screen.endScreen();
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
        startSnakeGame();

    }

}