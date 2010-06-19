package model.warehouse;

import static model.utils.ArgumentUtils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import model.exception.BusinessLogicException;
import model.game.Budget;
import model.game.time.DailyUpdatable;
import model.game.time.MonthlyUpdatable;
import model.game.time.WeeklyUpdatable;
import model.production.Product;
import model.production.RawMaterialType;
import model.production.RawMaterials;
import model.production.StorageArea;
import model.production.ValidProductionSequences;
import model.production.elements.ProductionLineElement;
import model.production.line.ProductionLine;

public class Warehouse implements MonthlyUpdatable, DailyUpdatable,
		WeeklyUpdatable {

	private static final double RENT = 0.01f;

	private Budget budget;
	private Ground ground;
	private StorageArea storageArea;
	private PriceMap priceMap;
	private int totalProductsMade;
	private int totalDefectiveProductsMade;
	private int salePrice;
	private int rentPrice;
	private Collection<ProductionLine> productionLines = new ArrayList<ProductionLine>();

	private WeeklyUpdatable weeklyPrices;

	private Warehouse(Ground ground, Budget budget, PriceMap map,
			ValidProductionSequences sequences, int salePrice, int rentPrice,
			WeeklyUpdatable weeklyPrices) {

		checkNotNull(ground, "ground");
		checkNotNull(budget, "budget");
		checkNotNull(map, "map");

		this.ground = ground;
		this.budget = budget;
		this.priceMap = map;
		this.totalDefectiveProductsMade = 0;
		this.totalProductsMade = 0;
		this.weeklyPrices = weeklyPrices;
		this.storageArea = new StorageArea(new RawMaterials(), sequences);
		this.setRentPrice(rentPrice);
		this.setSalePrice(salePrice);
	}

	public static Warehouse createPurchasedWarehouse(Ground ground,
			Budget budget, PriceMap map, ValidProductionSequences sequences,
			WeeklyUpdatable weeklyPrices) {
		return new Warehouse(ground, budget, map, sequences, ground.getPrice(),
				0, weeklyPrices);
	}

	public static Warehouse createRentedWarehouse(Ground ground, Budget budget,
			PriceMap map, ValidProductionSequences sequences,
			WeeklyUpdatable weeklyPrices) {
		return new Warehouse(ground, budget, map, sequences, 0,
				(int) (RENT * ground.getPrice()), weeklyPrices);
	}

	public void createProductionLines() {

		ProductionLinesCreator creator = new ProductionLinesCreator(
				this.storageArea);
		this.productionLines = creator.createFromGround(this.ground);
	}

	public void sell() {
		sellGround();
		sellMachines();
	}

	private void sellMachines() {
		if (this.productionLines != null) {
			for (ProductionLine productionLine : this.productionLines) {
				for (ProductionLineElement element : productionLine) {
					element.sell(this.budget);
				}
			}
		}
	}

	public void sellGround() {
		budget.increment((int) (0.8 * this.salePrice));
	}

	private void sellProducts() {
		double partial = 0;
		int productPrice = 0;

		for (Product prod : this.storageArea.getProductsProduced()) {
			productPrice = this.priceMap.getPrice(prod);
			partial += Math.pow(1 - this.getDefectivePercentage(), 2)
					* productPrice;
		}

		this.budget.increment((int) Math.round(partial));
	}

	public void updateMonth() {
		budget.decrement(this.rentPrice);
	}

	public void updateDay() {

		sellProducts();

		setTotalDefectiveProductsMade(this.totalDefectiveProductsMade
				+ this.storageArea.countDefectiveProducts());

		setTotalProductsMade(this.totalProductsMade
				+ this.storageArea.getProductsProduced().size());

		this.storageArea.getProductsProduced().clear();
	}

	public Collection<ProductionLine> getProductionLines() {
		return productionLines;
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

	@Override
	public void updateWeek() {
		this.weeklyPrices.updateWeek();
	}
	

	public PriceMap getPriceMap() {
		return this.priceMap;
	}
}