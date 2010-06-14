package model.production.line;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.production.Product;
import model.production.ProductionLineElement;
import model.production.RawMaterials;
import model.production.StorageArea;

class ValidProductionLine extends ProductionLine {
	
	// TODO: Pedir al último elemento, el output element, o lo que sea...
	private int dailyProduction;

	private StorageArea storageArea;
	private List<Integer> productionHistory;
	
	// TODO: Pedir al input line element
	private RawMaterials configuration;
	
	protected ValidProductionLine(ProductionLineElement firstLineElement,
			StorageArea storageArea, RawMaterials rawMaterialsConfiguration) {
		super(firstLineElement);
		
		this.setStorageArea(storageArea);
		this.setRawMaterialConfiguration(rawMaterialsConfiguration);
		this.productionHistory = new LinkedList<Integer>();
	}

	@Override
	public void updateTick() {

		Iterator<ProductionLineElement> iterator = this.iterator();
		Product product = this.getStorageArea().createProduct(
				this.configuration);

		while (iterator.hasNext()) {
			ProductionLineElement next = iterator.next();
			product = next.process(product);
		}

		// TODO check not null?
		if (product != null) {
			this.storageArea.addProduct(product);
			this.dailyProduction++;
		}
	}
	
	public void setRawMaterialConfiguration(
			RawMaterials rawMaterialsConfiguracion) {
		checkNotNull(rawMaterialsConfiguracion, "rawMaterialsConfiguracion");
		this.configuration = rawMaterialsConfiguracion;
	}

	@Override
	public void updateDay() {
		this.productionHistory.add(this.dailyProduction);
		this.dailyProduction = 0;
	}

	@Override
	public int getDailyProduction() {
		return dailyProduction;
	}

	public void setDailyProduction(int dailyProduction) {
		this.dailyProduction = dailyProduction;
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