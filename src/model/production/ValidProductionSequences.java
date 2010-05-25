package model.production;

import java.util.HashMap;
import java.util.Map;

public class ValidProductionSequences {

	private Map<ProductionSequence,ProductType> products;
	private final static ValidProductionSequences singleton 
			= new ValidProductionSequences();
	
	private ValidProductionSequences(){
		this.products = new HashMap<ProductionSequence,ProductType>();
	}
	
	public static ValidProductionSequences getInstance() {
		return singleton;
	}

	public void addValidProductionSequence(ProductionSequence sequence
			, ProductType productType){
	
		this.products.put(sequence, productType);
	}	
	
	public ProductType identifyProductType(ProductionSequence
			productionSequence) {
		
		ProductType productType = this.products.get(productionSequence);
		
		if (productType == null){
			return ProductType.createWaste(); 
		}
			
		return productType;
	}
}