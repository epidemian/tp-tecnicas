package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.game.time.DailyUpdatable;
import model.game.time.TickUpdatable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductionLine implements TickUpdatable, DailyUpdatable,
		Iterable<ProductionLineElement>, ProductionLineElementObserver  {

	/**
	 * First element in the production line.
	 */
	private ProductionLineElement firstLineElement;
	private int dailyProduction;

	private StorageArea storageArea;
	private List<Integer> productionHistory;
	
	/**
	 * A production line is working if none of its machines are broken.
	 */
	private int brokenMachines;
	
	private RawMaterials configuration;
	
	private ProductionLine(ProductionLineElement line, StorageArea storageArea,
			RawMaterials rawMaterialsConfiguration){
		this.setFirstProductionElement(line);
		this.setStorageArea(storageArea);
		this.setRawMaterialConfiguration(rawMaterialsConfiguration);
		this.productionHistory = new LinkedList<Integer>();
		
		this.setProductionLineElementsObserver();
		
		brokenMachines=0;
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
	
	public List<ProductionLineElement> getLineElements(){
		
		List<ProductionLineElement> elements = 
			new LinkedList<ProductionLineElement>();
	
		for (ProductionLineElement lineElement : this){
			elements.add(lineElement);
		}
		
		return elements;
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
	
	public List<Integer> getProductionHistory(){
		return this.productionHistory;
	}
	
	@Override
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
	
	public void setProductionLineElementsObserver(){
		Iterator<ProductionLineElement> iterator=this.iterator();
		while (iterator.hasNext()){
			iterator.next().setProductionLineElementObserver(this);
		}
		
	}
	
	public int productionLineSize(){
		Iterator<ProductionLineElement> iterator = this.iterator();	
		int size = 0;
		
		while(iterator.hasNext()){
			iterator.next();
			size++;
		}		
		return size;
	}
	
	public void updateBreakdown(){
		this.brokenMachines++;
	}
	
	/**
	 * Called when a Broken machine is repaired
	 */
	public void updateBrokenMachineRepair(){
		this.brokenMachines--;
	}
	
	/*
	private int countBrokenMachines(){
		Iterator<ProductionLineElement> iterator = this.iterator();	
		int broken = 0;
		
		while(iterator.hasNext()){
			if (iterator.next().isBroken())
			broken++;
		}		
		return broken;
	}
	*/
	
	/*
	 * TODO: Lo mismo que en Machine#equals().
	 */
//	@Override
//	public boolean equals(Object other){
//		
//		ProductionLine otherLine = (ProductionLine)other;
//		
//		if (this.productionLineSize() != otherLine.productionLineSize())
//			return false;
//		
//		Iterator<ProductionLineElement> iterator = this.iterator();	
//		Iterator<ProductionLineElement> iteratorOther = otherLine.iterator();	
//				
//		while(iterator.hasNext()){
//			ProductionLineElement element = iterator.next();
//			ProductionLineElement elementOther = iteratorOther.next();
//		
//			if (element == null && elementOther == null)
//				continue;
//
//			if (element == null || elementOther == null)
//				return false;
//			
//			if (!element.equals(elementOther))
//				return false;
//		}
//		
//		return true;
//	}
	

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
	
	public boolean isWorking(){
		return (this.brokenMachines == 0);
	}
}
