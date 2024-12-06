package Input;


import Game.Game;
import com.codeforall.online.simplegraphics.keyboard.Keyboard;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.online.simplegraphics.keyboard.KeyboardHandler;
/**
 * Handles keyboard input for the game by implementing the {@link KeyboardHandler} interface.
 * This class initializes keyboard events and maps them to game movements.
 */
public class MyGameKeyboard implements KeyboardHandler {
    private Keyboard keyboard;
    private Game game;


    /**
     * Initializes the keyboard handler and sets up key event listeners for the specified game.
     *
     * @param game the game instance to associate with this keyboard handler.
     */
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

    /**
     * Adds a key press event listener for the specified key.
     *
     * @param eventKey the key code for which a listener should be added.
     */
    private void addKey(int eventKey){
        KeyboardEvent newEvent = new KeyboardEvent();
        newEvent.setKey(eventKey);
        newEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(newEvent);
    }

    /**
     * Handles a key press event and translates it to a game movement command.
     *
     * @param keyboardEvent the keyboard event triggered when a key is pressed.
     */
    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_D || keyboardEvent.getKey() == KeyboardEvent.KEY_RIGHT)  {
            System.out.println("move right");
            game.gameKeyboardInput(Movements.RIGHT);
        }
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_A||keyboardEvent.getKey() == KeyboardEvent.KEY_LEFT)  {
            System.out.println("move left");
            game.gameKeyboardInput(Movements.LEFT);
        }
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_W||keyboardEvent.getKey() == KeyboardEvent.KEY_UP)  {
            System.out.println("move up");
            game.gameKeyboardInput(Movements.UP);
        }
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_S||keyboardEvent.getKey() == KeyboardEvent.KEY_DOWN)  {
            System.out.println("move down");
            game.gameKeyboardInput(Movements.DOWN);
        }
    }

    /**
     * Handles a key release event. This method is currently not implemented.
     *
     * @param keyboardEvent the keyboard event triggered when a key is released.
     */
    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        //not used in this game
    }

}
