package model.production;

import java.util.ArrayList;

import model.core.BusinessLogicException;

public class StorageArea {

	/**
	 * Contains all the raw materials of the AssemblyPlant 
	 */
	private ArrayList<RawMaterial> rawMaterials;
	
	public StorageArea(ArrayList<RawMaterial> rawMaterials){
		this.setRawMaterials(rawMaterials);
	}

	public ArrayList<RawMaterial> getRawMaterials() {
		return rawMaterials;
	}

	/**
	 * Creation of the product which will enter the inputStorage. If the
	 * SequenceProduction does not produce a known product it will create waste.
	 * 
	 * @param productTypeRequired determines which Product will try to create. 
	 * @return
	 */
	public Product getProduct(ProductType productType){
		
		ArrayList<RawMaterial> rawMaterialsNeededToProduce 
			= new ArrayList<RawMaterial>();
		/*
		 * We get the raw materials needed and analyze if there is enough
		 * of each one to produce.
		 */
		for (RawMaterial entry : productType.getRawMaterialsNeeded()){
			int index = this.rawMaterials.indexOf(entry);
			if (index >= 0){
				if (!this.rawMaterials.get(index)
						.isEnoughRawMaterial(entry.getQuantity())){
					rawMaterialsNeededToProduce
					.add(this.rawMaterials.get(index));
				}
				else 
					break;
			}			
		}
		
		/*
		 * Not finished yet! Coming soon!
		 * Do not touch this code! =)
		 */		
		return null;
	}
	
	public void storeRawMaterial(RawMaterial rawMaterial){
		int index = this.rawMaterials.indexOf(rawMaterial);
		if (index >= 0){
			this.rawMaterials.get(index).store(rawMaterial.getQuantity());
		}
		else{
			this.rawMaterials.add(rawMaterial);
		}
	}
	
	private void setRawMaterials(ArrayList<RawMaterial> rawMaterials) {
		if (rawMaterials == null)
			throw new BusinessLogicException("Invalid rawMaterials");
		this.rawMaterials = rawMaterials;
	}
}