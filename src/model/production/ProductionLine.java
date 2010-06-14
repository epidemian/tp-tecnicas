package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.game.Budget;
import model.game.time.DailyUpdatable;
import model.game.time.TickUpdatable;

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
	private List<Machine> brokenMachines;
	
	private RawMaterials configuration;
	
	private ProductionLine(ProductionLineElement line, StorageArea storageArea,
			RawMaterials rawMaterialsConfiguration){
		this.setFirstProductionElement(line);
		this.setStorageArea(storageArea);
		this.setRawMaterialConfiguration(rawMaterialsConfiguration);
		this.productionHistory = new LinkedList<Integer>();
		
		this.setProductionLineElementsObserver();
		
		brokenMachines=new ArrayList<Machine>();
	
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
			throw new UnsupportedOperationException();
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
	
	public void updateBreakdown(Machine machine){
		
		this.brokenMachines.add(machine);
	}
	
	/**
	 * Called when a Broken machine is repaired
	 */
	public void updateBrokenMachineRepair(Machine machine){
		
		this.brokenMachines.remove(machine);
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
	
	public boolean isWorking(){
		return (this.brokenMachines.size() == 0);		
	}
	
	public void repairAllMachines(Budget budget) 
					throws CannotRepairHealthyMachineException{
	
		// As items are removes in each iteration, we remove always the first item
		int originalSize=brokenMachines.size();
		
		// originalSize is used because if .size() would be used, the size 
		// would be recalculated on each iteration
		for(int i=0; i<originalSize;i++){
			brokenMachines.get(0).repair(budget);		
		}
		

	}
	
	// TODO check visibility
	public void breakAllMachines(){
		Iterator<ProductionLineElement> iter = this.iterator();
		while(iter.hasNext()){
			iter.next().breakUp();
		}
	}
	
	public List<Machine> getBrokenMachines(){
		return this.brokenMachines;
	}

}
