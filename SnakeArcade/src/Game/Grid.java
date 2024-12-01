package Game;
import com.codeforall.online.simplegraphics.graphics.Canvas;
import com.codeforall.online.simplegraphics.graphics.Rectangle;

public class Grid {

    public static final int PADDING = 20;

    private int cellSize;
    private int cols;
    private int rows;

    /**
     * Simple graphics grid constructor with a certain number of rows and columns
     *
     * @param cols number of the columns
     * @param rows number of rows
     */
    public Grid(int cols, int rows, int cellSize) {
        this.cols = cols;
        this.rows = rows;
        this.cellSize = cellSize;
    }

    /**
     * Initializes the field simple graphics rectangle and draws it
     */
    public void init() {
        Canvas.setMaxX((cols * cellSize)+PADDING);
        Canvas.setMaxY((rows * cellSize)+PADDING);
        Rectangle field = new Rectangle(PADDING, PADDING, cols * cellSize - PADDING, rows * cellSize - PADDING);
        field.draw();
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getCols() {
        return this.cols;
    }

    public int getRows() {
        return this.rows;
    }


    /**
     * Auxiliary method to compute the y value that corresponds to a specific row
     *
     * @param row index
     * @return y pixel value
     */
    public int rowToY(int row) {
        return PADDING + cellSize * row;
    }

    /**
     * Auxiliary method to compute the x value that corresponds to a specific column
     *
     * @param column index
     * @return x pixel value
     */
    public int columnToX(int column) {
        return PADDING + cellSize * column;
    }

}