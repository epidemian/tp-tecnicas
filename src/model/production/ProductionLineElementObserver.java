package model.production;

public interface ProductionLineElementObserver {
	
	public void updateBreakdown();
	
	// only when a broken machine is repaired.
	public void updateBrokenMachineRepair();
}
