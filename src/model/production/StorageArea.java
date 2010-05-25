package model.production;

import java.util.LinkedList;
import java.util.List;

import model.exception.BusinessLogicException;

public class StorageArea {

	private List<Product> productsProduced;
	
	/**
	 * Contains all the raw materials of the AssemblyPlant 
	 */
	private RawMaterials rawMaterials;
	
	public StorageArea(RawMaterials rawMaterials){
		this.setRawMaterials(rawMaterials);
		this.productsProduced = new LinkedList<Product>();
	}

	public RawMaterials getRawMaterials() {
		return rawMaterials;
	}

	public void addProduct(Product product){
		this.productsProduced.add(product);
	}
	
	public int countDefectiveProducts(){
		
		int defectiveProducts = 0;
		
		for (Product entry : this.productsProduced){
			if(entry.isDamaged())
				defectiveProducts++;
		}
		
		return defectiveProducts;
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