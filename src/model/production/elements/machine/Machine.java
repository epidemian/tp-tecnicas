package model.production.elements.machine;

import static model.utils.ArgumentUtils.checkNotNull;
import model.exception.BusinessLogicException;
import model.game.Budget;
import model.production.Direction;
import model.production.Product;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.states.CannotRepairHealthyMachineException;
import model.production.elements.machine.states.HealthyMachineState;
import model.production.elements.machine.states.MachineState;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public abstract class Machine extends ProductionLineElement implements
		MachineObservable {

	
	//Represent the chance of the machine of breaking or damaging after processing
	//private static final float BREAK_CHANCE = 0.05f;
	//private static final float DAMAGE_CHANCE = 0.15f;
	public static final float PRICE_REPAIR_COEF = 0.05f;

	private MachineState machineState;
	private MachineType machineType;
	private double currentCoef;

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
	 * setDefective a product, and a QualityControlMachine can discard it.
	 * 
	 * @param input
	 */
	public abstract void treatProduct(Product input);

	public void repair(Budget budget)
			throws CannotRepairHealthyMachineException {
		budget.decrement(Math.round(this.machineType.getPrice()
				* PRICE_REPAIR_COEF));
		this.getMachineState().repair(this);
	}

	public MachineState getMachineState() {
		return this.machineState;
	}

	public void setMachineState(MachineState newState) {
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

	public void notifyBreakdown() {
		this.getProductionLineElementObserver().updateBreakdown(this);
	}

	public void notifyBrokenMachineRepair() {
		this.getProductionLineElementObserver().updateBrokenMachineRepair(this);
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
		if (number < this.machineType.getDamageChance()) {
			this.damage();
		} else if (number > this.getMachineType().getBreakChance()) {
			this.breakUp();
		}
	}

	protected void damage() {
		this.getMachineState().damage(this);
	}

	public void breakUp() {
		this.getMachineState().breakUp(this);
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
	public Position getInputConnectionPosition() {
		return this.getPosition().add(machineType.getInputRelativePosition());
	}

	@Override
	public Position getOutputConnectionPosition() {
		return this.getPosition().add(machineType.getOutputRelativePosition());
	}

	public void setCurrentCoef(double currentCoef) {
		this.currentCoef = currentCoef;
	}

	public double getCurrentCoef() {
		return currentCoef;
	}

	@Override
	public int getSalePrice() {
		return this.machineState.getSalePrice(this);
	}
	
}
