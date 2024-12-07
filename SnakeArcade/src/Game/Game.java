package Game;

import Input.*;
import Orbs.*;
import Snake.Snake;
import com.codeforall.online.simplegraphics.graphics.*;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.pictures.*;

public class Game {

    //Game core components
    private Snake snake;
    private final Grid grid;
    private final ScoreSystem scoreSystem;

    //game speed and movement control
    private int baseSpeed = 200;
    private int speed = 200;
    private Movements movement = Movements.NONE;
    private Movements currentMovement = Movements.NONE;

    //UI elements
    Picture logo;
    private Text scoreText;

    //Orb Objects
    private GoodOrb goodOrb;
    private BadOrb badOrb;
    private BonusOrb bonusOrb;

    //orb cycle variables
    private int badOrbCycleDuration = 0;
    private int bonusOrbCycleNextAppearance = 0;
    private int bonusOrbCycleDuration = 0;

    //game cycle tracking
    private int cycleCount;

    //private LinkedList<SnakeOrbs> snakeOrbsLinkedList;   FOR A LATER ADDITION SAVE THE AMMOUNT OF ORBS ON A LINKED LIST AND HAVE MORE THAN ONE GOOD ORB AT A TIME, AND MAKE THEM DISAPPEAR WITH TIME

    //game constant
    public static int CELLSIZE = 20;

    //menu interaction
    private boolean menuButtonPressed = false;

    /**
     * Constructor initializes game grid and score system
     * Starts the initial game screen
     */
    public Game() throws InterruptedException {

        //initializes the grid, 50 cols, 30 rows and cell the size of CELLSIZE
        grid = new Grid(50, 30, CELLSIZE);

        //this.scoreText = new Text(0,0," ");
        //this.title = new Text(0,0," ");

        //starts the score tracking system,
        //this score tracking system remains constant thru the whole game.
        scoreSystem = new ScoreSystem();

        //displays the initial starting screen
        startingScreen();
    }


    /**
     * Initializes the main snake game
     * Sets up game grid, snake, keyboard, and initial game elements
     */
    private void startSnakeGame() throws InterruptedException {

        //starts the game grid
        grid.init();


        snake = new Snake(grid.columnToX(25), grid.rowToY(15));
        setSnake(snake);


        movement = Movements.NONE;


        Text title = new Text(Grid.PADDING ,grid.rowToY(grid.getRows())-10,"SnakeArcade");
        title.grow(20,15);
        title.translate(title.getX()+(Grid.PADDING-title.getX()),5);
        title.setColor(Color.BLACK);
        title.draw();

        this.scoreText = new Text(0,0," ");
        this.scoreText.grow(20,15);
        this.scoreText.translate(grid.getCols()*grid.getCellSize()-(5*grid.getCellSize()),grid.rowToY(grid.getRows())-7);

        drawScore();
        goodOrb = new GoodOrb(grid);
        badOrb = new BadOrb(grid);
        bonusOrb = new BonusOrb(grid);
        startNormalDifficulty();
    }


    /**
     * Main game loop for normal difficulty
     * Handles snake movement, orb interactions, and game progression
     */
    private void startNormalDifficulty() throws InterruptedException {

        while (true) {  //infinite loop
            speedStepsCalc();
            drawScore();

            if (movement != Movements.NONE && movement != currentMovement.getOpposite()) {
                snake.moveSnake(movement);
                currentMovement = movement;
                movement = Movements.NONE;
            } else {
                snake.moveSnake();
                movement = Movements.NONE;
            }

            if(CollisionCheck()) {
                endScreen();
                break;}

            orbCheck(goodOrb);
            orbCheck(badOrb);
            orbCheck(bonusOrb);

            cycleCount++;
            Thread.sleep(speed);

        }

    }
    /**
     * Calculates game speed based on current score
     * Increases difficulty as score progresses
     */
    private void speedStepsCalc(){
        int score = scoreSystem.getScore();

        // Base speed adjustment logic
        if (score < 1000) {
            speed = 200; // Default speed for scores below 1000
        } else {
            if(speed > 100) {
                speed = (int) (baseSpeed - (score - 1000) * 0.01); // Increase speed for scores above 1000
            }
           //System.out.println("speed change: " + speed);
        }
    }


    /**
     * Draws current score on screen
     */
    private void drawScore(){
        this.scoreText.setText("Score: " + scoreSystem.getScore());
        this.scoreText.setColor(Color.GREEN);
        this.scoreText.draw();
    }

    /**
     * Checks if snake has had any collision
     * Itself or with the map borders
     */
    public boolean CollisionCheck() {
        return boundsCollisionCheck() || snake.selfCollisionCheck();
    }

    /**
     * Checks if snake has hit game boundaries
     */
    public boolean boundsCollisionCheck() {

        int leftBound = Grid.PADDING;
        int topBound = Grid.PADDING;
        int rightBound = (grid.getCols() * grid.getCellSize()) - Grid.PADDING+1;
        int bottomBound = (grid.getRows() * grid.getCellSize()) - Grid.PADDING+1;

        return snake.getHeadX() < leftBound || snake.getHeadY() < topBound ||
                snake.getHeadX() > rightBound || snake.getHeadY() > bottomBound;
    }

