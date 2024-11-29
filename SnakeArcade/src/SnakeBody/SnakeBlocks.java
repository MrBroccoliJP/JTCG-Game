package SnakeBody;

import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Rectangle;

public class SnakeBlocks {
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle rectangle;
    private Color color;

    public SnakeBlocks(int x, int y){
        this.x = x;
        this.y = y;
        color = Color.GREEN;
        rectangle = new Rectangle(x,y,width,height);
        rectangle.setColor(color);
        rectangle.fill(); // Fill the rectangle
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Rectangle getRectangle() {
        return rectangle;
    }
}
