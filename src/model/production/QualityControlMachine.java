package model.production;

import static model.utils.ArgumentUtils.checkNotNull;
import model.warehouse.TileElementVisitor;

public class QualityControlMachine extends Machine{

	public QualityControlMachine(MachineType machineType, 
			ProductionLineElement next, ProductionLineElement previous, int width, int height) {
		super(machineType, next, previous, width, height);
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
	public void accept(TileElementVisitor visitor) {
		visitor.visitQualityControlMachine(this);
	}
	
	@Override
	public int getPrice() {
		return 0;
	}
}
