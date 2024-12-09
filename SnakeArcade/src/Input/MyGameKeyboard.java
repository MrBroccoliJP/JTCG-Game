package Input;


import Game.Game;
import Game.Types.GameType;
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
    private GameType gameType;
    private int gameStage = 0;
    //gameStage 0 = menu
    //gameStage 1 = game


    /**
     * Initializes the keyboard handler and sets up key event listeners for the specified game.
     *
     * @param game the game instance to associate with this keyboard handler.
     */
    public void init(Game game, GameType gameType) {
        this.game = game;
        this.gameType = gameType;
        keyboard = new Keyboard(this);
        addKey(KeyboardEvent.KEY_SPACE);
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

        if(gameStage == 1) {
            if (keyboardEvent.getKey() == KeyboardEvent.KEY_D || keyboardEvent.getKey() == KeyboardEvent.KEY_RIGHT) {
                gameType.gameKeyboardInput(Movements.RIGHT);
            }
            if (keyboardEvent.getKey() == KeyboardEvent.KEY_A || keyboardEvent.getKey() == KeyboardEvent.KEY_LEFT) {
                gameType.gameKeyboardInput(Movements.LEFT);
            }
            if (keyboardEvent.getKey() == KeyboardEvent.KEY_W || keyboardEvent.getKey() == KeyboardEvent.KEY_UP) {
                gameType.gameKeyboardInput(Movements.UP);
            }
            if (keyboardEvent.getKey() == KeyboardEvent.KEY_S || keyboardEvent.getKey() == KeyboardEvent.KEY_DOWN) {
                gameType.gameKeyboardInput(Movements.DOWN);
            }
        }
        else if(gameStage == 0) {
                game.menuKeyboardInput(keyboardEvent.getKey());
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

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void setGameStage(int gameStage) {
        this.gameStage = gameStage;
    }

}
