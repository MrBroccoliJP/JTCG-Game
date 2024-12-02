package Game;

import Input.Movements;
import Input.MyGameKeyboard;
import Orbs.BadOrb;
import Orbs.BonusOrb;
import Orbs.GoodOrb;
import Orbs.SnakeOrbs;
import Snake.Snake;
import com.codeforall.online.simplegraphics.graphics.Canvas;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Text;
import com.codeforall.online.simplegraphics.keyboard.Keyboard;
import com.codeforall.online.simplegraphics.pictures.Picture;

public class Game {

    Snake snake;
    Keyboard keyboard;
    Picture logo;
    private int baseSpeed = 200;
    private int speed = 200;
    private Movements movement = Movements.NONE;
    private Movements currentMovement = Movements.NONE;
    private final Grid grid;
    private final Text scoreText;
    private Text title;
    private GoodOrb goodOrb;
    private BadOrb badOrb;
    private BonusOrb bonusOrb;


    private int cycleCount;
    private final ScoreSystem scoreSystem;
    //private LinkedList<SnakeOrbs> snakeOrbsLinkedList;   FOR A LATER ADDITION SAVE THE AMMOUNT OF ORBS ON A LINKED LIST AND HAVE MORE THAN ONE GOOD ORB AT A TIME, AND MAKE THEM DISAPPEAR WITH TIME
    public static int CELLSIZE = 20;

    public Game() throws InterruptedException {
        //this only serves to initialize objects
        grid = new Grid(50, 30, CELLSIZE);
        MyGameKeyboard myKeyboard = new MyGameKeyboard();
        keyboard = new Keyboard(myKeyboard);
        movement = Movements.NONE;
        myKeyboard.init(this);
        this.scoreText = new Text(0,0," ");
        this.title = new Text(0,0," ");
        scoreSystem = new ScoreSystem();

        startingScreen();
    }

    private void startSnakeGame() throws InterruptedException {
        grid.init();
        snake = new Snake(grid.columnToX(25), grid.rowToY(15));
        setSnake(snake);

        Text title = new Text(Grid.PADDING ,grid.rowToY(grid.getRows())-Grid.PADDING,"SnakeArcade");
        title.grow(20,15);
        title.translate(title.getX()+(Grid.PADDING-title.getX()),5);
        title.setColor(Color.BLACK);
        title.draw();

        this.scoreText.translate(grid.getCols()*grid.getCellSize()-(5*grid.getCellSize()),grid.rowToY(grid.getRows())-Grid.PADDING);
        this.scoreText.grow(20,20);
        drawScore();
        goodOrb = new GoodOrb(grid);
        badOrb = new BadOrb(grid);
        bonusOrb = new BonusOrb(grid);
        startNormalDifficulty();
    }


    private void startNormalDifficulty() throws InterruptedException {
        int bonusCycleNextAppearance = 0;
        int bonusCycleDuration = 0;
        while (true) {  //infinite loop

            speedStepsCalc();
            Thread.sleep(speed); //speed of the game

            drawScore();

            if(!goodOrb.active()){
                goodOrb.randomSpawn();
            }
            if(!badOrb.active()){
                badOrb.randomSpawn();
            }

            if(!bonusOrb.active()){
                if(bonusCycleNextAppearance == cycleCount) {
                    bonusCycleDuration = 50 + (int) (Math.random() * 29) + 1;
                    bonusOrb.randomSpawn();
                    bonusCycleNextAppearance=-1;
                }
                else if(bonusCycleNextAppearance == -1){
                    bonusCycleNextAppearance = cycleCount + 15 + (int)(Math.random()*29)+1;
                }
            }
            if(bonusOrb.active()){
                if(bonusCycleDuration == 0){
                    bonusOrb.delete();
                }
                bonusCycleDuration--;
            }



            if (movement != Movements.NONE && movement != currentMovement.getOpposite()) {
                snake.moveSnake(movement);
                currentMovement = movement;
                movement = Movements.NONE;
                if(CollisionCheck()) {
                    endScreen();
                    break;}
            } else {
                snake.moveSnake();
                movement = Movements.NONE;
                if(CollisionCheck()) {
                    endScreen();
                    break;}
            }
            orbCheck(goodOrb);
            orbCheck(badOrb);
            orbCheck(bonusOrb);

            cycleCount++;
        }

    }

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

    private void drawScore(){
        this.scoreText.setText("Score: " + scoreSystem.getScore());
        this.scoreText.setColor(Color.GREEN);
        this.scoreText.draw();
    }

    public boolean CollisionCheck() {
        return boundsCollisionCheck() || snake.selfCollisionCheck();
    }

    public void orbCheck(SnakeOrbs orb){   //this is to check if the head of the snake "ate" the orb
        if(orb.getX() == snake.getHeadX() && orb.getY() == snake.getHeadY()){
            orb.delete();
            snake.setBlockBuffer(orb.getBuffer());
            scoreSystem.addScore(orb.getScore());
            System.out.println("SNAKE ATE THE A ORB :" + orb.getScore());
        }
    }

    public boolean boundsCollisionCheck() {

        int leftBound = Grid.PADDING;
        int topBound = Grid.PADDING;
        int rightBound = (grid.getCols() * grid.getCellSize()) - Grid.PADDING+1;
        int bottomBound = (grid.getRows() * grid.getCellSize()) - Grid.PADDING+1;

        return snake.getHeadX() < leftBound || snake.getHeadY() < topBound ||
                snake.getHeadX() > rightBound || snake.getHeadY() > bottomBound;
    }


    public void keyboardInput (Movements movement){
        //this method will handle keyboard inputs
        switch (movement) {
            case UP:
                this.movement = Movements.UP;
                break;
            case DOWN:
                this.movement = Movements.DOWN;
                break;
            case LEFT:
                this.movement = Movements.LEFT;
                break;
            case RIGHT:
                this.movement = Movements.RIGHT;
                break;
        }
    }

    private void startingScreen() throws InterruptedException{
        Canvas.setMaxX(grid.getCellSize()* grid.getCols());
        Canvas.setMaxY(grid.getCellSize()* grid.getRows());

        logo = new Picture();
        logo.load("/resources/snake_arcade_logo.png");  //this logo was created with the dimensions of the screen | todo: make it scale with the screen
        logo.draw();
        Thread.sleep(5000);
        logo.delete();
        startSnakeGame();
    }

    private void endScreen(){
        Canvas.setMaxX(grid.getCellSize()* grid.getCols());
        Canvas.setMaxY(grid.getCellSize()* grid.getRows());
        Text text = new Text(((double) grid.columnToX(grid.getCols()) /2), 0, "GAME OVER");
        text.translate((double) -text.getWidth() ,0);
        text.setColor(Color.RED);
        text.grow(300,70);
        text.translate(0,grid.getRows()+((double) text.getHeight() /2));
        text.draw();
    }


    public void setSnake (Snake snake){
                this.snake = snake;
    }

    private int getSpeed(){
        return speed;
    }


}