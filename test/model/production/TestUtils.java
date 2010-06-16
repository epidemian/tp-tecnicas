package model.production;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.production.elements.machine.MachineType;

public class TestUtils {

	public static Map<RawMaterialType,Integer> createRawMaterialMap(
			int size, int startRawMaterialIndex, int quantity){
		
		Map<RawMaterialType,Integer> map = new HashMap<RawMaterialType
			, Integer>();
		List<RawMaterialType> list = TestUtils
			.createRawMaterialTypeList(size,startRawMaterialIndex);
		
		for (RawMaterialType entry : list){
			map.put(entry, new Integer(quantity));
		}
		
		return map;
	}
	
	public static List<RawMaterialType> createRawMaterialTypeList(
			int size, int startRawMaterialTypeIndex){
		
		String rawMaterialName = new String("rawMaterial");
		List<RawMaterialType> list = new ArrayList<RawMaterialType>();
		
		for (int i = startRawMaterialTypeIndex; i < size 
			+ startRawMaterialTypeIndex; i++){
			list.add(new RawMaterialType(rawMaterialName + i));
		}
		return list;
	}
	
	public static RawMaterials createRawMaterials(int size, 
			int startRawMaterialIndex, int quantity){
		return new RawMaterials(TestUtils.createRawMaterialMap(
				size, startRawMaterialIndex, quantity));
	}
	
	public static List<MachineType> createMachineTypeList(
		int size, int startMachineTypeIndex){
	
		String machineName = new String("machine");
		List<MachineType> list = new ArrayList<MachineType>();
		
		for (int i = startMachineTypeIndex; i < size 
			+ startMachineTypeIndex; i++){
			list.add(new MachineType(machineName + i));
		}
		return list;
	}
	
	public static List<ProductType> createProductTypeList(
			int size, int startProductTypeIndex){
		
		String productName = new String("product");
		List<ProductType> list = new ArrayList<ProductType>();
		
		for (int i = startProductTypeIndex; i < size 
			+ startProductTypeIndex; i++){
			list.add(new ProductType(productName + i));
		}
		return list;
	}
	
	public static List<Product> creatProductsWithEmptyRawMaterial(int size){
		List<Product> list = new LinkedList<Product>();
		
		for (int i = 0; i < size; i++)
			list.add(new Product(new RawMaterials()));
		
		return list;
	}
	
	public static ProductionSequence createProductionSequence(int sizeMachines
			, int startMachinesIndex, int sizeRawMaterials
			, int startRawMaterialTypeIndex, int quantityRawMaterial){
		return  new ProductionSequence(
				TestUtils.createMachineTypeList(sizeMachines,startMachinesIndex)
			    ,TestUtils.createRawMaterials(sizeRawMaterials,
				startRawMaterialTypeIndex, quantityRawMaterial));
	}

	public  static List<MachineType> createMachineTypeList(String... names) {
		List<MachineType> machineTypes = new ArrayList<MachineType>(
				names.length);
		for (String name : names)
			machineTypes.add(new MachineType(name));
		return machineTypes;
	}
}
