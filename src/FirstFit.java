import java.util.ArrayList;

public class FirstFit extends Algorithm {

    private int indexBox = 0;
    private ArrayList<Product> products;

    public FirstFit(ArrayList<Product> products) {
        Algorithm.selectedAlgorithm = "FirstFit";
        this.products= products;
    }

    public void startCalculation(){
        for(int i = 0; i < products.size()-1; i++) {
            calculate(products.get(i));
            if(i==products.size()-1){
                calculationAndPdf(products.get(i));
            }
        }
    }

    protected void calculate(Product product) {
        //Working code
        boolean isStored = false;
        for(Storage box: boxes) {
            if(!isStored && box.addProduct(product)) {
                isStored = true;
                break;
            }
        }

        //IF there is no place for storage in the old boxes create a new one
        if(!isStored) {
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
