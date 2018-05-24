import java.util.ArrayList;

public class AlgorithmStarter {

    private ArrayList<Product> products;

    private ArrayList<Storage> boxes;

    public AlgorithmStarter(ArrayList<Product> products) {
        System.out.println(products);
        //RANDOM PRODUCTS FOR TESTING PURPOSES
        if(products.size()==0){
            this.products = ProductGenerator.generateRandomProducts(50);
        }
        else{
            this.products=products;
        }

    }

    public void startFirstFit() {
        FirstFit FirstFit = new FirstFit(products);
        FirstFit.startCalculation();


        this.boxes = FirstFit.getBoxes();
    }

    public void printBoxes() {
        System.out.println("------------------------------------------");
        System.out.println("Amount of boxes: " + boxes.size());
        System.out.println("Amount of products: " + products.size());
        for (Storage box : boxes) {
            System.out.println(box.toString());
        }
    }

    public void startFirstFitDecreasing() {
        FirstFitDecreasing firstFitDecreasing = new FirstFitDecreasing(products);
        firstFitDecreasing.startCalculation();

        this.boxes = firstFitDecreasing.getBoxes();

    }

    public void startFirstFitIncreasing() {
        FirstFitIncreasing firstFitIncreasing= new FirstFitIncreasing(products);
        firstFitIncreasing.startCalculation();

        this.boxes = firstFitIncreasing.getBoxes();

    }

    public void startNextFit() {
        NextFit nextFit = new NextFit(products);
        nextFit.startCalculation();
        this.boxes = nextFit.getBoxes();
    }

    public void startWorstFit() {

        WorstFit WorstFit = new WorstFit(products);
        WorstFit.startCalculation();
        this.boxes = WorstFit.getBoxes();
    }

    public void startAlgorithm(int algorithm) {
        switch (algorithm) {
            case 0:
                startFirstFit();
                break;
            case 1:
                startFirstFitDecreasing();
                break;
            case 2:
                startFirstFitIncreasing();
                break;
            case 3:
                startWorstFit();
                break;
            case 4:
                startNextFit();
            default:
                startNextFit();
                break;
        }
        printBoxes();
    }

    public ArrayList<Storage> getBoxes() {
        return boxes;
    }
}
