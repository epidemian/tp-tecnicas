package persistence;

import java.util.List;
import java.util.Map;

import model.lab.TechnologyTree;
import model.production.MachineType;
import model.production.RawMaterialType;
import model.production.ValidProductionSequences;
import model.warehouse.Ground;

public abstract class InputFactory {
	public abstract TechnologyTree 
				loadTechnologies(String path,
							ValidProductionSequences validSequences) 
			throws Exception;
	
	
	public abstract List<Ground>  loadGrounds(String path) 
			throws Exception;
	
	public abstract Map<String,Integer> loadPrices(String weekNr) 
			throws Exception;
	
	public abstract List<MachineType> loadProductionMachines(String availableMachines)
			throws Exception;
	
	public abstract List<MachineType> loadQualityControlMachines(String availableMachines)
	throws Exception;
	
	public abstract List<RawMaterialType> loadRawMaterialTypes
												(String availableRaw)
			throws Exception;
			
	
}
