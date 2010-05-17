package model.production;

import model.core.BusinessLogicException;

/**
 * Representation of a product that can be created in the assembly plant.
 * 
 */
public class Product {

	private ProductType productType;
	private boolean damaged;

	public Product(ProductType productType){
		this.setDamaged(false);
		this.setProductType(productType);
	}	
	
	public void setDamaged(boolean damage) {
		this.damaged = damage;
	}

	public boolean isDamaged() {
		return damaged;
	}

	public void breakUp(){
		this.damaged = true;
	}
	
	public ProductType getProductType() {
		return productType;
	}	

	private void setProductType(ProductType productType) {
		if (productType == null)
			throw new BusinessLogicException("Invalid productType");
		this.productType = productType;
	}
}
