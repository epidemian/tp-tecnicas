package model.production;

import java.util.HashMap;
import java.util.Map;

public class ProductionSequenceTree {

	private Map<ProductRecipe,ProductType> products;
	
	public ProductionSequenceTree(){
		this.products = new HashMap<ProductRecipe,ProductType>();
	}
	
	public ProductType identifyProduct(ProductRecipe productRecipe){
	
		ProductType productType = this.products.get(productRecipe);
		
		if (productType == null){
			return ProductType.createWaste(productRecipe.getIngredients()); 
		}
		
		return productType;
	}
	
	public void addValidProductionSequence(ProductRecipe productRecipe
			, ProductType productType){
	
		this.products.put(productRecipe, productType);
	}		
}
