import java.util.ArrayList;
import java.util.Collections;

public class FirstFitIncreasing extends Algorithm {
    private ArrayList<Product> products;

    private ArrayList<Product> sortedArray;

    public FirstFitIncreasing(ArrayList<Product> products) {
        Algorithm.selectedAlgorithm = "FirstFitDecreasing";
        this.products = new ArrayList<Product>(products);
        this.sortedArray = new ArrayList<Product>();
    }

    public void startCalculation() {

        if (products.size() > 0) {
            //init the variable so no errors will be thrown.
            Product LowestHeight = products.get(0);
            for (Product product : products) {
                if(product.getHeight() < LowestHeight.getHeight()) {
                    LowestHeight = product;
                }
            }
            products.remove(LowestHeight);
            sortedArray.add(LowestHeight);
            startCalculation();
        }


        FirstFit firstFit = new FirstFit(sortedArray);
        firstFit.startCalculation();
        this.boxes = firstFit.getBoxes();
//            calculate(sortedArray.get(i));
    }

}
