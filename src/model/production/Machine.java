package model.production;

import static model.utils.ArgumentUtils.checkNotNull;
import model.warehouse.TileElementVisitor;

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
	
	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitMachine(this);
	}
	
	private void setMachineType(MachineType machineType) {
		checkNotNull(machineType, "machineType");
		this.machineType = machineType;
	}
	
	public abstract int getPrice();

	@Override
	public String toString() {
		return "ProductionMachine [" + this.getMachineType().toString() + "]";
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
//	@Override
//	public boolean equals(Object other){
//		Machine otherMachine = (Machine)other;
//		return (this.machineType.equals(otherMachine.machineType));
//	}
}
