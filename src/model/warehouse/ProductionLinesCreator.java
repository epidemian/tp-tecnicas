package model.warehouse;

import static model.production.ProductionLine.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.production.Conveyor;
import model.production.ProductionLine;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.production.RawMaterials;
import model.production.StorageArea;

public class ProductionLinesCreator {

	private StorageArea storageArea;

	public ProductionLinesCreator(StorageArea storageArea) {
		this.storageArea = storageArea;
	}

	public List<ProductionLine> createFromGround(Ground ground) {

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

	private List<ProductionLine> createFromProductionLineElements(
			List<ProductionLineElement> lineElements) {

		List<ProductionLine> lines = new ArrayList<ProductionLine>();
		List<ProductionLineElement> touchedElements;
		touchedElements = new ArrayList<ProductionLineElement>();

		Iterator<ProductionLineElement> lineElementsIterator = 
			lineElements.iterator();
		
		while (lineElementsIterator.hasNext()){
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
		return circularLine ? createCircularProductionLine(previous,
				this.storageArea, new RawMaterials())
				: createValidProductionLine(firstElement, this.storageArea,
						new RawMaterials());

	}

	private class ProductionLineElementCollector implements TileElementVisitor {

		private List<ProductionLineElement> productionLineElements;

		public ProductionLineElementCollector() {
			this.productionLineElements = new ArrayList<ProductionLineElement>();
		}

		public List<ProductionLineElement> getProductionLineElements() {
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
		public void visitProductionMachine(ProductionMachine machine){
			this.productionLineElements.add(machine);
		}
		
		@Override
		public void visitQualityControlMachine(QualityControlMachine machine){
			this.productionLineElements.add(machine);
		}
	}
}
