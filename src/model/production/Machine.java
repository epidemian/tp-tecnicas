package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;

import model.warehouse.Position;

public abstract class Machine extends ProductionLineElement implements
		MachineObservable {

	// TODO: esto debería levantese de algún archivo de confg o algo...
	private static final double BREAK_CHANCE = 0.05;
	private static final double DAMAGE_CHANCE = 0.15;

	private MachineState machineState;
	private MachineType machineType;

	/*
	 * public Machine(MachineType machineType, int width, int height) {
	 * super(width, height); this.setMachineType(machineType);
	 * this.setMachineState(new HealthyMachineState()); }
	 */

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
	 * Template method to treat the product. The ProductionMachine can
	 * setDefective a producto, and a QualityControlMachine can discard it.
	 * 
	 * @param input
	 */
	public abstract void treatProduct(Product input);

	public void repair() throws CannotRepairHealthyMachineException {
		this.getMachineState().repair(this);
	}

	public MachineState getMachineState() {
		return this.machineState;
	}

	public void setMachineState(MachineState newState) {
		this.machineState = newState;
	}

	public abstract int getPrice();

	@Override
	public String toString() {
		return "ProductionMachine [" + this.getMachineType().toString() + "]";
	}

	public void notifyBreakdown() {
		this.getProductionLineElementObserver().updateBreakdown();
	}

	public void notifyBrokenMachineRepair() {
		this.getProductionLineElementObserver().updateBrokenMachineRepair();
	}

	private void setMachineType(MachineType machineType) {
		checkNotNull(machineType, "machineType");
		this.machineType = machineType;
	}

	// @Override
	// public void addToGround(Ground ground, Position position) {
	// // TODO Auto-generated method stub
	// if (getPreviousLineElement() != null && getNextLineElement() != null)
	// throw new BusinessLogicException("Already connected");
	//		
	//		
	// }

	/*
	 * Analizes whether after processing the product, the machine becomes
	 * damaged or broken.
	 */
	private void processMachineDeterioration() {
		double number = Math.random();
		if (number < DAMAGE_CHANCE) {
			this.damage();
		} else if (number > 1 - BREAK_CHANCE) {
			this.breakUp();
		}
	}

	protected void damage() {
		this.getMachineState().damage(this);
	}

	protected void breakUp() {
		this.getMachineState().breakUp(this);
	}

}
