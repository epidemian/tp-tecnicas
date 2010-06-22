package model.production.elements;

public interface ConnectionRules {

	/**
	 * Whether two production line elements can be connected under these
	 * connection rules.
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	boolean canConnect(ProductionLineElement element1,
			ProductionLineElement element2);

}
