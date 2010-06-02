package model.production;

/**
 * Represents the state of a machine which has a higher rate of defective
 * products than a healthy machine
 * 
 */
public class DamagedMachineState extends MachineState{
	public void repair(Machine machine){
		machine.setMachineState(new HealthyMachineState());
	}
}
