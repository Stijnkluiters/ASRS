
import java.lang.Package;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stijn Kluiters
 */
public class Worker extends Thread {

    private AlgorithmTSPInterface a = null;
    private ArrayList<Product> products;
    private ThreadListener listener;

    public Worker(AlgorithmTSPInterface a, ArrayList<Product> products) {
        this.a = a;
        this.products = products;
    }

    public AlgorithmTSPInterface getAlgorithm() {
        return this.a;
    }

    public void setListener(ThreadListener listener) {
        this.listener = listener;
    }

    public void notifyListener() {
        this.listener.notifyOnDone(this);

    }

    @Override
    public void run() {
        try {
            this.a.execute(products);
        } finally {
            this.notifyListener();
        }
    }

}
