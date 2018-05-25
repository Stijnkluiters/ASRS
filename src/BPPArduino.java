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
            Product nextProduct = products.get(0);
            for (Storage box : boxes) {
                for (Product product : box.getProducts()) {
                    if (nextProduct == product) {
                        boxNumber = box.getBoxNumber();
                    }
                }
            }
            String command = "5";
            command += boxNumber;
            this.writeCurrentPort(command);
            products.remove(0);


        }
    }

    public void setBoxes(ArrayList<Storage> boxes) {
        this.boxes = boxes;
    }

    public void start() {
        if (products == null) {
            popUp.error("Er zijn geen producten geinitialiseerd");
        }
        sendCommand(5);

    }
}
