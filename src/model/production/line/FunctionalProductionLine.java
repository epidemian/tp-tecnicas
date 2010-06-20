package model.production.line;

import static model.utils.ArgumentUtils.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.production.Product;
import model.production.StorageArea;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;

class FunctionalProductionLine extends ProductionLine {

	private StorageArea storageArea;
	private List<Integer> productionHistory;
	private InputProductionLineElement inputElement;
	private OutputProductionLineElement outputElement;

	protected FunctionalProductionLine(StorageArea storageArea,
			InputProductionLineElement inputElement,
			OutputProductionLineElement outputElement) {
		super(inputElement);

		checkNotNull(inputElement, "input element");
		checkNotNull(outputElement, "output element");

		List<ProductionLineElement> lineElements = getLineElements();
		ProductionLineElement lastElement = lineElements.get(lineElements
				.size() - 1);
		checkArgCondition(outputElement, outputElement.equals(lastElement),
				"output element is not last element!");

		this.setStorageArea(storageArea);
		this.inputElement = inputElement;
		this.outputElement = outputElement;
		this.productionHistory = new LinkedList<Integer>();
	}

	@Override
	public void updateTick() {
		System.out.println("Update tick functional line, is working: " + isWorking());
		if (isWorking()) {
			Iterator<ProductionLineElement> iterator = this.iterator();
			Product product = this.getStorageArea().createProduct(
					this.inputElement.getRawMaterialsConfiguration());
			
			while (iterator.hasNext()) {
				ProductionLineElement next = iterator.next();
				product = next.process(product);
			}

			if (product != null) {
				this.storageArea.addProduct(product);
			}
		}
	}

	@Override
	public void updateDay() {
		this.productionHistory.add(this.getDailyProduction());
		this.outputElement.resetDailyProduction();
	}

	@Override
	public int getDailyProduction() {
		return this.outputElement.getDailyProduction();
	}

	@Override
	public List<Integer> getProductionHistory() {
		return this.productionHistory;
	}

	public StorageArea getStorageArea() {
		return storageArea;
	}

	private void setStorageArea(StorageArea storageArea) {
		checkNotNull(storageArea, "storageArea");
		this.storageArea = storageArea;
	}

}