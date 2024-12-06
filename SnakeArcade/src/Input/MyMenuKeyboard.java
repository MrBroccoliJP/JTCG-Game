package Input;

import Game.Game;
import com.codeforall.online.simplegraphics.keyboard.Keyboard;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.online.simplegraphics.keyboard.KeyboardHandler;

/**
 * Handles keyboard input specifically for the game menu by implementing the {@link KeyboardHandler} interface.
 * This class listens for specific key presses and communicates them to the game menu system.
 */
public class MyMenuKeyboard implements KeyboardHandler {
    private Keyboard keyboard;
    private Game game;
    /**
     *
     * Initializes the keyboard handler for the menu and sets up key event listeners.
     *
     * @param game the game instance to associate with this keyboard handler.
     */
    public void init(Game game){
        this.game = game;
        keyboard = new Keyboard(this);

        addKey(KeyboardEvent.KEY_SPACE);

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
     * Handles a key press event and forwards the input to the game's menu system.
     *
     * @param keyboardEvent the keyboard event triggered when a key is pressed.
     */
    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        game.menuKeybordInput(keyboardEvent.getKey());
    }

    /**
     * Handles a key release event. This method is currently not implemented.
     *
     * @param keyboardEvent the keyboard event triggered when a key is released.
     */
    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        //not used
    }
}
