package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import model.game.Player;
import model.warehouse.TileElement;
import view.game.GamePanel;
import view.game.LineElementsMarketPanel;
import view.game.RawMaterialsMarketPanel;
import view.game.ToolBarPanel;
import view.game.edition.EditionActions;
import view.game.edition.KeyInputActionMapper;
import controller.MainController;
import java.awt.image.BufferedImage;
import model.production.elements.InputProductionLineElement;
import model.production.elements.machine.Machine;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.TileElementVisitor;
import view.game.Dialog;
import view.game.InputSelectionPanel;
import view.game.MachineSelectionPanel;
import view.game.ResearchLabPanel;
import view.game.TileElementImageRecognizer;

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
		final EditionActions editionActions = new EditionActions(this, player);
		KeyInputActionMapper mapper = new KeyInputActionMapper(editionActions);
		mapper.mapActions(gamePanel.getInputMap(), gamePanel.getActionMap());


		int balance = player.getBudget().getBalance();
		gamePanel.getBudgetPanel().setMoneyBalance(balance);

		ToolBarPanel toolBar = gamePanel.getToolBarPanel();

		JButton lineElementsMarketButton = toolBar
				.getLineElementsMarketButton();
		lineElementsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				
				LineElementsMarketPanel lineElementsMarketPanel = new LineElementsMarketPanel();
				new LineElementsMarketPanelController(player, lineElementsMarketPanel,
						editionActions);
				gamePanel.setToolPanel(lineElementsMarketPanel);
			}
		});

		JButton rawMaterialsMarketButton = toolBar
				.getRawMaterialsMarketButton();
		rawMaterialsMarketButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				
				RawMaterialsMarketPanel rawMaterialsMarketPanel = new RawMaterialsMarketPanel();
				new RawMaterialsMarketPanelController(player, rawMaterialsMarketPanel,
						gamePanel);
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

        public GamePanel getGamePanel(){
            return this.gamePanel;
        }

        public void setToolPanelFromSelectionTool(TileElement selectedTileElement) {


            selectedTileElement.accept(new TileElementVisitor() {

                // TODO hacer un controller para esto!
                private MachineSelectionPanel visitMachine(Machine machine){
                    MachineSelectionPanel machinePanel = new MachineSelectionPanel();

                    int price = machine.getSalePrice();
                    BufferedImage image = TileElementImageRecognizer.getMachineImage(machine.getMachineType());
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
                    new InputSelectionPanelController(inputLineElement, inputPanel, player);
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
