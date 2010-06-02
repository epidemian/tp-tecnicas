package model.production;

public abstract class MachineState {
	
	public abstract void repair(Machine machine) throws CannotRepairHealthyMachineException;
	
	public void broke(Machine machine) {}
	
	public  void damage(Machine machine){}
	
	
	public boolean equals(Object anotherObj){
		return this.getClass().equals(((MachineState) anotherObj).getClass());
	}
	
}
