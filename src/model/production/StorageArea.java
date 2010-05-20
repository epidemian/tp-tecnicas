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
		 * We get the raw materials needed and store them into a vector.
		 */
		for (RawMaterial entry : productType.getRawMaterialsNeeded()){
			int index = this.rawMaterials.indexOf(entry);
			if (index >= 0){
				if (!this.rawMaterials.get(index)
						.canExtract(entry.getQuantity())){
					rawMaterialsNeededToProduce
					.add(this.rawMaterials.get(index));
				}
				else 
					break;
			}			
		}

		Product product = null;
		
		if (rawMaterialsNeededToProduce.size() == productType
			.getRawMaterialsNeeded().size())
		{
			/*
			 * We can produce =), so we extract the raw material needed.
			 */
			for (int i = 0; i < rawMaterialsNeededToProduce.size(); i++){
				try {
					this.rawMaterials.get(i).extract
					(productType.getRawMaterialsNeeded().get(i).getQuantity());
				} catch (NotEnoughRawMaterialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			product = new Product(productType);
		}
		
		return product;
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