package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.LinkedList;
import java.util.List;

public class ProductionSequence {

	/**
	 * Contains the machines types of a specific line of production
	 */
	private List<MachineType> lineMachines;
	private RawMaterials rawMaterials;
	

	public ProductionSequence(List<MachineType> lineMachines,
			RawMaterials rawMaterials) {
		this.setLineMachines(lineMachines);
		this.setRawMaterials(rawMaterials);
	}
	
	public ProductionSequence(RawMaterials rawMaterials) {
		this(new LinkedList<MachineType>(),rawMaterials);
	}
	
	public void addMachineType(MachineType machineType){
		this.lineMachines.add(machineType);
	}
	
	public RawMaterials getRawMaterials() {
		return rawMaterials;
	}
	
	public List<MachineType> getLineMachines() {
		return lineMachines;
	}
	
	private void setRawMaterials(RawMaterials rawMaterials) {
		checkNotNull(rawMaterials, "rawMaterials");
		this.rawMaterials = rawMaterials;
	}

	private void setLineMachines(List<MachineType> lineMachines) {
		checkNotNull(lineMachines, "lineMachines");
		this.lineMachines = lineMachines;
	}

	public ProductType identifyProductType(){
		return ValidProductionSequences.getInstance().identifyProductType(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lineMachines == null) ? 0 : lineMachines.hashCode());
		result = prime * result
				+ ((rawMaterials == null) ? 0 : rawMaterials.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionSequence other = (ProductionSequence) obj;
		if (lineMachines == null) {
			if (other.lineMachines != null)
				return false;
		} else if (!lineMachines.equals(other.lineMachines))
			return false;
		if (rawMaterials == null) {
			if (other.rawMaterials != null)
				return false;
		} else if (!rawMaterials.equals(other.rawMaterials))
			return false;
		return true;
	}
}