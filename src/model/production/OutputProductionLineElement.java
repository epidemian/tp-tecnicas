package model.production;

import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class OutputProductionLineElement extends ProductionLineElement{
		
	public OutputProductionLineElement() {
		super(1,1);
	}

	@Override
	public Direction getInputConnectionDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position getInputConnectionPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Direction getOutputConnectionDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position getOutputConnectionPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		// TODO Auto-generated method stub
		
	}	

}
