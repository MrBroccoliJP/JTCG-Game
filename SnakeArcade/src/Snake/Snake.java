package Snake;

import Game.Game;
import Game.Grid;
import Input.Movements;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Rectangle;

import java.util.LinkedList;

public class Snake {
    private LinkedList<SnakeBlocks> snakeBlocksList; //snake body
    private final int block_size = Game.CELLSIZE;  //sets the size of the blocks that make up the snake
    private int blockBuffer = 0;  //manages snake growth or shrinkage
    private Movements lastMove = Movements.NONE;
    private Color snakeColor = new Color(0,255,0);
    private boolean rainbowActive = false;


    /**
     * Constructor creates snake at specified starting coordinates
     * Initializes with 3 initial blocks
     *
     * @param x Starting x-coordinate
     * @param y Starting y-coordinate
     */
    public Snake(int x, int y) {  //x, y starting location
        snakeBlocksList = new LinkedList<>();
        snakeColor = new Color(0,255,0); //ensure it starts at the right color
        //starts with 3 blocks
        for (int i = 0; i < 3; i++) {
            SnakeBlocks block = new SnakeBlocks(x - (i * block_size), y ,snakeColor);
            snakeBlocksList.add(block);
            block.getRectangle().fill();
            snakeColor = new Color(0,255-i*2,0);
        }
    }

    /**
     * Move snake in a specified direction
     *
     * @param movement Desired movement direction
     */
    public void moveSnake(Movements movement) {
            lastMove = movement;
            switch(movement){
                case UP:
                    moveUP();
                    break;

                case DOWN:
                    moveDOWN();
                    break;

                case LEFT:
                    moveLEFT();
                    break;

                case RIGHT:
                    moveRIGHT();
                    break;
            }
        //}
    }

    /**
     * Move snake in the last known direction
     */
    public void moveSnake() {
        switch(lastMove){
            case UP:
                moveUP();
                break;

            case DOWN:
                moveDOWN();
                break;

            case LEFT:
                moveLEFT();
                break;

            case RIGHT:
                moveRIGHT();
                break;
        }
    }

    /**
     * Moves the snake UP
     */
    private void moveUP() {
        int previousX, previousY; // To store the previous position of the current block

        // Move the head block
        SnakeBlocks head = snakeBlocksList.getFirst();
        Rectangle headRectangle = head.getRectangle();

        // Save the head's current position
        previousX = head.getX();
        previousY = head.getY();

        // Move the head rectangle up and update its coordinates
        headRectangle.translate(0, -block_size);
        head.setX(headRectangle.getX());
        head.setY(headRectangle.getY());

        moveBody(previousX, previousY);
    }

    /**
     * Moves the snake Down
     */
    private void moveDOWN() {
        int previousX, previousY; // To store the previous position of the current block

        // Move the head block
        SnakeBlocks head = snakeBlocksList.getFirst();
        Rectangle headRectangle = head.getRectangle();

        // Save the head's current position
        previousX = head.getX();
        previousY = head.getY();

        // Move the head rectangle up and update its coordinates
        headRectangle.translate(0, block_size);
        head.setX(headRectangle.getX());
        head.setY(headRectangle.getY());

        moveBody(previousX, previousY);
    }

    /**
     * Moves the snake Left
     */
    private void moveLEFT() {
        int previousX, previousY; // To store the previous position of the current block

        // Move the head block
        SnakeBlocks head = snakeBlocksList.getFirst();
        Rectangle headRectangle = head.getRectangle();

        // Save the head's current position
        previousX = head.getX();
        previousY = head.getY();

        // Move the head rectangle up and update its coordinates
        headRectangle.translate(-block_size, 0);
        head.setX(headRectangle.getX());
        head.setY(headRectangle.getY());

        moveBody(previousX, previousY);
    }

    /**
     * Moves the snake Right
     */
    private void moveRIGHT() {
        int previousX, previousY; // To store the previous position of the current block

        // Move the head block
        SnakeBlocks head = snakeBlocksList.getFirst();
        Rectangle headRectangle = head.getRectangle();

        // Save the head's current position
        previousX = head.getX();
        previousY = head.getY();

        // Move the head rectangle up and update its coordinates
        headRectangle.translate(block_size, 0);
        head.setX(headRectangle.getX());
        head.setY(headRectangle.getY());

        moveBody(previousX, previousY);
    }


