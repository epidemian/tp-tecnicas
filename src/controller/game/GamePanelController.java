package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import model.game.GameState;
import model.game.Player;
import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.EmptyTileElement;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;
import view.game.Dialog;
import view.game.GamePanel;
import view.game.InputSelectionPanel;
import view.game.LineElementsMarketPanel;
import view.game.MachineSelectionPanel;
import view.game.OutputSelectionPanel;
import view.game.RawMaterialsMarketPanel;
import view.game.ResearchLabPanel;
import view.game.ToolBarPanel;
import view.game.ValidSequencesPanel;
import view.game.ground.GroundPanel;
import controller.MainController;
import controller.game.edition.EditionActions;
import controller.game.edition.KeyInputActionMapper;
import java.util.Observer;
import view.game.PricesPanel;

public class GamePanelController {

	private static final int UPDATE_INTERVAL = 40;
	private static final int TICK_INTERVAL = 500;

	private boolean isPaused = true;
	private boolean pausePressed = true;
	private int timeCount = 0;

	private ToolBarPanel toolBar;

	private ContainerAdapter gamePanelRemovedListener;
	private JToggleButton playButton;
	private JToggleButton pauseButton;
	private GamePanel gamePanel;
	private Player player;
	private GroundPanelController groundPanelController;

	// Panels controllers.
	private LineElementsMarketPanelController lineElementsMarketPanelController;
	private RawMaterialsMarketPanelController rawMaterialMarketPanelController;

	// Panels.
	private LineElementsMarketPanel lineElementsMarketPanel;
	private RawMaterialsMarketPanel rawMaterialsMarketPanel;
	private ResearchLabPanel labPanel;
	private ValidSequencesPanel validSequencesPanel;
	
	private Refreshable refreshablePanelController = NULL_REFRESHABLE;
	private static Refreshable NULL_REFRESHABLE = new NullRefreshable();

	private MainController mainController;
	private Timer mainLoopTimer;
	
	public ContainerAdapter getGamePanelRemovedListener() {
		return gamePanelRemovedListener;
	}

	public GamePanelController(final Player player, final GamePanel gamePanel,
			final MainController mainController) {

		this.gamePanel = gamePanel;
		this.player = player;
		this.mainController = mainController;

		final EditionActions editionActions = new EditionActions(this, player);
		KeyInputActionMapper mapper = new KeyInputActionMapper(editionActions);
		mapper.mapActions(gamePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW),
				gamePanel.getActionMap());

		initBudgetObserver(player, gamePanel);

		GroundPanel groundPanel = new GroundPanel(player);
		gamePanel.setGroundPanel(groundPanel);
		this.groundPanelController = new GroundPanelController(groundPanel);

		this.toolBar = gamePanel.getToolBarPanel();

		initLineElementsMarket(player, gamePanel, editionActions);
		initRawMaterialMarket(player, gamePanel);
		initLab(player, gamePanel);
		initValidProductionSequences(player);
		initExitButton();
		initSellButton(player, mainController);
		initPrices();

