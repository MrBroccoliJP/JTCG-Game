package Input;


import SnakeBody.Snake;
import com.codeforall.online.simplegraphics.keyboard.Keyboard;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.online.simplegraphics.keyboard.KeyboardHandler;

public class MyKeyboard implements KeyboardHandler {
    private Keyboard keyboard;
    private Snake snake;

    public void init(){
        keyboard = new Keyboard(this);

        KeyboardEvent goRight = new KeyboardEvent();
        goRight.setKey(KeyboardEvent.KEY_D);
        goRight.setKey(KeyboardEvent.KEY_RIGHT);
        goRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(goRight);

        KeyboardEvent goLeft = new KeyboardEvent();
        goLeft.setKey(KeyboardEvent.KEY_A);
        goLeft.setKey(KeyboardEvent.KEY_LEFT);
        goLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(goLeft);

        KeyboardEvent goUp = new KeyboardEvent();
        goUp.setKey(KeyboardEvent.KEY_W);
        goUp.setKey(KeyboardEvent.KEY_UP);
        goUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(goUp);

        KeyboardEvent goDown = new KeyboardEvent();
        goDown.setKey(KeyboardEvent.KEY_S);
        goDown.setKey(KeyboardEvent.KEY_DOWN);
        goDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(goDown);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_D || keyboardEvent.getKey() == KeyboardEvent.KEY_RIGHT)  {
            System.out.println("move right");
            snake.moveSnake(Snake.Movement.RIGHT); //this is asynchronous at the moment
        }
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_A||keyboardEvent.getKey() == KeyboardEvent.KEY_LEFT)  {
            System.out.println("move left");
            snake.moveSnake(Snake.Movement.LEFT); //this is asynchronous at the moment
        }
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_W||keyboardEvent.getKey() == KeyboardEvent.KEY_UP)  {
            System.out.println("move up");
            snake.moveSnake(Snake.Movement.UP); //this is asynchronous at the moment
        }
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_S||keyboardEvent.getKey() == KeyboardEvent.KEY_DOWN)  {
            System.out.println("move down");
            snake.moveSnake(Snake.Movement.DOWN); //this is asynchronous at the moment
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
    public void setSnake(Snake snake){
        this.snake = snake;
    }

}
