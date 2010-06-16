package model.production.elements;

import model.production.elements.machine.Machine;


public interface ProductionLineElementObserver {
	
	public void updateBreakdown(Machine machine);
	
	// only when a broken machine is repaired.
	public void updateBrokenMachineRepair(Machine machine);
}
