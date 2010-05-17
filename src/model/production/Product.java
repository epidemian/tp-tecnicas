package model.production;

/**
 * Representation of a product that can be created in the assembly plant.
 * 
 */
public class Product {

	private ProductType productType;

	private boolean damaged;

	public void setDamaged() {
		this.damaged = true;
	}

	public boolean isDamaged() {
		return damaged;
	}	
}
