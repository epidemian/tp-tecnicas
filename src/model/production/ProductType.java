package model.production;

import java.util.ArrayList;

import model.core.BusinessLogicException;

/**
 * Specifies a product type.
 * A product type has a name and the raw materials needed to create a product. 
 * 
 */
public class ProductType {
	
	/**
	 * Every sequenceProduct which does not produce a known product produces 
	 * ProductType waste 
	 */
	public static ProductType waste; 
	
	private ArrayList<RawMaterial> rawMaterialsNeeded;
	private String name;
	
	public ProductType(String name, ArrayList<RawMaterial> rawMaterialsNeeded){
		this.setName(name);		
		this.setRawMaterialsNeeded(rawMaterialsNeeded);
	}
	
	public ArrayList<RawMaterial> getRawMaterialsNeeded() {
		return rawMaterialsNeeded;
	}
	
	public String getName() {
		return name;
	}
	
	public int hashCode(){
		return this.name.hashCode();
	}	

	private void setRawMaterialsNeeded(ArrayList<RawMaterial> rawMaterialsNeeded) {
		if (rawMaterialsNeeded == null)
			throw new BusinessLogicException("Invalid rawMaterialsNeeded");
		this.rawMaterialsNeeded = rawMaterialsNeeded;
	}

	private void setName(String name) {
		if (name == null)
			throw new BusinessLogicException("Invalid name");
		this.name = name;
	}
	
}
