package SnakeBody;

import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Rectangle;

import java.util.LinkedList;

public class SnakeBody {
    private LinkedList<SnakeBlocks> snakeBlocksList;
    private int head_x;
    private int head_y;
    private int block_size = 20;


    public SnakeBody(int x, int y) {
        //starting location
        snakeBlocksList = new LinkedList<>();
        this.head_x = x;
        this.head_y = y;

        //starts with 3 blocks
        for (int i = 0; i < 3; i++) {
            SnakeBlocks block = new SnakeBlocks(head_x - (i * block_size), head_y);
            snakeBlocksList.add(block);
            block.getRectangle().draw(); // Explicitly draw each block
            block.getRectangle().fill();
        }

    }


}
