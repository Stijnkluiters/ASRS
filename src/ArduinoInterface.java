/**
 * @author Stijn Kluiters
 */
interface ArduinoInterface {

    public void receiveCommand(int commandNumber);

    public void sendCommand(int commandNumber);

}
