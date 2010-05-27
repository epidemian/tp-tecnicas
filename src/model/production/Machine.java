package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

public abstract class Machine extends ProductionLineElement{

	public Machine(MachineType machineType, ProductionLineElement next, 
		ProductionLineElement previous) {
		super(next, previous);
		this.setMachineType(machineType);
	}

	private MachineType machineType;

	public MachineType getMachineType() {
		return machineType;
	}
	
	public Product process(Product input){
		if (input != null)
			this.treatProduct(input);
		return super.process(input);
	}
	
	/**
	 * Template method to treat the product. The ProductionMachine can 
	 * setDefective a producto, and a QualityControlMachine can discard it.
	 * @param input
	 */
	public abstract void treatProduct(Product input);
	
	private void setMachineType(MachineType machineType) {
		checkNotNull(machineType, "machineType");
		this.machineType = machineType;
	}
	
	public abstract int getPrice();

	@Override
	public String toString() {
		return "ProductionMachine [" + this.getMachineType().toString() + "]";
	}
	
	@Override
	public boolean equals(Object other){
		Machine otherMachine = (Machine)other;
		return (this.machineType.equals(otherMachine.machineType));
	}
}
