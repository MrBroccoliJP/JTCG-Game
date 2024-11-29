package Game;

import Input.MyKeyboard;
import SnakeBody.Snake;

public class Game {
    Snake snake;

    public Game(){
        MyKeyboard myKeyboard = new MyKeyboard();
        snake = new Snake(200, 200);
        myKeyboard.setSnake(snake);
        myKeyboard.init();
    }
    public void init() throws InterruptedException{
        while(1<2){
            try {
                Thread.sleep(1000);
                snake.moveSnake();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void keyboardInput(){

    }
}
