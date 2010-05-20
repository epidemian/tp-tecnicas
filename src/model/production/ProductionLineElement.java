package model.production;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductionLineElement extends Container{

	public void process(){
		throw new NotImplementedException();
	}
	
	public boolean isProductionMachine(){
		return false;
	}
}
