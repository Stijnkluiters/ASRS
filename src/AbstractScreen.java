import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AbstractScreen extends JFrame implements ActionListener {
    private JButton jbStart;
    private JButton jbSelecteerOrder;

    public AbstractScreen() {
        setTitle("Title");
        setSize(2000, 1000);
        setLayout(new FlowLayout());

        jbSelecteerOrder = new JButton("Selecteer Order");
        add(jbSelecteerOrder);
        jbSelecteerOrder.addActionListener(this);

        AlgorithmSelector AS = new AlgorithmSelector();
        add(AS.getCb());
        AS.setVisible(true);

        jbStart = new JButton("Start");
        add(jbStart);
        jbStart.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jbSelecteerOrder){
            FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            String file = dialog.getFile();
            System.out.println(file + " chosen.");
        }
    }
}
