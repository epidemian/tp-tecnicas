package model.production.line;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.game.Budget;
import model.game.time.DailyUpdatable;
import model.game.time.TickUpdatable;
import model.production.Machine;
import model.production.Product;
import model.production.ProductionLineElement;
import model.production.ProductionLineElementObserver;
import model.production.RawMaterials;
import model.production.StorageArea;
import model.production.machineState.CannotRepairHealthyMachineException;

public abstract class ProductionLine implements TickUpdatable, DailyUpdatable,
		Iterable<ProductionLineElement>, ProductionLineElementObserver {

	/**
	 * First element in the production line.
	 */
	ProductionLineElement firstLineElement;

	/**
	 * A production line is working if none of its machines are broken.
	 */
	List<Machine> brokenMachines;

	protected ProductionLine(ProductionLineElement firstLineElement) {
		this.setFirstProductionElement(firstLineElement);
		this.setProductionLineElementsObserver();
		brokenMachines = new ArrayList<Machine>();

	}

	public static ProductionLine createCircularProductionLine(
			ProductionLineElement firstLineElement) {
		return new CircularProductionLine(firstLineElement);
	}

	public static ProductionLine createValidProductionLine(
			ProductionLineElement firstLineElement, StorageArea storageArea,
			RawMaterials rawMaterialConfiguration) {
		return new ValidProductionLine(firstLineElement, storageArea,
				rawMaterialConfiguration);
	}

	public ProductionLineElement getFirstLineElement() {
		return firstLineElement;
	}

	public List<ProductionLineElement> getLineElements() {

		List<ProductionLineElement> elements = new LinkedList<ProductionLineElement>();

		for (ProductionLineElement lineElement : this) {
			elements.add(lineElement);
		}

		return elements;
	}

	@Override
	public Iterator<ProductionLineElement> iterator() {
		return new ProductionLineIterator();
	}

	private void setFirstProductionElement(ProductionLineElement firstLineElement) {
		checkNotNull(firstLineElement, "first production line element");
		this.firstLineElement = firstLineElement;
	}

	private class ProductionLineIterator implements
			Iterator<ProductionLineElement> {

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

	public void setProductionLineElementsObserver() {
		for (ProductionLineElement element : this)
			element.setProductionLineElementObserver(this);
	}

	public int productionLineSize() {
		Iterator<ProductionLineElement> iterator = this.iterator();
		int size = 0;

		while (iterator.hasNext()) {
			iterator.next();
			size++;
		}
		return size;
	}

	@Override
	public void updateBreakdown(Machine machine) {

		this.brokenMachines.add(machine);
	}

	/**
	 * Called when a Broken machine is repaired
	 */
	@Override
	public void updateBrokenMachineRepair(Machine machine) {

		this.brokenMachines.remove(machine);
	}

	private String toStringLine() {

		Iterator<ProductionLineElement> iterator = this.iterator();
		String toString = new String();

		while (iterator.hasNext()) {
			ProductionLineElement element1 = iterator.next();
			toString += element1.toString() + " ";
		}

		return toString;
	}

	@Override
	public String toString() {

		return "ProductionLine [" + this.toStringLine() + "]";
	}

	public boolean isWorking() {
		return (this.brokenMachines.size() == 0);
	}

	public void repairAllMachines(Budget budget)
			throws CannotRepairHealthyMachineException {

		// As items are removes in each iteration, we repair always the first
		// item
		while (!this.brokenMachines.isEmpty())
			brokenMachines.get(0).repair(budget);

	}

	public void breakAllMachines() {
		for (ProductionLineElement element : this) 
			element.breakUp();
	}

	public List<Machine> getBrokenMachines() {
		return this.brokenMachines;
	}

	public int getDailyProduction() {
		return 0;
	}

	public List<Integer> getProductionHistory() {
		return new ArrayList<Integer>();
	}
	
	public void sell(Budget budget){
		for (ProductionLineElement element : this){
			element.sell(budget);
		}
	}

}
