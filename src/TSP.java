
import javax.swing.*;
import java.util.ArrayList;

public class TSP implements Service, ThreadListener {

    private final Worker worker;
    private final Drawpanel drawPanel;
    private JLabel timeValue;
    public static TSPArduino arduino;


    public TSP(ArrayList<Product> packages, int algorithm, Drawpanel drawpanel) {
        this.drawPanel = drawpanel;
        Route route = new Route(drawpanel);
        switch (algorithm) {
                case 0:
                    this.worker = new Worker(new BruteForce(route), packages);
                    break;
                case 1:
                    this.worker = new Worker(new FarthestInsertion(route), packages);
                    break;
                case 2:
                    this.worker = new Worker(new NearestNeighbour(route), packages);
                    break;
                case 3:
                    this.worker = new Worker(new RandomAlgorithm(route), packages);
                    break;
                default:
                    this.worker = new Worker(new NearestNeighbour(route), packages);
                break;
        }
    }

    @Override
    public void serve() {
        this.worker.setListener(this);
        this.worker.start();
    }


    public Route getRoute() {
        return this.worker.getAlgorithm().getRoute();
    }


    @Override
    public void notifyOnDone(Thread t) {
        drawPanel.update();

        String timeLabel = Long.toString(
                getRoute().getRunningTime()
        );
        this.timeValue.setText(timeLabel);
        arduino.setRoute(getRoute());

        t.interrupt();
    }

    public void setTimeValue(JLabel timeValue) {
        this.timeValue = timeValue;
    }

    public void setArduino(TSPArduino nonStaticArduino) {
        arduino = nonStaticArduino;
    }
}
