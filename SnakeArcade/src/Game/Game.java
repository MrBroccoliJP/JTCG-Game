package Game;

import Input.Movements;
import Input.MyKeyboard;
import Snake.Snake;
import Snake.*;
import com.codeforall.online.simplegraphics.graphics.Canvas;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Text;
import com.codeforall.online.simplegraphics.keyboard.Keyboard;

public class Game {

    Snake snake;
    Keyboard keyboard;
    private int speed = 200;
    private Movements movement = Movements.NONE;
    private Movements currentMovement = Movements.NONE;
    private Grid grid;
    private Text scoreText;
    private Text title;
    private GoodOrb goodOrb;
    private int cycleCount;
    private ScoreSystem scoreSystem;
    public static int CELLSIZE = 20;

    public Game() throws InterruptedException {
        //this only serves to initialize objects
        grid = new Grid(50, 30, CELLSIZE);
        MyKeyboard myKeyboard = new MyKeyboard();
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
        goodOrb = new GoodOrb();
        goodOrb.setGrid(grid);

        Text title = new Text(Grid.PADDING ,grid.rowToY(grid.getRows())-Grid.PADDING,"SnakeArcade");
        title.grow(20,15);
        title.translate(title.getX()+(Grid.PADDING-title.getX()),5);
        title.setColor(Color.BLACK);
        title.draw();

        this.scoreText.translate(grid.getCols()*grid.getCellSize()-(5*grid.getCellSize()),grid.rowToY(grid.getRows())-Grid.PADDING);
        this.scoreText.grow(20,20);
        drawScore();
        start();
        //goodOrb.randomSpawn();

    }

    private void start() throws InterruptedException {

        while (true) {  //infinite loop

            Thread.sleep(speed); //speed of the game
            //System.out.println("currentMovement: " + currentMovement);
            //System.out.println("movement: " + movement);
            //System.out.println("opposite: " + movement.getOpposite());
            drawScore();

            if(!goodOrb.active()){
                goodOrb.randomSpawn();
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
        if(orb instanceof GoodOrb){
            if(orb.getX() == snake.getHeadX() && orb.getY() == snake.getHeadY()){
                orb.delete();
                snake.setBlockBuffer(+1);
                scoreSystem.addScore(100);
                System.out.println("SNAKE ATE THE ORB");
            }
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
        Text text = new Text(((double) grid.columnToX(grid.getCols()) /2), 0, "Snake Arcade");
        text.translate(-text.getWidth(),0);
        text.setColor(Color.GREEN);
        text.grow(300,70);

        for(int i = 0; i < grid.getRows()+(text.getHeight()/2); i++){
            text.translate(0,1);
            text.draw();
            Thread.sleep(20);
        }
        Thread.sleep(500);
        text.delete();
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


}