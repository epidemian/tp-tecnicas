package model.production;

import static model.utils.ArgumentUtils.checkNotNull;
import model.exception.BusinessLogicException;
import model.game.Budget;
import model.warehouse.Position;

public abstract class Machine extends ProductionLineElement implements
		MachineObservable {

	private static final double BREAK_CHANCE = 0.05;
	private static final double DAMAGE_CHANCE = 0.15;
	public static final float PRICE_REPAIR_COEF = (float) 0.05;

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

	public int getPrice() {
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

	@Override
	public Direction getInputConnectionDirection() {
		return getInOutDirectionByPosition(this.getInputRelativePosition());
	}

	@Override
	public Direction getOutputConnectionDirection() {
		return getInOutDirectionByPosition(this.getOutputRelativePosition());
	}

	private Direction getInOutDirectionByPosition(Position position) {
		if (position.getRow() == -1)
			return Direction.NORTH;
		if (position.getRow() == getHeight())
			return Direction.SOUTH;
		if (position.getCol() == -1)
			return Direction.WEST;
		if (position.getCol() == getWidth())
			return Direction.EAST;
		throw new BusinessLogicException("Invalid position");
	}

	@Override
	public Position getInputConnectionPosition() {
		return this.getPosition().add(this.getInputRelativePosition());
	}

	@Override
	public Position getOutputConnectionPosition() {
		return this.getPosition().add(this.getInputRelativePosition());
	}

	public Position getInputRelativePosition() {
		return machineType.getInputRelativePosition();
	}

	public Position getOutputRelativePosition() {
		return machineType.getOutputRelativePosition();
	}

}
