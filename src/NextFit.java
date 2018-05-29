import java.util.ArrayList;

public class NextFit extends Algorithm {
    private ArrayList<Product> products;

    private int indexBox = 0;
    public NextFit(ArrayList<Product> products) {
        Algorithm.selectedAlgorithm = "Next-Fit";
        this.products= new ArrayList<Product>(products);
    }

    public void startCalculation(){
        for(int i = 0; i < products.size(); i++) {
            calculate(products.get(i));
            if(i==products.size()-1){
                calculationAndPdf(products.get(i));
            }
        }

    }


    protected void calculate(Product product){

        Storage currentBox = boxes.get(boxes.size()-1);
//        Storage currentBox = boxes.get(indexBox);

        if(currentBox.addProduct(product)) {
            // DONE
        } else {
            Storage newBox = createNewBox();
            newBox.addProduct(product);
            System.out.println(currentBox);
        }

    }
    public void calculationAndPdf(Product product){
//        calculate(product);
        PdfCreator pdfCreator = new PdfCreator();
        pdfCreator.setBoxes(boxes);
        pdfCreator.CreatePdf();
    }



}
