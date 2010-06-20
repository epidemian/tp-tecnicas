package model.production.line;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.game.Budget;
import model.game.time.DailyUpdatable;
import model.game.time.TickUpdatable;
import model.production.Product;
import model.production.RawMaterials;
import model.production.StorageArea;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;
import model.production.elements.ProductionLineElementObserver;
import model.production.elements.machine.Machine;
import model.production.elements.machine.states.CannotRepairHealthyMachineException;

public abstract class ProductionLine implements TickUpdatable, DailyUpdatable,
		Iterable<ProductionLineElement>, ProductionLineElementObserver {

	/**
	 * First element in the production line.
	 */
	private ProductionLineElement firstLineElement;

	/**
	 * A production line is working if none of its machines are broken.
	 */
	private List<Machine> brokenMachines;

	protected ProductionLine(ProductionLineElement firstLineElement) {
		this.setFirstProductionElement(firstLineElement);
		this.setProductionLineElementsObserver();
		brokenMachines = new ArrayList<Machine>();

	}

	public static ProductionLine createCircularProductionLine(
			ProductionLineElement firstLineElement) {
		return new CircularProductionLine(firstLineElement);
	}

	public static ProductionLine createFunctionalProductionLine(
			StorageArea storageArea, InputProductionLineElement inputElement,
			OutputProductionLineElement outputElement) {
		return new FunctionalProductionLine(storageArea, inputElement,
				outputElement);
	}

	public static ProductionLine createDisfunctionalProductionLine(
			ProductionLineElement firstLineElement) {
		return new DisfunctionalProductionLine(firstLineElement);
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

	private void setFirstProductionElement(
			ProductionLineElement firstLineElement) {
		checkNotNull(firstLineElement, "first production line element");
		this.firstLineElement = firstLineElement;
	}

	private class ProductionLineIterator implements
			Iterator<ProductionLineElement> {

		ProductionLineElement current = firstLineElement;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public ProductionLineElement next() {
			ProductionLineElement returnElement = this.current;
			if (this.current.canHaveNextLineElement()) {
				ProductionLineElement next = this.current.getNextLineElement();
				this.current = (next == firstLineElement ? null : next);
			}
			else 
				this.current = null;
			return returnElement;
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
		return this.getLineElements().size();
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
		return this.brokenMachines.isEmpty();
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

	public void sell(Budget budget) {
		for (ProductionLineElement element : this) {
			element.sell(budget);
		}
	}

}
