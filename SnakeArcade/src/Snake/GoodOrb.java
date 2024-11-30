package Snake;

import Game.Grid;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Rectangle;

public class GoodOrb implements SnakeOrbs {
    private int x;
    private int y;
    private Rectangle rectangle;
    private Grid grid;
    @Override
    public void randomSpawn() {
        int random_x = (int)Math.round(Math.random()*(grid.getCols()+1));
        int random_y = (int)Math.round(Math.random()*(grid.getRows()+1));
        rectangle = new Rectangle(random_x, random_y, 18, 18);
        rectangle.setColor(Color.RED);
        rectangle.fill();

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
