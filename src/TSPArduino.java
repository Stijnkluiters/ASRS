import java.util.ArrayList;
import java.util.Collections;

public class TSPArduino extends Arduino {

    private ArrayList<Product> tempArray;

    private Product nextProduct;
    private int tempIndex;
    private boolean isFinished = false;
    private BPPArduino bppArduino;
    private boolean isAtStart;

    // opening the port depending on String
    public TSPArduino(String portName) {
        super(portName);
//        sendCommand(2);
    }

    private ArrayList<Product> getTemp() {

        if (tempArray == null) {
            tempArray = new ArrayList<>();
            //reversing because the algorithm expecting us to go from right to left and back
            Collections.reverse(products);
            for (Product product : products) {
                tempArray.add(product);
            }
        }
        return tempArray;
    }

    public void start() {
        if (products == null) {
            popUp.error("Er zijn nog geen producten geimporteerd in de arduino");
        }
        sendCommand(2);
    }

    public boolean closeCurrentPort() {
        return currentPort.closePort();
    }


    @Override
    public void receiveCommand(int commandNumber) {
        try {
            switch (commandNumber) {
                /**
                 * Met deze commando wordt er vanuit de Arduino
                 * van de ophaal robot een bericht gestuurd naar
                 * de Java-applicatie dat er een product is opgehaald en klaar staat
                 * voor de volgende coördinaten
                 */
                case 1:
                    if(isFinished) {
                        popUp.notify("Alle producten zijn opgehaald");
                        return;
                    }
                    tempIndex++;
                    // gather the next product out of the temporary arraylist
                    System.out.println("Current index: " + tempIndex);
                    // als alle producten zijn opgehaald moet de robot terug naar de start positie
                    if (tempArray.size() == 0) {
                        System.out.println("Products are empty 60");

                            if (!isFinished && isAtStart) {

                                System.out.println("trying to send 0");
                                sendCommand(0);
//                                currentPort.closePort();
                                isFinished = true;
                            } else {
                                System.out.println("trying to send 3");
                                sendCommand(3);
                                isAtStart = true;
                            }
                    } else if (tempIndex % 3 == 0) {
                        sendCommand(3);
                        System.out.println("Normaly started BPP NOW");
//                        this.writeCurrentPort("3");
                    } else {
                        try {
                            System.out.println("Trying to send 2");
                            sendCommand(2);
                            this.route.removeProduct();
                        } catch (IndexOutOfBoundsException e) {
                            popUp.notify("Alle producten zijn opgehaald");
                            System.out.println("alle producten zijn opgehaald");
                        }
                    }
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("producten zijn op.. regel 46");
            System.out.println(e.getMessage());
        }
    }

    public Product getNextProduct() {
        if (nextProduct == null) {
            System.out.println("Next product is null");
            this.nextProduct = this.getTemp().get(0);
            System.out.println(this.nextProduct + " Nice");
        }
        return nextProduct;
    }

    @Override
    public void sendCommand(int commandNumber) {
        try {
            switch (commandNumber) {
                /**
                 * Met deze commando wordt er vanuit Java een bericht gestuurd naar de Arduino van de ophaal robot.
                 * Er wordt gecommuniceerd dat de ophaal robot de commando heeft ontvangen en begrepen.
                 */
                case 0:
                    // send this command to send the robot back to the starting position
                    this.writeCurrentPort("0");
//                    popUp.notify("Alle producten opgehaald TSP is klaar");
                    break;
                case 2:
                    // geeft coordinaten door van het volgende product.
                    /**
                     * 2 is commando
                     * x = 2e cijfer
                     * y = 3e cijfer
                     */
                    nextProduct = getTemp().get(0);
                    String coordinets = "2";
                    coordinets += nextProduct.getX();
                    coordinets += nextProduct.getY();
                    this.writeCurrentPort(coordinets);
                    System.out.println("-------------------------");
                    System.out.println("Removing: " + nextProduct.name);
                    tempArray.remove(0);
                    break;
                case 3:
                    this.writeCurrentPort("3");
                    popUp.notify("Terug naar de start positie om te lossen");
                    // init the BPPArduino. from here the BPP arduino must be activated and
                    // communication needs to be started.
//                    bppArduino.setProducts(products);
//                    bppArduino.start();

                    // return to start point.
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("producten zijn op.. regel 82");
            System.out.println(e.getMessage());
        }
    }

    public void setBppArduino(BPPArduino bppArduino) {
        this.bppArduino = bppArduino;
    }
}
