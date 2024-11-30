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

        double random_x = Math.random();
        double random_y = Math.random();
        int padding_x = 0;
        int padding_y = 0;
        if (random_x < 0.5) {
            padding_x = Grid.PADDING;
        } else {
            padding_x = -Grid.PADDING;
        }
        if (random_y < 0.5) {
            padding_y = Grid.PADDING;
        } else {
            padding_y = -Grid.PADDING;
        }

        int x = (int) Math.floor(random_x * (grid.getCols() * grid.getCellSize() + 1) + padding_x);
        int y = (int) Math.floor(random_y * (grid.getRows() * grid.getCellSize() + 1) + padding_y);

        rectangle = new Rectangle(x, y, 18, 18);
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
