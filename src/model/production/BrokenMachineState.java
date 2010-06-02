package model.production;

/**
 * Represents the state in which the Machine makes the production line
 * not to produce nothing at all.
 */
public class BrokenMachineState extends MachineState{
public void repair(Machine machine){
		machine.setMachineState(new HealthyMachineState());
	}
}
