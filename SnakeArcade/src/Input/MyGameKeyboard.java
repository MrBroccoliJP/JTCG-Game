package Input;


import Game.Game;
import com.codeforall.online.simplegraphics.keyboard.Keyboard;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.online.simplegraphics.keyboard.KeyboardHandler;

public class MyGameKeyboard implements KeyboardHandler {
    private Keyboard keyboard;
    private Game game;

    public void init(Game game){
        this.game = game;
        keyboard = new Keyboard(this);

        addKey(KeyboardEvent.KEY_RIGHT);
        addKey(KeyboardEvent.KEY_LEFT);
        addKey(KeyboardEvent.KEY_UP);
        addKey(KeyboardEvent.KEY_DOWN);
        addKey(KeyboardEvent.KEY_D);
        addKey(KeyboardEvent.KEY_A);
        addKey(KeyboardEvent.KEY_S);
        addKey(KeyboardEvent.KEY_W);
    }

    private void addKey(int eventKey){
        KeyboardEvent newEvent = new KeyboardEvent();
        newEvent.setKey(eventKey);
        newEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(newEvent);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_D || keyboardEvent.getKey() == KeyboardEvent.KEY_RIGHT)  {
            System.out.println("move right");
            game.keyboardInput(Movements.RIGHT); //this is asynchronous at the moment
        }
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_A||keyboardEvent.getKey() == KeyboardEvent.KEY_LEFT)  {
            System.out.println("move left");
            game.keyboardInput(Movements.LEFT); //this is asynchronous at the moment
        }
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_W||keyboardEvent.getKey() == KeyboardEvent.KEY_UP)  {
            System.out.println("move up");
            game.keyboardInput(Movements.UP); //this is asynchronous at the moment
        }
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_S||keyboardEvent.getKey() == KeyboardEvent.KEY_DOWN)  {
            System.out.println("move down");
            game.keyboardInput(Movements.DOWN); //this is asynchronous at the moment
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }

}
