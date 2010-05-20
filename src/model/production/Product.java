package model.production;

import static model.core.ArgumentUtils.*;

/**
 * Representation of a product that can be created in the assembly plant.
 * 
 */
public class Product {

	private ProductType productType;
	private boolean defective;

	public Product(ProductType productType){
		this.setDefective(false);
		this.setProductType(productType);
	}
	
	public boolean isDamaged() {
		return defective;
	}

	public void setDefective(){
		this.defective = true;
	}
	
	public ProductType getProductType() {
		return productType;
	}	

	private void setDefective(boolean defective) {
		this.defective = defective;
	}

	private void setProductType(ProductType productType) {
		checkNotNull(productType, "productType");
		this.productType = productType;
	}
}
