package model.production.elements.machine;

public interface MachineObservable {
	public void notifyBreakdown();
	public void notifyBrokenMachineRepair();
}
