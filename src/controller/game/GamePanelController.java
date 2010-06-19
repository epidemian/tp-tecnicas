package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import model.game.Player;
import view.game.GamePanel;
import view.game.LineElementsMarketPanel;
import view.game.RawMaterialsMarketPanel;
import view.game.ToolBarPanel;
import view.game.edition.EditionActions;
import view.game.edition.KeyInputActionMapper;
import controller.MainController;
import view.game.ResearchLabPanel;

public class GamePanelController {

	private static final int UPDATE_INTERVAL = 40;
	private static final int TICK_INTERVAL = 500;
	
	private boolean isPaused = true;
	private int timeCount = 0;
	
	private ContainerAdapter gamePanelRemovedListener;
	private JToggleButton playButton;
	private JToggleButton pauseButton;
	private GamePanel gamePanel;
	private Player player;

	public ContainerAdapter getGamePanelRemovedListener() {
		return gamePanelRemovedListener;
	}

	public GamePanelController(final Player player, final GamePanel gamePanel,
			final MainController mainController) {

		this.gamePanel = gamePanel;
		this.player = player;
		EditionActions editionActions = new EditionActions(gamePanel, player);
		KeyInputActionMapper mapper = new KeyInputActionMapper(editionActions);
		mapper.mapActions(gamePanel.getInputMap(), gamePanel.getActionMap());

		final LineElementsMarketPanel lineElementsMarketPanel = new LineElementsMarketPanel();
		new LineElementsMarketPanelController(player, lineElementsMarketPanel,
				editionActions);

		final RawMaterialsMarketPanel rawMaterialsMarketPanel = new RawMaterialsMarketPanel();
		new RawMaterialsMarketPanelController(player, rawMaterialsMarketPanel,
				gamePanel);

		int balance = player.getBudget().getBalance();
		final ResearchLabPanel labPanel = new ResearchLabPanel();
		new ResearchLabPanelController(player, labPanel);

		gamePanel.getBudgetPanel().setMoneyBalance(balance);

		ToolBarPanel toolBar = gamePanel.getToolBarPanel();

		JButton lineElementsMarketButton = toolBar
				.getLineElementsMarketButton();
		lineElementsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				gamePanel.setToolPanel(lineElementsMarketPanel);
			}
		});

		JButton rawMaterialsMarketButton = toolBar
				.getRawMaterialsMarketButton();
		rawMaterialsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				gamePanel.setToolPanel(rawMaterialsMarketPanel);
			}
		});

		JButton researchLabButton = toolBar.getLabButton();
		researchLabButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				gamePanel.setToolPanel(labPanel);
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
					player.sellWarehouse();
					mainController.setGroundSelectionPanel(player);
				}
			}
		});

		final Timer mainLoopTimer = new Timer(UPDATE_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainLoop();
			}
		});
		mainLoopTimer.start();

		playButton = toolBar.getPlayButton();
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playButtonPressed();
			}
		});

		pauseButton = toolBar.getPauseButton();
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pauseButtonPressed();
			}
		});

		gamePanelRemovedListener = new GamePanelRemovedListener(mainLoopTimer);
		
		updateView();
	}

	private void updateView() {
		updateTimeView();
		updateBudgetView();
	}

	private void updateBudgetView() {
		int balance = this.player.getBudget().getBalance();
		this.gamePanel.getBudgetPanel().setMoneyBalance(balance);
	}

	private void updateTimeView() {
		String dateString = this.player.getDate();
		this.gamePanel.setTimeLabel(dateString);
	}

	protected void mainLoop() {

		if (!this.isPaused) {
			this.timeCount += UPDATE_INTERVAL;
			while (this.timeCount >= TICK_INTERVAL) {
				this.timeCount -= TICK_INTERVAL;
				this.player.updateTick();
				updateView();
			}
		}
		repaintGroundPanel();
	}

	private void repaintGroundPanel() {
		this.gamePanel.getGroundPanelContainer().getGroundPanel().repaint();
	}

	private void pauseButtonPressed() {
		this.isPaused = true;
		updatePlayPauseButtons();
	}

	private void playButtonPressed() {
		this.isPaused = false;
		updatePlayPauseButtons();
	}

	private void updatePlayPauseButtons() {
		this.pauseButton.setSelected(isPaused);
		this.playButton.setSelected(!isPaused);
	}

}

final class GamePanelRemovedListener extends ContainerAdapter {
	private final Timer mainLoopTimer;

	GamePanelRemovedListener(Timer mainLoopTimer) {
		this.mainLoopTimer = mainLoopTimer;
	}

	@Override
	public void componentRemoved(ContainerEvent e) {
		System.out.println("TIMER STOP!");
		mainLoopTimer.stop();
	}
}
