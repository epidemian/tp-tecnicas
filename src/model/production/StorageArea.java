package model.production;

import java.util.HashMap;
import java.util.ArrayList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class StorageArea {
	
	/**
	 * Contains all the raw materials of the AssemblyPlant 
	 */
	private ArrayList<RawMaterial> availableMaterials;
	
	/**
	 * Creation of the product which will enter the inputStorage. If the
	 * SequenceProduction does not produce a known product it will create waste.
	 * 
	 * @param productTypeRequired determines which Product will try to create. 
	 * @return
	 */
	public Product getProduct(ProductType productTypeRequired){
		
		throw new NotImplementedException();
	}
}
