package Game;

import Input.Movements;
import Input.MyKeyboard;
import Snake.Snake;
import com.codeforall.online.simplegraphics.graphics.Canvas;
import com.codeforall.online.simplegraphics.keyboard.Keyboard;

public class Game {

    Snake snake;
    Keyboard keyboard;
    private Movements movement = Movements.NONE;
    private Movements currentMovement = Movements.NONE;
    private Grid grid;

    public Game() {
        grid = new Grid(50, 50, 20);
        grid.init();

        MyKeyboard myKeyboard = new MyKeyboard();
        snake = new Snake(200, 200);
        setSnake(snake);
        keyboard = new Keyboard(myKeyboard);
        movement = Movements.NONE;
        myKeyboard.init(this);


    }

    public void start() throws InterruptedException {

        while (true) {  //infinite loop

            Thread.sleep(200); //speed of the game
            System.out.println("currentMovement: " + currentMovement);
            System.out.println("movement: " + movement);
            System.out.println("opposite: " + movement.getOpposite());

            if (movement != Movements.NONE && movement != currentMovement.getOpposite()) {
                snake.moveSnake(movement);
                currentMovement = movement;
                movement = Movements.NONE;
            } else {
                snake.moveSnake();
                movement = Movements.NONE;
            }

            if (snake.boundsCheck(grid.getCols() * grid.getCellSize(),
                    grid.getRows() * grid.getCellSize())) {
                System.out.println("Snake hit the bounds");
                break;
            }

            if (snake.selfCollisionCheck()) {
                System.out.println("Snake hit the self");
                break;
            }
        }
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