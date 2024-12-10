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

    public SnakeBlocks(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        //color = Color.GREEN;
        rectangle = new Rectangle(x+1,y+1,width,height);
        rectangle.setColor(color);
        this.color = color;

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
    public Color getColor(){
        return this.color;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
