import javax.swing.*;

public class popUp {

    public static void error(String error) {
        JOptionPane.showMessageDialog(null, error, "Error: ", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void notify(String message) {
        JOptionPane.showMessageDialog(null, message, "Bericht: ", JOptionPane.INFORMATION_MESSAGE);
    }

}

