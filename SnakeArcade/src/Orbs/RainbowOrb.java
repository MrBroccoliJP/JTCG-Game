package Orbs;

import Game.Map;
import Game.ResourcePath;
import com.codeforall.online.simplegraphics.graphics.Rectangle;
import com.codeforall.online.simplegraphics.pictures.Picture;
/**
 * Represents a "Rainbow Orb"  in the game,
 * gives triple points (compared with Good Orb) to the player when collected.
 * This orb increases the score and changes the colors of the game for some cycles
 */
public class RainbowOrb implements SnakeOrbs{

        private int score = 300;  // score of a good orb
        private Rectangle rectangle;
        private Map map;
        private boolean active = false;
        private int buffer = +3;
        private Picture picture;

        /**
         * Constructs a new {@code BonusOrb} with the specified map for positioning.
         *
         * @param map the game map to determine orb placement.
         */
        public RainbowOrb(Map map){
            this.map = map;
        }

        /**
         * Spawns the Bonus orb at a random location on the map.
         * The orb is drawn as a rectangle and with a picture
         */
        @Override
        public void randomSpawn() {
            int randomCol = (int) (Math.random() * (map.getCols()-1));
            int randomRow = (int) (Math.random() * (map.getRows()-1));
            this.active = true;
            rectangle = new Rectangle(map.columnToX(randomCol)+1, map.rowToY(randomRow)+1, map.getCellSize()-2, map.getCellSize()-2);
            picture = new Picture(rectangle.getX()-1,rectangle.getY()-1, ResourcePath.RESOURCE_PATH + "Rainbow_18_18.png");
            //picture = new Picture(rectangle.getX()-1,rectangle.getY()-1,"VIM_18_18.png"); //THIS IS ONLY FOR THE ANT BUILD
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
         * Returns the x-coordinate of the bonus orb's position.
         *
         * @return the x-coordinate in pixels.
         */
        @Override
        public int getX() {
            return rectangle.getX();
        }

        /**
         * Returns the y-coordinate of the bonus orb's position.
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
            //rectangle.translate(-map.getCols(),-map.getRows());
            rectangle.delete();
            picture.delete();
        }

        /**
         * Returns the buffer value for the bonus orb.
         * This increments the size of the snake (+2)
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
