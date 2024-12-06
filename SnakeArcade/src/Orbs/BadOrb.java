package Orbs;

import Game.Grid;
import com.codeforall.online.simplegraphics.graphics.Rectangle;
import com.codeforall.online.simplegraphics.pictures.Picture;

/**
 * Represents a "Bad Orb" in the game, which penalizes the player when collected.
 * This orb decreases the score
 */
public class BadOrb implements SnakeOrbs {

    private int score = -100;  // score of a good orb
    private Rectangle rectangle;
    private Grid grid;
    private boolean active = false;
    private int buffer = -1;
    private Picture picture;

    /**
     * Constructs a new {@code BadOrb} with the specified grid for positioning.
     *
     * @param grid the game grid to determine orb placement.
     */
    public BadOrb(Grid grid){
        this.grid = grid;
    }

    /**
     * Spawns the bad orb at a random location on the grid.
     * The orb is drawn as a rectangle and with a picture
     */
    @Override
    public void randomSpawn() {
        int randomCol = (int) (Math.random() * (grid.getCols()-1));
        int randomRow = (int) (Math.random() * (grid.getRows()-1));
        this.active = true;
        rectangle = new Rectangle(grid.columnToX(randomCol)+1, grid.rowToY(randomRow)+1, 18, 18);
        //rectangle.setColor(Color.PINK);
        picture = new Picture(rectangle.getX()-1,rectangle.getY()-1,"resources/BAD_ORB_18_18.png");
        //picture = new Picture(rectangle.getX()-1,rectangle.getY()-1,"BAD_ORB_18_18.png"); //**THIS IS ONLY FOR THE ANT BUILD
        picture.draw();
        //rectangle.fill();
    }


    /**
     * Returns the score penalty associated with this orb.
     *
     * @return the score penalty value.
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Returns the x-coordinate of the bad orb's position.
     *
     * @return the x-coordinate in pixels.
     */
    @Override
    public int getX() {
        return rectangle.getX();
    }

    /**
     * Returns the y-coordinate of the bad orb's position.
     *
     * @return the y-coordinate in pixels.
     */
    @Override
    public int getY() {
        return rectangle.getY();
    }

    /**
     * Deletes the bad orb from the game, making it inactive.
     * Removes the graphical representation of the orb.
     */
    @Override
    public void delete(){
        this.active = false;
        rectangle.delete();
        picture.delete();
    }

    /**
     * Returns the buffer value for the bad orb.
     * This decrements the size of the snake
     *
     * @return the buffer value.
     */
    @Override
    public int getBuffer(){
        return this.buffer;
    }

    /**
     * Checks if the bad orb is currently active in the game.
     *
     * @return {@code true} if the orb is active, {@code false} otherwise.
     */
    @Override
    public boolean active() {
        return this.active;
    }

}
