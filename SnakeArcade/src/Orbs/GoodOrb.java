package Orbs;

import Game.Grid;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Rectangle;
import com.codeforall.online.simplegraphics.pictures.Picture;


/**
 * Represents a "Good Orb"  in the game,
 * gives standard 100 points to the player when collected.
 * This orb increases the score
 */
public class GoodOrb implements SnakeOrbs {

    private int score = 100;  // score of a good orb
    private Rectangle rectangle;
    private Grid grid;
    private boolean active = false;
    private int buffer = +1;
    private Picture picture;

    /**
     * Constructs a new {@code BonusOrb} with the specified grid for positioning.
     *
     * @param grid the game grid to determine orb placement.
     */
    public GoodOrb(Grid grid){
        this.grid = grid;
    }

    /**
     * Spawns the Good orb at a random location on the grid.
     * The orb is drawn as a rectangle and with a picture
     */
    @Override
    public void randomSpawn() {
        int randomCol = (int) (Math.random() * (grid.getCols()-1));
        int randomRow = (int) (Math.random() * (grid.getRows()-1));
        this.active = true;
        rectangle = new Rectangle(grid.columnToX(randomCol)+1, grid.rowToY(randomRow)+1, grid.getCellSize()-2, grid.getCellSize()-2);
        picture = new Picture(rectangle.getX()-1,rectangle.getY()-1,"resources/IntelliJ_18_18.png");
        //picture = new Picture(rectangle.getX()-1,rectangle.getY()-1,"IntelliJ_18_18.png"); //THIS IS ONLY FOR THE ANT BUILD
        //rectangle.setColor(Color.WHITE);
        //rectangle.draw();
        picture.draw();
    }


    /**
     * Returns the score points associated with this orb.
     *
     * @return the score points value.
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Returns the x-coordinate of the orb's position.
     *
     * @return the x-coordinate in pixels.
     */
    @Override
    public int getX() {
       return rectangle.getX();
    }

    /**
     * Returns the y-coordinate of the orb's position.
     *
     * @return the y-coordinate in pixels.
     */
    @Override
    public int getY() {
        return rectangle.getY();
    }


    /**
     * Deletes the bonus orb from the game, making it inactive.
     * Removes the graphical representation of the orb.
     */
    @Override
    public void delete(){
        this.active = false;
        rectangle.delete();
        picture.delete();
    }

    /**
     * Returns the buffer value for the good orb.
     * This increments the size of the snake (+1)
     *
     * @return the buffer value.
     */
    @Override
    public int getBuffer(){
        return this.buffer;
    }

    /**
     * Checks if the bonus orb is currently active in the game.
     *
     * @return {@code true} if the orb is active, {@code false} otherwise.
     */
    @Override
    public boolean active() {
        return this.active;
    }

}
