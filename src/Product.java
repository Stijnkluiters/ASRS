import java.awt.*;
import java.util.Random;

public class Product {
    public String name;
    private int height;
    private int x;
    private int y;

    private Color color;
    public Product(String name,int height, int x, int y,Color color){
        this.name=name;
        this.height=height;
        this.x=x;
        this.y=y;
        this.color=color;
    }

    public Product(String name, int height){
        this.name= name;
        this.height=height;
    }
    public Product(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public String getName(){
        return name;
    }
    public String toString(){
        return name +" " + height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {

        if(color == null) {
            Random rand = new Random();
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            this.color = new Color(r, g, b);
        } else {
            this.color = color;
        }

    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
