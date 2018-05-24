import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * @author Stijn Kluiters
 */
public class Drawpanel extends JPanel {

    private final int PANEL_WIDTH = 333;
    private final int PANEL_HEIGHT = 300;

    private int width;
    private int height;
    private int incrementx;
    private int incrementy;

    private Product target;
    private BufferedImage image;

    private Route route;
    private ArrayList<int[]> drawList;
    private ArrayList<Product> subRoute;
    private ArrayList<Product> points;
    private Graphics2D imageGraphics;

    public void update() {


        this.target = this.route.getTarget();
        this.subRoute = this.route.getSubroute();

        this.createImage();
        this.render();
        try {
            Thread.sleep(0);
        } catch (InterruptedException ex) {
//            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public Drawpanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        drawList = new ArrayList<>();
        width = 1;
        height = 1;

    }

    /**
     * Setup the private variables with a minimum of 1.
     * @param x int
     * @param y int
     */
    private void setWidthHeight(int x, int y) {
        width = x;
        height = y;
        if (width == 0) {
            width = 1;
        }
        if (height == 0) {
            height = 1;
        }
    }

    public void setroute(Route route) {
        this.route = route;
    }


    public void setPoints(ArrayList<Product> points) {
        this.points = points;
    }

    public void render() {
        Graphics g = this.getGraphics();
        super.paint(g);
        g.drawImage(this.image, 0, 0, this);
        g.dispose();
    }

    public void setGrid(int x, int y) {
        this.setWidthHeight(x, y);
        repaint();
    }

    /**
     * This function is used for the product position.
     * coords[0] is the X coordinate
     * coords[1] is the Y coordinate
     *
     * @param incrementx int
     * @param incrementy int
     * @param hor int
     * @param ver int
     * @return
     */
    private int[] getLoc(int incrementx, int incrementy, int hor, int ver) {
        int coords[] = new int[2];
        coords[0] = (hor * incrementx) + (incrementx / 2);
        coords[1] = (ver * incrementy) + (incrementy / 2);
        return coords;
    }

    /**
     * Creating lines and drawings for the products.
     */
    private void drawGrid() {
        incrementx = this.getWidth() / width;
        int x = incrementx;
        incrementy = this.getHeight() / height;
        int y = incrementy;
        while (this.getWidth() - x > 20) {
            this.imageGraphics.drawLine(x, 20, x, this.getHeight() - 10);
            x += incrementx;
        }
        while (this.getHeight() - y > 20) {
            this.imageGraphics.drawLine(10, y, this.getWidth() - 10, y);
            y += incrementy;
        }
        // drawing all points on grid
        for (Product p : this.points) {
            drawPointForProduct(p);
        }
    }

    /**
     * setting up a point for the Graphical side
     * @param p Product
     */
    public void drawPointForProduct(Product p) {
        int[] coordinates = this.getLoc(this.incrementx, this.incrementy, p.getX(), p.getY());
        drawList.add(this.getLoc(this.incrementx, this.incrementy, p.getX(), p.getY()));
        this.imageGraphics.fillRect(coordinates[0] - 4, coordinates[1] - 4, width, height);

        this.imageGraphics.setColor(null);
        this.imageGraphics.fillOval(coordinates[0] - 4, coordinates[1] - 4, 15, 15);
        this.imageGraphics.setColor(p.getColor());
        this.imageGraphics.fillOval(coordinates[0] - 4, coordinates[1] - 4, 15, 15);
    }

    /**
     * create an image with the given height and width. in the image we create a grid 5x5.
     * Display the product points and draw lines between them depending on the order of the array.
     */
    private void createImage() {
        image = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.imageGraphics = image.createGraphics();
        this.imageGraphics.setColor(Color.BLACK);
        this.drawGrid();
        if (this.subRoute != null) {
            for (int i = 0; i < this.subRoute.size() - 1; i++) {
                Product next = this.subRoute.get(i + 1);
                Product current = this.subRoute.get(i);

                int[] nextCoordinates = this.getLoc(incrementx, incrementy, next.getX(), next.getY());
                int[] currentCoordinates = this.getLoc(incrementx, incrementy, current.getX(), current.getY());

                imageGraphics.setColor(Color.BLUE);
                imageGraphics.setStroke(new BasicStroke(4));
                imageGraphics.drawLine(currentCoordinates[0], currentCoordinates[1], nextCoordinates[0], nextCoordinates[1]);
            }
        }

    }

}
