import java.util.ArrayList;
import java.util.Random;

public class RandomAlgorithm implements AlgorithmTSPInterface<ArrayList<Product>> {

    private final Route route;
    private ArrayList<Product> products;
    private ArrayList<Product> newRoute;
    private Product startPoint;

    private final String name;

    public RandomAlgorithm(Route route) {
        this.route = route;
        this.name = "Random algorithm";
    }

    @SuppressWarnings("unchecked")
    public int randInt(int min, int max) {

        Random rn = new Random();
        int range = max - min + 1;
        return rn.nextInt(range) + min;
    }

    private ArrayList<Product> sortRoute() {
        ArrayList<Product> sortedRoute = new ArrayList<>();
        ArrayList<Product> temporaryProductList = this.products;
        while (temporaryProductList.size() > 0) {
            int randomInt = this.randInt(0, temporaryProductList.size() - 1);
            Product product = temporaryProductList.get(randomInt);
            sortedRoute.add(product);
            temporaryProductList.remove(randomInt);
            this.route.updateRoute(this.name, sortedRoute, null);
        }
        this.route.updateRoute(this.name, sortedRoute, null);

        return sortedRoute;
    }

    @Override
    public Route getRoute() {
        return this.route;
    }

    @Override
    public  ArrayList execute(ArrayList products) {
        this.products = products;
        this.startPoint = (Product) products.get(0);
        this.newRoute = sortRoute();
        return newRoute;
    }
}
