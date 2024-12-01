package Snake;

import Game.Grid;
import Input.Movements;
import com.codeforall.online.simplegraphics.graphics.Rectangle;

import java.util.LinkedList;

public class Snake {
    private LinkedList<SnakeBlocks> snakeBlocksList;
    private final int block_size = 20;
    private int blockBuffer = 0;
    private Movements lastMove = Movements.NONE;


    public Snake(int x, int y) {  //x, y starting location
        snakeBlocksList = new LinkedList<>();
        //starts with 3 blocks
        for (int i = 0; i < 3; i++) {
            SnakeBlocks block = new SnakeBlocks(x - (i * block_size), y);
            snakeBlocksList.add(block);
            block.getRectangle().fill();
        }
    }

    public void moveSnake(Movements movement) {
        //if(movement != lastMove) {
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


    private void moveBody(int previousX, int previousY){
        int newPreviousX, newPreviousY; // To store the new position to pass to the next block

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
            }

        }

        if(blockBuffer > 0){
            SnakeBlocks block = new SnakeBlocks(previousX,previousY);
            snakeBlocksList.add(block);
            block.getRectangle().fill();
            blockBuffer--;
        }

    }

    public void setBlockBuffer(int addition) {
        this.blockBuffer+=addition;
    }

    public boolean selfCollisionCheck() {
        SnakeBlocks head = snakeBlocksList.getFirst();
        for (int i = 1; i < snakeBlocksList.size(); i++) {
            SnakeBlocks block = snakeBlocksList.get(i);
            if (head.getRectangle().getX() == block.getRectangle().getX() &&
                    head.getRectangle().getY() == block.getRectangle().getY()) {
                return true;
            }
        }

        return false;
    }

    public int getHeadX(){
        return snakeBlocksList.getFirst().getRectangle().getX();
    }

    public int getHeadY(){
        return snakeBlocksList.getFirst().getRectangle().getY();
    }


}
