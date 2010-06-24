package model.production.elements.machine;

import static model.utils.ArgumentUtils.checkNotNull;
import static model.utils.RandomUtils.randomBoolean;

import model.game.Budget;
import model.production.Direction;
import model.production.Product;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.states.HealthyMachineState;
import model.production.elements.machine.states.MachineState;
import model.warehouse.Position;

public abstract class Machine extends ProductionLineElement {

	private MachineState machineState;
	private MachineType machineType;

	public Machine(MachineType machineType) {
		super(machineType.getWidth(), machineType.getHeight());
		this.setMachineType(machineType);
		this.setMachineState(new HealthyMachineState());
	}

	public MachineType getMachineType() {
		return machineType;
	}

	public Product process(Product input) {
		if (input != null) {
			this.treatProduct(input);
			this.processMachineDeterioration();
		}
		return super.process(input);
	}

	/**
	 * Method to treat the product. The ProductionMachine can setDefective a
	 * product, and a QualityControlMachine can discard it.
	 */
	protected abstract Product treatProduct(Product input);

	public void repair(Budget budget) {
		this.getMachineState().repair(this, budget);
	}

	public MachineState getMachineState() {
		return this.machineState;
	}

	public void setMachineState(MachineState newState) {
		checkNotNull(newState, "machine state");
		this.machineState = newState;
	}

	@Override
	public int getPurchasePrice() {
		return this.machineType.getPrice();
	}

	@Override
	public String toString() {
		return "ProductionMachine [" + this.getMachineType().toString() + "]";
	}

	private void setMachineType(MachineType machineType) {
		checkNotNull(machineType, "machine type");
		this.machineType = machineType;
	}

	/**
	 * Analyzes whether after processing the product, the machine becomes
	 * damaged or broken.
	 */
	private void processMachineDeterioration() {
		if (willBecomeDamage())
			this.damage();
		if (willBreakUp())
			this.breakUp();
	}

	protected boolean willBecomeDamage() {
		return randomBoolean(this.getMachineType().getDamageChance());
	}

	protected boolean willBreakUp() {
		return randomBoolean(this.getMachineType().getBreakChance());
	}

	private void damage() {
		this.getMachineState().damage(this);
	}

	@Override
	public void breakUp() {
		this.getMachineState().breakUp(this);
	}

	@Override
	public boolean isBroken() {
		return this.machineState.isBroken();
	}

	public double getFailProductProcessChance() {
		return this.machineState.getFailProductProcessChance(this);
	}

	@Override
	public Direction getInputConnectionDirection() {
		return this.machineType.getInputConnectionDirection();
	}

	@Override
	public Direction getOutputConnectionDirection() {
		return this.machineType.getOutputConnectionDirection();
	}

	@Override
	public Position getInputConnectionRelativePosition() {
		return machineType.getInputRelativePosition();
	}

	@Override
	public Position getOutputConnectionRelativePosition() {
		return machineType.getOutputRelativePosition();
	}

	@Override
	public int getSalePrice() {
		return this.machineState.getSalePrice(this);
	}

}