		this.mainLoopTimer = new Timer(UPDATE_INTERVAL,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						mainLoop();
					}
				});

		this.mainLoopTimer.start();

		initPlayButton();
		initPauseButton();

		gamePanelRemovedListener = new GamePanelRemovedListener(mainLoopTimer);

		updateTimeView();
	}

	public void setToolPanelFromSelectionTool(TileElement selectedTileElement) {

		selectedTileElement.accept(new TileElementVisitor() {

			@Override
			public void visitProductionMachine(ProductionMachine machine) {

				MachineSelectionPanel machinePanel = new MachineSelectionPanel();
				refreshablePanelController = new SelectionMachinePanelController(
						player, machinePanel, machine, getGamePanel());
				machinePanel.setProductionMachineLabels();

				getGamePanel().setToolPanel(machinePanel);
			}

			@Override
			public void visitQualityControlMachine(QualityControlMachine machine) {

				MachineSelectionPanel machinePanel = new MachineSelectionPanel();
				refreshablePanelController = new SelectionMachinePanelController(
						player, machinePanel, machine, getGamePanel());
				machinePanel.setQualityControlMachineLabels();

				getGamePanel().setToolPanel(machinePanel);
			}

			@Override
			public void visitInputProductionLineElement(
					InputProductionLineElement inputLineElement) {

				InputSelectionPanel inputPanel = new InputSelectionPanel();
				refreshablePanelController = new InputSelectionPanelController(
						inputLineElement, inputPanel, player);
				getGamePanel().setToolPanel(inputPanel);
			}

			@Override
			public void visitEmptyElement(EmptyTileElement emptyTileElement) {
				// If there is an element selected.
				if (refreshablePanelController != NULL_REFRESHABLE) {
					getGamePanel().setToolPanel(null);
					refreshablePanelController = NULL_REFRESHABLE;
				}
			}

			@Override
			public void visitOutputProductionLineElement(
					OutputProductionLineElement outputLineElement) {

				OutputSelectionPanel outputPanel = new OutputSelectionPanel();
				refreshablePanelController = new OutputSelectionPanelController(
						outputLineElement, outputPanel, player);
				getGamePanel().setToolPanel(outputPanel);
			}

			@Override
			public void visitConveyor(Conveyor conveyor) {
				this.visitEmptyElement(null);
			}

			@Override
			public void visitWall(Wall wall) {
				this.visitEmptyElement(null);
			}
		});
	}

	public GroundPanelController getGroundPanelController() {
		return groundPanelController;
	}

	private void initPauseButton() {
		this.pauseButton = this.toolBar.getPauseButton();
		this.pauseButton.setEnabled(false);
		this.pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				pauseButtonPressed();
				// Enable the construction buttons and it's panel.
				lineElementsMarketPanelController.setEnabled(true);
				toolBar.getLineElementsMarketButton().setEnabled(true);
				playButton.setEnabled(true);
				pauseButton.setEnabled(false);
			}
		});
	}

	private void initPlayButton() {
		this.playButton = this.toolBar.getPlayButton();
		this.playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				playButtonPressed();
				// Disable the construction buttons and it's panel.
				toolBar.getLineElementsMarketButton().setEnabled(false);
				lineElementsMarketPanelController.setEnabled(false);
				playButton.setEnabled(false);
				pauseButton.setEnabled(true);
			}
		});
	}

	private void initBudgetObserver(final Player player,
			final GamePanel gamePanel) {
		refreshBudgetPanel();

		/**
		 * The budget panel is updated by the observer pattern.
		 */
		player.getBudget().addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				refreshBudgetPanel();
			}
		});
	}

	private void refreshBudgetPanel() {
		int balance = this.player.getBudget().getBalance();
		this.gamePanel.getBudgetPanel().setMoneyBalance(balance);
	}

	private void initSellButton(final Player player,
			final MainController mainController) {
		JButton sellButton = toolBar.getSellButton();
		sellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Dialog.showDialog("Sell", "Wanna' sell the warehouse?")) {
					player.sellWarehouse();
					mainController.setGroundSelectionPanel(player);
					disposeGamePanel();
				}
			}
		});
	}

	private void disposeGamePanel() {
		this.player.getBudget().deleteObservers();
		this.mainLoopTimer.stop();
	}

	private void initValidProductionSequences(Player player) {
		this.validSequencesPanel = new ValidSequencesPanel();
		final ValidSequencesPanelController validController = new ValidSequencesPanelController(
				player, validSequencesPanel);

		JButton researchLabButton = toolBar.getValidProductionLinesButton();
		researchLabButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				validController.refresh();
				gamePanel.setToolPanel(validSequencesPanel);
			}
		});
		
	}

	private void initExitButton() {
		JButton exitButton = toolBar.getExitButton();
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (Dialog.showDialog("Exit", "Are you sure?"))
					System.exit(0);
			}
		});
	}

	private void initLab(final Player player, final GamePanel gamePanel) {
		this.labPanel = new ResearchLabPanel();
		final ResearchLabPanelController labController = new ResearchLabPanelController(
				player, labPanel);

		JButton researchLabButton = toolBar.getLabButton();
		researchLabButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				refreshablePanelController = labController;
				gamePanel.setToolPanel(labPanel);
			}
		});
	}

	private void initRawMaterialMarket(final Player player,
			final GamePanel gamePanel) {
		this.rawMaterialsMarketPanel = new RawMaterialsMarketPanel();
		this.rawMaterialMarketPanelController = new RawMaterialsMarketPanelController(
				player, this.rawMaterialsMarketPanel, gamePanel);

		JButton rawMaterialsMarketButton = this.toolBar
				.getRawMaterialsMarketButton();
		rawMaterialsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				refreshablePanelController = rawMaterialMarketPanelController;
				gamePanel.setToolPanel(rawMaterialsMarketPanel);
			}
		});
	}

	private void initLineElementsMarket(final Player player,
			final GamePanel gamePanel, final EditionActions editionActions) {
		this.lineElementsMarketPanel = new LineElementsMarketPanel();
		this.lineElementsMarketPanelController = new LineElementsMarketPanelController(
				player, lineElementsMarketPanel, editionActions, player
						.getConfig());

		JButton lineElementsMarketButton = this.toolBar
				.getLineElementsMarketButton();
		lineElementsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				gamePanel.setToolPanel(lineElementsMarketPanel);
			}
		});
	}

	private void initPrices() {

		final PricesPanel pricesPanel = new PricesPanel();
		final PricesPanelController pricesController = new PricesPanelController(
				pricesPanel, player);

		JButton pricesButton = this.toolBar.getPricesButton();
		pricesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refreshablePanelController = pricesController;
				gamePanel.setToolPanel(pricesPanel);
			}
		});
	}

	private void updateTimeView() {
		String dateString = this.player.getDate();
		this.gamePanel.setTimeLabel(dateString);
	}

	protected void mainLoop() {

		if (!this.isPaused) {
			this.timeCount += UPDATE_INTERVAL;
			if (this.timeCount >= TICK_INTERVAL) {
				this.timeCount -= TICK_INTERVAL;
				this.player.updateTick();
				updateTimeView();
				if (this.pausePressed)
					setPaused(true);
			}
		}
		this.refreshablePanelController.refresh();

		GameState gameState = this.player.getGameState();
		if (gameState.equals(GameState.WIN))
			this.win();
		else if (gameState.equals(GameState.LOSE))
			this.lose();

		repaintGroundPanel();
	}

	private void setPaused(boolean pause) {
		this.isPaused = pause;
		if (this.isPaused)
			this.player.getWarehouse().clearProductionLines();
		else
			this.player.getWarehouse().createProductionLines();
	}

	private void repaintGroundPanel() {
		double elapsedTickTime = (double) this.timeCount / TICK_INTERVAL;
		GroundPanel groundPanel = getGroundPanel();
		groundPanel.setElapsedTickTime(elapsedTickTime);
		groundPanel.setPaused(this.isPaused);
		groundPanel.repaint();
	}

	private GroundPanel getGroundPanel() {
		return this.gamePanel.getGroundPanel();
	}

	private void pauseButtonPressed() {
		this.pausePressed = true;
		updatePlayPauseButtons();
	}

	private void playButtonPressed() {
		if (this.isPaused) {
			setPaused(false);
		}
		this.pausePressed = false;
		updatePlayPauseButtons();
	}

	private void win() {
		JOptionPane.showMessageDialog(null, "We have a new winner!",
				"Won the game", JOptionPane.WARNING_MESSAGE);
		this.mainController.setGroundSelectionPanel(player);
		disposeGamePanel();
	}

	private void lose() {
		JOptionPane.showMessageDialog(null, "We have a new loser!",
				"Lost the game", JOptionPane.ERROR_MESSAGE);
		this.mainController.setMainPanel();
		disposeGamePanel();
	}

	private void updatePlayPauseButtons() {
		this.pauseButton.setSelected(pausePressed);
		this.playButton.setSelected(!pausePressed);
	}

	public GamePanel getGamePanel() {
		return this.gamePanel;
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
