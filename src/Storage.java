import java.util.ArrayList;

public class Storage {
    private ArrayList<Product> box;

    private int MAXBOXSIZE = 100;

    public Storage(){
        box = new ArrayList<Product>();
    }


    public void getProductsHeight(){
        for(Product product: box) {
            System.out.println(product.getHeight());
        }
    }

    public ArrayList<Product> getProducts(){
        return box;
    }

    public boolean doesItFit(Product product) {
        return (this.getCurrentCapacity() + product.getHeight()) <= MAXBOXSIZE;
    }

    /**
     * Compare the added product against the current capacity
     */
    public boolean addProduct(Product product) {
        if(doesItFit(product)) {
            this.box.add(product);
            return true;
        }
        return false;
    }

    public int getCurrentCapacity() {
        int sum = 0;
        for(Product product : box) {
            sum += product.getHeight();
        }
        return sum;
    }
    public String toString(){
        String toString = new String();
        for(Product product:box){
            toString += product.getName() + " " + product.getHeight() + " ";
        }

        return toString + ": " +  getCurrentCapacity();
    }

}

