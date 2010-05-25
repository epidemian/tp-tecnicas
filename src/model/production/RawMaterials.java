package model.production;

import static model.utils.ArgumentUtils.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of the raw materials that can be used to create products.
 */
public class RawMaterials {

	private Map<RawMaterialType,Integer> rawMaterials;
	
	public RawMaterials(Map<RawMaterialType,Integer> rawMaterials){
		this.rawMaterials = rawMaterials;
	}
	
	public RawMaterials(){
		this.rawMaterials = new HashMap<RawMaterialType, Integer>();
	}

	public void extract(RawMaterialType rawMaterialType, int quantityNeeded)
		   throws NotEnoughRawMaterialException{
		
		validateQuantity(quantityNeeded);
		
		boolean isEnough = canExtract(rawMaterialType,quantityNeeded);
		if (isEnough){
			Integer quantity = this.rawMaterials.get(rawMaterialType);
			this.rawMaterials.put(rawMaterialType, 
					quantity.intValue()-quantityNeeded);
		}
		else
			throw new NotEnoughRawMaterialException();
	}
	
	public void extract(RawMaterials rawMaterials) 
		   throws NotEnoughRawMaterialException{
		
		for (RawMaterialType key : rawMaterials.rawMaterials.keySet()){
			Integer value = this.rawMaterials.get(key);
			
			if (!this.canExtract(key, value))
				throw new NotEnoughRawMaterialException();
		}
		
		for (RawMaterialType key : rawMaterials.rawMaterials.keySet()){
			Integer value = this.rawMaterials.get(key);
		
			this.extract(key, value.intValue());
		}
	}
	
	public boolean canExtract(RawMaterialType rawMaterialType,
			int quantityNeeded){
		
		Integer quantity = this.rawMaterials.get(rawMaterialType);
		if (quantity != null){
			return quantity.intValue() >= quantityNeeded;
		}
		else
			return false;
	}

	public void store(RawMaterialType rawMaterialType, int quantityStore){
	
		validateQuantity(quantityStore);
		
		Integer quantity = this.rawMaterials.get(rawMaterialType);
		if (quantity == null){
			this.rawMaterials.put(rawMaterialType, quantityStore);
		}
		else{
			this.rawMaterials.put(rawMaterialType, 
					quantityStore + quantity.intValue());
		}
	}

	public boolean equals(Object other){
		return this.rawMaterials.equals(((RawMaterials)other).rawMaterials);
	}
	
	private void validateQuantity(int quantity){
		checkGreaterEqual(quantity,0,"quantity");
	}
}