package model.production;

import static model.utils.ArgumentUtils.*;

import java.util.List;

/**
 * Specifies a product type.
 * A product type has a name and the raw materials needed to create a product. 
 * 
 */
public class ProductType extends AbstractType {
	
	private List<RawMaterial> rawMaterialsNeeded;
	private static final String WASTE_NAME = "waste";
	
	public static ProductType createWaste(List<RawMaterial> rawMaterialsNeeded){
		return new ProductType(WASTE_NAME, rawMaterialsNeeded);
	}
	
	public ProductType(String name, List<RawMaterial> rawMaterialsNeeded){
		super(name);		
		checkWasteName(name);
		this.setRawMaterialsNeeded(rawMaterialsNeeded);
	}
	
	public List<RawMaterial> getRawMaterialsNeeded() {
		return rawMaterialsNeeded;
	}
	
	private void setRawMaterialsNeeded(List<RawMaterial>
		rawMaterialsNeeded) {
		checkNotNull(rawMaterialsNeeded, "rawMaterialsNeeded");
		this.rawMaterialsNeeded = rawMaterialsNeeded;
	}
	
	private void checkWasteName(String name){
		checkArgCondition(name, !name.equals(WASTE_NAME), "cannot be " 
				+ this.WASTE_NAME);
	}
}
