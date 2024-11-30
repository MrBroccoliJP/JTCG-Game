package Game;

import Input.Movements;
import Input.MyKeyboard;
import Snake.Snake;
import Snake.*;
import com.codeforall.online.simplegraphics.graphics.Canvas;
import com.codeforall.online.simplegraphics.keyboard.Keyboard;

public class Game {

    Snake snake;
    Keyboard keyboard;
    private Movements movement = Movements.NONE;
    private Movements currentMovement = Movements.NONE;
    private Grid grid;
    private GoodOrb goodorb;

    public Game() {
        grid = new Grid(50, 50, 20);
        grid.init();

        MyKeyboard myKeyboard = new MyKeyboard();
        snake = new Snake(200, 200);
        setSnake(snake);
        keyboard = new Keyboard(myKeyboard);
        movement = Movements.NONE;
        myKeyboard.init(this);

        //TEST APPEARENCE

         GoodOrb goodOrb = new GoodOrb();
        goodOrb.setGrid(grid);
         goodOrb.randomSpawn();


    }

    public void start() throws InterruptedException {

        while (true) {  //infinite loop

            Thread.sleep(1000); //speed of the game
            System.out.println("currentMovement: " + currentMovement);
            System.out.println("movement: " + movement);
            System.out.println("opposite: " + movement.getOpposite());


            if (movement != Movements.NONE && movement != currentMovement.getOpposite()) {
                if(CollisionCheck()) { break;}
                snake.moveSnake(movement);
                currentMovement = movement;
                movement = Movements.NONE;
            } else {
                if(CollisionCheck()) { break;}
                snake.moveSnake();
                movement = Movements.NONE;
            }
        }
    }

    public boolean CollisionCheck() {
        if (boundsCollisionCheck()){
            System.out.println("Snake hit the bounds");
            return true;
        }

        if (snake.selfCollisionCheck()) {
            System.out.println("Snake hit itself");
            return true;
        }
        return false;
    }


    //*joao: Moved this method to the game section because in my opinion should be the game to detect the collision
    public boolean boundsCollisionCheck() {

        int leftBound = Grid.PADDING*2;
        int topBound = Grid.PADDING*2;
        int rightBound = (grid.getCols() * grid.getCellSize()) - Grid.PADDING*2;
        int bottomBound = (grid.getRows() * grid.getCellSize()) - Grid.PADDING*2;

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