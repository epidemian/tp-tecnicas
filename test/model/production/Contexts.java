package model.production;

import java.util.*;

public class Contexts {

	public static Map<RawMaterialType,Integer> createRawMaterialMap(
			int size, int startRawMaterialIndex, int quantity){
		
		Map<RawMaterialType,Integer> map = new HashMap<RawMaterialType
			, Integer>();
		List<RawMaterialType> list = Contexts
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
		return new RawMaterials(Contexts.createRawMaterialMap(
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
	
	public static ProductionSequence createProductionSequence(int sizeMachines
			, int startMachinesIndex, int sizeRawMaterials
			, int startRawMaterialTypeIndex, int quantityRawMaterial){
		return  new ProductionSequence(
				Contexts.createMachineTypeList(sizeMachines,startMachinesIndex)
			    ,Contexts.createRawMaterials(sizeRawMaterials,
				startRawMaterialTypeIndex, quantityRawMaterial));
	}
	
	public static void main(String[] args) {

		Map<RawMaterialType,Integer> cm1 = createRawMaterialMap(4,2,10);
		List<RawMaterialType> cm2 = createRawMaterialTypeList(4,4);
		RawMaterials cm3 = createRawMaterials(4,10,10); 
		List<MachineType> cm4 = createMachineTypeList(4,20);
		List<ProductType> cm5 = createProductTypeList(4,20);
		ProductionSequence cm6 = createProductionSequence(3,3,4,2,10);		
		
		System.out.println(cm1);
		System.out.println(cm2);
		System.out.println(cm3);
		System.out.println(cm4);
		System.out.println(cm5);
		System.out.println(cm6);		
	}
}
