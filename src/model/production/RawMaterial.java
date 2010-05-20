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

	public void extract(int quantity) throws NotEnoughRawMaterialException{
		boolean isEnough = canExtract(quantity);
		if (isEnough)
			this.setQuantity(this.quantity - quantity);
		else
			throw new NotEnoughRawMaterialException("You try to extract " + quantity  + " from " + this.name);
	}
	
	public boolean canExtract(int quantity){
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

	public boolean equals(Object other){
		return this.name.equals(((RawMaterial)other).name);
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