import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Stijn Kluiters
 */
public class FarthestInsertion implements AlgorithmTSPInterface {

    private final String name;
    private ArrayList<Product> route;
    private Product start;
    private final Route model;
    private ArrayList<Product> subroute;

    public FarthestInsertion(Route route) {
        this.model = route;
        this.name = "Farthest Insertion";
    }

    @Override
    public Route getRoute() {
        return this.model;
    }

    @Override
    public ArrayList execute(ArrayList products) {
        this.route = new ArrayList(products);
        this.start = this.route.get(0);
        return getNewRoute();
    }


    /**
     * This method returns the most optimal route, calculated with the farthest
     * insertion algorithm. The idea is to find the hull of the points, by using
     * the Farthest Selection algorithm. And then inserting is into the new route
     * with a Nearest Insertion. This way you'll always have a circle.
     *
     * @return ArrayList
     */
    public ArrayList<Product> getNewRoute() {
        ArrayList<Product> controlList = new ArrayList<>(this.route);
        this.subroute = new ArrayList<>();
        // Add start point to subroute
        this.subroute.add(this.start);
        //Add farthest from startpoint to subroute
        Point startPoint = new Point(this.start.getX(), this.start.getY());
        Product farthestFromStart = this.findFarthest(startPoint, this.route);
        this.subroute.add(farthestFromStart);
        // Remove the startpoint and farthest from it from the controllist
        for (Product s : this.subroute) {
            controlList.remove(s);
        }
        // Add farthest from the subroute's line or hull to subroute
        Point medPointOfSubroute;
        Product farthestFromSubroute;
        while (!controlList.isEmpty()) {
            // Get the median point for the subroute (x, y)
            medPointOfSubroute = this.getMedPoint(this.subroute);
            // Get the farthest Object from the found point
            farthestFromSubroute = this.findFarthest(medPointOfSubroute, controlList);
            // Insert Product into subroute
            this.subroute = this.insert(this.subroute, farthestFromSubroute);
            // Remove from the control list
            controlList.remove(farthestFromSubroute);
        }
        this.model.updateRoute(name, this.subroute, null);
        return this.subroute;
    }

    /**
     * This method is used to find the best possible spot for
     * the given Product in the given route.
     * It works by injecting the Product into the given route,
     * checking the total distance, and then injecting it into
     * the next position in the list and so on...
     * This will find the route with the minimum possible arc.
     *
     * @param route ArrayList<Product>
     * @param p     Product
     * @return ArrayList<Product>
     */
    private ArrayList<Product> insert(ArrayList<Product> route, Product p) {
        double distance = Double.MAX_VALUE;
        Product subject = p;
        ArrayList<Product> minimumArc = null;
        ArrayList<Product> temp = new ArrayList<>(route);
        for (int i = 1; i < temp.size(); i++) {
            temp.add(i, subject);
//      System.out.println(temp);
            double tempDistance = this.getDistance(temp);
            if (tempDistance < distance) {
                distance = tempDistance;
                minimumArc = new ArrayList<>(temp);
            }
            temp.remove(subject);
            temp.removeAll(Collections.singleton(null));
        }
        return minimumArc;
    }

    /**
     * Get the Product that is the farthest away from the specified point, from the given route.
     *
     * @param p     Point
     * @param route List<Product>
     * @return Product
     */
    private Product findFarthest(Point p, ArrayList<Product> route) {
        double distance = 0;
        int farthestIndex = 0;
        ArrayList<Product> temp = route;

        for (int i = 0; i < temp.size(); i++) {
            Product current = temp.get(i);
            double tempDistance = this.getDistance(p.x, current.getX(), p.y, current.getY());
            if (tempDistance > distance) {
                this.model.updateRoute(name, this.subroute, current);
                distance = tempDistance;
                farthestIndex = i;
            }
        }

        Product farthest = temp.get(farthestIndex);
        return farthest;
    }

    /**
     * Gets the median point from the given subroute.
     *
     * @param subroute ArrayList<Product>
     * @return Point
     */
    private Point getMedPoint(ArrayList<Product> subroute) {
        int medX = 0;
        int medY = 0;
        Point p = new Point();

        for (Product s : subroute) {
            medX += s.getX();
            medY += s.getY();
        }
        // create average point
        medX = (medX / subroute.size());
        medY = (medY / subroute.size());

        p.setLocation(medX, medY);
        return p;
    }

    /**
     * Calculates the distance between a and b using their x and y coordinates
     *
     * @param x1 double
     * @param x2 double
     * @param y1 double
     * @param y2 double
     * @return double
     */
    private double getDistance(double x1, double x2, double y1, double y2) {
        double distance = Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);
        return Math.sqrt(distance);
    }

    /**
     * Get the total distance of the given route.
     *
     * @param route List<Product>
     * @return double
     */
    private double getDistance(ArrayList<Product> route) {
        double distance = 0;
        ArrayList<Product> temp = new ArrayList<>(route);
        for (int i = 0; i < temp.size() - 1; i++) {
            Product current = temp.get(i);
            Product next = temp.get(i + 1);
            distance += this.getDistance(current.getX(), next.getX(), current.getY(), next.getY());
        }
        return distance;
    }
}
