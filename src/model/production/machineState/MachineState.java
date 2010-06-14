package model.production.machineState;

import model.production.Machine;

public abstract class MachineState {

	//generic coefficients for brokenMachineState and DamagedMachineState
	public static double damagedCoef=0.15;
	public static double healthyCoef=0.05;
	
	
	public abstract void repair(Machine machine) throws CannotRepairHealthyMachineException;
	
	public void breakUp(Machine machine) {}
	
	public  void damage(Machine machine){}
	
	public abstract int sell(Machine machine);
	
	public boolean equals(Object anotherObj){
		return this.getClass().equals(((MachineState) anotherObj).getClass());
	}
	
}
