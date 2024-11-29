//import com.codeforall.online.simplegraphics.*;

import Game.Game;
import com.codeforall.online.simplegraphics.graphics.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Canvas.setMaxX(800);
        Canvas.setMaxY(800);

        Game game = new Game();
        game.start();



    }
}