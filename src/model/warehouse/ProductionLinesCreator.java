package model.warehouse;

import static model.production.line.ProductionLine.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.production.RawMaterials;
import model.production.StorageArea;
import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
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
		List<ProductionLineElement> allLineElements = collector
				.getProductionLineElements();
		List<InputProductionLineElement> inputElements = collector
				.getInputElements();
		List<OutputProductionLineElement> outputElements = collector
				.getOutputElements();

		return createFromProductionLineElements(allLineElements, inputElements,
				outputElements);
	}

	private List<ProductionLine> createFromProductionLineElements(
			List<ProductionLineElement> lineElements,
			List<InputProductionLineElement> inputElements,
			List<OutputProductionLineElement> outputElements) {

		List<ProductionLine> lines = new ArrayList<ProductionLine>();
		List<ProductionLineElement> touchedElements;
		touchedElements = new ArrayList<ProductionLineElement>();

		for (ProductionLineElement lineElement : lineElements)
			if (!touchedElements.contains(lineElement))
				lines.add(processLineElement(lineElement, touchedElements,
						inputElements, outputElements));

		return lines;
	}

	private ProductionLine processLineElement(
			ProductionLineElement lineElement,
			List<ProductionLineElement> touchedElements,
			List<InputProductionLineElement> inputElements,
			List<OutputProductionLineElement> outputElements) {

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
		ProductionLineElement lastElement = lineElement;
		if (!circularLine) {

			ProductionLineElement next = lineElement.getNextLineElement();

			while (next != null) {
				lastElement = next;
				touchedElements.add(next);
				next = next.getNextLineElement();
			}
		}

		ProductionLine line;
		if (circularLine) {
			line = createCircularProductionLine(firstElement);
		} else {
			int indexOfFirstElement = inputElements.indexOf(firstElement);
			int indexOfLastElement = outputElements.indexOf(lastElement);

			if (indexOfFirstElement != -1 && indexOfLastElement != -1) {
				InputProductionLineElement inputElement = inputElements
						.get(indexOfFirstElement);
				OutputProductionLineElement outputElement = outputElements
						.get(indexOfLastElement);

				line = createFunctionalProductionLine(storageArea, inputElement,
						outputElement);
			}
			else {
				line = createDisfunctionalProductionLine(firstElement);
			}
		}
		
		return line;
	}

	private class ProductionLineElementCollector extends TileElementVisitor {

		private List<ProductionLineElement> productionLineElements = new ArrayList<ProductionLineElement>();
		private List<InputProductionLineElement> inputElements = new ArrayList<InputProductionLineElement>();
		private List<OutputProductionLineElement> outputElements = new ArrayList<OutputProductionLineElement>();

		public List<ProductionLineElement> getProductionLineElements() {
			return this.productionLineElements;
		}

		public List<InputProductionLineElement> getInputElements() {
			return inputElements;
		}

		public List<OutputProductionLineElement> getOutputElements() {
			return outputElements;
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

		@Override
		public void visitInputProductionLineElement(
				InputProductionLineElement inputProductionLineElement) {
			this.productionLineElements.add(inputProductionLineElement);
			this.inputElements.add(inputProductionLineElement);
		}

		@Override
		public void visitOutputProductionLineElement(
				OutputProductionLineElement outputProductionLineElement) {
			this.productionLineElements.add(outputProductionLineElement);
			this.outputElements.add(outputProductionLineElement);
		}

	}
}
