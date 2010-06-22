package model.production;

import model.production.elements.ConnectionRules;
import model.production.elements.ProductionLineElement;

public class PermissiveConnectionRules implements ConnectionRules{

	@Override
	public boolean canConnect(ProductionLineElement element1,
			ProductionLineElement element2) {
		return true;
	}
	
}
