package model.warehouse;

import model.production.Conveyor;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;

public abstract class TileElementVisitor {

	public void visitConveyor(Conveyor conveyor) {
	}

	public void visitProductionMachine(ProductionMachine machine) {
	}

	public void visitQualityControlMachine(QualityControlMachine machine) {
	}

	public void visitWall(Wall wall) {
	}

	public void visitEmptyElement(EmptyTileElement emptyTileElement) {
	}
}
