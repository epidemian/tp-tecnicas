package model.production;

import static model.utils.ArgumentUtils.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of the raw materials that can be used to create products.
 */
public class RawMaterials {

	public Map<RawMaterialType,Integer> rawMaterials;
	
	public RawMaterials(Map<RawMaterialType,Integer> rawMaterials){
		checkNotNull(rawMaterials, "rawMaterials");
		this.rawMaterials = rawMaterials;
	}
	
	public RawMaterials(){
		this.rawMaterials = new HashMap<RawMaterialType, Integer>();
	}

	public void extract(RawMaterials rawMaterials) 
	   throws NotEnoughRawMaterialException{
	
		if (canExtract(rawMaterials)){
			for (RawMaterialType key : rawMaterials.rawMaterials.keySet()){
				Integer value = rawMaterials.rawMaterials.get(key);
				this.extract(key, value.intValue());
			}
		}
		else
			throw new NotEnoughRawMaterialException();
	}
	
	public void extract(RawMaterialType rawMaterialType, int quantityNeeded)
		   throws NotEnoughRawMaterialException{
		
		validateQuantity(quantityNeeded);
		
		if (canExtract(rawMaterialType,quantityNeeded)){
			Integer quantity = this.rawMaterials.get(rawMaterialType);
			this.rawMaterials.put(rawMaterialType, 
					quantity.intValue()-quantityNeeded);
		}
		else
			throw new NotEnoughRawMaterialException();
	}
	
	public boolean canExtract(RawMaterials rawMaterials){
		
		for (RawMaterialType key : rawMaterials.rawMaterials.keySet()){
			int value = rawMaterials.getRawMaterialQuantity(key);
			
			if (!this.canExtract(key, value))
				return false;	
		}
		
		return true;
	}
	
	public boolean canExtract(RawMaterialType rawMaterialType,
			int quantityNeeded){
		
		int quantity = this.getRawMaterialQuantity(rawMaterialType);
		return quantity >= quantityNeeded;
	}

	public void store(RawMaterialType rawMaterialType, int quantityStore){
	
		validateQuantity(quantityStore);
		
		int quantity = this.getRawMaterialQuantity(rawMaterialType);
		this.rawMaterials.put(rawMaterialType, quantityStore + quantity);
	}
	
	public int getRawMaterialQuantity(RawMaterialType rawMaterialType){
		
		Integer quantity = this.rawMaterials.get(rawMaterialType);
		if (quantity != null)
			return quantity.intValue();
		else
			return 0;
	}

	private void validateQuantity(int quantity){
		checkGreaterEqual(quantity,0,"quantity");
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rawMaterials == null) ? 0 : rawMaterials.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "RawMaterials [rawMaterials=" + rawMaterials + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RawMaterials other = (RawMaterials) obj;
		if (rawMaterials == null) {
			if (other.rawMaterials != null)
				return false;
		} else if (!rawMaterials.equals(other.rawMaterials))
			return false;
		return true;
	}

	public String toPrettyString() {
		// TODO Auto-generated method stub
		return "";
	}
}