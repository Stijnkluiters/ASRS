import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ProductGenerator
{


    public static ArrayList<Product> generateRandomProducts(int countProducts) {

        ArrayList<Product> products = new ArrayList<>();
        for(int i = 0; i < countProducts; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 60 + 1);

            Product product = new Product("Kaas"+i, randomNum);
            product.setColor(null);
            products.add(product);
        }


        return products;
    }
}
