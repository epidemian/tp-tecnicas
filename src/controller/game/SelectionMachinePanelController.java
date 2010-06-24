package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import static model.utils.ArgumentUtils.*;
import model.game.Budget;
import model.game.Player;
import model.production.elements.machine.Machine;
import view.game.GamePanel;
import view.game.MachineSelectionPanel;
import view.game.TileElementImageRecognizer;

public class SelectionMachinePanelController implements Refreshable{

	private Machine machine;
	private MachineSelectionPanel machinePanel;

	public SelectionMachinePanelController(Player player,
			MachineSelectionPanel machinePanel, Machine machine,
			GamePanel gamePanel) {

		checkNotNull(player, "player");
		checkNotNull(machinePanel, "machinePanel");
		checkNotNull(machine, "machine");
		checkNotNull(gamePanel, "gamePanel");

		this.machine = machine;
		this.machinePanel = machinePanel;
		
		refresh();		
		initRepairButton(machinePanel, player.getBudget());
	}

	private void setState(MachineSelectionPanel machinePanel) {
		String state = machine.getMachineState().toString();
		machinePanel.setMachineState(state);
	}

	private void setPrice(MachineSelectionPanel machinePanel) {
		int price = machine.getSalePrice();
		machinePanel.setMachinePrice(price);
	}

	private void setImage(MachineSelectionPanel machinePanel) {
		BufferedImage image = TileElementImageRecognizer
				.getMachineImage(machine.getMachineType());
		machinePanel.setMachineImage(image);
	}
	
	private void setMachineType(MachineSelectionPanel machinePanel){
		String machineType = machine.getMachineType().getName();
		machinePanel.setMachineType(machineType);
	}

	private void setFail(MachineSelectionPanel machinePanel){
		double fail = machine.getFailProductProcessChance();
		machinePanel.setFailProductProcessChance(fail);
	}
	
	private void initRepairButton(final MachineSelectionPanel machinePanel,
			final Budget budget) {

		JButton repair = machinePanel.getRepairButton();

		repair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				machine.repair(budget);
			}
		});
	}
	
	@Override
	public void refresh(){
		
		this.setState(machinePanel);
		this.setPrice(machinePanel);
		this.setImage(machinePanel);
		this.setMachineType(machinePanel);
		this.setFail(machinePanel);
	}
}
