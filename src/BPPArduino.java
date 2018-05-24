import java.util.ArrayList;

public class BPPArduino extends Arduino {

    private ArrayList<Storage> boxes;

    public BPPArduino(String portName) {
        super(portName);
    }

    @Override
    public void receiveCommand(int commandNumber) {
        if(commandNumber == 1) {
            sendCommand(5);
        }
    }

    @Override
    public void sendCommand(int commandNumber) {

        if(commandNumber == 5) {
            // hier de logica bepalen welke doos het voorwerp inmoet.


            this.writeCurrentPort("54");
        }
    }

    public void setBoxes(ArrayList<Storage> boxes) {
        this.boxes = boxes;
    }

    public void start() {
        if(products == null) {
            popUp.error("Er zijn geen producten geinitialiseerd");
        }

        sendCommand(5);

    }
}
