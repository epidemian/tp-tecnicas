package model.production;

import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;

public class ProductionLineElementUtils {

	private static final ProductionLineElementRecognizer lineElementRecognizer = new ProductionLineElementRecognizer();

	private ProductionLineElementUtils() {
	}

	public static ProductionLineElement recognizeProductionLineElement(
			TileElement tileElement) {

		lineElementRecognizer.lineElement = null;
		tileElement.accept(lineElementRecognizer);
		return lineElementRecognizer.lineElement;
		// TODO: this is the same as as
		// return tileElement instanceof ProductionLineElement ?
		// (ProductionLineElement) tileElement : null;
		// but with much more code.... I don't know if it should be changed.
		// Demian
	}

	public static boolean isConveyor(ProductionLineElement lineElement) {
		lineElementRecognizer.conveyor = null;
		lineElement.accept(lineElementRecognizer);
		return lineElementRecognizer.conveyor != null;
	}
	
	public static boolean isMachine(ProductionLineElement lineElement) {
		lineElementRecognizer.machine = null;
		lineElement.accept(lineElementRecognizer);
		return lineElementRecognizer.machine != null;
	}

	private static class ProductionLineElementRecognizer extends
			TileElementVisitor {
		private ProductionLineElement lineElement = null;
		private Conveyor conveyor = null;
		private Machine machine = null;

		@Override
		public void visitConveyor(Conveyor conveyor) {
			this.lineElement = conveyor;
			this.conveyor = conveyor;
		}

		@Override
		public void visitProductionMachine(ProductionMachine machine) {
			this.lineElement = machine;
			this.machine = machine;
		}

		@Override
		public void visitQualityControlMachine(QualityControlMachine machine) {
			this.lineElement = machine;
			this.machine = machine;
		}
	}
}
