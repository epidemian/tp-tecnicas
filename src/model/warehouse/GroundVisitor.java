package model.warehouse;

import model.production.Conveyor;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;

public abstract class GroundVisitor implements TileElementVisitor {

	Position currentPosition = new Position();

	public Position getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public void visitConveyor(Conveyor conveyor) {
	}

	@Override
	public void visitProductionMachine(ProductionMachine machine) {
	}

	@Override
	public void visitQualityControlMachine(QualityControlMachine machine) {
	}

	@Override
	public void visitWall(Wall wall) {
	}

}