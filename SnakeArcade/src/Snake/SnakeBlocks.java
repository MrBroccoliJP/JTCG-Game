package Snake;

import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Rectangle;

public class SnakeBlocks {
    private int x;
    private int y;
    private int width = 18;
    private int height = 18;
    private Rectangle rectangle;
    private Color color;

    public SnakeBlocks(int x, int y){
        this.x = x;
        this.y = y;
        color = Color.GREEN;
        rectangle = new Rectangle(x,y,width,height);
        rectangle.setColor(color);
        //rectangle.fill(); // Fill the rectangle
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }


    public Rectangle getRectangle() {
        return rectangle;
    }
}