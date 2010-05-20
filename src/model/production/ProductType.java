package model.production;

import static model.core.ArgumentUtils.*;

import java.util.ArrayList;

/**
 * Specifies a product type.
 * A product type has a name and the raw materials needed to create a product. 
 * 
 */
public class ProductType extends AbstractType {
	
	private ArrayList<RawMaterial> rawMaterialsNeeded;
	
	public ProductType(String name, ArrayList<RawMaterial> rawMaterialsNeeded){
		super(name);		
		this.setRawMaterialsNeeded(rawMaterialsNeeded);
	}
	
	public ArrayList<RawMaterial> getRawMaterialsNeeded() {
		return rawMaterialsNeeded;
	}
	
	private void setRawMaterialsNeeded(ArrayList<RawMaterial>
		rawMaterialsNeeded) {
		checkNotNull(rawMaterialsNeeded, "rawMaterialsNeeded");
		this.rawMaterialsNeeded = rawMaterialsNeeded;
	}
}
