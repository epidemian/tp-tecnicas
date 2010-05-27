package model.production;

import model.exception.BusinessLogicException;

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
	
	private void setMachineType(MachineType machineType) {
		if (machineType == null)
			throw new BusinessLogicException("Invalid machineType");
		this.machineType = machineType;
	}

	@Override
	public Product process(Product input) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public abstract int getPrice();
}
