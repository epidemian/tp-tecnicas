package model.production;

public abstract class MachineState {
	
	public abstract void repair(Machine machine) throws CannotRepairHealthyMachineException;
	
	public boolean equals(Object anotherObj){
		return this.getClass().equals(((MachineState) anotherObj).getClass());
	}
	
}
