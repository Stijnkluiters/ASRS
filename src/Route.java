import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Stijn Kluiters
 */
public class Route {

    private Map<ArrayList<Product>, Float> routesWithDistance;

    private long currentTime;
    private long oldTime;
    private long runningTime;

    private String algorithm;
    private ArrayList<Product> subroute;
    private Product target;
    private Drawpanel drawpanel;

    public Route(Drawpanel drawpanel) {
        /**
         * HashMap makes absolutely no guarantees about the iteration order. It can (and will) even change completely when new elements are added.
         * TreeMap will iterate according to the "natural ordering" of the keys according to their compareTo() method (or an externally supplied Comparator).
         *  Additionally, it implements the SortedMap interface, which contains methods that depend on this sort order.
         * LinkedHashMap will iterate in the order in which the entries were put into the map
         */
        this.routesWithDistance = new LinkedHashMap<>();
        this.drawpanel = drawpanel;
    }

    @SuppressWarnings("unchecked")
    public <T> void updateRoute(String from, ArrayList<T> route, Product t) {
        this.algorithm = from;
        this.subroute = (ArrayList<Product>) route;
        this.target = t;
        // TIME
        this.oldTime = this.currentTime;
        this.currentTime = System.currentTimeMillis();

        if (this.oldTime != 0) {
            this.runningTime += (this.currentTime - this.oldTime);
        }

        // DISTANCE
        float totalDistance = 0;
        if (this.target == null) {
            totalDistance = this.getRouteDistance(subroute);
        }

        this.routesWithDistance.put(this.subroute, totalDistance);
        System.out.println("Total time for "+ from + " in Milli seconds: " + runningTime);


        drawpanel.update();
    }

    public ArrayList<Product> getSubroute() {
        return this.subroute;
    }


    private float getRouteDistance(ArrayList<Product> p) {
        // DISTANCE
        float totalDistance = 0;
        for (int i = 0; i < p.size() - 1; i++) {
            double x1 = p.get(i).getX();
            double y1 = p.get(i).getY();
            double x2 = p.get(i + 1).getX();
            double y2 = p.get(i + 1).getY();
            double temp = this.getDistance(x1, x2, y1, y2);

            totalDistance += temp;
        }
        return totalDistance;
    }

    /**
     * get the distance between 2 points http://www.teacherschoice.com.au/maths_library/analytical%20geometry/alg_15.htm
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return sqrt(x²,y²)
     */
    private double getDistance(double x1, double x2, double y1, double y2) {
        return Math.hypot(x1-x2, y1-y2);
    }

    public long getRunningTime() {
        return this.runningTime;
    }

    public void removeProduct() {
        this.getSubroute().remove(0);
        this.drawpanel.update();
    }
}
