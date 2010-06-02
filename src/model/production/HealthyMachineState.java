package model.production;

/**
 * Represents the state in which the machine has the lowest 
 * defective production rate.
 */
public class HealthyMachineState extends MachineState{
	public void repair(Machine machine) throws CannotRepairHealthyMachineException{
		throw new CannotRepairHealthyMachineException();
	}
}
