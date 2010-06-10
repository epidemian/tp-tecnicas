package model.production;

import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;

public class ProductionLineElementUtils {

	private static ProductionLineElementRecognizer LINE_ELEMENT_RECOGNIZER = new ProductionLineElementRecognizer();

	private ProductionLineElementUtils() {
	}

	public static ProductionLineElement recognizeProductionLineElement(
			TileElement tileElement) {

		return LINE_ELEMENT_RECOGNIZER
				.recognizeProductionLineElement(tileElement);
		// TODO: this is the same as as
		// return tileElement instanceof ProductionLineElement ?
		// (ProductionLineElement) tileElement : null;
		// but with much more code.... I don't know if it should be changed.
		// Demian
	}

	private static class ProductionLineElementRecognizer extends
			TileElementVisitor {
		private ProductionLineElement lineElement = null;

		public ProductionLineElement recognizeProductionLineElement(
				TileElement tileElement) {
			this.lineElement = null;
			tileElement.accept(this);
			return this.lineElement;
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
	}
}