    /**
     * Manages orb spawning, duration, and interactions
     */
     public void orbCheck(SnakeOrbs orb){   //this is to check if the head of the snake "ate" the orb
        if(orb instanceof GoodOrb && !orb.active()){
            orb.randomSpawn();
        }
        if(orb instanceof BonusOrb && !orb.active()){
            if(bonusOrbCycleNextAppearance == cycleCount) {
                bonusOrbCycleDuration = 50 + (int) (Math.random() * 29) + 1;
                orb.randomSpawn();
                bonusOrbCycleNextAppearance =-1;
            }
            else if(bonusOrbCycleNextAppearance == -1){
                bonusOrbCycleNextAppearance = cycleCount + 15 + (int)(Math.random()*29)+1;
            }
        } else if(orb instanceof BonusOrb && orb.active()){
            if(bonusOrbCycleDuration == 0){
                orb.delete();
                bonusOrbCycleNextAppearance =-1;
            }
            bonusOrbCycleDuration--;

        }

        if(orb instanceof BadOrb && !orb.active()){
                badOrbCycleDuration = 30 + (int) (Math.random() * 29) + 1;
                orb.randomSpawn();
        } else if(orb instanceof BadOrb && orb.active()){
            if(badOrbCycleDuration == 0){
                orb.delete();
            }
            badOrbCycleDuration--;
            //System.out.println(badOrbCycleDuration);
        }

        if(orb.active() && (orb.getX() == snake.getHeadX() && orb.getY() == snake.getHeadY())){
            orb.delete();
            snake.setBlockBuffer(orb.getBuffer());
            scoreSystem.addScore(orb.getScore());
            System.out.println("SNAKE ATE THE AN ORB :" + orb.getScore());

            if(orb instanceof BadOrb){
                scoreSystem.addBadOrbsEaten(1);
            }
            else{
                scoreSystem.addGoodOrbsEaten(1);
            }
        }

    }



    /**
     * Receives keyboard input for game movement
     */
    public void gameKeyboardInput(Movements movement){
        //this method recieves the keyboard input to make it synchronous with the game
        this.movement = movement;
    }

    /**
     * Receives keyboard input for menu interaction
     */
    public void menuKeybordInput(){
            menuButtonPressed = true;  //spacebar pressed
    }

    /**
     * Displays initial starting screen
     */
    private void startingScreen() throws InterruptedException{
        Canvas.setMaxX(grid.getCellSize()* grid.getCols());
        Canvas.setMaxY(grid.getCellSize()* grid.getRows());

        logo = new Picture();
        logo.load("snake_arcade_logo.png");  //this logo was created with the dimensions of the screen | todo: make it scale with the screen
        //logo.load("snake_arcade_logo.png"); //ONLY FOR THE ANT BUILD

        logo.draw();
        Text instruction = new Text(logo.getX()+50,logo.getY()+logo.getHeight()-50, "Press SPACE to start -- and press arrow keys or 'w,a,s,d' to play");
        instruction.draw();

        //START KEYBOARD
        MyGameKeyboard mygameKeyboard = new MyGameKeyboard();
        mygameKeyboard.init(this);
        while (!menuButtonPressed) {
            Thread.sleep(100);
        }
        menuButtonPressed = false;
        instruction.delete();
        logo.delete();
        startSnakeGame();
    }

    /**
     * Displays end screen with game stats
     */
    private void endScreen() throws InterruptedException {
        menuButtonPressed = false;
        scoreSystem.saveHighScore();
        //System.out.println(scoreSystem.printHighScoreList());

        Text end = new Text(((double) grid.columnToX(grid.getCols()) /2), 0, "GAME OVER");
        end.translate((double) -end.getWidth() ,0);
        end.setColor(Color.RED);
        end.grow(300,70);
        end.translate(0,grid.getRows()+((double) end.getHeight() /2));
        end.draw();



        Text stats = new Text(end.getX(), end.getY()+end.getHeight(), "Stats: ");
        Text stats1 = new Text(end.getX(), stats.getY()+stats.getHeight(),  "High Score:" +  scoreSystem.printHighScoreList(0));
        Text stats2 = new Text(end.getX(), stats1.getY()+stats1.getHeight(),  "                " + scoreSystem.printHighScoreList(1));
        Text stats3 = new Text(end.getX(), stats2.getY()+stats2.getHeight(),  "                " + scoreSystem.printHighScoreList(2));
        Text stats4 = new Text(end.getX(), stats3.getY()+stats3.getHeight(),  "                " + scoreSystem.printHighScoreList(3));
        Text stats5 = new Text(end.getX(), stats4.getY()+stats4.getHeight(),  "                " + scoreSystem.printHighScoreList(4));
        Text info = new Text(end.getX(), stats5.getY()+(stats5.getHeight()*2), "Press space to start again");


        stats.draw();
        stats1.draw();
        stats2.draw();
        stats3.draw();
        stats4.draw();
        stats5.draw();
        info.draw();

        while (!menuButtonPressed) {
            Thread.sleep(100);
        }
        menuButtonPressed = false;
        deleteOrbs();
        snake.delete();
        stats.delete();
        stats1.delete();
        stats2.delete();
        stats3.delete();
        stats4.delete();
        stats5.delete();
        info.delete();

        end.delete();
        currentMovement = Movements.NONE;
        movement = Movements.NONE;
        scoreText.delete();
        scoreSystem.resetScore();
        startSnakeGame();

    }

    /**
     * Deletes active orbs from the game
     */
    private void deleteOrbs(){
        if(goodOrb.active()){
            goodOrb.delete();
        }
        if(badOrb.active()){
            badOrb.delete();
        }
        if(bonusOrb.active()){
            bonusOrb.delete();
        }
    }

    /**
     * Sets the current snake for the game
     */
    public void setSnake (Snake snake){
                this.snake = snake;
    }

}