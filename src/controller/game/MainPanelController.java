package controller.game;

import static model.utils.ArgumentUtils.checkNotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.game.Player;
import model.utils.Config;
import view.game.MainPanel;
import controller.MainController;

public class MainPanelController {

    private static final String[] DIFFICULTY_LEVELS = { "Easy", "Normal",
			"Hard" };
    private static final int[] INITIAL_MONEY = { 10000, 5000, 2000 };

    public MainPanelController(MainPanel mainPanel, MainController mainControler, Config config){
        checkNotNull(mainPanel, "mainPanel");

        initDifficultyCombo(mainPanel);
        initActionListeners(mainPanel, mainControler, config);
    }

    private void initDifficultyCombo(MainPanel mainPanel) {

        JComboBox difficultyCombo = mainPanel.getDifficultyCombo();

        difficultyCombo.removeAllItems();
        for (int i = 0; i < DIFFICULTY_LEVELS.length; i++) {
            difficultyCombo.addItem(DIFFICULTY_LEVELS[i]);
        }
    }

    private void initActionListeners(MainPanel mainPanel,
        final MainController mainControler, final Config config) {

        final JTextField nameField = mainPanel.getPlayerNameTextArea();
        final JComboBox difficultyCombo = mainPanel.getDifficultyCombo();

        ActionListener al = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                    int selectedItem = difficultyCombo.getSelectedIndex();
                    int money = INITIAL_MONEY[selectedItem];
                    String name = nameField.getText();

                    if (!name.isEmpty()) {
                            Player player = new Player(name, money, mainControler.getInputFactory(),config);
                            mainControler.setGroundSelectionPanel(player);
                    } else
                            JOptionPane.showMessageDialog(
                                            mainControler.getMainFrame(),
                                            "Please insert you name", "Empty name",
                                            JOptionPane.ERROR_MESSAGE);
            }
        };

        mainPanel.addStartActionListener(al);
        nameField.addActionListener(al);
    }
}
