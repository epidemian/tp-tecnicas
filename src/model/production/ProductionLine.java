package model.production;

import java.util.List;

public class ProductionLine {

	
	
	/**
	 * 
	 */
	private List<Container> line;
	
	
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
	
	
	
}
