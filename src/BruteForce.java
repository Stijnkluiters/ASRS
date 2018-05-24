import java.util.*;

class BruteForce implements AlgorithmTSPInterface<ArrayList<Product>> {

    private final Route route;
    private final String name;
    private double optimalDistance = 0.0;
    private ArrayList<Product> optimalRoute;
    private Product startPoint;

    public BruteForce(Route route) {
        this.route = route;
        this.name = "Brute force";

    }

    public void bruteForceFindBestRoute(
            ArrayList<Product> r,
            ArrayList<Product> citiesNotInRoute
    ) {
        if (!citiesNotInRoute.isEmpty()) {
            for (int i = 0; i < citiesNotInRoute.size(); i++) {
                Product justRemoved = citiesNotInRoute.remove(0);
                ArrayList<Product> newRoute = (ArrayList<Product>) r.clone();
                newRoute.add(justRemoved);

                this.bruteForceFindBestRoute(newRoute, citiesNotInRoute);
                citiesNotInRoute.add(justRemoved);
            }
        } else //if(citiesNotInRoute.isEmpty())
        {
            if (isBestRoute(r)) {
                this.optimalRoute = r;
                this.startPoint = this.optimalRoute.get(0);
                this.route.updateRoute(name, r, null);
            }
        }

    }

    private double getDistance(double x1, double x2, double y1, double y2) {
        double distance = Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);
        return Math.sqrt(distance);
    }

    /**
     * Get the total distance of the given route.
     * @param route List<Package>
     * @return double
     */
    private double getDistance(ArrayList<Product> route) {
        double distance = 0;
        ArrayList<Product> temp = new ArrayList<Product>(route);
        for (int i = 0; i < temp.size()-1; i++) {
            Product current = temp.get(i);
            Product next = temp.get(i+1);
            this.route.updateRoute(name, temp, next);
            distance += this.getDistance(current.getX(), next.getX(), current.getY(), next.getY());
        }
        return distance;
    }

    private boolean isBestRoute(ArrayList<Product> r) {
        double tempDistance = this.getDistance(r);

        if(this.optimalDistance == 0.0) {
            this.optimalDistance = tempDistance;
        }

        if(tempDistance < optimalDistance) {
            optimalDistance = tempDistance;
            return true;
        }

        return false;
    }

    @Override
    public Route getRoute() {
        return this.route;
    }

    @Override
    public <T> ArrayList<T> execute(ArrayList<T> products) {
        ArrayList<Product> route = new ArrayList<Product>();
        this.bruteForceFindBestRoute(route, (ArrayList<Product>) products);



        return (ArrayList<T>) this.optimalRoute;
    }
}
