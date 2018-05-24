import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class AlgorithmSelector extends JComboBox implements ActionListener {


    private JComboBox cb;

    private  static String[] algorithms = { "Selecteer uw algoritme" , "Worst-fit", "Next-fit", "First-fit", "Brute force","First-fit decreasing" };


    public AlgorithmSelector() {

        cb = new JComboBox(algorithms);
        cb.addActionListener(this);
    }

    public JComboBox getCb() {
        return cb;
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String algorithm = (String)cb.getSelectedItem();

        // convert the options array to list object
        // so we can check if the selected value is inside the option list
        List<String> list = Arrays.asList(algorithms);
        // remove the first option of the list, we assume that the first option is the one without a value. Used for more user friendly interface.
        list.remove(0);

        if(!list.contains(algorithm)) {
            JOptionPane.showMessageDialog(null, "Er is geen algoritme geselecteerd", "Foutmelding", JOptionPane.INFORMATION_MESSAGE);
            return;
        }



    }
}