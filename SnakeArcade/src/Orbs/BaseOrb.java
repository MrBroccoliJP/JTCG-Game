package Orbs;

import Game.Map;
import com.codeforall.online.simplegraphics.graphics.Rectangle;
import com.codeforall.online.simplegraphics.pictures.Picture;

public abstract class BaseOrb implements SnakeOrbs{
    private int score = -100;  // score of a good orb
    private Rectangle rectangle;
    private Map map;
    private boolean active = false;
    private int buffer = -1;
    private Picture picture;
    private String picturePath;

    public BaseOrb(Map map, int score, String PicturePath){
        this.map = map;
        this.picturePath = PicturePath;
        this.score = score;
    }

    /**
     * Spawns the orb at a random location on the map.
     * The orb is drawn as a rectangle and with a picture
     */
    @Override
    public void randomSpawn() {
        int randomCol = (int) (Math.random() * (map.getCols()-1));
        int randomRow = (int) (Math.random() * (map.getRows()-1));
        this.active = true;
        rectangle = new Rectangle(map.columnToX(randomCol)+1, map.rowToY(randomRow)+1, 18, 18);
        picture = new Picture(rectangle.getX()-1,rectangle.getY()-1, picturePath);
        picture.draw();
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
