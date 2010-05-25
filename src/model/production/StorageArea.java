package model.production;

import model.exception.BusinessLogicException;

public class StorageArea {

	/**
	 * Contains all the raw materials of the AssemblyPlant 
	 */
	private RawMaterials rawMaterials;
	
	public StorageArea(RawMaterials rawMaterials){
		this.setRawMaterials(rawMaterials);
	}

	public RawMaterials getRawMaterials() {
		return rawMaterials;
	}

	/**
	 * Creation of the product which will enter the inputStorage.
	 */
	public Product createProduct(RawMaterials inputRawMaterials){
		
		try{
			this.rawMaterials.extract(inputRawMaterials);
		}
		catch (NotEnoughRawMaterialException e) {
			return null;
		}
		
		return new Product(inputRawMaterials);
	}
	
	public void storeRawMaterial(RawMaterialType rawMaterialType,
			int quantityStore){
		this.rawMaterials.store(rawMaterialType, quantityStore);
	}
	
	private void setRawMaterials(RawMaterials rawMaterials) {
		if (rawMaterials == null)
			throw new BusinessLogicException("Invalid rawMaterials");
		this.rawMaterials = rawMaterials;
	}
}