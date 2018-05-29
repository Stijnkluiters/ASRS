import java.util.ArrayList;

public class BPPArduino extends Arduino {

    private ArrayList<Storage> boxes;

    public BPPArduino(String portName) {
        super(portName);
    }

    @Override
    public void receiveCommand(int commandNumber) {
        if (commandNumber == 1) {
            if (products.size() != 0) {
                sendCommand(5);
            } else {
                this.currentPort.closePort();
            }

        }
    }

    @Override
    public void sendCommand(int commandNumber) {

        if (commandNumber == 5) {
            // hier de logica bepalen welke doos het voorwerp inmoet.
            int boxNumber = 0;
            Product nextProduct = this.products.get(0);
            System.out.println(products);
            for (Storage box : boxes) {
                for (Product product : box.getProducts()) {
                    if (nextProduct == product) {
                        boxNumber = box.getBoxNumber();
                    }
                }
            }
            this.products.remove(0);
            String command = "5";
            command += boxNumber;
            this.writeCurrentPort(command);


        }
    }

    public void setBoxes(ArrayList<Storage> boxes) {
        this.boxes = boxes;
    }

    public void start() {





        if (this.boxes == null) {
            popUp.error("Er zijn geen producten geinitialiseerd");
        }
        ArrayList<Product> tempProducts = new ArrayList<>();
        for (Storage box : boxes) {
            for (Product product : box.getProducts()) {
                tempProducts.add(product);
            }
        }
        this.setProducts(tempProducts);
        sendCommand(5);

    }
}
