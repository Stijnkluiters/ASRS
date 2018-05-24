import javax.swing.*;

public class ScoresPanel extends JPanel {
    private JLabel timeLabel;

    public void setTime(long time) {
        String timeString = Long.toString(time);
        this.timeLabel.setText(timeString);
    }

    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }
}
