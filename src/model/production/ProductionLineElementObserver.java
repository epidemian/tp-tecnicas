package model.production;

public interface ProductionLineElementObserver {
	
	public void updateBreakdown(Machine machine);
	
	// only when a broken machine is repaired.
	public void updateBrokenMachineRepair(Machine machine);
}
