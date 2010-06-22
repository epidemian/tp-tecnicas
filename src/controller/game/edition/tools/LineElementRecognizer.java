package controller.game.edition.tools;

import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;

class LineElementRecognizer extends TileElementVisitor {

	private static final LineElementRecognizer INSTANCE = new LineElementRecognizer();

	private LineElementRecognizer() {
	}

	private ProductionLineElement lineElement;

	public static ProductionLineElement recognizeLineElement(
			TileElement tileElement) {
		// Note: this wold be much easier using instanceof (and cleaner...)
		INSTANCE.lineElement = null;
		tileElement.accept(INSTANCE);
		return INSTANCE.lineElement;
	}

	@Override
	public void visitConveyor(Conveyor conveyor) {
		this.lineElement = conveyor;
	}

	@Override
	public void visitProductionMachine(ProductionMachine machine) {
		this.lineElement = machine;
	}

	@Override
	public void visitQualityControlMachine(QualityControlMachine machine) {
		this.lineElement = machine;
	}

	@Override
	public void visitInputProductionLineElement(
			InputProductionLineElement inputLineElement) {
		this.lineElement = inputLineElement;
	}

	@Override
	public void visitOutputProductionLineElement(
			OutputProductionLineElement outputLineElement) {
		this.lineElement = outputLineElement;
	}

}