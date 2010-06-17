package model.production.elements;

import model.exception.BusinessLogicException;
import model.production.Direction;
import model.production.Product;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class OutputProductionLineElement extends ProductionLineElement {

	private final Direction inputConnectionDirection;

	private int dailyProduction = 0;
	
	public OutputProductionLineElement() {
		this(Direction.WEST);
	}

	public OutputProductionLineElement(Direction direction) {
		super(1, 1);
		inputConnectionDirection = direction;
	}
	
	public int getDailyProduction() {
		return dailyProduction;
	}
	
	public void resetDailyProduction() {
		this.dailyProduction = 0;
	}
	
	

	@Override
	public boolean canHaveNextLineElement() {
		return false;
	}

	@Override
	public Direction getInputConnectionDirection() {
		return this.inputConnectionDirection;
	}

	@Override
	public Position getInputConnectionPosition() {
		return this.getPosition().add(
				this.getInputConnectionDirection().getAssociatedPosition());
	}

	@Override
	public Direction getOutputConnectionDirection() {
		throw new BusinessLogicException("Illegal operation");
	}

	@Override
	public Position getOutputConnectionPosition() {
		throw new BusinessLogicException("Illegal operation");
	}

	@Override
	public void accept(TileElementVisitor visitor) {
		visitor.visitOutputProductionLineElement(this);
	}

	@Override
	public int getPurchasePrice() {
		return 0;
	}

	@Override
	public int getSalePrice() {
		return 0;
	}

	@Override
	public Product process(Product input) {
		Product output = super.process(input);
		if (output != null)
			this.dailyProduction++;
		return output;
	}
	
	

}
