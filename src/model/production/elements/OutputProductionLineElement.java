package model.production.elements;

import model.production.Direction;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class OutputProductionLineElement extends ProductionLineElement{
		
	private final Direction inputConnectionDirection;
	
	public OutputProductionLineElement() {
		super(1,1);
		inputConnectionDirection=Direction.NORTH;
	}
	
	public OutputProductionLineElement(Direction direction) {
		super(1,1);
		inputConnectionDirection=direction;
	}

	@Override
	public Direction getInputConnectionDirection() {
		return this.inputConnectionDirection;
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

	@Override
	public int getPurchasePrice() {
		return 0;
	}

	@Override
	public int getSalePrice() {
		return 0;
	}	

}
