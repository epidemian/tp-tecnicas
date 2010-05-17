package model.production;

import model.core.BusinessLogicException;

/**
 * Representation of a raw material that can be used to create products.
 * 
 */
public class RawMaterial {

	private int quantity;
	private String name;
	
	public RawMaterial(String name, int quantity){
		this.setName(name);
		this.setQuantity(quantity);
	}

	public String getName() {
		return name;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public boolean extract(int quantity){
		boolean isEnough = isEnoughRawMaterial(quantity);
		if (isEnough)
			this.setQuantity(this.quantity - quantity);
		return isEnough;
	}
	
	public boolean isEnoughRawMaterial(int quantity){
		this.validateQuantity(quantity);
		return (this.quantity - quantity >= 0);
	}
	
	public boolean equals(RawMaterial anotherRawMaterial){
		return this.name.equals(anotherRawMaterial.name);
	}
	
	public void store(int quantity){
		this.validateQuantity(quantity);
		this.setQuantity(this.quantity + quantity);
	}
	
	private void setName(String name) {
		this.validateName(name);
		this.name = name;
	}

	private void setQuantity(int quantity) {
		this.validateQuantity(quantity);
		this.quantity = quantity;
	}
	
	private void validateQuantity(int quantity){
		if (quantity < 0)
			throw new BusinessLogicException("Invalid quantity");
	}
	
	private void validateName(String name){
		if (name == null)
			throw new BusinessLogicException("Invalid name");
	}
}