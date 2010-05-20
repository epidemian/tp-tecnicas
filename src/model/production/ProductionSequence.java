package model.production;

import java.util.ArrayList;
import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductionSequence {

	/**
	 * Contains all the known combinations which produce valid products
	 * and also the productType related to the sequence.
	 */
	static private ProductionSequenceCollection validSequences;

	/**
	 * Contains the machines and conveyers of a specific line of production
	 */
	private ArrayList<MachineType> lineMachines;
	
	public ProductionSequence(ArrayList<MachineType> lineMachines){
		this.lineMachines=lineMachines;
	}
	
	static private boolean isValidSequence(ProductionSequence sequence){
		throw new NotImplementedException();
	}
	
	public boolean isValid(){
		return ProductionSequence.isValidSequence(this);
	}
	
	public ProductType identifyProduct(){
		throw new NotImplementedException();
	}

	public void setLineMachines(ArrayList<MachineType> lineMachines) {
		this.lineMachines = lineMachines;
	}

	public ArrayList<MachineType> getLineMachines() {
		return lineMachines;
	}
	
	public boolean equals(Object obj){
		
		ProductionSequence anotherSequence=(ProductionSequence) obj;
		
		Iterator<MachineType> itList=this.getLineMachines().iterator();
		Iterator<MachineType> itAnotherList = anotherSequence.getLineMachines().iterator();
		
		// compare pairs of machinetypes
		while(itList.hasNext() && itAnotherList.hasNext() ){
			if (!itList.next().equals(itAnotherList.next()))
				return false;
		}
		
		// there were more ProductionLineElements in one of the sequences
		if (itList.hasNext() || itAnotherList.hasNext())
			return false;
		
		
		return true;
	}
}

