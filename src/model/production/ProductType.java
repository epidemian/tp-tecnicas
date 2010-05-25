package model.production;

/**
 * Specifies a product type.
 * A product type has a name and the raw materials needed to create a product. 
 * 
 */
public class ProductType extends AbstractType {
	
	private static final String WASTE_NAME = "waste";
	
	public static ProductType createWaste(){
		return new ProductType(WASTE_NAME);
	}
	
	public ProductType(String name){
		super(name);		
	}
}
