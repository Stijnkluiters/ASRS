import java.util.ArrayList;

public class WorstFit extends Algorithm {
    private ArrayList<Product> products;
    private boolean isStored = false;


    public WorstFit(ArrayList<Product> products) {
        Algorithm.selectedAlgorithm = "Next-Fit";
        this.products = new ArrayList<>(products);
    }

    @Override
    protected void startCalculation() {
        for (int i = 0; i < products.size(); i++) {
            calculate(products.get(i));
            if(i==products.size()-1){
                calculationAndPdf(products.get(i));
            }
        }
    }
    private Storage sortDesending(ArrayList<Storage> markedBoxes) {
        Storage lowestHeight = markedBoxes.get(0);
        for(Storage box: markedBoxes) {
            if(box.getCurrentCapacity() < lowestHeight.getCurrentCapacity()) {
                lowestHeight = box;
            }
        }
        // return the lowest box
        return lowestHeight;
    }


    protected void calculate(Product product) {
        //Check if there is a box
        ArrayList<Storage> markedBoxes = new ArrayList<>();
        for(Storage box : boxes) {
             if(box.doesItFit(product)) {
                 markedBoxes.add(box);
             }
        }
        if(markedBoxes.size() > 0) {
            this.sortDesending(markedBoxes).addProduct(product);
        } else {
            Storage box = createNewBox();
            box.addProduct(product);
        }

    }
    public void calculationAndPdf(Product product){
        calculate(product);
        PdfCreator pdfCreator = new PdfCreator();
        pdfCreator.setBoxes(boxes);
        pdfCreator.CreatePdf();
    }
}
