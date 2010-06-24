package model.warehouse;

import static model.utils.ArgumentUtils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import model.game.Budget;
import model.game.time.DailyUpdatable;
import model.game.time.MonthlyUpdatable;
import model.game.time.TickUpdatable;
import model.production.Product;
import model.production.RawMaterials;
import model.production.StorageArea;
import model.production.ValidProductionSequences;
import model.production.elements.ProductionLineElement;
import model.production.line.ProductionLine;
import model.utils.StringUtils;

public class Warehouse implements MonthlyUpdatable, DailyUpdatable,
		TickUpdatable {

	private Budget budget;
	private Ground ground;
	private StorageArea storageArea;
	private MarketPrices marketPrices;
	private int totalProductsMade;
	private int totalDefectiveProductsMade;
	private int salePrice;
	private int rentPrice;
	private Collection<ProductionLine> productionLines = new ArrayList<ProductionLine>();

	private Warehouse(Ground ground, Budget budget, MarketPrices map,
			ValidProductionSequences sequences, int salePrice, int rentPrice) {

		checkNotNull(ground, "ground");
		checkNotNull(budget, "budget");
		checkNotNull(map, "map");

		this.ground = ground;
		this.budget = budget;
		this.marketPrices = map;
		this.totalDefectiveProductsMade = 0;
		this.totalProductsMade = 0;
		this.storageArea = new StorageArea(new RawMaterials(), sequences);
		this.setRentPrice(rentPrice);
		this.setSalePrice(salePrice);
	}

	public static Warehouse purchaseWarehouse(Ground ground, Budget budget,
			MarketPrices marketPrices, ValidProductionSequences sequences) {
		budget.decrement(ground.getPurchasePrice());
		return new Warehouse(ground, budget, marketPrices, sequences, ground
				.getSalePrice(), 0);
	}

	public static Warehouse rentWarehouse(Ground ground, Budget budget,
			MarketPrices marketPrices, ValidProductionSequences sequences) {
		return new Warehouse(ground, budget, marketPrices, sequences, 0, ground
				.getRentPrice());
	}

	/**
	 * Creates the production lines corresponding to the elements connected on
	 * the ground.
	 */
	public void createProductionLines() {

		ProductionLinesCreator creator = new ProductionLinesCreator(
				getStorageArea());
		this.productionLines = creator.createFromGround(this.ground);
	}

	/**
	 * Erases all logical production lines (but production line elements are not
	 * removed from ground).
	 */
	public void clearProductionLines() {
		this.productionLines.clear();
	}

	public void sell() {
		sellGround();
		sellMachines();
	}

	private void sellMachines() {
		for (ProductionLine productionLine : this.productionLines) {
			for (ProductionLineElement element : productionLine) {
				element.sell(this.budget);
			}
		}
	}

	private void sellGround() {
		budget.increment(this.salePrice);
	}

	private void sellProducts() {
		double partial = 0;
		double quality = 1 - this.getDefectivePercentage();

		for (Product prod : this.storageArea.getProductsProduced()) {
			int productPrice = this.marketPrices.getPrice(prod);
			partial += quality * quality * productPrice;
		}
	
		this.budget.increment((int) Math.round(partial));
		this.storageArea.clearProductsProduced();
	}

	@Override
	public void updateTick() {
		for (ProductionLine line : this.productionLines)
			line.updateTick();
	}

	@Override
	public void updateDay() {
		for (ProductionLine line : this.productionLines)
			line.updateDay();

		sellProducts();

		setTotalDefectiveProductsMade(this.totalDefectiveProductsMade
				+ this.storageArea.countDefectiveProducts());

		setTotalProductsMade(this.totalProductsMade
				+ this.storageArea.getProductsProduced().size());

		this.storageArea.getProductsProduced().clear();
	}

	@Override
	public void updateMonth() {
		budget.decrement(this.rentPrice);
	}

	public Collection<ProductionLine> getProductionLines() {
		return Collections.unmodifiableCollection(productionLines);
	}

	public int getTotalProductsMade() {
		return totalProductsMade;
	}

	public int getTotalDefectiveProductsMade() {
		return totalDefectiveProductsMade;
	}

	private double getDefectivePercentage() {
		return (this.totalProductsMade != 0 ? (double) this.totalDefectiveProductsMade
				/ this.totalProductsMade
				: 0);
	}

	public Ground getGround() {
		return this.ground;
	}

	public StorageArea getStorageArea() {
		return storageArea;
	}

	public int getSalePrice() {
		return this.salePrice;
	}

	public int getRentPrice() {
		return this.rentPrice;
	}

	private void setSalePrice(int salePrice) {
		checkGreaterEqual(salePrice, 0);
		this.salePrice = salePrice;
	}

	private void setRentPrice(int rentPrice) {
		checkGreaterEqual(rentPrice, 0);
		this.rentPrice = rentPrice;
	}

	private void setTotalProductsMade(int totalProductsMade) {
		this.totalProductsMade = totalProductsMade;
	}

	private void setTotalDefectiveProductsMade(int totalDefectiveProductsMade) {
		this.totalDefectiveProductsMade = totalDefectiveProductsMade;
	}

	public MarketPrices getMarketPrices() {
		return this.marketPrices;
	}

}