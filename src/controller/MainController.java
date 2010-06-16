package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.game.GamePanelController;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import model.game.Game;
import model.warehouse.Ground;
import view.MainFrame;
import view.game.GamePanel;
import view.game.GroundPanelContainer;
import view.game.GroundSelectionPanel;
import view.game.MainPanel;

public class MainController {

	private Game game;
	private MainFrame mainFrame;
        
        private int comboGroundSelectionIndex;

        private static final String GROUND_PREFIX = "Ground ";
        private static final String[] DIFFICULTY_LEVELS = { "Easy", "Normal", "Hard" };
        private static final int[] INITIAL_MONEY = {10000,15000,20000};
        private static final float RENT_FACTOR = 0.01f;

        private static final Dimension MAIN_PANEL_SIZE = new Dimension(640, 480);
        private static final Dimension GROUND_SELECTION_PANEL_SIZE = new Dimension(293, 553);

	public MainController(Game game, final MainFrame mainFrame) {
		this.game = game;
		this.mainFrame = mainFrame;

		setGamePanel();
                //setMainPanel();

		mainFrame.setVisible(true);
		mainFrame.requestFocus();

		/*
		 * Main loop. Refresh 20 frames per second. TODO: Sacar main loop de
		 * acá...
		 */
		Timer mainLoopTimer = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.repaint();
			}
		});
		mainLoopTimer.start();

		// Give focus to GamePanel when selected.
		mainFrame.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				mainFrame.getContentPane().requestFocusInWindow();
			}
		});
	}

	private void setGamePanel() {

        GroundPanelContainer groundPanel
                    = new GroundPanelContainer(this.game.getGround());
		GamePanel gamePanel = new GamePanel(groundPanel);
		new GamePanelController(this.game, gamePanel);
                setMainFramePanel(gamePanel);
		
		// TODO
//		ToolBarPanel toolbar = gamePanel.getToolbar();
//		toolbar.addSellActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO
//				//game.sellGround();
//			}
//		});
		
	}

        private void setMainPanel(){
           MainPanel mainPanel = new MainPanel();

           final JComboBox difficultyCombo = mainPanel.getDifficultyCombo();
           final JTextField nameTextField = mainPanel.getPlayerNameTextArea();

           // Init combo.
           difficultyCombo.removeAllItems();
           for (int i = 0; i < DIFFICULTY_LEVELS.length; i++) {
                difficultyCombo.addItem(DIFFICULTY_LEVELS[i]);
           }

           ActionListener al = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int selectedItem = difficultyCombo.getSelectedIndex();
                int money = INITIAL_MONEY[selectedItem];
                String name = nameTextField.getText();

                if (!name.isEmpty()) {
                    MainController.this.game.setInitialMoney(money);
                    MainController.this.game.setPlayerName(name);
                    MainController.this.setGroundSelectionPanel();
                } else
                      JOptionPane.showMessageDialog(MainController.this.mainFrame,
                              "Please insert you name", "Empty name",
                              JOptionPane.ERROR_MESSAGE);
                }
            };

           mainPanel.addStartActionListener(al);
           nameTextField.addActionListener(al);

           this.mainFrame.setResizable(false);
           this.mainFrame.setSize(MAIN_PANEL_SIZE);
           this.mainFrame.setLocationRelativeTo(null);
           this.setMainFramePanel(mainPanel);
        }

        private void setGroundSelectionPanel(){

            final List<Ground> grounds = this.game.getGrounds();
            
            GroundPanelContainer gamePanel = null;
            GroundSelectionPanel selectionPanel = null;

            /*
             * TODO que pasa cuando la lista esta vacía!
             */
            if (!grounds.isEmpty()){
                gamePanel = new GroundPanelContainer(grounds.get(0));
                selectionPanel = new GroundSelectionPanel(gamePanel);
            }
            else{

            }

            JComboBox buyCombo = selectionPanel.getGroundCombo();
            buyCombo.removeAllItems();
            int i = 0;
            for (Ground ground : grounds)
                buyCombo.addItem(GROUND_PREFIX + ++i);

            buyCombo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                throw new UnsupportedOperationException("Not supported yet.");

                /*
                 * Elige terreno.
                 * Le asigna el terreno a GroundPanelContainer.
                 * Actualiza las labels.
                 */
            }
            });

            selectionPanel.addBuyButtonActionListener(new ActionListener() {

            /*
             * TODO Refactorizar! Codigo repetido en action listeners.
             */
            @Override
            public void actionPerformed(ActionEvent ae) {

                // Crear Purchase Warehouse!
                Ground ground = grounds.get(MainController.this.comboGroundSelectionIndex);
                MainController.this.game.setGround(ground);
                MainController.this.game.getBudget().decrement(ground.getPrice());
                MainController.this.setGamePanel();
            }
            });

            selectionPanel.addRentButtonActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                // Crear Rent Warehouse!
                Ground ground = grounds.get(MainController.this.comboGroundSelectionIndex);
                MainController.this.game.setGround(ground);
                MainController.this.game.getBudget().decrement((int)(ground.getPrice() * RENT_FACTOR));
                MainController.this.setGamePanel();
            }
            });

            selectionPanel.addBackButtonActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                MainController.this.setMainPanel();
            }
            });


           this.mainFrame.setResizable(false);
           this.mainFrame.setSize(GROUND_SELECTION_PANEL_SIZE);
           this.mainFrame.setLocationRelativeTo(null);
           this.setMainFramePanel(selectionPanel);
        }

	private void setMainFramePanel(JPanel panel) {
		panel.setOpaque(true);
		this.mainFrame.setContentPane(panel);
	}
}
