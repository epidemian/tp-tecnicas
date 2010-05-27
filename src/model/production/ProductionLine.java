package model.production;

import java.util.List;

public class ProductionLine {

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
	
	public ProductionLineElement getFirstElement(){
		return line;		
	}
}
