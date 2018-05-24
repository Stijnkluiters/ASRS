import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BoxDisplayPanel extends JPanel {
    private ArrayList<Storage> boxes = new ArrayList<>();
    private int PANEL_WIDTH = 1400;
    private int PANEL_HEIGHT =2200;
    private Graphics graphics;
    private boolean isPainted = false;

    public BoxDisplayPanel(){
        setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));

    }


    public void updateGrid() {
        int start = 20;


        if(boxes.size() > 0) {
            int i=0;
            int x=start;
            int y=0;
            int pyChange = 0;
            int width = 250;
            int height= 300;
            //Looping all boxes
            for (Storage box : boxes) {
                this.graphics.setColor(Color.BLACK);
                //Painting the edges of the box
                this.graphics.drawRect(x, y, width, height);


                int py=300+pyChange;
                //Looping all the products in 1 box
                for(Product product: box.getProducts()){
                    int pHeight=0;
                    //Giving a color to each product
                    pHeight=(product.getHeight()*3);

                    this.graphics.setColor(product.getColor());
                    py=py-pHeight;
                    this.graphics.fillRect(x,py,width,pHeight);
                    repaint();
                }

                repaint();
                //Adding a x value for every extra box
                x += 270;
                y += 0;
                // Print out the x and y location of the box
//                System.out.println("X: " + x + " Y: " + y);
                i++;
                //Add a new row every 5 boxes
                if(i%5==0){
                    y+= 370;
                    x=start;
                    pyChange+= 370;
                }

                this.repaint();

            }

            this.repaint();
        } else {
            System.out.println("Geen dozen beschikbaar?");
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.graphics = g;
//
        if(boxes.size() > 0) {
            updateGrid();
        }
//        g.setColor(Color.BLUE);
//        g.fillRect(0, 0, 20, 100);

        repaint();
    }

    public void setBoxes(ArrayList<Storage> boxes) {
        this.boxes = boxes;
    }

}
