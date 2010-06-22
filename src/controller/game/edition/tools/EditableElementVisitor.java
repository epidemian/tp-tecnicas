package controller.game.edition.tools;

import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.EmptyTileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;

abstract class EditableElementVisitor extends TileElementVisitor {

	@Override
	public void visitConveyor(Conveyor conveyor) {
		this.visitEditableElement(conveyor);
	}

	@Override
	public void visitEmptyElement(EmptyTileElement emptyTileElement) {
		this.visitNonEditableElement();
	}

	@Override
	public void visitProductionMachine(ProductionMachine machine) {
		this.visitEditableElement(machine);
	}

	@Override
	public void visitQualityControlMachine(QualityControlMachine machine) {
		this.visitEditableElement(machine);
	}

	@Override
	public void visitWall(Wall wall) {
		this.visitNonEditableElement();
	}

	@Override
	public void visitInputProductionLineElement(
			InputProductionLineElement inputLineElement) {
		visitEditableElement(inputLineElement);
	}

	@Override
	public void visitOutputProductionLineElement(
			OutputProductionLineElement outputLineElement) {
		visitNonEditableElement();
	}

	protected abstract void visitEditableElement(ProductionLineElement element);

	protected abstract void visitNonEditableElement();

}