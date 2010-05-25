package model.production;

import java.util.List;

public class ProductionLine {

	/**
	 * A container to store the raw material before it enters the first machine
	 * No production can be made if the ProductionLine does not have this Container
	 */
	
	private Container inputStorage;
	
	
	/**
	 * A container to store the the product after passing through all the machines in the line.
	 * No production can be made if the ProductionLine does not have this Container
	 */
	private Container outputStorage;

	/**
	 * First element in the production line.
	 */
	private ProductionLineElement line;
	
	private ProductionLine(ProductionLineElement line){
		this.line = line;
	}
	
	public static ProductionLine createCircularProductionLine(
			ProductionLineElement line){
		// TODO not implemented yet
		return null;
	}
	
	public static ProductionLine createValidProductionLine(
			ProductionLineElement line){
		return new ProductionLine(line);
	}	
}
