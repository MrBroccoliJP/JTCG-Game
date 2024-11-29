package SnakeBody;

import java.util.LinkedList;

public class SnakeBody {
    private LinkedList<SnakeBlocks> SnakeBlocksList;
    private int head_x;
    private int head_y;


    SnakeBody(int x, int y) {
        //starting location
        SnakeBlocksList = new LinkedList<SnakeBlocks>();
        this.head_x = x;
        this.head_y = y;
    }

}
