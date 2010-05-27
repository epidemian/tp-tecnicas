package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

public class QualityControlMachine extends Machine{

	public QualityControlMachine(MachineType machineType, 
			ProductionLineElement next, ProductionLineElement previous) {
		super(machineType, next, previous);
	}
	
	/**
	 * Represents the number of defective products until one product become
	 * non-defective. 
	 */
	private int discardDamagedFrequency;
	private int defectiveProducts;

	@Override
	public void treatProduct(Product input) {
		checkNotNull(input, "input product");
		if (input.isDamaged()){
			this.defectiveProducts++;
			if (this.discardDamagedFrequency == this.defectiveProducts)
				input = null;
		}
	} 
	
	@Override
	public int getPrice() {
		return 0;
	}
}
