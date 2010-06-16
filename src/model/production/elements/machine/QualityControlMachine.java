package model.production.elements.machine;

import static model.utils.ArgumentUtils.checkNotNull;
import model.production.Product;
import model.production.elements.machine.states.MachineState;
import model.warehouse.TileElementVisitor;

public class QualityControlMachine extends Machine {

	public QualityControlMachine(MachineType machineType){
		super(machineType);
	}

	/* DEPRECATED
	 * Represents the number of defective products until one product become
	 * non-defective.
	 */
	//private int discardDamagedFrequency;
	//private int defectiveProducts;
	/*
	@Override
	public void treatProduct(Product input) {
		checkNotNull(input, "input product");
		if (input.isDamaged()) {
			this.defectiveProducts++;
			if (this.discardDamagedFrequency == this.defectiveProducts)
				input = null;
		}
	}
	*/
	
	@Override
	/*
	 * Uses machine coefficient. The bigger it is, the worse it is.
	 * Therefore the bigger it is, it will discard less defective products.    
	 */
	public void treatProduct(Product input) {
		checkNotNull(input, "input product");
		if (input.isDamaged()) {
			//if it is damaged, it will analyze the probs...
			double randomNum = Math.random();
			if (randomNum>this.getCurrentCoef()){
				//Discards!!
				input = null;
			}		
		}
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitQualityControlMachine(this);
	}

}
