package Snake;

import Game.Grid;
import com.codeforall.online.simplegraphics.graphics.Rectangle;

public class GoodOrb implements SnakeOrbs {
    private int x;
    private int y;
    private Rectangle rectangle;
    private Grid grid;
    @Override
    public void randomSpawn() {
        rectangle = new Rectangle(x, y, 0, 0);
    }

    @Override
    public void getScore() {

    }

    @Override
    public void getX() {

    }

    @Override
    public void getY() {

    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
