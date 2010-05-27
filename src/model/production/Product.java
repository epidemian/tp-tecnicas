package model.production;

import static model.utils.ArgumentUtils.*;

import model.exception.BusinessLogicException;

/**
 * Representation of a product that can be created in the assembly plant.
 * 
 */
public class Product {

	private ProductionSequence history;
	
	private ProductType productType;
	private boolean defective;

	public Product(RawMaterials rawMaterials){
		this.setDefective(false);
		this.history = new ProductionSequence(rawMaterials);
	}
	
	public boolean isDamaged() {
		return defective;
	}

	public void setDefective(){
		this.defective = true;
	}
	
	public ProductType getProductType() {
		if (productType == null)
			throw new BusinessLogicException("Product type not resolved");
		
		return productType;
	}	
	
	public void resolveProductType(ValidProductionSequences validSequences){
		this.setProductType(this.history.identifyProductType(validSequences));
	}
	
	public void addMachineTypeToHistory(MachineType machineType){
		checkNotNull(machineType, "machineType");
		this.history.addMachineType(machineType);
	}

	private void setDefective(boolean defective) {
		this.defective = defective;
	}

	private void setProductType(ProductType productType) {
		checkNotNull(productType, "productType");
		this.productType = productType;
	}
}
