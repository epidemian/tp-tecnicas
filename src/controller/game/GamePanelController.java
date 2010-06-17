package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import model.game.Game;
import view.game.GamePanel;
import view.game.LineElementsMarketPanel;
import view.game.RawMaterialsMarketPanel;
import view.game.ToolBarPanel;
import view.game.edition.EditionActions;
import view.game.edition.KeyInputActionMapper;
import controller.MainController;

public class GamePanelController {

	private boolean isPaused = true;
	private ContainerAdapter gamePanelRemovedListener;
	
	public ContainerAdapter getGamePanelRemovedListener() {
		return gamePanelRemovedListener;
	}

	public GamePanelController(Game game, final GamePanel gamePanel,
			final MainController mainController) {
		
		EditionActions editionActions = new EditionActions(gamePanel, game);
		KeyInputActionMapper mapper = new KeyInputActionMapper(editionActions);
		mapper.mapActions(gamePanel.getInputMap(), gamePanel.getActionMap());

		final LineElementsMarketPanel lineElementsMarketPanel = new LineElementsMarketPanel();
		new LineElementsMarketPanelController(game,
				lineElementsMarketPanel, editionActions);

		final RawMaterialsMarketPanel rawMaterialsMarketPanel = new RawMaterialsMarketPanel();
		new RawMaterialsMarketPanelController(game,
				rawMaterialsMarketPanel, gamePanel);

		int balance = game.getBudget().getBalance();
		gamePanel.getBudgetPanel().setMoneyBalance(balance);

		ToolBarPanel toolBar = gamePanel.getToolBarPanel();

		
		JButton lineElementsMarketButton = toolBar.getLineElementsMarketButton();
		lineElementsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				gamePanel.setToolPanel(lineElementsMarketPanel);
			}
		});
		
		JButton rawMaterialsMarketButton = toolBar.getRawMaterialsMarketButton();
		rawMaterialsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				gamePanel.setToolPanel(rawMaterialsMarketPanel);
			}
		});

		JButton exitButton = toolBar.getExitButton();
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (MainController.showDialog("Exit", "Are you sure?"))
					System.exit(0);
			}
		});

		JButton sellButton = toolBar.getSellButton();
		sellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (MainController.showDialog("Sell", "Are you sure?")) {
					// MainController.this.game.getWarehouse().sell();
					mainController.setGroundSelectionPanel();
				}
			}
		});
		
		final Timer mainLoopTimer = new Timer(40, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.getGroundPanelContainer().getGroundPanel().repaint();
			}
		});
		mainLoopTimer.start();
		
		JToggleButton playButton = toolBar.getPlayButton();
		playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JToggleButton pauseButton = toolBar.getPauseButton();
		pauseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		gamePanelRemovedListener = new ContainerAdapter() {

			@Override
			public void componentRemoved(ContainerEvent e) {
				mainLoopTimer.stop();
			}
		};
	}
	

}
