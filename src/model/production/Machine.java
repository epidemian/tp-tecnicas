package model.production;

import model.core.BusinessLogicException;

public class Machine extends ProductionLineElement{

	private MachineType machineType;
	
	public Machine(MachineType machineType){
		this.setMachineType(machineType);
	}

	public MachineType getMachineType() {
		return machineType;
	}
	
	private void setMachineType(MachineType machineType) {
		if (machineType == null)
			throw new BusinessLogicException("Invalid machineType");
		this.machineType = machineType;
	}
}
