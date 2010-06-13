package model.production;

import static model.utils.ArgumentUtils.checkNotNull;
import model.warehouse.TileElementVisitor;

public class QualityControlMachine extends Machine {

	public QualityControlMachine(MachineType machineType){
		super(machineType);
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
		if (input.isDamaged()) {
			this.defectiveProducts++;
			if (this.discardDamagedFrequency == this.defectiveProducts)
				input = null;
		}
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitQualityControlMachine(this);
	}

}
