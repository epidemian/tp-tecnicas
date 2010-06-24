package model.production.elements;

import java.util.ArrayList;

import java.util.List;

import model.exception.BusinessLogicException;
import model.production.Direction;
import model.production.Product;
import model.warehouse.Position;
import model.warehouse.TileElementVisitor;

public class OutputProductionLineElement extends ProductionLineElement {

	private final Direction inputConnectionDirection;

	private List<Integer> dailyProductionHistory;
	private int dailyProduction = 0;
	
	public OutputProductionLineElement() {
		this(Direction.WEST);
	}

	public OutputProductionLineElement(Direction direction) {
		super(1, 1);
		this.inputConnectionDirection = direction;
		this.dailyProductionHistory = new ArrayList<Integer>();
	}
	
	public int getDailyProduction() {
		return dailyProduction;
	}
	
	public List<Integer> getDailyProductionHistory(){
		return this.dailyProductionHistory;
	}
	
	public void resetDailyProduction() {
		this.dailyProductionHistory.add(this.dailyProduction);
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
	public Position getInputConnectionRelativePosition() {
		return this.getInputConnectionDirection().getAssociatedPosition();
	}

	@Override
	public Direction getOutputConnectionDirection() {
		throw new BusinessLogicException("Illegal operation");
	}

	@Override
	public Position getOutputConnectionRelativePosition() {
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
