package view.game;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Dialog {

    private static final Dimension DIALOG_SIZE = new Dimension(350, 150);

    public static boolean showDialog(String title, String message) {
            final JDialog dialog = new JDialog((JFrame) null, title, true);
            final JOptionPane optionPane = new JOptionPane(message,
                            JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);

            optionPane.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                            String prop = e.getPropertyName();

                            if (dialog.isVisible() && (e.getSource() == optionPane)
                                            && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                                    dialog.setVisible(false);
                            }
                    }
            });

            dialog.setContentPane(optionPane);
            dialog.setSize(DIALOG_SIZE);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            return ((Integer) optionPane.getValue()).intValue() == JOptionPane.YES_OPTION;
    }
}
