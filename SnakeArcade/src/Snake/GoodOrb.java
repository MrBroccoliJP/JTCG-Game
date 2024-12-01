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
        int randomCol = (int) (Math.random() * grid.getCols());
        int randomRow = (int) (Math.random() * grid.getRows());

        rectangle = new Rectangle(grid.columnToX(randomCol), grid.rowToY(randomRow), 18, 18);
        rectangle.setColor(Color.RED);
        rectangle.fill();
    }

    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
        rectangle = new Rectangle(x, y, 18, 18);
        rectangle.setColor(Color.PINK);
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
