package Game.Types;

import Input.Movements;

public interface GameType {
    void start();
    void gameKeyboardInput(Movements movement);
    void end();
    void delete();
}
