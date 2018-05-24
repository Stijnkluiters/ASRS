
import com.fazecast.jSerialComm.*;

import java.io.*;
import java.util.ArrayList;

abstract public class Arduino implements ArduinoInterface {

    private static ArrayList<SerialPort> serialPorts = new ArrayList<>();

    private static ArrayList<String> serialNames = new ArrayList<>();

    protected InputStream input;

    protected OutputStream output;

    protected SerialPort currentPort;

    protected ArrayList<Product> products;

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void writeCurrentPort(int number) {
        try {
            for (int i = 0; i < 1; ++i) {
                output.write(number);
                System.out.println("Written: " + number);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCurrentPort(String string) {
        try {
            for (int i = 0; i < 1; ++i) {
                output.write(string.getBytes());
                System.out.println("Written: " + string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCurrentPort() {
        currentPort.openPort();
        if (!currentPort.isOpen()) {
            System.out.println("Port not available");
            return;
        }
        System.out.println("Port initialized!");
        output = currentPort.getOutputStream();


        currentPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if (serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }
                input = currentPort.getInputStream();
                try {
                    int command = 0;
                    for (int j = 0; j < 1; j++) {
                        command = (byte) input.read();
                    }
                    input.close();
                    System.out.println("Received: " + command);
                    receiveCommand(command);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public Arduino(String portName) {
        this.setCurrentPort(portName);
        this.openCurrentPort();
    }

    public static ArrayList<String> gatherPorts() {
        SerialPort[] tempSerialPorts = SerialPort.getCommPorts();

        if (tempSerialPorts.length > 0) {
            for (SerialPort port : tempSerialPorts) {
                System.out.println(port.getSystemPortName());
                serialPorts.add(port);
                serialNames.add(port.getSystemPortName());
            }
        } else {
            System.out.println("Geen poorten beschikbaar");
            serialNames.add("Geen poort beschikbaar");
        }
        return serialNames;
    }

    public void setCurrentPort(String name) {
        for (SerialPort port : serialPorts) {
            if (port.getSystemPortName().equalsIgnoreCase(name)) {
                currentPort = port;
                currentPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 3000, 3000);
            }
        }
    }
}
