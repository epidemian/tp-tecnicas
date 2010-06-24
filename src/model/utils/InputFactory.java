package model.utils;

import java.util.List;
import java.util.Map;

import model.lab.TechnologyTree;
import model.production.RawMaterialType;
import model.production.ValidProductionSequences;
import model.production.elements.machine.MachineType;
import model.warehouse.Ground;

public abstract class InputFactory {
	public abstract TechnologyTree loadTechnologies(
			ValidProductionSequences validSequences) throws Exception;

	public abstract List<Ground> loadGrounds() throws Exception;

	public abstract Map<String, Integer> loadPrices(int weekNumber) throws Exception;

	public abstract List<MachineType> loadProductionMachines() throws Exception;

	public abstract List<MachineType> loadQualityControlMachines()
			throws Exception;

	public abstract List<RawMaterialType> loadRawMaterialTypes()
			throws Exception;

}
