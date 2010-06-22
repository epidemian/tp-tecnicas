package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import model.game.Player;
import model.production.elements.InputProductionLineElement;
import model.production.elements.machine.Machine;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import view.game.Dialog;
import view.game.GamePanel;
import view.game.InputSelectionPanel;
import view.game.LineElementsMarketPanel;
import view.game.MachineSelectionPanel;
import view.game.RawMaterialsMarketPanel;
import view.game.ResearchLabPanel;
import view.game.TileElementImageRecognizer;
import view.game.ToolBarPanel;
import view.game.ground.GroundPanel;
import controller.MainController;
import controller.game.edition.EditionActions;
import controller.game.edition.KeyInputActionMapper;
import java.util.Observer;

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
	private GroundPanelController groundPanelController;

	public ContainerAdapter getGamePanelRemovedListener() {
		return gamePanelRemovedListener;
	}

	public GamePanelController(final Player player, final GamePanel gamePanel,
			final MainController mainController) {

		this.gamePanel = gamePanel;
		this.player = player;
		final EditionActions editionActions = new EditionActions(this, player);
		KeyInputActionMapper mapper = new KeyInputActionMapper(editionActions);
		mapper.mapActions(gamePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW),
				gamePanel.getActionMap());

		/**
		 * The budget panel is updated by the observer pattern.
		 */
		player.getBudget().addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				int balance = player.getBudget().getBalance();
				gamePanel.getBudgetPanel().setMoneyBalance(balance);
			}
		});

		GroundPanel groundPanel = new GroundPanel(player.getGround());
		gamePanel.getGroundPanelContainer().setGroundPanel(groundPanel);
		groundPanelController = new GroundPanelController(player, groundPanel);

		ToolBarPanel toolBar = gamePanel.getToolBarPanel();

		JButton lineElementsMarketButton = toolBar
				.getLineElementsMarketButton();
		lineElementsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				LineElementsMarketPanel lineElementsMarketPanel = new LineElementsMarketPanel();
				new LineElementsMarketPanelController(player,
						lineElementsMarketPanel, editionActions);
				gamePanel.setToolPanel(lineElementsMarketPanel);
			}
		});

		JButton rawMaterialsMarketButton = toolBar
				.getRawMaterialsMarketButton();
		rawMaterialsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				RawMaterialsMarketPanel rawMaterialsMarketPanel = new RawMaterialsMarketPanel();
				new RawMaterialsMarketPanelController(player,
						rawMaterialsMarketPanel, gamePanel);
				gamePanel.setToolPanel(rawMaterialsMarketPanel);
			}
		});

		JButton researchLabButton = toolBar.getLabButton();
		researchLabButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				ResearchLabPanel labPanel = new ResearchLabPanel();
				new ResearchLabPanelController(player, labPanel);
				gamePanel.setToolPanel(labPanel);
			}
		});

		JButton exitButton = toolBar.getExitButton();
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (Dialog.showDialog("Exit", "Are you sure?"))
					System.exit(0);
			}
		});

		JButton sellButton = toolBar.getSellButton();
		sellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Dialog.showDialog("Sell", "Are you sure?")) {
					player.sellWarehouse();
					mainController.setGroundSelectionPanel(player);
				}
			}
		});

		final Timer mainLoopTimer = new Timer(UPDATE_INTERVAL,
				new ActionListener() {

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

		updateTimeView();
	}

	public GroundPanelController getGroundPanelController() {
		return groundPanelController;
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
				updateTimeView();
			}
		}
		repaintGroundPanel();
	}

	private void repaintGroundPanel() {
		double elapsedTickTime = this.timeCount / TICK_INTERVAL;
		getGroundPanel().setElapsedTickTime(elapsedTickTime);
		getGroundPanel().repaint();
	}

	private GroundPanel getGroundPanel() {
		return this.gamePanel.getGroundPanelContainer().getGroundPanel();
	}

	private void pauseButtonPressed() {
		this.isPaused = true;
		updatePlayPauseButtons();
	}

	private void playButtonPressed() {
		if (isPaused) {
			this.isPaused = false;
			this.player.getWarehouse().createProductionLines();
		}
		updatePlayPauseButtons();
	}

	private void updatePlayPauseButtons() {
		this.pauseButton.setSelected(isPaused);
		this.playButton.setSelected(!isPaused);
	}

	public GamePanel getGamePanel() {
		return this.gamePanel;
	}

	public void setToolPanelFromSelectionTool(TileElement selectedTileElement) {

		selectedTileElement.accept(new TileElementVisitor() {

			// TODO hacer un controller para esto!
			private MachineSelectionPanel visitMachine(Machine machine) {
				MachineSelectionPanel machinePanel = new MachineSelectionPanel();

				int price = machine.getSalePrice();
				BufferedImage image = TileElementImageRecognizer
						.getMachineImage(machine.getMachineType());
				String machineType = machine.getMachineType().getName();
				double fail = machine.getFailProductProcessChance();
				String state = machine.getMachineState().toString();

				machinePanel.setMachinePrice(price);
				machinePanel.setMachineImage(image);
				machinePanel.setMachineType(machineType);
				machinePanel.setFailProductProcessChance(fail);
				machinePanel.setMachineState(state);

				return machinePanel;
			}

			@Override
			public void visitProductionMachine(ProductionMachine machine) {
				// Creates machine panel.
				MachineSelectionPanel machinePanel = this.visitMachine(machine);
				machinePanel.setProductionMachineLabels();
				// Sets panel.
				getGamePanel().setToolPanel(machinePanel);
			}

			@Override
			public void visitQualityControlMachine(QualityControlMachine machine) {
				// Creates machine panel.
				MachineSelectionPanel machinePanel = this.visitMachine(machine);
				machinePanel.setQualityControlMachineLabels();
				// Sets panel.
				getGamePanel().setToolPanel(machinePanel);
			}

			@Override
			public void visitInputProductionLineElement(
					InputProductionLineElement inputLineElement) {

				InputSelectionPanel inputPanel = new InputSelectionPanel();
				new InputSelectionPanelController(inputLineElement, inputPanel,
						player);
				getGamePanel().setToolPanel(inputPanel);
			}
		});
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
