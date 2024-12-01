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
    private GoodOrb goodOrb;

    public Game() {
        grid = new Grid(50, 30, 20);
        grid.init();
        Text title = new Text(Grid.PADDING,0,"SnakeArcade:");
        title.setColor(Color.BLACK);
        title.draw();

        MyKeyboard myKeyboard = new MyKeyboard();
        snake = new Snake(grid.columnToX(25), grid.rowToY(15));
        setSnake(snake);
        keyboard = new Keyboard(myKeyboard);
        movement = Movements.NONE;
        myKeyboard.init(this);

        //TEST ORBS
         goodOrb = new GoodOrb();
         goodOrb.setGrid(grid);
         goodOrb.randomSpawn();
         goodOrb.spawn(grid.columnToX(0), grid.rowToY(0));


         //TEST SCORE TEXT
        Text score = new Text(0,0,"Score:");
        score.translate(grid.columnToX(grid.getCols())-score.getWidth()- Grid.PADDING,0);
        score.setColor(Color.BLACK);
        score.draw();

    }

    public void start() throws InterruptedException {

        while (true) {  //infinite loop

            Thread.sleep(speed); //speed of the game
            System.out.println("currentMovement: " + currentMovement);
            System.out.println("movement: " + movement);
            System.out.println("opposite: " + movement.getOpposite());

            if (movement != Movements.NONE && movement != currentMovement.getOpposite()) {
                snake.moveSnake(movement);
                currentMovement = movement;
                movement = Movements.NONE;
                if(CollisionCheck()) { break;}
            } else {
                snake.moveSnake();
                movement = Movements.NONE;
                if(CollisionCheck()) { break;}
            }
        }
    }

    public boolean CollisionCheck() {
        return boundsCollisionCheck() || snake.selfCollisionCheck();
    }

    public boolean boundsCollisionCheck() {

        int leftBound = Grid.PADDING;
        int topBound = Grid.PADDING;
        int rightBound = (grid.getCols() * grid.getCellSize()) - Grid.PADDING;
        int bottomBound = (grid.getRows() * grid.getCellSize()) - Grid.PADDING;

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

            public void setSnake (Snake snake){
                this.snake = snake;
            }
        }