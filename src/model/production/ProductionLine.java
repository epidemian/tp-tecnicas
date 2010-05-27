package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.Iterator;
import java.util.LinkedList;

import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import model.game.time.DailyUpdatable;
import model.game.time.TickUpdatable;

public class ProductionLine implements TickUpdatable, DailyUpdatable{

	/**
	 * First element in the production line.
	 */
	private ProductionLineElement firstLineElement;
	private int dailyProduction;

	private StorageArea storageArea;
	private List<Integer> productionHistory;
	
	private RawMaterials configuration;
	
	private ProductionLine(ProductionLineElement line, StorageArea storageArea,
			RawMaterials rawMaterialsConfiguration){
		this.setFirstProductionElement(line);
		this.setStorageArea(storageArea);
		this.setRawMaterialConfiguration(rawMaterialsConfiguration);
		this.productionHistory = new LinkedList<Integer>();
	}
	
	public static ProductionLine createCircularProductionLine(
			ProductionLineElement line, StorageArea storageArea
			, RawMaterials rawMaterialsConfiguration){
		// TODO not implemented yet
		return null;
	}
	
	public static ProductionLine createValidProductionLine(
			ProductionLineElement line, StorageArea storageArea,
			RawMaterials rawMaterialConfiguration){
		return new ProductionLine(line, storageArea,rawMaterialConfiguration);
	}

	public ProductionLineElement getFirstLineElement() {
		return firstLineElement;
	}	
	
	public void setRawMaterialConfiguration(
		RawMaterials rawMaterialsConfiguracion){
		checkNotNull(rawMaterialsConfiguracion, "rawMaterialsConfiguracion");
		this.configuration = rawMaterialsConfiguracion;
	}

	@Override
	public void updateTick(){
	
		Iterator<ProductionLineElement> iterator = this.iterator();
		Product product = this.storageArea.createProduct(this.configuration);
		
		while (iterator.hasNext()){
			ProductionLineElement next = iterator.next();
			product = next.process(product);
		}
			
		// TODO check not null?
		if (product != null){
			this.storageArea.addProduct(product);
			this.dailyProduction++;
		}	
	}
	
	@Override
	public void updateDay() {
		this.productionHistory.add(this.dailyProduction);
		this.dailyProduction = 0;
	}

	public int getDailyProduction() {
		return dailyProduction;
	}
	
	public void setDailyProduction(int dailyProduction) {
		this.dailyProduction = dailyProduction;
	}
	
	public Iterator<ProductionLineElement> iterator(){
		return new ProductionLineIterator();
	}

	public StorageArea getStorageArea() {
		return storageArea;
	}
	
	private void setStorageArea(StorageArea storageArea) {
		checkNotNull(storageArea, "storageArea");
		this.storageArea = storageArea;
	}

	private void setFirstProductionElement(ProductionLineElement line) {
		checkNotNull(line, "line");
		this.firstLineElement = line;
	}
	
	private class ProductionLineIterator implements 
		Iterator<ProductionLineElement>{

		ProductionLineElement current = firstLineElement;
		
		@Override
		public boolean hasNext() {
			return (this.current != null);
		}

		@Override
		public ProductionLineElement next() {
			ProductionLineElement current = this.current;
			this.current = this.current.getNextLineElement();
			return current;
		}

		@Override
		public void remove() {
			throw new NotImplementedException();
		}
	}

	/*	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionLine other = (ProductionLine) obj;
		if (configuration == null) {
			if (other.configuration != null)
				return false;
		} else if (!configuration.equals(other.configuration))
			return false;
		if (dailyProduction != other.dailyProduction)
			return false;
		if (firstLineElement == null) {
			if (other.firstLineElement != null)
				return false;
		} else if (!equalsLineElements(other.firstLineElement))
			return false;
		if (productionHistory == null) {
			if (other.productionHistory != null)
				return false;
		} else if (!productionHistory.equals(other.productionHistory))
			return false;
		return true;
	}
*/
	public int productionLineSize(){
		Iterator<ProductionLineElement> iterator = this.iterator();	
		int size = 0;
		
		while(iterator.hasNext()){
			iterator.next();
			size++;
		}		
		return size;
	}
	
	@Override
	public boolean equals(Object other){
		
		ProductionLine otherLine = (ProductionLine)other;
		
		if (this.productionLineSize() != otherLine.productionLineSize())
			return false;
		
		Iterator<ProductionLineElement> iterator = this.iterator();	
		Iterator<ProductionLineElement> iteratorOther = otherLine.iterator();	
				
		while(iterator.hasNext()){
			ProductionLineElement element = iterator.next();
			ProductionLineElement elementOther = iteratorOther.next();
		
			if (element == null && elementOther == null)
				continue;

			if (element == null || elementOther == null)
				return false;
			
			if (!element.equals(elementOther))
				return false;
		}
		
		return true;
	}

	private String toStringLine(){
	
		Iterator<ProductionLineElement> iterator=this.iterator();	
		String toString = new String();
		
		while(iterator.hasNext()){
			ProductionLineElement element1 = iterator.next();
			toString += element1.toString() + " ";
		}
		
		return toString;
	}
		
	@Override
	public String toString() {
		
		return "ProductionLine [" + this.toStringLine() + "]";
	}	
}
