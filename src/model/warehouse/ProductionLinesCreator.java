package model.warehouse;

import static model.production.ProductionLine.*;

import java.util.ArrayList;
import java.util.Collection;

import model.production.Conveyor;
import model.production.Machine;
import model.production.ProductionLine;
import model.production.ProductionLineElement;
import model.production.RawMaterials;
import model.production.StorageArea;

public class ProductionLinesCreator {

	private StorageArea storageArea;

	public ProductionLinesCreator(StorageArea storageArea) {
		this.storageArea = storageArea;
	}

	public Collection<ProductionLine> createFromGround(Ground ground) {

		ProductionLineElementCollector collector = new ProductionLineElementCollector();

		for (int i = 0; i < ground.getRows(); i++) {
			for (int j = 0; j < ground.getCols(); j++) {
				TileElement tileElement = ground.getTile(i, j).getTileElement();
				if (tileElement != null)
					tileElement.accept(collector);
			}
		}

		return createFromProductionLineElements(collector
				.getProductionLineElements());
	}

	private Collection<ProductionLine> createFromProductionLineElements(
			Collection<ProductionLineElement> lineElements) {

		Collection<ProductionLine> lines = new ArrayList<ProductionLine>();
		Collection<ProductionLineElement> touchedElements;
		touchedElements = new ArrayList<ProductionLineElement>();

		while (!lineElements.isEmpty()) {
			ProductionLineElement lineElement = lineElements.iterator().next();
			if (!touchedElements.contains(lineElement))
				lines.add(processLineElement(lineElement, touchedElements));
		}

		return lines;
	}

	private ProductionLine processLineElement(
			ProductionLineElement lineElement,
			Collection<ProductionLineElement> touchedElements) {

		touchedElements.add(lineElement);

		boolean circularLine = false;
		ProductionLineElement previous = lineElement.getPreviousLineElement();

		/*
		 * Try to find the first element in the line.
		 */
		while (previous != null && !circularLine) {

			if (previous == lineElement)
				circularLine = true;
			else
				touchedElements.add(previous);

			previous = previous.getPreviousLineElement();
		}

		/*
		 * Add to the touchedElements list the production elements between
		 * lineElement and the last one in the line.
		 */
		if (!circularLine) {

			ProductionLineElement next = lineElement.getNextLineElement();

			while (next != null) {
				touchedElements.add(next);
				next = lineElement.getNextLineElement();
			}
		}

		// TODO: Ver qué hacer con los new RawMaterials()
		return circularLine ? createCircularProductionLine(previous,
				this.storageArea, new RawMaterials())
				: createValidProductionLine(previous, this.storageArea,
						new RawMaterials());

	}

	private class ProductionLineElementCollector implements TileElementVisitor {

		private Collection<ProductionLineElement> productionLineElements;

		public ProductionLineElementCollector() {
			this.productionLineElements = new ArrayList<ProductionLineElement>();
		}

		public Collection<ProductionLineElement> getProductionLineElements() {
			return this.productionLineElements;
		}

		@Override
		public void visitWall(Wall wall) {
			// I don't care 'bout walls you know...
		}

		@Override
		public void visitConveyor(Conveyor conveyor) {
			// TODO: it might be a good idea to distinguish between conveyors
			// and machines, as the production line will probably have to do it
			// after.
			this.productionLineElements.add(conveyor);
		}

		@Override
		public void visitMachine(Machine machine) {
			this.productionLineElements.add(machine);
		}

	}
}
