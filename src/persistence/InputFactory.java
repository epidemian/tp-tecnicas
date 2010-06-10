package persistence;

import java.util.List;
import java.util.Map;

import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.MachineType;
import model.warehouse.Ground;

public abstract class InputFactory {
	public abstract List<NewProductionSequenceTechnology> 
				loadTechnologies(String path) 
			throws Exception;
	
	public abstract List<Ground>  loadGrounds(String path) 
			throws Exception;
	
	public abstract Map<String,Integer> loadPrices(String weekNr) 
			throws Exception;
	
	public abstract List<MachineType> loadMachines(String availableMachines)
			throws Exception;;
		
	
	
}
