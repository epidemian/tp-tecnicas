package model.warehouse;

import static model.production.line.ProductionLine.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.production.RawMaterials;
import model.production.StorageArea;
import model.production.elements.Conveyor;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.production.line.ProductionLine;

public class ProductionLinesCreator {

	private StorageArea storageArea;

	public ProductionLinesCreator(StorageArea storageArea) {
		this.storageArea = storageArea;
	}

	public List<ProductionLine> createFromGround(Ground ground) {

		ProductionLineElementCollector collector = new ProductionLineElementCollector();
		ground.visitElements(collector);
		return createFromProductionLineElements(collector
				.getProductionLineElements());
	}

	private List<ProductionLine> createFromProductionLineElements(
			List<ProductionLineElement> lineElements) {

		List<ProductionLine> lines = new ArrayList<ProductionLine>();
		List<ProductionLineElement> touchedElements;
		touchedElements = new ArrayList<ProductionLineElement>();

		Iterator<ProductionLineElement> lineElementsIterator = lineElements
				.iterator();

		while (lineElementsIterator.hasNext()) {
			ProductionLineElement lineElement = lineElementsIterator.next();
			if (!touchedElements.contains(lineElement))
				lines.add(processLineElement(lineElement, touchedElements));
		}

		return lines;
	}

	private ProductionLine processLineElement(
			ProductionLineElement lineElement,
			List<ProductionLineElement> touchedElements) {

		touchedElements.add(lineElement);

		boolean circularLine = false;
		ProductionLineElement previous = lineElement.getPreviousLineElement();
		ProductionLineElement firstElement = lineElement;

		/*
		 * Try to find the first element in the line.
		 */
		while (previous != null && !circularLine) {

			if (previous == lineElement)
				circularLine = true;
			else
				touchedElements.add(previous);

			firstElement = previous;
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
				next = next.getNextLineElement();
			}
		}

		// TODO: Ver qué hacer con los new RawMaterials()
		return circularLine ? createCircularProductionLine(previous)
				: createValidProductionLine(firstElement, this.storageArea,
						new RawMaterials());

	}

	private class ProductionLineElementCollector extends TileElementVisitor {

		private List<ProductionLineElement> productionLineElements;

		public ProductionLineElementCollector() {
			this.productionLineElements = new ArrayList<ProductionLineElement>();
		}

		public List<ProductionLineElement> getProductionLineElements() {
			return this.productionLineElements;
		}

		@Override
		public void visitConveyor(Conveyor conveyor) {
			this.productionLineElements.add(conveyor);
		}

		@Override
		public void visitProductionMachine(ProductionMachine machine) {
			this.productionLineElements.add(machine);
		}

		@Override
		public void visitQualityControlMachine(QualityControlMachine machine) {
			this.productionLineElements.add(machine);
		}
	}
}
