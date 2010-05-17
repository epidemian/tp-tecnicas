package model.production;

import java.util.ArrayList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductionSequence {

	/**
	 * Contains all the known combinatios which produce valid products
	 * and also the productType related to the sequence.
	 */
	static private ProductionSequenceCollection validSequences;

	/**
	 * Contains the machines and conveyours of a specific line of production
	 */
	private ArrayList<ProductionLineElement> lineElements;
	
	static private boolean isValidSequence(ProductionSequence sequence){
		throw new NotImplementedException();
	}
	
	public boolean isValid(){
		throw new NotImplementedException();
	}
	
	public ProductType identifyProduct(){
		throw new NotImplementedException();
	}
}

