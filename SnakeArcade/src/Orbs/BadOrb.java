package Orbs;

import Game.Map;
import Game.ResourcePath;
import com.codeforall.online.simplegraphics.graphics.Rectangle;
import com.codeforall.online.simplegraphics.pictures.Picture;

/**
 * Represents a "Bad Orb" in the game, which penalizes the player when collected.
 * This orb decreases the score
 */
public class BadOrb implements SnakeOrbs {

    private int score = -100;  // score of a good orb
    private Rectangle rectangle;
    private Map map;
    private boolean active = false;
    private int buffer = -1;
    private Picture picture;

    /**
     * Constructs a new {@code BadOrb} with the specified map for positioning.
     *
     * @param map the game map to determine orb placement.
     */
    public BadOrb(Map map){
        this.map = map;
    }

    /**
     * Spawns the bad orb at a random location on the map.
     * The orb is drawn as a rectangle and with a picture
     */
    @Override
    public void randomSpawn() {
        int randomCol = (int) (Math.random() * (map.getCols()-1));
        int randomRow = (int) (Math.random() * (map.getRows()-1));
        this.active = true;
        rectangle = new Rectangle(map.columnToX(randomCol)+1, map.rowToY(randomRow)+1, 18, 18);
        //rectangle.setColor(Color.PINK);
        picture = new Picture(rectangle.getX()-1,rectangle.getY()-1, ResourcePath.RESOURCE_PATH + "BAD_ORB_18_18.png");
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
