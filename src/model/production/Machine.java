package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

import model.exception.BusinessLogicException;

public abstract class Machine extends ProductionLineElement implements
		MachineObservable {

	// TODO: esto debería levantese de algún archivo de confg o algo...
	private static final double BREAK_CHANCE = 0.05;
	private static final double DAMAGE_CHANCE = 0.15;


	private MachineState machineState;

	public Machine(MachineType machineType, int width, int height) {
		super(width, height);
		this.setMachineType(machineType);
		this.setMachineState(new HealthyMachineState());
	}

	private MachineType machineType;

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

	private void setMachineType(MachineType machineType) {
		checkNotNull(machineType, "machineType");
		this.machineType = machineType;
	}

	public void repair() throws CannotRepairHealthyMachineException {
		this.getMachineState().repair(this);
	}

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

	/*
	 * TODO: Por qué dos Machines son iguales si sus tipos son iguales
	 * solamente? Si en la fábrica tengo 2 "hornos" por ejemplo, no son iguales,
	 * por más de que sus tipos sean iguales. Si fuesen iguales, no se podría
	 * buscar uno o el otro en una colección que los contenga.
	 * 
	 * Además, nunca redefinir el equals y dejar el hashCode sin redefinir: se
	 * rompe la interfaz del hashCode.
	 */
	// @Override
	// public boolean equals(Object other){
	// Machine otherMachine = (Machine)other;
	// return (this.machineType.equals(otherMachine.machineType));
	// }
}
