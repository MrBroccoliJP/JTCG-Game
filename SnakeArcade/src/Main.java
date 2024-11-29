//import com.codeforall.online.simplegraphics.*;
import com.codeforall.online.simplegraphics.graphics.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Canvas.setMaxX(400);
        Canvas.setMaxY(400);

        Rectangle rectangle = new Rectangle(10,10,10,10);
        rectangle.setColor(Color.RED);
        rectangle.draw();
        rectangle.fill();

    }
}