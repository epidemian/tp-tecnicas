package model.warehouse;

import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;

/**
 * Note: this is not an interface so implementors can choose which method to
 * override.
 */
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

	public void visitOutputProductionLineElement(
			OutputProductionLineElement outputLineElement) {
	}

	public void visitInputProductionLineElement(
			InputProductionLineElement inputLineElement) {
	}
}