    /**
     * Manages snake body movement graphically and growth/shrinkage
     *
     * @param previousX Previous x-coordinate of preceding block [head]
     * @param previousY Previous y-coordinate of preceding block [head]
     */
    private void moveBody(int previousX, int previousY){
        int newPreviousX, newPreviousY; // To store the new position to pass to the next block
        Color previousBlockColor = Color.PINK; //Just in case.
        // Move the rest of the snake blocks
        for (int i = 1; i < snakeBlocksList.size(); i++) {
            SnakeBlocks block = snakeBlocksList.get(i);
            Rectangle blockRectangle = block.getRectangle();

            if(blockBuffer < 0 && i == snakeBlocksList.size()-1){
                snakeBlocksList.remove(i);
                block.getRectangle().fill();
                block.getRectangle().delete();
                blockBuffer++;
            }
            else {

                // Save the current block's position
                newPreviousX = block.getX();
                newPreviousY = block.getY();

                // Move the block to the old position of the block in front
                blockRectangle.translate(previousX - block.getX(), previousY - block.getY());
                block.setX(blockRectangle.getX());
                block.setY(blockRectangle.getY());

                // Update previous position for the next iteration
                previousX = newPreviousX;
                previousY = newPreviousY;
                previousBlockColor = block.getColor();

            }

        }

        if(blockBuffer > 0){
            SnakeBlocks block;
            if(rainbowActive){
                block = new SnakeBlocks(previousX, previousY, new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            }
            else {
                int greenCalc = Math.max(255 - (snakeBlocksList.size() * 2), 0);
                block = new SnakeBlocks(previousX, previousY, new Color(0,greenCalc , 0));
            }
            snakeBlocksList.add(block);
            block.getRectangle().fill();
            blockBuffer--;
        }

    }

    /**
     * Adjusts snake length via block buffer
     * Prevents snake from shrinking below 3 blocks
     *
     * @param addition Number of blocks to add or remove
     */
    public void setBlockBuffer(int addition) {
        if(snakeBlocksList.size() > 3){
            this.blockBuffer+=addition;
        }
        else{           //PREVENT THE SNAKE FROM GETTING BELOW 3 BLOCKS
            if(addition > 0){
                this.blockBuffer+=addition;
            }
            else{
                this.blockBuffer=0;
            }
        }

    }

    /**
     * Checks if snake has collided with itself
     *
     * @return True if self-collision occurs, false otherwise
     */
    public boolean selfCollisionCheck() {
        SnakeBlocks head = snakeBlocksList.getFirst();
        for (int i = 1; i < snakeBlocksList.size(); i++) {
            SnakeBlocks block = snakeBlocksList.get(i);

            double headX = head.getRectangle().getX();
            double headY = head.getRectangle().getY();
            double headWidth = head.getRectangle().getWidth();
            double headHeight = head.getRectangle().getHeight();

            double blockX = block.getRectangle().getX();
            double blockY = block.getRectangle().getY();
            double blockWidth = block.getRectangle().getWidth();
            double blockHeight = block.getRectangle().getHeight();

            if (headX < blockX + blockWidth &&
                    headX + headWidth > blockX &&
                    headY < blockY + blockHeight &&
                    headY + headHeight > blockY) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return X-coordinate of snake's head
     */
    public int getHeadX(){
        return snakeBlocksList.getFirst().getRectangle().getX();
    }

    /**
     * @return Y-coordinate of snake's head
     */
    public int getHeadY(){
        return snakeBlocksList.getFirst().getRectangle().getY();
    }

    /**
     * Deletes all snake blocks from game
     */
    public void delete(){
        for(SnakeBlocks block : snakeBlocksList) {
            block.getRectangle().delete();
        }
        snakeBlocksList.clear();
    }

    public boolean getRainbowStatus(){
        return rainbowActive;
    }

    public void rainbowEffectToggle(){ //this method acts as a toggle
        Rectangle rectangle;
        if(rainbowActive){
            rainbowActive = false;
            //snakeColor = new Color(0,255,0); //ensure it starts at the right color
            for (int i = 0; i < snakeBlocksList.size(); i++) {
                rectangle = snakeBlocksList.get(i).getRectangle();
                rectangle.setColor(new Color(0, Math.max((255-(i*2)),0) ,0));
                rectangle.fill();
            }
        }
        else {
            rainbowActive = true;
            for (SnakeBlocks block : snakeBlocksList) {
                rectangle = block.getRectangle();
                rectangle.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                rectangle.fill();
            }
        }
    }



}
