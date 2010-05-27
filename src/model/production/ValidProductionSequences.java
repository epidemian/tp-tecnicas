package model.production;

import static model.utils.ArgumentUtils.*;

import java.util.HashMap;
import java.util.Map;

public class ValidProductionSequences {

	private Map<ProductionSequence,ProductType> products;
	
	public ValidProductionSequences(){
		this.products = new HashMap<ProductionSequence,ProductType>();
	}
	
	public void addValidProductionSequence(ProductionSequence sequence,
			ProductType productType) {

		checkNotNull(sequence, "sequence");
		checkNotNull(productType, "productType");
		checkArgCondition(sequence, !this.products.containsKey(sequence),
				"production sequence already contained");
		this.products.put(sequence, productType);
	}
	
	public ProductType identifyProductType(ProductionSequence
			productionSequence) {
		
		ProductType productType = this.products.get(productionSequence);
		
		if (productType == null){
			return ProductType.getWaste(); 
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
	public String toString() {
		return "ValidProductionSequences [products=" + products + "]";
	}
}