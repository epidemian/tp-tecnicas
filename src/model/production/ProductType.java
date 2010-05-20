package model.production;

import static model.core.ArgumentUtils.*;

import java.util.ArrayList;

/**
 * Specifies a product type.
 * A product type has a name and the raw materials needed to create a product. 
 * 
 */
public class ProductType {
	
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
	
	private void setRawMaterialsNeeded(ArrayList<RawMaterial> rawMaterialsNeeded) {
		checkNotNull(rawMaterialsNeeded, "rawMaterialsNeeded");
		this.rawMaterialsNeeded = rawMaterialsNeeded;
	}

	private void setName(String name) {
		checkNotNull(name, "name");
	}
}
