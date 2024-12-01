package Snake;

import Game.Grid;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Rectangle;

public class GoodOrb implements SnakeOrbs {
    private int col;
    private int row;
    private int score = 100;  // score of a good orb
    private Rectangle rectangle;
    private Grid grid;
    private boolean active = false;
    @Override

    public void randomSpawn() {
        int randomCol = (int) (Math.random() * (grid.getCols()-1));
        int randomRow = (int) (Math.random() * (grid.getRows()-1));
        this.active = true;
        rectangle = new Rectangle(grid.columnToX(randomCol)+1, grid.rowToY(randomRow)+1, 18, 18);
        rectangle.setColor(Color.RED);
        rectangle.fill();
    }

    @Override
    public void spawn(int x, int y) {
        this.active = true;
        rectangle = new Rectangle(x, y, 18, 18);
        rectangle.setColor(Color.PINK);
        rectangle.fill();
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getX() {
       return rectangle.getX();
    }

    @Override
    public int getY() {
        return rectangle.getY();
    }

    @Override
    public int getCol() {
        return this.col;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public void delete(){
        this.active = false;
        rectangle.delete();
    }

    @Override
    public boolean active() {
        return this.active;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
