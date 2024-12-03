package Input;

import Game.Game;
import com.codeforall.online.simplegraphics.keyboard.Keyboard;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.online.simplegraphics.keyboard.KeyboardHandler;

public class MyMenuKeyboard implements KeyboardHandler {
    private Keyboard keyboard;
    private Game game;

    public void init(Game game){
        this.game = game;
        keyboard = new Keyboard(this);

        addKey(KeyboardEvent.KEY_SPACE);

    }

    private void addKey(int eventKey){
        KeyboardEvent newEvent = new KeyboardEvent();
        newEvent.setKey(eventKey);
        newEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(newEvent);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        game.menuKeybordInput(keyboardEvent.getKey());
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
}
