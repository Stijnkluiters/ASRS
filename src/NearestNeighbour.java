import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author Stijn Kluiters
 */
public class NearestNeighbour implements AlgorithmTSPInterface<ArrayList<Product>> {

    private final String name;
    private ArrayList<Product> subRoute = new ArrayList<>();
    private final Route route;


    public NearestNeighbour(Route route) {
        this.route = route;
        this.name = "Nearest Neighbour";
    }

    @Override
    public Route getRoute() {
        return this.route;
    }

    @Override
    public  ArrayList execute(ArrayList products) {
        ArrayList<Product> controlList = new ArrayList<>();
        controlList.addAll((Collection<? extends Product>) products);

        if (!this.subRoute.contains(products.get(0))) {
            this.subRoute.add((Product) products.get(0));
        }

        for (Product p : this.subRoute) {
            controlList.remove(p);
        }

        int i = 0;
        while (!controlList.isEmpty()) {
            Product pa = this.findNearest(controlList, this.subRoute.get(i));
            this.subRoute.add(pa);
            controlList.remove(pa);
            i++;
        }

        this.route.updateRoute(this.getClass().getSimpleName(), this.subRoute, null);

        return this.removeDuplicates(this.subRoute);
    }


    /**
     * Finds the nearest Product to p
     *
     * @param products List
     * @param p        Product
     * @return Product
     */
    public Product findNearest(List<Product> products, Product p) {
        Product nearest = null;
        double distance = Double.MAX_VALUE;

        for (Product s : products) {
            if (this.getDistance(p.getX(), s.getX(), p.getY(), s.getY()) < distance) {
                this.route.updateRoute(this.getClass().getSimpleName(), this.subRoute, s);
                distance = this.getDistance(p.getX(), s.getX(), p.getY(), s.getY());
                nearest = s;
            }
        }

        return nearest;
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
     * Removes the duplicates by using a Linked Hash Set
     *
     * @param products ArrayList<Product>
     * @return List<Product>
     */
    private ArrayList<Product> removeDuplicates(ArrayList<Product> products) {
        LinkedHashSet lhs = new LinkedHashSet();
        ArrayList<Product> temp = products;
        lhs.addAll(temp);
        temp.clear();
        temp.addAll(lhs);
        lhs.clear();
        lhs = null;
        return temp;
    }


}
