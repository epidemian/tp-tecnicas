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
	
	public static synchronized ValidProductionSequences getInstance() {
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
	
	public boolean isEmpty(){
		return this.products.isEmpty();
	}
	
	public void clear(){
		this.products.clear();
	}
	
	@Override
	public String toString(){
		
		String string = new String("Valid Production Sequences: \n");
		
		for (ProductionSequence key : this.products.keySet()){
			 ProductType value = this.products.get(key);
		
			 string += "ProductType: " + value.toString() + " - Machines: " 
			 		+ key.toString() + "\n";
		} 
		
		return string;
		
	}
}